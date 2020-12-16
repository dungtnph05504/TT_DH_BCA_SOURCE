package com.nec.asia.nic.framework.report.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;

/**
 * 
 * @author chengwei
 *
 */
public class ReportRowSetDynaClass extends RowSetDynaClass {

	public ReportRowSetDynaClass(ResultSet resultSet, boolean lowerCase)
			throws SQLException {
		super(resultSet, lowerCase);
	}

	/**
	 * Extends the default behavior by creating a String type in replacement for CLOB type
	 */
	protected DynaProperty createDynaProperty(ResultSetMetaData metadata, int i)
			throws SQLException {
		String columnName = metadata.getColumnName(i);
		String name = lowerCase ? columnName.toLowerCase() : columnName;
		String className = null;
		try {
			int sqlType = metadata.getColumnType(i);
			switch (sqlType) {
			case java.sql.Types.DATE:
				return new DynaProperty(name, java.sql.Date.class);
			case java.sql.Types.TIMESTAMP:
				return new DynaProperty(name, java.sql.Timestamp.class);
			case java.sql.Types.TIME:
				return new DynaProperty(name, java.sql.Time.class);
			case java.sql.Types.CLOB:
				return new DynaProperty(name, java.lang.String.class);
			default:
				className = metadata.getColumnClassName(i);
			}
		} catch (SQLException e) {
			// this is a patch for HsqlDb to ignore exceptions
			// thrown by its metadata implementation
		}

		// Default to Object type if no class name could be retrieved
		// from the metadata
		Class clazz = Object.class;
		if (className != null) {
			clazz = loadClass(className);
		}
		return new DynaProperty(name, clazz);
	}

	/**
	 * Extends the default behavior by getting a String value in replacement of CLOB value
	 */
	protected Object getObject(ResultSet resultSet, String name)
			throws SQLException {
		Object result = null;
		ResultSetMetaData metadata = resultSet.getMetaData();
		int n = metadata.getColumnCount();
		int col = 0;
		for (int i = 1; i <= n; i++) { // JDBC is one-relative!
			String columnName = metadata.getColumnName(i);
			if (columnName.equalsIgnoreCase(name)) {
				col = i;
				break;
			}
		}
		try {
			int sqlType = metadata.getColumnType(col);
			switch (sqlType) {
			case java.sql.Types.DATE:
				result = resultSet.getDate(col);
				break;
			case java.sql.Types.TIMESTAMP:
				result = resultSet.getTimestamp(col);
				break;
			case java.sql.Types.TIME:
				result = resultSet.getTime(col);
				break;
			case java.sql.Types.CLOB:
				result = getString(resultSet.getClob(col));
				break;
			default:
				result = resultSet.getObject(col);
				break;
			}
		} catch (SQLException e) {
			// this is a patch for HsqlDb to ignore exceptions
			// thrown by its metadata implementation
		}
		return result;
	}

	private String getString(Clob clob) throws SQLException {
		StringBuffer sb = new StringBuffer();
		Reader reader = null;
		BufferedReader bufferedReader = null;
		try {
			reader = clob.getCharacterStream();
			bufferedReader = new BufferedReader(reader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw new SQLException(e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					throw new SQLException(e);
				}
			}
		}
		return sb.toString();
	}

}

package com.nec.asia.nic.comp.perso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FileUtil;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nec.asia.nic.comp.perso.model.InputDataDTO;
import com.nec.asia.nic.comp.perso.model.PersonalizationData;
import com.nec.asia.nic.comp.perso.model.Unit;
import com.nec.asia.nic.comp.perso.model.UnitDTO;
import com.nec.asia.nic.comp.perso.model.UnitItem;
import com.nec.asia.nic.comp.perso.model.ValueDTO;
import com.nec.asia.nic.comp.trans.service.exception.PersoServiceException;
import com.nec.asia.nic.util.PersoInputXmlConvertor;


/**
 * @setia budiyono
 */
/*
 * Modification History:
 * 5 June 2017 setia :  - Using apache vfs2 framework to access folder sharing in the network
 * 						- To add downloadFile method to copy from remote folder to local folder
 */

@Component("persoManager")
public class PersoManager {
	
	public final Logger logger = LoggerFactory.getLogger(PersoManager.class);
	
	private final static String PROTOCOL = "smb";
	PersoInputXmlConvertor persoXmlConvertor = new PersoInputXmlConvertor();
	FileSystemOptions opts = new FileSystemOptions();
	
	private int connectionTimeOut = 10000;
	private String networkFolderPath;
	private String userName;
	private String password;
	private String hostName;
	
	private String NETWORK_FOLDER;
	
	public String generateOutputXml (PersonalizationData data) throws PersoServiceException {
		String persoDataXml = null;
		if (data == null)
			throw new PersoServiceException ("PersonalizationData cannot be null");
		
		long startTime = System.currentTimeMillis();
		logger.debug("start transXmlUtils - parsePersoDataToXml");
		try {
			
			List<Unit> unitItemList = new ArrayList<Unit> ();
			InputDataDTO inputDataDto = new InputDataDTO();
			UnitDTO unitDto = new UnitDTO();
			Unit unit =  new Unit();
			unit.setComment(data.getComment());
			unit.setName(data.getJobName());
			unit.setProduct(data.getProduct());
			ValueDTO value = new ValueDTO("Hex");
			unit.setCustomerUnitData(value);
			unit.setUnitMatching(value);
			unit.setType(data.getType());
			unit.setPriority(data.getPriority());
			
			UnitItem unitItem = new UnitItem();
			unitItem.setName(data.getTransactionId());
			unitItem.setType("Card");
			unitItem.setPriority(data.getPriority());
			
			
			//Populate data field
			UnitItem.DataFields dataFields = new UnitItem.DataFields();
			
			UnitItem.DataField dataField = new UnitItem.DataField();
			ValueDTO fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getDocumentNumber());			
			dataField.setItemName("DocumentNumber");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);


			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getFirstName());			
			dataField.setItemName("FirstName");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);
			

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getMiddleName());			
			dataField.setItemName("MiddleName");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getLastName());			
			dataField.setItemName("LastName");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getFullName());			
			dataField.setItemName("FullName");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getPassportType());			
			dataField.setItemName("PassportType");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);
			
			//2017 Jul 03 - add country code
			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getCountryCode());			
			dataField.setItemName("CountryCode");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);
			
			//2017 Jul 03 - add nationality
			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getNationality());			
			dataField.setItemName("Nationality");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);
			
			

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getSex());			
			dataField.setItemName("Sex");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);
			
			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getDateOfBirth());			
			dataField.setItemName("DateOfBirth");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getPlaceOfBirth());			
			dataField.setItemName("PlaceOfBirth");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getDateOfIssue());			
			dataField.setItemName("DateOfIssue");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getPlaceOfIssue());			
			dataField.setItemName("PlaceOfIssue");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getDateOfExpiry());			
			dataField.setItemName("DateOfExpiry");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getNationalIdNumber());			
			dataField.setItemName("NationalIDNumber");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Base64");
			fieldValue.setData(data.getPhotoInline64Encoded());			
			dataField.setItemName("Photo_Inline");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Base64");
			fieldValue.setData(data.getFingerprint1Inline64Encoded());			
			dataField.setItemName("Fingerprint1_Inline");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getFingerprint1Position());			
			dataField.setItemName("Fingerprint1_Position");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Base64");
			fieldValue.setData(data.getFingerprint2Inline64Encoded());			
			dataField.setItemName("Fingerprint2_Inline");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getFingerprint2Position());			
			dataField.setItemName("Fingerprint2_Position");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);
			
			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getMrz1());			
			dataField.setItemName("MRZ1");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Text");
			fieldValue.setData(data.getMrz2());			
			dataField.setItemName("MRZ2");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Hex");
			fieldValue.setData(data.getCom());			
			dataField.setItemName("COM");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);
			
			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Hex");
			fieldValue.setData(data.getDg1());			
			dataField.setItemName("DG1");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);
			
			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Hex");
			fieldValue.setData(data.getDg2());			
			dataField.setItemName("DG2");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);
			
			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Hex");
			fieldValue.setData(data.getDg3());			
			dataField.setItemName("DG3");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);
			
			dataField = new UnitItem.DataField();
			fieldValue = new ValueDTO("Hex");
			fieldValue.setData(data.getSod());			
			dataField.setItemName("SOD");
			dataField.setValue(fieldValue);
			dataFields.getDataFieldList().add(dataField);

			
			unitItem.setDataFields(dataFields);	
			unit.setUnitItem(unitItem);
			unitItemList.add(unit);
			unitDto.setUnits(unitItemList);
			
					
			inputDataDto.setUnits(unitDto);
			
		
			persoDataXml = persoXmlConvertor.marshal(inputDataDto);
			//persoDataXml =persoDataXml.replaceAll("<data>", "");
			//persoDataXml = persoDataXml.replaceAll("</data>", "");
			//logger.debug("[PersoData] xml: " + persoDataXml);
		} catch (Exception e) {
			throw new PersoServiceException(e);
		}
		long endTime = System.currentTimeMillis();
		logger.debug(" end  transXmlUtils - parsePersoDataToXml: taken = "+(endTime - startTime));
		return persoDataXml;	
	}
	
	
	public void uploadFile (byte[] sourceData, String fileName) throws PersoServiceException {
			
		if (fileName == null)
			throw new PersoServiceException ("Output Filename cannot be empty or null");
		
		if (sourceData == null)
			throw new PersoServiceException ("Data cannot be empty or null");
				
		FileObject fo = null;
		try {
			if (this.validate() == null) 
				throw new PersoServiceException( "hostName, username, password and network folder path are cannot be empty. Please call setAuthenticationInfo()!"  );

			FileSystemManager manager = VFS.getManager();
			if (!manager.hasProvider(PROTOCOL)) throw new RuntimeException("Provider 'smb' is missing");
			fo = manager.resolveFile(NETWORK_FOLDER + fileName, opts);		
			fo.getContent().getOutputStream(true).write(sourceData);
	       
		} catch (Exception ex) {
			throw new PersoServiceException(ex);
		} finally {
			try {
				if (fo!=null)
					fo.close();				
			}catch (IOException io) {
				logger.error(io.getMessage());
			}
		}
	}
	

	public void downloadFile (String localFolderPath) throws PersoServiceException {
				
		if (localFolderPath ==null)
			throw new PersoServiceException( "LocalFolderPath cannot be empty or null" );
		
		try {
			if (this.validate() == null) 
				throw new PersoServiceException( "hostName, username, password and network folder path are cannot be empty. Please call setAuthenticationInfo()!"  );

			FileSystemManager manager = VFS.getManager();
			if (!manager.hasProvider("smb")) throw new RuntimeException("Provider missing");
			FileObject remoteFile = manager.resolveFile(NETWORK_FOLDER ,opts);
			
			FileObject localFile = manager.resolveFile(localFolderPath);
			FileObject[] children = remoteFile.getChildren();
			for (FileObject child : children) {
				localFile = manager.resolveFile(localFolderPath+ child.getName().getBaseName());
				FileUtil.copyContent(child, localFile);
			}
			
		}catch (Exception ex) {
			throw new PersoServiceException (ex);
		}
		
		

		
	}	
	private FileSystemManager validate() throws FileSystemException {
		if (StringUtils.isEmpty(this.hostName) || StringUtils.isEmpty(password)  
				|| StringUtils.isEmpty(this.userName) || StringUtils.isEmpty(this.networkFolderPath) ) {
			return null;
		}
				
		NETWORK_FOLDER = PROTOCOL+"://" + this.hostName + this.networkFolderPath;
		StaticUserAuthenticator auth = new StaticUserAuthenticator(null, this.userName, this.password);
		DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);

		return VFS.getManager();
	}
	
	public void setAuthenticationInfo (String hostName, String userName, String password, String networkFolderPath) {
		this.hostName = hostName;
		this.userName = userName;
		this.password = password;
		this.networkFolderPath = networkFolderPath;
	}
	
	
}

package com.nec.asia.nic.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * <p>
 * Title: HTTP Request Client class
 * </p>
 * <p>
 * Description: 
 * this class helps to send POST HTTP requests with various form
 * data, including parameters, objects and files.
 * </p>
 * 
 * @author bright_zheng
 * @version 1.0
 */
public class HttpRequestClient {
	URLConnection connection = null;
	OutputStream os = null;

	/**
	 * Creates a new multipart POST HTTP request on a freshly opened
	 * URLConnection
	 * 
	 * @param connection
	 *            an already open URL connection
	 * @throws IOException
	 */
	public HttpRequestClient(URLConnection connection) throws IOException {
		this.connection = connection;
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);
	}

	/**
	 * Creates a new multipart POST HTTP request for a specified URL
	 * 
	 * @param url
	 *            the URL to send request to
	 * @throws IOException
	 */
	public HttpRequestClient(URL url) throws IOException {
		this(url.openConnection());
	}

	/**
	 * Creates a new multipart POST HTTP request for a specified URL string
	 * 
	 * @param urlString
	 *            the string representation of the URL to send request to
	 * @throws IOException
	 */
	public HttpRequestClient(String urlString) throws IOException {
		this(new URL(urlString));
	}

	/**
	 * adds a string parameter to the request
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws IOException
	 */
	public void setParameter(String name, String value) throws IOException {
		boundary();
		writeName(name);
		newline();
		newline();
		writeln(value);
	}

	/**
	 * adds a file parameter to the request
	 * 
	 * @param name
	 *            parameter name
	 * @param filename
	 *            the name of the file
	 * @param is
	 *            input stream to read the contents of the file from
	 * @throws IOException
	 */
	public void setParameter(String name, String filename, InputStream is)
			throws IOException {
		boundary();
		writeName(name);
		write("; filename=\"");
		write(filename);
		write('"');
		newline();
		write("Content-Type: ");
		String type = URLConnection.guessContentTypeFromName(filename);
		if (type == null)
			type = "application/octet-stream";
		writeln(type);
		newline();
		pipe(is, os);
		newline();
	}

	/**
	 * adds a file parameter to the request
	 * 
	 * @param name
	 *            parameter name
	 * @param file
	 *            the file to upload
	 * @throws IOException
	 */
	public void setParameter(String name, File file) throws IOException {
		setParameter(name, file.getPath(), new FileInputStream(file));
	}

	/**
	 * adds a parameter to the request; if the parameter is a File, the file is
	 * uploaded, otherwise the string value of the parameter is passed in the
	 * request
	 * 
	 * @param name
	 *            parameter name
	 * @param object
	 *            parameter value, a File or anything else that can be
	 *            stringified
	 * @throws IOException
	 */
	public void setParameter(String name, Object object) throws IOException {
		if (object instanceof File) {
			setParameter(name, (File) object);
		} else if (object instanceof DTO) {
			setParameter(name, (DTO<?>)object);
		} else {
			setParameter(name, object.toString());
		}
	}

	public void setParameter(String name, DTO<?> object)
			throws IOException {
		boundary();
		writeName(name);
		write("; filename=\"" + object.getClass().getName() + "\"");
		newline();
		write("Content-Type: application/octet-stream");
		newline();
		newline();
		pipe(os, object);
		newline();
	}

	/**
	 * adds parameters to the request
	 * 
	 * @param parameters
	 *            "name-to-value" map of parameters; if a value is a file, the
	 *            file is uploaded, otherwise it is stringified and sent in the
	 *            request
	 * @throws IOException
	 */
	public void setParameters(Map<String, Object> parameters)
			throws IOException {
		if (parameters == null)
			return;
		for (Iterator<Entry<String, 
			 Object>> i = parameters.entrySet().iterator();
			 i.hasNext();) {
			Map.Entry<String, Object> entry = i.next();
			setParameter(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * posts the requests to the server
	 * that were added
	 * 
	 * @return input stream with the server response
	 * @throws IOException
	 */
	public InputStream post() throws IOException {
		boundary();
		writeln("--");
		os.close();
		return connection.getInputStream();
	}

	/**
	 * posts the requests to the server, with all the parameters
	 * that were added before (if any), and with parameters that are passed in
	 * the argument
	 * 
	 * @param parameters
	 *            request parameters
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameters
	 */
	public InputStream post(Map<String, Object> parameters) throws IOException {
		setParameters(parameters);
		return post();
	}

	/**
	 * post the POST request to the server, with the specified parameter
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameter
	 */
	public InputStream post(String name, Object value) throws IOException {
		setParameter(name, value);
		return post();
	}

	/**
	 * posts a new request to specified URL, with parameters that are passed in
	 * the argument
	 * 
	 * @param parameters
	 *            request parameters
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameters
	 */
	public static InputStream post(URL url, Map<String, Object> parameters)
			throws IOException {
		return new HttpRequestClient(url).post(parameters);
	}

	/**
	 * post the POST request specified URL, with the specified parameter
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @return input stream with the server response
	 * @throws IOException
	 * @see setParameter
	 */
	public static InputStream post(URL url, String name, Object value)
			throws IOException {
		return new HttpRequestClient(url).post(name, value);
	}

	protected void connect() throws IOException {
		if (os == null)
			os = connection.getOutputStream();
	}

	protected void write(char c) throws IOException {
		connect();
		os.write(c);
	}

	protected void write(String s) throws IOException {
		connect();
		os.write(s.getBytes());
	}

	protected void newline() throws IOException {
		connect();
		write("\r\n");
	}

	protected void writeln(String s) throws IOException {
		connect();
		write(s);
		newline();
	}

	private static Random random = new Random();

	protected static String randomString() {
		return Long.toString(random.nextLong(), 36);
	}

	String boundary = "---------------------------" + randomString()
			+ randomString() + randomString();

	private void boundary() throws IOException {
		write("--");
		write(boundary);
	}

	private void writeName(String name) throws IOException {
		newline();
		write("Content-Disposition: form-data; name=\"");
		write(name);
		write('"');
	}

	private static void pipe(InputStream in, OutputStream out)
			throws IOException {
		byte[] buf = new byte[500000];
		int nread;
		int total = 0;
		synchronized (in) {
			while ((nread = in.read(buf, 0, buf.length)) >= 0) {
				out.write(buf, 0, nread);
				total += nread;
			}
		}
		out.flush();
		buf = null;
	}

	private static void pipe(OutputStream out, Object obj) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(out);
		ObjectOutputStream so = new ObjectOutputStream(bos);
		so.writeObject(obj);
		so.flush();
		so = null;
	}
}

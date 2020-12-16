package com.nec.asia.nic.comp.integration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.nec.asia.nic.comp.integration.impl.FtpServer;
import com.sshtools.j2ssh.sftp.SftpFile;
import com.sshtools.j2ssh.util.Base64.InputStream;


/**
 * The Interface FileTransferService.
 *
 * @author Alvin Chua
 */
public interface FileTransferService {
	
	/** The MOD e_ ascii. */
	public final int MODE_ASCII=0;
	
	/** The MOD e_ binary. */
	public final int MODE_BINARY=1;
	
	/**
	 * Connect.
	 *
	 * @param server the server
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean connect(FtpServer server) throws Exception;
	
	/**
	 * Sets the transfer mode.
	 *
	 * @param mode the mode
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean setTransferMode(int mode) throws Exception;
	
	/**
	 * Send files.
	 *
	 * @param localPath the local path
	 * @param filePattern the file pattern
	 * @return the list
	 * @throws Exception the exception
	 */
	public List sendFiles(String localPath, String filePattern) throws Exception;
	
	/**
	 * Send files.
	 *
	 * @param localPath the local path
	 * @return the list
	 * @throws Exception the exception
	 */
	public List sendFiles(String localPath) throws Exception;
	
	/**
	 * Send file.
	 *
	 * @param localPath the local path
	 * @param filename the filename
	 * @return true, if successful
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean sendFile(String localPath, String filename) throws FileNotFoundException, IOException;
	
	/**
	 * Send file.
	 *
	 * @param localPath the local path
	 * @param filename the filename
	 * @param bDeleteFileIfExists the b delete file if exists
	 * @return true, if successful
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean sendFile(String localPath, String filename, boolean bDeleteFileIfExists) throws FileNotFoundException, IOException;
	
	/**
	 * Gets the file.
	 *
	 * @param remotePath the remote path
	 * @param filename the filename
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean getFile(String remotePath, String filename) throws IOException;
	
	/**
	 * Gets the file.
	 *
	 * @param remotePath the remote path
	 * @param filename the filename
	 * @param bDeleteFileIfExists the b delete file if exists
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean getFile(String remotePath, String filename, boolean bDeleteFileIfExists) throws IOException;
	
	/**
	 * Disconnect.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean disconnect() throws Exception;
	
	/**
	 * Change remote directory.
	 *
	 * @param remotePath the remote path
	 * @return true, if successful
	 */
	public boolean changeRemoteDirectory(String remotePath);
	
	/**
	 * Change local directory.
	 *
	 * @param remotePath the remote path
	 * @return true, if successful
	 */
	public boolean changeLocalDirectory(String remotePath);
	
	/**
	 * Verify if ftp client is connected to a server 
	 * 
	 * @param server
	 * @return
	 */
	public boolean isConnected();
	
	/**
	 * Put file into remote folder by input stream
	 * 
	 * @param in
	 * @param filename
	 * @throws IOException
	 */
	public void putFile(InputStream in, String filename) throws IOException;
	
	/**
	 * 
	 * @param in
	 * @param filename
	 * @param bDeleteFileIfExists
	 * @throws IOException
	 */
	public void putFile(InputStream in, String filename, boolean bDeleteFileIfExists) throws IOException;
	
	/**
	 * 
	 * @param data
	 * @param filename
	 * @throws IOException
	 */
	public void putFile(byte[] data, String filename)  throws IOException;
	
	/**
	 * 
	 * @param data
	 * @param filename
	 * @param bDeleteFileIfExists
	 * @throws IOException
	 */
	public void putFile(byte[] data, String filename, boolean bDeleteFileIfExists) throws IOException;
	
	/**
	 * 
	 * @param remoteFileName
	 * @return
	 * @throws IOException
	 */
	public byte[] readRemoteFile(String remoteFileName) throws IOException;
	
	/**
	 * 
	 * @param remoteDirectory
	 * @return
	 * @throws IOException
	 */
	public List<String> getRemoteFileName(String remoteDirectory) throws IOException;
	
	/**
	 * 
	 * @param remoteDirectory
	 * @param remoteFileNames
	 * @throws IOException
	 */
	public void deleteRemoteFiles(String remoteDirectory, List<String> remoteFileNames) throws IOException;

	/**
	 * 
	 * @param in
	 * @param fullFilename
	 * @throws IOException
	 */
	public void putFile(java.io.InputStream in, String fullFilename) throws IOException;

	/**
	 * 
	 * @param remoteDir
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<SftpFile> ls(String remoteDir) throws FileNotFoundException, IOException;

	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<SftpFile> ls() throws FileNotFoundException, IOException;

	/**
	 * 
	 * @param oldpath
	 * @param newpath
	 * @return
	 * @throws IOException
	 */
	public boolean rename(String oldpath, String newpath) throws IOException;

	public String getCwd() throws IOException;

	boolean moveFile(String oldpath, String newpath, boolean bDeleteFileIfExists) throws IOException;
	public boolean mkDir(String filename, boolean force) throws IOException;
}

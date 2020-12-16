package com.nec.asia.nic.comp.integration.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.integration.FileTransferService;
import com.nec.asia.nic.framework.exception.NotImplementedException;
import com.nec.asia.nic.utils.StringUtil;
import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.authentication.PublicKeyAuthenticationClient;
import com.sshtools.j2ssh.configuration.ConfigurationLoader;
import com.sshtools.j2ssh.sftp.FileAttributes;
import com.sshtools.j2ssh.sftp.SftpFile;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;
import com.sshtools.j2ssh.transport.publickey.SshPrivateKey;
import com.sshtools.j2ssh.transport.publickey.SshPrivateKeyFile;
import com.sshtools.j2ssh.util.Base64.InputStream;
import com.sshtools.j2ssh.util.Base64.OutputStream;

/**
 * The Class FileTransferSshImpl.
 *
 * @author Alvin Chua
 */
@Service("fileTransferService")
public class FileTransferSshImpl implements FileTransferService {

	/** The logger. */
	private final Log logger = LogFactory.getLog(getClass());

	/** The sftp. */
	private SftpClient sftp = null;

	/** The ssh. */
	private SshClient ssh = null;

	/** The server. */
	private FtpServer server = null;

	/** The is connected. */
	private boolean isConnected = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nec.asia.baf.comp.integration.FileTransferService#
	 * changeRemoteDirectory(java.lang.String)
	 */
	public boolean changeRemoteDirectory(String remotePath) {
		try {
			sftp.cd(remotePath);
			String pwd = sftp.pwd();
			server.setWorkingDirectory(pwd);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nec.asia.baf.comp.integration.FileTransferService#
	 * changeLocalDirectory(java.lang.String)
	 */
	public boolean changeLocalDirectory(String localPath) {
		try {
			sftp.lcd(localPath);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#connect(com.nec.
	 * asia.baf.comp.integration.impl.FtpServer)
	 */
	@Override
	public boolean connect(FtpServer server) throws Exception {
		this.server = server;
		return this.establish(this.server);
	}

	/**
	 * Connect.
	 *
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public boolean connect() throws Exception {
		return this.establish(this.server);
	}

	/**
	 * Establish.
	 *
	 * @param server
	 *            the server
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	private boolean establish(FtpServer server) throws Exception {
		boolean bResult = false;
		ConfigurationLoader.initialize(false);
		SecureFtpServer securedServer = (SecureFtpServer) server;
		if (server != null) {
			ssh = new SshClient();
			ssh.connect(server.getHost(), new IgnoreHostKeyVerification());

			int result = AuthenticationProtocolState.FAILED;
			if (securedServer.getPasskey() != null && securedServer.getPasskey().length() > 0) {
				PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
				pwd.setUsername(securedServer.getUserId());
				pwd.setPassword(securedServer.getPasskey());
				// Try the authentication
				result = ssh.authenticate(pwd);
			} else if (securedServer.getSslPrivateKeyFile() != null
					&& securedServer.getSslPrivateKeyFile().length() > 0) {
				PublicKeyAuthenticationClient pk = new PublicKeyAuthenticationClient();
				pk.setUsername(server.getUserId());
				// If the private key is passphrase protected then ask for the
				// passphrase
				SshPrivateKeyFile file = SshPrivateKeyFile.parse(new File(securedServer.getSslPrivateKeyFile()));
				String passphrase = "";
				if (file.isPassphraseProtected()) {
					logger.debug("PassPhrase protected.");
					passphrase = server.getPasskey();
				}
				SshPrivateKey key = file.toPrivateKey(passphrase);
				pk.setKey(key);
				result = ssh.authenticate(pk);

			} else {
				throw new Exception("Invalid authentication mode.");
			}

			// Evaluate the result
			if (result == AuthenticationProtocolState.COMPLETE) {
				sftp = ssh.openSftpClient();

				if ((server.getWorkingDirectory() != null) && (server.getWorkingDirectory().trim().length() > 0)) {
					if (this.changeRemoteDirectory(server.getWorkingDirectory()) == false) {
						this.disconnect();
						bResult = false;
						throw new Exception("Unable to change FTP remote path. FTP will now disconnect.");
					}
				}
				this.isConnected = true;
				bResult = true;
				logger.info("Connected to " + server.getHost());

			} else {
				String error = "Unable to establish connection.";
				bResult = false;
				throw new Exception(error);
			}
		} else {
			throw new Exception("No FTP server has been defined.");
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nec.asia.baf.comp.integration.FileTransferService#disconnect()
	 */
	public boolean disconnect() {
		boolean bResult = false;
		try {
			sftp.quit();
			ssh.disconnect();
			bResult = true;
			isConnected = false;
		} catch (Exception e) {
			bResult = false;
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#listFiles(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public List<SftpFile> ls() throws FileNotFoundException, IOException {
		return sftp.ls();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#listFiles(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public List<SftpFile> ls(String remoteDir) throws FileNotFoundException, IOException {
		sftp.cd(remoteDir);
		return sftp.ls();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#sendFile(java.lang.
	 * String, java.lang.String)
	 */
	public boolean sendFile(String localPath, String filename) throws FileNotFoundException, IOException {
		return this.sendFile(localPath, filename, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#sendFile(java.lang.
	 * String, java.lang.String, boolean)
	 */
	public boolean sendFile(String localPath, String filename, boolean bDeleteFileIfExists)
			throws FileNotFoundException, IOException {
		boolean bResult = false;
		String fullFilename = null;
		if (this.isConnected == true) {
			if (StringUtil.isEmpty(localPath) == false) {
				fullFilename = localPath + File.separatorChar + filename;
			} else {
				fullFilename = filename;
			}
			String filenametmp = filename + ".tmp";

			File file = new File(fullFilename);
			if (file.exists() == false) {
				throw new FileNotFoundException("Invalid File Name or File not found.");
			}

			boolean fileExists = false;
			try {
				sftp.chmod(0777, filename);
				// File exists
				if (bDeleteFileIfExists)
					sftp.rm(filename, true, false);
				else
					fileExists = true;
			} catch (IOException io) {
			}
			if (fileExists) {
				throw new IOException("Failed transfer, " + filename + " exists");
			}

			try {
				sftp.chmod(0777, filenametmp);
				sftp.rm(filenametmp);
			} catch (IOException io) {
			}

			try {
				sftp.put(fullFilename, filenametmp);
			} catch (IOException io) {
				
				throw new IOException("Failed transferring " + filename + " ( " + io.getMessage() + ")");
			}

			try {
				sftp.rename(filenametmp, filename);
			} catch (IOException io) {
				throw new IOException("Failed renaming " + filename + " ( " + io.getMessage() + ")");
			}

			bResult = true;
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#sendFiles(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public List<String> sendFiles(String localPath, String filePattern) throws Exception {
		ArrayList<String> list = null;

		if (this.isConnected == true) {
			list = new ArrayList<String>();
			File dir = new File(localPath);
//			if (dir == null) {
//				logger.error("<" + localPath + "> Invalid Directory.");
//				return null;
//			}
			logger.debug("Files selection: " + localPath);
			File files[] = null;
			List filters = (List) StringUtil.TokenizeString(filePattern, ",");

			if ((filters != null) || (filters.size() > 0)) {
				FileFilter fileFilter = new FileFilter(filters);
				files = dir.listFiles(fileFilter);
			} else {
				files = dir.listFiles();
			}
			String filename = null;
			String fullFilename = null;
			String filenametmp = null;

			if (files != null) {
				logger.debug("Files: " + files.length);
			} else {
				logger.debug("Files: No File found");
			}

			for (int i = 0; i < files.length; i++) {

				fullFilename = files[i].toString();
				filename = files[i].getName();
				filenametmp = filename + ".tmp";

				logger.debug("<" + fullFilename + "><" + files[i].getName() + "> transfering to remote Ftp Server.");
				try {
					sftp.put(fullFilename, filenametmp);
					sftp.rename(filenametmp, filename);
					list.add(fullFilename);
				} catch (FileNotFoundException fe) {
					logger.debug("<" + fullFilename + "> " + fe.toString());
					continue;
				} catch (IOException ioe) {
					logger.debug("<" + fullFilename + "> " + ioe.toString());
					continue;
				} catch (Exception e) {
					logger.debug("<" + fullFilename + "> " + e.toString());
					continue;
				}
			}
		} else {
			list = null;
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#sendFiles(java.lang
	 * .String)
	 */
	@Override
	public List<String> sendFiles(String localPath) throws Exception {
		if (this.isConnected == true) {
			return sendFiles(localPath, "");
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#getFile(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public boolean getFile(String remotePath, String filename) throws IOException {
		return this.getFile(remotePath, filename, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#getFile(java.lang.
	 * String, java.lang.String, boolean)
	 */
	@Override
	public boolean getFile(String remotePath, String filename, boolean bDeleteFileIfExists) throws IOException {
		if (this.isConnected == true) {
			if (bDeleteFileIfExists == true) {
				File file = new File(filename);
				if (file.exists() == true) {
					file.delete();
				}
			}
			String fullFilename = "";
			if (remotePath != null && remotePath.length() > 0) {
				fullFilename = remotePath + "/" + filename;
			} else {
				fullFilename = filename;
			}
			sftp.get(fullFilename, filename);
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#getFile(java.lang.
	 * String, OutputStream, boolean)
	 */
	public boolean getFile(String fullFilename, OutputStream out) throws IOException {
		boolean fileReceived = false;

		if (this.isConnected == true) {
			FileAttributes fileAttributes = sftp.get(fullFilename, out);
			if (fileAttributes != null) {
				fileReceived = true;
			}
		}
		return fileReceived;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#getFile(java.lang.
	 * String, OutputStream, boolean)
	 */
	@Override
	public void putFile(InputStream in, String fullFilename) throws IOException {
		if (this.isConnected == true) {
			sftp.put(in, fullFilename);
		}
	}

	@Override
	public void putFile(java.io.InputStream in, String fullFilename) throws IOException {
		if (this.isConnected == true) {
			sftp.put(in, fullFilename);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#setTransferMode(
	 * int)
	 */
	@Override
	public boolean setTransferMode(int mode) {
		boolean bResult = false;
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#rmFile(java.lang.
	 * String, boolean, boolean)
	 */
	
	public boolean rmFile(String filename, boolean force, boolean recurse) throws FileNotFoundException, IOException {
		boolean bResult = false;

		if (this.isConnected == true) {
			try {
				sftp.rm(filename, force, recurse);
				bResult = true;
			} catch (IOException io) {
				throw new IOException("Failed to remove " + filename + " ( " + io.getMessage() + ")");
			}
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.baf.comp.integration.FileTransferService#rmFile(java.lang.
	 * String, boolean, boolean)
	 */
	
	public boolean mkDir(String filename, boolean force) throws IOException {
		boolean bResult = false;

		if (this.isConnected == true) {
			try {
				sftp.mkdir(filename);
				bResult = true;
			} catch (IOException io) {
				throw new IOException("Failed to mkdir " + filename + " ( " + io.getMessage() + ")");
			}
		}
		return bResult;
	}

	@Override
	public boolean isConnected() {
		if (sftp != null) {
			return !sftp.isClosed();
		}

		return false;
	}

	@Override
	public void putFile(InputStream in, String filename, boolean bDeleteFileIfExists) throws IOException {
		throw new NotImplementedException();
	}

	@Override
	public void putFile(byte[] data, String filename) throws IOException {
		throw new NotImplementedException();
	}

	@Override
	public void putFile(byte[] data, String filename, boolean bDeleteFileIfExists) throws IOException {
		if (this.isConnected == true) {
			String filenametmp = filename + ".part";

			boolean fileExists = false;
			try {
				sftp.chmod(0777, filename);
				// File exists
				if (bDeleteFileIfExists)
					sftp.rm(filename, true, false);
				else
					fileExists = true;
			} catch (IOException io) {
			}
			if (fileExists) {
				throw new IOException("Failed transfer, " + filename + " exists");
			}

			try {
				sftp.chmod(0777, filenametmp);
				sftp.rm(filenametmp);
			} catch (IOException io) {
			}

			try {
				ByteArrayInputStream bis = new ByteArrayInputStream(data);
				sftp.put(bis, filenametmp);
				logger.debug("Transfered file: " + filenametmp);
			} catch (IOException io) {
				throw new IOException("Failed transferring " + filename + " ( " + io.getMessage() + ")");
			}

			try {
				sftp.rename(filenametmp, filename);
				logger.debug("Renamed file: " + filenametmp + " to " + filename);
			} catch (IOException io) {
				throw new IOException("Failed renaming " + filename + " ( " + io.getMessage() + ")");
			}
		}
	}

    @Override
    public byte[] readRemoteFile(String remoteFileName) throws IOException {
        if (this.isConnected == true) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            FileAttributes fileAttributes = sftp.get(remoteFileName, out);
            out.close();
            if (fileAttributes != null) {
                return out.toByteArray();
            }
        }

        return null;
    }

	@Override
	public List<String> getRemoteFileName(String remoteDirectory) throws IOException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteRemoteFiles(String remoteDirectory, List<String> remoteFileNames) throws IOException {
		throw new NotImplementedException();
	}

	/*
	 * (non-Javadoc)
	 * @see com.nec.asia.nic.comp.integration.FileTransferService#rename(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean rename(String oldpath, String newpath) throws IOException {
        boolean bResult = false;

        if (this.isConnected == true) {
            try {
                sftp.rename(oldpath, newpath);
                bResult = true;
            } catch (IOException io) {
                throw new IOException("Failed to rename from " + oldpath + " to " + newpath + " ( " + io.getMessage() + ")");
            }
        }
        return bResult;
    }
	
	@Override
	public boolean moveFile(String oldpath, String newpath, boolean bDeleteFileIfExists) throws IOException {
		boolean fileExists = false;
		boolean bResult = false;
		if (this.isConnected == true) {
			try {
				sftp.chmod(0777, newpath);
				// File exists
				if (bDeleteFileIfExists)
					sftp.rm(newpath, true, false);
				else
					fileExists = true;
			} catch (IOException io) {
			}
			if (fileExists) {
				throw new IOException("Failed transfer, " + newpath + " exists");
			}

			try {
				sftp.rename(oldpath, newpath);
				bResult = true;
			} catch (IOException io) {
				throw new IOException("Failed renaming " + newpath + " ( " + io.getMessage() + ")");
			}
		}
		return bResult;
	}
	
    @Override
    public String getCwd() {
        String cwd = null;

        if (this.isConnected == true) {
            cwd = sftp.pwd();
        }

        return cwd;
    }
}

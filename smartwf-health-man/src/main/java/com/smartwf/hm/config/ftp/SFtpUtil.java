package com.smartwf.hm.config.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SFtpUtil {

    // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
    private static final String SESSION_CONFIG_STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";
 
    /**
     * 创建SFTP连接
     * @return
     * @throws Exception
     */
    public static ChannelSftp createSftp(SFtpConfig config) throws Exception {
        JSch jsch = new JSch();
        log.info("Try to connect sftp[" + config.getUsername() + "@" + config.getHost() + "], use password[" + config.getPassword() + "]");
 
        Session session = createSession(config,jsch, config.getHost(), config.getUsername(), config.getPort());
        session.setPassword(config.getPassword());
        session.connect(config.getSessionConnectTimeout());
 
        log.info("Session connected to {}.", config.getHost());
 
        Channel channel = session.openChannel(config.getProtocol());
        channel.connect(config.getChannelConnectedTimeout());
 
        log.info("Channel created to {}.", config.getHost());
 
        return (ChannelSftp) channel;
    }
 
    /**
     * 加密秘钥方式登陆
     * @return
     */
    private ChannelSftp connectByKey(SFtpConfig config) throws Exception {
        JSch jsch = new JSch();
        // 设置密钥和密码 ,支持密钥的方式登陆
        if (StringUtils.isNotBlank(config.getPrivateKey())) {
            if (StringUtils.isNotBlank(config.getPassphrase())) {
                // 设置带口令的密钥
                jsch.addIdentity(config.getPrivateKey(), config.getPassphrase());
            } else {
                // 设置不带口令的密钥
                jsch.addIdentity(config.getPrivateKey());
            }
        }
        log.info("Try to connect sftp[" + config.getUsername() + "@" + config.getHost() + "], use private key[" + config.getPrivateKey()
                + "] with passphrase[" + config.getPassphrase() + "]");
 
        Session session = createSession(config,jsch, config.getHost(), config.getUsername(), config.getPort());
        // 设置登陆超时时间
        session.connect(config.getSessionConnectTimeout());
        log.info("Session connected to " + config.getHost() + ".");
        // 创建sftp通信通道
        Channel channel = session.openChannel(config.getProtocol());
        channel.connect(config.getChannelConnectedTimeout());
        log.info("Channel created to " + config.getHost() + ".");
        return (ChannelSftp) channel;
    }
 
    /**
     * 创建session
     * @param jsch
     * @param host
     * @param username
     * @param port
     * @return
     * @throws Exception
     */
    private static Session createSession(SFtpConfig config,JSch jsch, String host, String username, Integer port) throws Exception {
        Session session = null;
 
        if (port <= 0) {
            session = jsch.getSession(username, host);
        } else {
            session = jsch.getSession(username, host, port);
        }
 
        if (session == null) {
            throw new Exception(host + " session is null");
        }
 
        session.setConfig(SESSION_CONFIG_STRICT_HOST_KEY_CHECKING, config.getSessionStrictHostKeyChecking());
        return session;
    }
 
    /**
     * 关闭连接
     * @param sftp
     */
    private static void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                } else if (sftp.isClosed()) {
                    log.info("sftp is closed already");
                }
                if (null != sftp.getSession()) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
    
    /**
	 * 说明：将文件上传到指定目录
	 * @author WCH
	 * @DateTime 2020-10-13 21:05:44
	 * */
	public static boolean uploadFile(SFtpConfig config, String targetPath, InputStream inputStream) throws Exception {
	    ChannelSftp sftp = SFtpUtil.createSftp(config);
	    try {
	        sftp.cd(config.getRoot());
	        log.info("Change path to {}", config.getRoot());
	        int index = targetPath.lastIndexOf("/");
	        String fileDir = targetPath.substring(0, index);
	        String fileName = targetPath.substring(index + 1);
	        boolean dirs = createDirs(config, fileDir, sftp);
	        if (!dirs) {
	            log.error("Remote path error. path:{}", targetPath);
	            throw new Exception("Upload File failure");
	        }
	        sftp.put(inputStream, fileName);
	        return true;
	    } catch (Exception e) {
	        log.error("Upload file failure. TargetPath: {}", targetPath, e);
	        throw new Exception("Upload File failure");
	    } finally {
	        disconnect(sftp);
	    }
	}
	
	/**
	 * 说明：创建远程文件夹
	 * @author WCH
	 * @DateTime 2020-10-13 21:05:44
	 * */
	private static boolean createDirs(SFtpConfig config,String dirPath, ChannelSftp sftp) {
	    if (dirPath != null && !dirPath.isEmpty() && sftp != null) {
	        String[] dirs = Arrays.stream(dirPath.split("/")).filter(StringUtils::isNotBlank).toArray(String[]::new);
	        for (String dir : dirs) {
	            try {
	                sftp.cd(dir);
	                log.info("Change directory {}", dir);
	            } catch (Exception e) {
	                try {
	                    sftp.mkdir(dir);
	                    log.info("Create directory {}", dir);
	                } catch (SftpException e1) {
	                     log.error("Create directory failure, directory:{}", dir, e1);
	                     e1.printStackTrace();
	                }
	                try {
	                    sftp.cd(dir);
	                    log.info("Change directory {}", dir);
	                } catch (SftpException e1) {
	                    log.error("Change directory failure, directory:{}", dir, e1);
	                    e1.printStackTrace();
	                }
	            }
	        }
	        return true;
	    }
	    return false;
	}
		

	/**
     * 删除文件
     * @param targetPath
     * @return
     * @throws Exception
     */
	public static boolean deleteFile(SFtpConfig config,String targetPath) throws Exception {
	    ChannelSftp sftp = null;
	    try {
	        sftp = createSftp(config);
	        sftp.cd(config.getRoot());
	        sftp.rm(targetPath);
	        return true;
	    } catch (Exception e) {
	        log.error("Delete file failure. TargetPath: {}", targetPath, e);
	        throw new Exception("Delete File failure");
	    } finally {
	        disconnect(sftp);
	    }
	}
		
	/**
     * 说明：下载文件
     * @param targetPath
     * @return
     * @throws Exception
     */
	public static File downloadFile(SFtpConfig config,String targetPath) throws Exception {
	    ChannelSftp sftp = createSftp(config);
	    OutputStream outputStream = null;
	    try {
	        sftp.cd(config.getRoot());
	        log.info("Change path to {}", config.getRoot());
	        File file = new File(targetPath.substring(targetPath.lastIndexOf("/") + 1));
	        outputStream = new FileOutputStream(file);
	        sftp.get(targetPath, outputStream);
	        log.info("Download file success. TargetPath: {}", targetPath);
	        return file;
	    } catch (Exception e) {
	        log.error("Download file failure. TargetPath: {}", targetPath, e);
	         throw new Exception("Download File failure");
	    } finally {
	        if (outputStream != null) {
	            outputStream.close();
	        }
	        disconnect(sftp);
	    }
	}
}

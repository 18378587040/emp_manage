package com.zgm.server.utils;

import com.zgm.server.pojo.FastDFSFile;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * 文件上传工具类
 */
public class FastDFSUtil {
    /**
     * 加载Tracker连接信息
     */
    static {
        try {
            // 查找文件
            String filename = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] upload(FastDFSFile fastDFSFile) throws IOException, MyException {
        // 查找文件
//        String filename = new ClassPathResource("fdfs_client.conf").getPath();
//        ClientGlobal.init(filename);
        // 创建一个Tracker访问的客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        // 获取Trackerclient访问TrackerServer链接
        TrackerServer trackerServer = trackerClient.getConnection();
        // 通过TrackerServer的链接信息可以获取Storage的链接信息，创建Storage Client对象存储Storage的链接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        /**
         * 通过Storage Client访问Storeage， 实现文件上传，并且获取文件上传后的存储信息
         * 1.文件字节数组
         * 2.文件的扩展名
         * 3.附加信息
         */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), null);
        return uploads;
    }
}

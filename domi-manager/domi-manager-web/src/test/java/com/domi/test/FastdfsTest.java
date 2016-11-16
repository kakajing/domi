package com.domi.test;

import com.domi.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * 测试图片服务的使用
 * Created by jing on 2016/11/16.
 */
public class FastdfsTest {

    @Test
    public void testUpload() throws Exception{
        // 1、把FastDFS提供的jar包添加到工程中
        // 2、初始化全局配置。加载一个配置文件。
        ClientGlobal.init("E:\\domi\\domi-manager\\domi-manager-web\\src\\main\\resources\\resource\\client.conf");
        // 3、创建一个TrackerClient对象。
        TrackerClient trackerClient = new TrackerClient();
        // 4、创建一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 5、声明一个StorageServer对象，null。
        StorageServer storageServer = null;
        // 6、获得StorageClient对象
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 7、直接调用StorageClient对象方法上传文件即可
        String[] strings = storageClient.upload_file("E:\\图片\\1.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);
        }

    }

    /**
     * 使用FastDFSClient工具类上传图片
     * @throws Exception
     */
    @Test
    public void testFastDfsClient() throws Exception{
        FastDFSClient client =
                new FastDFSClient("E:\\domi\\domi-manager\\domi-manager-web\\src\\main\\resources\\resource\\client.conf");
        String uploadFile = client.uploadFile("E:\\图片\\2.jpg", "jpg");
        System.out.println(uploadFile);
    }
}

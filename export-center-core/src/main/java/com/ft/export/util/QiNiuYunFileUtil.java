package com.ft.export.util;

import com.ft.export.enums.YesOrNoEnum;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author mask
 * @date 2019/7/17 14:03
 * @desc 七牛云文件工具类
 */
@Component
public class QiNiuYunFileUtil {

    private static Logger logger = LogManager.getLogger(QiNiuYunFileUtil.class);

    private static String accessKey;
    @Value("${qiniuyun.access.key}")
    public void setAccessKey(String accessKey) {
        QiNiuYunFileUtil.accessKey = accessKey;
    }

    private static String secretKey;
    @Value("${qiniuyun.secret.key}")
    public void setSecretKey(String secretKey) {
        QiNiuYunFileUtil.secretKey = secretKey;
    }

    private static String bucketName;
    @Value("${qiniuyun.bucket.name}")
    public void setBucketName(String bucketName) {
        QiNiuYunFileUtil.bucketName = bucketName;
    }

    private static Integer switchFlag;
    @Value("${qiniuyun.switch.flag}")
    public void setBucketName(Integer switchFlag) {
        QiNiuYunFileUtil.switchFlag = switchFlag;
    }

    private static UploadManager uploadManager;

    private static String buildToken(){
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucketName);
    }

    private static UploadManager buildUploadManager(){
        if(uploadManager != null){
            return uploadManager;
        }
        //构造一个带指定Zone对象的配置类
        //这里要根据买的存储空间所在的区域
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        uploadManager = new UploadManager(cfg);
        return uploadManager;
    }

    public static void uploadFile(String fileAbsolutePath, String key){
        if(!YesOrNoEnum.YES.getValue().equals(switchFlag)){
            //开关关闭不上传
            return;
        }
        try {
            UploadManager uploadManager = buildUploadManager();
            String upToken = buildToken();
            Response response = uploadManager.put(fileAbsolutePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //System.out.println(putRet.key);
            //System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            logger.error("{}文件上传七牛云服务器失败", key);
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}

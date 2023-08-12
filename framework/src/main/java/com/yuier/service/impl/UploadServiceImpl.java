package com.yuier.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.yuier.constants.HTTPConstants;
import com.yuier.constants.SystemConstants;
import com.yuier.domain.ResponseResult;
import com.yuier.enums.AppHttpCodeEnum;
import com.yuier.exception.SystemException;
import com.yuier.service.UploadService;
import com.yuier.utils.SecurityUtils;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/8 11:19
 **/

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private Environment environment;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        if (img.getSize() > SystemConstants.MAX_AVATAR_BYTE) {
            throw new SystemException(AppHttpCodeEnum.AVATAR_TOO_LARGE);
        }
        File tempFile = multipartFile2File(img);
        String fileType = FileTypeUtil.getType(tempFile);
        if (!SystemConstants.VALID_IMG_TYPE.contains(fileType)) {
            throw new SystemException(AppHttpCodeEnum.IMAGE_TYPE_ERROR);
        }

        ResponseResult result = uploadToQcloud(tempFile);

//        ResponseResult result = uploadToSMMS(tempFile);
        tempFile.delete();
        return result;
    }

    public ResponseResult uploadToQcloud(File tempFile) {
        // 创建客户端
        String secretId = environment.getProperty("COS.secret-id");
        String secretKey = environment.getProperty("COS.secret-key");
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 上传文件
        String bucketName = environment.getProperty("COS.bucket-name.yui-bucket");
        String key = getFileKey(tempFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, tempFile);
        cosClient.putObject(putObjectRequest);
        URL url = cosClient.getObjectUrl(bucketName, key);
        return ResponseResult.okResult(url);
    }

    public ResponseResult uploadToSMMS(File tempFile) {

        // 将文件上传到 SMMS
        Response response;
        OkHttpClient client = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse(HTTPConstants.CONTENY_TYPE.FORM_DATA), tempFile);
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("format", HTTPConstants.FORMAT.JSON)
                .addFormDataPart("smfile", tempFile.getName(), fileBody)
                .build();
        Request request = new Request.Builder()
                .url(environment.getProperty("smms.baseurl") + environment.getProperty("smms.upload-image"))
                .removeHeader("User-Agent")
                .addHeader("User-Agent", Objects.requireNonNull(environment.getProperty("user-agent")))
                .addHeader("cookie", Objects.requireNonNull(environment.getProperty("smms.cookie")))
                .addHeader("Authorization", Objects.requireNonNull(environment.getProperty("smms.authorization")))
                .post(requestBody)
                .build();
        System.out.println(request);

        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                System.out.println(responseBody);
                String url = jsonObject.getJSONObject("data").getString("url");
                System.out.println(url);
                response.close();
                return ResponseResult.okResult(url);
            } else {
                System.out.println("请求失败，错误码：" + response.code());
                return ResponseResult.okResult(response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.okResult(e);
        }
    }

    public static File multipartFile2File(MultipartFile file) {
        // 临时文件保存路径
        String tempPath = System.getProperty("user.dir") + "\\temp" + "\\" + file.getOriginalFilename();
        File tempFile = new File(tempPath);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        // 转存文件
        try {
            file.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public static String getFileKey(File file) {
        String userId = String.valueOf(SecurityUtils.getUserId());
        String time = String.valueOf(System.currentTimeMillis());
        String filename = file.getName();
        String key = userId + "-" + time + "-" + filename;
        return key;
    }
}

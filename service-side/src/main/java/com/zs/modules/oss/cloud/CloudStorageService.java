

package com.zs.modules.oss.cloud;

import com.zs.common.exception.RenException;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 阿里云存储
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class CloudStorageService {

    //private String filePath = "D:\\AppFile\\";
    private String filePath = "C:\\Users\\86157\\Desktop\\AppFile\\";
    public String upload(byte[] bytes,String suffix) {
        OutputStream output = null;
        InputStream input = new ByteArrayInputStream(bytes);
        try{
            System.out.println("文件长度1："+input.available());
            String md5 = getMD5Checksum(bytes);
            System.out.println("文件长度2："+input.available());
            String fileDirStr = filePath + md5;
            System.out.println("文件长:3："+input.available());
            File fileDir = new File(fileDirStr);
            System.out.println("文件长度4："+input.available());
            if (!fileDir.exists() && !fileDir.mkdirs()) {
                throw new RenException("无法创建文件夹: " + fileDir.getAbsolutePath());
            }
            System.out.println("文件长度5:"+input.available());
            File file = new File(fileDirStr+"\\"+suffix);
            System.out.println("文件长度6:"+input.available());
            output = new FileOutputStream(file);
            System.out.println("文件长度7:"+input.available());
            IOUtils.copy(input,output);
            return "\\"+md5+"\\"+suffix;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RenException(e.getMessage());
        }finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                throw new RenException(e.getMessage());
            }
        }

    }

    public static String getMD5Checksum(byte[] bytes) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 创建一个可更新MD5哈希值的`DigestInputStream`（这里我们直接读取字节并更新md）

        md.update(bytes);
        // 完成哈希计算
        byte[] digest = md.digest();

        // 将字节数组转换为十六进制字符串
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        // 返回十六进制字符串
        return sb.toString();
    }

}
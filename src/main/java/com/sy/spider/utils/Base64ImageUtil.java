package com.sy.spider.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Created by zhaolu on 2017/7/7.
 */
public class Base64ImageUtil {

  private static Logger logger = LoggerFactory.getLogger(Base64ImageUtil.class);

  public static String getImageStr(File photo) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
    byte[] data = null;

    // 读取图片字节数组
    InputStream in = null;
    try {
      in = new FileInputStream(photo);
      data = new byte[in.available()];
      in.read(data);
    } catch (IOException e) {
      logger.error("GetImageStr : read error ", e);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
        logger.error("GetImageStr : close error ", e);
      }
    }

    // 对字节数组Base64编码
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encode(data);// 返回Base64编码过的字节数组字符串
  }


  public static boolean generateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
    if (imgStr == null) // 图像数据为空
    {
      return false;
    }
    // 生成jpeg图片
    OutputStream out = null;
    BASE64Decoder decoder = new BASE64Decoder();
    try {
      // Base64解码
      byte[] bytes = decoder.decodeBuffer(imgStr);
      for (int i = 0; i < bytes.length; ++i) {
        if (bytes[i] < 0) {// 调整异常数据
          bytes[i] += 256;
        }
      }
      // 生成jpeg图片
      out = new FileOutputStream(imgFilePath);
      out.write(bytes);
      out.flush();
    } catch (Exception e) {
      logger.error("GenerateImage : read error ", e);
      return false;
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException e) {
        logger.error("GenerateImage : close error ", e);
      }
    }

    return true;
  }


  /**
   * Base64字符串转 二进制流
   *
   * @param base64String Base64
   * @return base64String
   * @throws IOException 异常
   */
  public static byte[] getStringImage(String base64String) throws IOException {
    BASE64Decoder decoder = new BASE64Decoder();
    return base64String != null ? decoder.decodeBuffer(base64String) : null;
  }

}

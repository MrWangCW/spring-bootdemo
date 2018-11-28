package com.wang.rptimpl.wechatrpt.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.net.URL;


/**
 * Created by wangyanwei on 2018/11/22.
 *
 * @author wangyanwei
 * @version 1.0
 */
public class UploadMediaUtil {

    private static Logger logger = LoggerFactory.getLogger(WeChatMessageUtil.class);

    /**
     * 新增永久图文素材 POST，https协议
     */
    private static final String FOREVER_MEDIA = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=%s";
    /**
     * 新增临时素材，有效期3天素材上传(POST)URL
     * {"media_id":"X-GNN6jPoNTD_RpgWvM9_AzcPygE__fsFbe961E45BwsiDrSfeKu6sUa4RytuwlH","created_at":1542958945,"type":"image"}
     */
    private static final String UPLOAD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/upload";

    /**
     * 上传图文消息内的图片获取URL  返回图片的URL
     * {"url":"http://mmbiz.qpic.cn/mmbiz_jpg/0zZKPy44DFyFuaWq1z4Ccx9QXqO17cKlsejjfqpBxKDicqo7S8adzF4WT8eEu6w0MhUjSyHhIGiaYyFLmjU6LKdw/0"}
     */
    private static final String IMAGE_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
    /**
     * 素材下载:不支持视频文件的下载(GET)
     */
    private static final String DOWNLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";


    /**
     * 素材上传到微信服务器
     *
     * @param file        File file = new File(filePath); // 获取本地文件
     * @param accessToken access_token
     * @param type        type只支持四种类型素材
     *                    图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
     *                    语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
     *                    视频（video）：10MB，支持MP4格式
     *                    缩略图（thumb）：64KB，支持JPG格式
     * @param urlNum      1是新增永久图文素材 2是新增临时素材 3是上传图文消息内的图片仅支持jpg/png格式，大小必须在1MB以下
     *                    默认是2
     * @return
     */
    public static JSONObject uploadMedia(File file, String accessToken, String type, int urlNum) {
        if (file == null || accessToken == null) {
            return null;
        }
        if (!file.exists()) {
            logger.info("---上传文件不存在,请检查!---");
            return null;
        }

        try {
            FilePart media = new FilePart("media", file);
            PostMethod post;
            Part[] parts;
            if (urlNum == 1) {
                post = new PostMethod(FOREVER_MEDIA);
                return null;
            } else if (urlNum == 2) {
                post = new PostMethod(UPLOAD_MEDIA);
                parts = new Part[]{
                        new StringPart("access_token", accessToken),
                        new StringPart("type", type),
                        media
                };
            } else if (urlNum == 3) {
                post = new PostMethod(IMAGE_URL);
                parts = new Part[]{
                        new StringPart("access_token", accessToken),
                        media
                };
            } else {
                post = new PostMethod(UPLOAD_MEDIA);
                parts = new Part[]{
                        new StringPart("access_token", accessToken),
                        new StringPart("type", type),
                        media
                };
            }
            return upload(file, post, parts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static JSONObject upload(File file, PostMethod post, Part[] parts) {

        JSONObject jsonObject = null;
        try {
            post.setRequestHeader("Connection", "Keep-Alive");
            post.setRequestHeader("Cache-Control", "no-cache");

            HttpClient httpClient = new HttpClient();
            //信任任何类型的证书
            Protocol myHttps = new Protocol("https", new SSLProtocolSocketFactory(), 443);
            Protocol.registerProtocol("https", myHttps);

            MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
            post.setRequestEntity(entity);
            int status = httpClient.executeMethod(post);
            if (status == HttpStatus.SC_OK) {
                logger.info("---上传素材成功---");
                String text = post.getResponseBodyAsString();
                jsonObject = JSONObject.parseObject(text);
            } else {
                logger.info("---上传素材失败---" + status);
            }
        } catch (Exception e) {
            logger.info("---上传素材失败---", e);
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static File downloadMedia(String fileName, String accessToken, String mediaId) {
        String downUrl = String.format(DOWNLOAD_MEDIA, accessToken, mediaId);

        if (fileName == null) {
            return null;
        }
        File file = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fileOut = null;
        try {
            URL url = new URL(downUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");

            inputStream = conn.getInputStream();
            if (inputStream != null) {
                file = new File(fileName);
            } else {
                return null;
            }

            //写入到文件
            fileOut = new FileOutputStream(file);
            int c = inputStream.read();
            while (c != -1) {
                fileOut.write(c);
                c = inputStream.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

            try {
                inputStream.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void main(String[] args) {
        String accessToken = "16_FcyX7VnToZwHyTFaiCerGVxv_uf5NizsX4ZGjnfGKrxTxOyHt4-aqlX7xKDxUmQtk3JtEIzqi0u6V5wi3iTYL1xzUqylc9QbZmXYpBegfWQmM4COz1q2ruZsgygKVHcAFAWHT";
        String filePath = "F:\\迅雷下载\\20181119.jpg";
        File file = new File(filePath);
        String type = "IMAGE";
        JSONObject jsonObject = uploadMedia(file, accessToken, type, 2);
        System.out.println(jsonObject.getString("url"));
/*{"type":"image","media_id":"ZriiZlhU1__sDTOIQnVf5MFkDyIOhpxonKcy3zbbF-LfJbKsZtqpjdBbUHWvGkev","created_at":1543305974}*/
/*{"type":"image","media_id":"o_twRp6sRFRH79CP5i0ECv7BiH_bK1v7yvBE3-0B9aAQVYPiR0fKo1V8mZheGU2e","created_at":1543306056}*/
    }
}

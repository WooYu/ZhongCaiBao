package com.developer.lecai.http;

import android.text.TextUtils;
import android.util.Log;

import com.developer.lecai.app.XyApplication;
import com.developer.lecai.bean.LoginBean;
import com.developer.lecai.control.UserController;
import com.developer.lecai.utils.MyUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by raotf on 2017/5/11.
 */

public class HttpRequest {
    public static final int CONN_TIME_OUT = 5000;
    public static final int READ_TIME_OUT = 30000;
    public static final int WRITE_TIME_OUT = 30000;

    public Map<String, String> params = new HashMap<>();

    private static OkHttpClient mOkHttpClient;

    {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        }
    }

    public HttpRequest() {
    }

    public HttpRequest(Builder builder) {
        this.params = builder.params;
    }

    public static class Builder {
        private static HttpRequest httpRequest;
        private Map<String, String> params = new HashMap<>();

        public Builder() {
            httpRequest = new HttpRequest(this);
        }

        public HttpRequest build() {
            if (httpRequest == null) {
                httpRequest = new HttpRequest(this);
            }
            return httpRequest;
        }

        public Builder addParam(String key, String value) {
            if (params == null) {
                params = new HashMap<>();
            }
            params.put(key, value);
            return this;
        }

        public Builder addParams(Map<String, String> paramMap) {
            if (params == null) {
                params = new HashMap<>();
            }
            params.putAll(paramMap);
            return this;
        }
    }


    public void post(String url, StringCallback callback) {
        post(url, null, callback);
    }

    public void post(String url, Map<String, String> headers, StringCallback callback) {
        OkHttpUtils.post()
                .url(url)
                .headers(headers == null ? new HashMap<String, String>() : headers)
                .params(bindParams())
                .build()
                .connTimeOut(CONN_TIME_OUT)
                .readTimeOut(READ_TIME_OUT)
                .writeTimeOut(WRITE_TIME_OUT)
                .execute(callback);
    }

    public void postNoParams(String url, Map<String, String> headers, StringCallback callback) {
        OkHttpUtils.post()
                .url(url)
                .headers(headers == null ? new HashMap<String, String>() : headers)
                .build()
                .connTimeOut(CONN_TIME_OUT)
                .readTimeOut(READ_TIME_OUT)
                .writeTimeOut(WRITE_TIME_OUT)
                .execute(callback);
    }

    public void get(String url, StringCallback callback) {
        get(url, null, callback);
    }

    public void get(String url, Map<String, String> headers, StringCallback callback) {
        OkHttpUtils.get()
                .url(url)
                .headers(headers == null ? new HashMap<String, String>() : headers)
                .params(bindParams())
                .build()
                .connTimeOut(CONN_TIME_OUT)
                .readTimeOut(READ_TIME_OUT)
                .writeTimeOut(WRITE_TIME_OUT)
                .execute(callback);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     * @param fileName
     */
    public void down(String url, String filePath, String fileName) {
        down(url, null, new FileHttpCallback(filePath, fileName));
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     * @param fileName
     * @param saveType
     */
    public void down(String url, String filePath, String fileName, int saveType) {
        down(url, null, new FileHttpCallback(filePath, fileName, saveType));
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath 存储文件路径
     * @param fileName 存储文件名
     * @param saveType 保存类型，DOWN_OVER_DEL、DOWN_OVER_BOTH、DOWN_OVER_OLD
     * @param headers
     */
    public void down(String url, String filePath, String fileName, int saveType, Map<String, String> headers) {
        down(url, headers, new FileHttpCallback(filePath, fileName, saveType));
    }

    /**
     * 下载文件
     *
     * @param url
     * @param headers
     * @param callback
     */
    public void down(String url, Map<String, String> headers, FileHttpCallback callback) {
        OkHttpUtils.post()
                .url(url)
                .headers(headers == null ? new HashMap<String, String>() : headers)
                .params(bindParams())
                .build()
                .connTimeOut(CONN_TIME_OUT)
                .readTimeOut(READ_TIME_OUT)
                .writeTimeOut(WRITE_TIME_OUT)
                .execute(callback);
    }

    /**
     * 无进度回调上传
     *
     * @param url
     * @param fileTag
     * @param fileName
     * @param file
     * @param callback
     */
    public void upload(String url, String fileTag, String fileName, File file, StringCallback callback) {
        OkHttpUtils.post()
                .url(url)
                .params(bindParams())
                .addFile(fileTag, fileName, file)
                .build()
                .connTimeOut(5000)
                .readTimeOut(5000)
                .execute(callback);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param headers
     * @param fileTag
     * @param fileName
     * @param file
     * @param callback
     * @return
     */
    public Call upload(String url, Map<String, String> headers, String fileTag, String fileName, File file, UploadCallback callback) {
        MultipartBody.Builder builder = (new MultipartBody.Builder()).setType(MultipartBody.FORM);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        builder.addFormDataPart(fileTag, fileName, createCustomRequestBody(MultipartBody.FORM, file, callback));
        Request.Builder reqBuilder = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                reqBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = reqBuilder.url(url).post(builder.build()).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    /**
     * 创建RequestBody
     *
     * @param contentType
     * @param file
     * @param callback
     * @return
     */
    private RequestBody createCustomRequestBody(final MediaType contentType, final File file, final UploadCallback callback) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    Long remaining = contentLength();
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        callback.onProgress(contentLength(), remaining -= readCount, remaining == 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 取消请求
     *
     * @param url
     */
    public void cancleRequest(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .cancel();
    }

    private Map<String, String> bindParams() {
        String account = "";
        UserController userController = UserController.getInstance();
        LoginBean loginBean=userController.getLoginBean();
        if(loginBean==null){
            account="";
        }else {
            account = loginBean.getLoginname();
        }
        params.put(H.Param.account, account);
        params.put(H.Param.imei, MyUtil.getIMEI(XyApplication.appContext));
        params.put(H.Param.signature, MyUtil.getSignature(account));
        Log.e("params", params.toString());
        return params;
    }

    public void addParam(String key, String value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
    }

    public void addParams(Map<String, String> paramMap) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.putAll(paramMap);
    }

}

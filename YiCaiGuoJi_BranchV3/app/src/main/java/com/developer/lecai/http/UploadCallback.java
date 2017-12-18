package com.developer.lecai.http;

import okhttp3.Callback;

/**
 * Created by raotf on 2017/6/20.
 */

public interface UploadCallback extends Callback {
    public void onProgress(long totalBytes, long remainingBytes, boolean done);
}

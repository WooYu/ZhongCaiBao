package com.developer.lecai.http;

import android.util.Log;

import com.developer.lecai.utils.FileUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 下载文件回调，可设置是否断点续传，选择文件保存方式
 * Created by raotf on 2017/6/20.
 */
public class FileHttpCallback extends FileCallBack {
    public static String SUFFIX_TMP = ".tmp"; // 文件后缀

    public static final int DOWN_OVER_DEL = 0; // 下载完成存在同名文件,删除
    public static final int DOWN_OVER_BOTH = 1; // 保留两者,新文件命名:filename(num);
    public static final int DOWN_OVER_OLD = 2; // 保留两者,有且只有两个文件,源文件更名为filename.old,新文件为源文件名

    private int saveType = DOWN_OVER_DEL;
    private String destFileDir;
    private String destFileName;

    private FileOutputStream fos;
    private InputStream is;
    private long readBytes = 0;
    private boolean isDownContinueLast = false; // 是否断点续传

    public FileHttpCallback(String destFileDir, String destFileName) {
        super(destFileDir, destFileName);
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    public FileHttpCallback(String destFileDir, String destFileName, int saveType) {
        super(destFileDir, destFileName);
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        this.saveType = saveType;
    }

    public void setIsDownContinueLast(boolean isDownContinueLast) {
        this.isDownContinueLast = isDownContinueLast;
    }

    /**
     * 下载进度回调
     * @param progress
     * @param totle
     */
    @Override
    public void inProgress(float progress, long totle) {
        Log.e("FileHttpCallback", "onResponse" + progress + " " + totle);
    }

    /**
     * 请求失败回调
     * @param call
     * @param e
     */
    @Override
    public void onError(Call call, Exception e) {
        Log.e("FileHttpCallback", "onError");
    }

    /**
     * 请求成功回调，一些返回参数处理
     * @param call
     * @param file
     */
    @Override
    public void onResponse(Call call, File file) {
        Log.e("FileHttpCallback", "onResponse" + file);
    }

    /**
     * 重载saveFile，使其支持断点续传
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public File saveFile(Response response) throws IOException {
        int resCode = response.code();
        if(resCode == 404 || resCode == 500) {
            Log.e("FileHttpCallback", "任务下载异常或服务器异常");
            return null;
        }
        byte[] buf = new byte[4096];
        final long total = response.body().contentLength();
        int len = 0;
        try {
            is = response.body().byteStream();
            File tmpFile = new File(destFileDir, destFileName + SUFFIX_TMP);
            if(!tmpFile.getParentFile().exists()) {
                tmpFile.getParentFile().mkdirs();
            }
            Log.e("FileHttpCallback", "------down onResponse-----" + tmpFile.getPath());
            fos = new FileOutputStream(tmpFile, isDownContinueLast);
            while ((len = is.read(buf)) != -1) {
                readBytes += len;
                fos.write(buf, 0, len);
                OkHttpUtils.getInstance().getDelivery().post(new Runnable() {
                    public void run() {
                        FileHttpCallback.this.inProgress((float)readBytes * 1.0F / (float)total, total);
                    }
                });
            }
            fos.flush();
            // 通知读写完成
            Log.e("FileHttpCallback", "------down onResponse----11----" + readBytes);
            File file = new File(destFileDir, destFileName);
            saveFileBySaveType(file, tmpFile, saveType);
            return file;
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 将下载完成的临时文件根据saveType规则替换
     * @param srcFile
     * @param downFile
     * @param saveType
     */
    public void saveFileBySaveType(File srcFile, File downFile, int saveType) {
        if (srcFile != null && srcFile.exists()) {
            switch (saveType) {
                case DOWN_OVER_DEL:
                    srcFile.delete();
                    downFile.renameTo(srcFile);
                    break;
                case DOWN_OVER_BOTH:
                    int num = getNewFileName(srcFile.getParent(), srcFile.getName(), 0);
                    downFile.renameTo(new File(downFile.getPath() + "(" + num + ")"));
                    break;
                case DOWN_OVER_OLD:
                    FileUtil.renameFile(srcFile, new File(srcFile.getParent(), srcFile.getName() + ".old"));
                    downFile.renameTo(srcFile);
                    break;
            }
        } else {
            downFile.renameTo(srcFile);
        }
    }

    /**
     * 通过递归判断源文件当前最大版本:源文件格式filename, filename(1)...;
     * @param path
     * @param filename
     */
    private int getNewFileName(String path, String filename, int num) {
        int i = num;
        File file = null;
        if(num > 0) {
            file = new File(path, filename + "(" + num + ")");
        } else {
            file = new File(path, filename);
        }
        if(file.exists()) {
            i++;
            return getNewFileName(path, filename, i);
        } else {
            return i;
        }
    }
}

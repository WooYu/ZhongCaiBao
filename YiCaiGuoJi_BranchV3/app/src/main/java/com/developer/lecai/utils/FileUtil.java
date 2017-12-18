package com.developer.lecai.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 文件处理工具类
 * Created by raotf on 16-1-29.
 */
public class FileUtil {
    /**
     * 写文件
     *
     * @param file write file
     * @param str  write content
     * @params isAppend 是否追加
     */
    public static void writeFile(File file, String str, boolean isAppend) {
        if (file == null) {
            return;
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(file, isAppend);
            byte[] sBytes = str.getBytes();
            fout.write(sBytes);
            fout.flush();
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 读文件
     *
     * @param file
     * @return
     */
    public static String readFile(File file) {
        if (file == null) {
            return null;
        }

        StringBuilder ret = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmpStr = null;
            while ((tmpStr = reader.readLine()) != null) {
                ret.append(tmpStr);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return ret.toString();
    }

    /**
     * 读取文件信息
     *
     * @param stream
     * @return
     */
    public static String readFile(InputStream stream) {
        StringBuilder builder = new StringBuilder();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
        String line = "";
        try {
            while ((line = buffer.readLine()) != null) {
                builder.append(line);
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * 按行读取文件信息
     *
     * @param stream
     * @return
     */
    public static String readFile4Row(InputStream stream) {
        StringBuilder builder = new StringBuilder();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
        String line = "";
        try {
            while ((line = buffer.readLine()) != null) {
                builder.append(line + "\n");
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * 重命名
     *
     * @param oldFile
     * @param newFile
     */
    public static void renameFile(File oldFile, File newFile) {
        if (oldFile != null && oldFile.exists()) {
            oldFile.renameTo(newFile);
        }
    }

    /**
     * 清除文件,但过滤filters中的文件
     *
     * @param path    需要清理的路径
     * @param filters 不用清除的文件列表
     */
    public static void clearFilesInFilter(String path, List<String> filters) {
        File filePath = new File(path);
        if (filePath.exists() && filePath.isDirectory()) {
            File[] files = filePath.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                String fName = f.getName();

                Log.d("---", "-----" + f.getPath());
                boolean isNeedFilter = false;
                if (filters != null) {
                    for (String filter : filters) {
                        if (fName.equals(filter)) {
                            isNeedFilter = true;
                            break;
                        }
                    }
                }

                if (!isNeedFilter) {
                    f.delete();

                }
            }
        }
    }

    /**
     * 清除文件夹下的所有文件
     *
     * @param file
     * @return
     */
    public static boolean clearFiles(File file) {
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                boolean success = clearFiles(f);
                if (!success) {
                    return false;
                }
            }
        }

        return file.delete();
    }

    /**
     * sdcard是否存在
     *
     * @return
     */
    public static boolean sdCardExist() {
        String state = Environment.getExternalStorageState();
        return !state.equals(Environment.MEDIA_MOUNTED) ? false : true;
    }

    /**
     * 获取sdcard目录
     *
     * @return
     */
    public static String getSdCardPath() {
        if (!sdCardExist()) {
            return null;
        }
        File externalDir = Environment.getExternalStorageDirectory();
        return externalDir.getAbsolutePath();
    }

    /**
     * 创建文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean createFile(File file) {
        if (file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            file.mkdirs();
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context 上下文
     * @param type    文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
     */
    public static File getCacheDirectory(Context context, String type) {
        File appCacheDir = getExternalCacheDirectory(context, type);
        if (appCacheDir == null) {
            appCacheDir = getInternalCacheDirectory(context, type);
        }

        if (appCacheDir == null) {
            Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is mobile phone unknown exception !");
        } else {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is make directory fail !");
            }
        }
        return appCacheDir;
    }

    /**
     * 获取SD卡缓存目录
     *
     * @param context 上下文
     * @param type    文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *                否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     *                {@link Environment#DIRECTORY_MUSIC},
     *                {@link Environment#DIRECTORY_PODCASTS},
     *                {@link Environment#DIRECTORY_RINGTONES},
     *                {@link Environment#DIRECTORY_ALARMS},
     *                {@link Environment#DIRECTORY_NOTIFICATIONS},
     *                {@link Environment#DIRECTORY_PICTURES}, or
     *                {@link Environment#DIRECTORY_MOVIES}.or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    public static File getExternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (TextUtils.isEmpty(type)) {
                appCacheDir = context.getExternalCacheDir();
            } else {
                appCacheDir = context.getExternalFilesDir(type);
            }

            if (appCacheDir == null) {// 有些手机需要通过自定义目录
                appCacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/" + context.getPackageName() + "/cache/" + type);
            }

            if (appCacheDir == null) {
                Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard unknown exception !");
            } else {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is make directory fail !");
                }
            }
        } else {
            Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !");
        }
        return appCacheDir;
    }

    /**
     * 获取内存缓存目录
     *
     * @param type 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    public static File getInternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if (TextUtils.isEmpty(type)) {
            appCacheDir = context.getCacheDir();// /data/data/app_package_name/cache
        } else {
            appCacheDir = new File(context.getFilesDir(), type);// /data/data/app_package_name/files/type
        }

        if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
            Log.e("getInternalDirectory", "getInternalDirectory fail ,the reason is make directory fail !");
        }
        return appCacheDir;
    }
}

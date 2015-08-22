package org.maxwe.android.utils.download;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;

import java.io.File;

/**
 * Created by Pengwei Ding on 2015-08-06 17:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 下载管理器管理的对象
 */
public interface IDownloader{
    /**
     * 提供ID
     * @return
     */
    String getDownloaderId();

    /**
     * 提供下载地址
     * @return
     */
    String getRemoteUri();

    /**
     * 提供保存路径
     * @return
     */
    String getLocalUri();

    /**
     * 提供文件名称
     * @return
     */
    String getSaveName();

    RequestParams getRequestParams();

    void addDownloaderSelf(IDownloader iDownloader);

    void onDownloadStart();
    void onDownloadProgress(long total,long current);
    void onDownloadError(HttpException error, String msg);
    void onDownloadFinish(ResponseInfo<File> responseInfo);
    void onCancelled();
}

package org.maxwe.android.utils.views.sample.download;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import org.maxwe.android.utils.download.ADownloader;
import org.maxwe.android.utils.download.IDownloader;

import java.io.File;

/**
 * Created by Pengwei Ding on 2015-08-10 17:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 构建测试下载管理器的图书
 */
public class Book extends ADownloader {
    private String remoteUri;
    private String localUri;
    private String saveName;

    public Book(String remoteUri,String localUri,String saveName){
        this.remoteUri = remoteUri;
        this.localUri = localUri;
        this.saveName = saveName;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String getDownloaderId() {
        return super.getDownloaderId();
    }


    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String getRemoteUri() {
        return null;
    }

    public void setRemoteUri(String remoteUri) {
        this.remoteUri = remoteUri;
    }

    @Override
    public String getLocalUri() {
        return null;
    }

    public void setLocalUri(){

    }

    @Override
    public String getSaveName() {
        return null;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    @Override
    public RequestParams getRequestParams() {
        return null;
    }

    @Override
    public void addDownloaderSelf(IDownloader iDownloader) {

    }

    @Override
    public void onDownloadStart() {
        System.out.println("=================== " + this.getSaveName() + "马上开始下载 =================");
    }

    @Override
    public void onDownloadProgress(long total, long current) {
        System.out.println("========= " + this.getSaveName() + "下载进度 " + ((float)current/total) + "============");
    }

    @Override
    public void onDownloadError(HttpException error, String msg) {
        System.out.println("============== " + this.getSaveName() + "下载错误 =================");
    }

    @Override
    public void onDownloadFinish(ResponseInfo<File> responseInfo) {
        System.out.println("============== " + this.getSaveName() + "下载完成 =================");
    }

    @Override
    public void onCancelled() {
        System.out.println("============== " + this.getSaveName() + "下载取消 =================");
    }
}

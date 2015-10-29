package org.maxwe.android.utils.download;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


/**
 * Created by Pengwei Ding on 2015-08-06 17:45.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 下载管理器，实现的主要功能是
 */
public class DownloadManager {
    /**
     * 单例化下载管理器
     */
    private static DownloadManager downloadManager = null;

    private DownloadManager() {
    }

    public static DownloadManager getDownloadManager() {
        if (downloadManager == null) {
            synchronized (DownloadManager.class) {
                if (downloadManager == null) {
                    downloadManager = new DownloadManager();
                }
            }
        }
        return downloadManager;
    }

    /**
     * 全局网络请求参数
     */
    private RequestParams globalRequestParams = null;

    /**
     * 获取全局网络请求参数
     *
     * @return
     */
    public RequestParams getGlobalRequestParams() {
        return globalRequestParams;
    }

    /**
     * 设置全局网络请求参数
     *
     * @param globalRequestParams
     */
    public void setGlobalRequestParams(RequestParams globalRequestParams) {
        this.globalRequestParams = globalRequestParams;
    }

    /**
     * 同时下载图书的最大数量
     */
    private static int maxDownloadingNum = 1;

    /**
     * 获取最大下载数量
     *
     * @return
     */
    public int getMaxDownloadingNum() {
        return maxDownloadingNum;
    }

    /**
     * 设置最大下载数量
     *
     * @param maxDownloadingNum
     */
    public void setMaxDownloadingNum(int maxDownloadingNum) {
        this.maxDownloadingNum = maxDownloadingNum;
    }

    /**
     * 正在下载的图书的集合
     */
    private static final LinkedHashMap<String, DownloadUnit> downloadingBooks = new LinkedHashMap<String, DownloadUnit>();
    /**
     * 等待下载的图书的集合
     */
    private static final LinkedList<IDownloader> waitForDownloadBooks = new LinkedList<IDownloader>();

    /**
     * 添加排队的下载者
     *
     * @param downloaders
     */
    public synchronized void addDownloader(IDownloader... downloaders) {
        for (IDownloader downloader : downloaders) {
            if (!waitForDownloadBooks.contains(downloader) && !downloadingBooks.containsKey(downloader.getDownloaderId())) {
                waitForDownloadBooks.add(downloader);
            } else if (downloadingBooks.containsKey(downloader.getDownloaderId())) {
                DownloadUnit downloadUnit = downloadingBooks.get(downloader.getDownloaderId());
                downloadUnit.getDownloader().addDownloaderSelf(downloader);
            }
        }
        this.addDownloaderToScheduled();
    }

    /**
     * 添加排队的下载者
     *
     * @param downloaders
     */
    public synchronized void addDownloader(LinkedList<IDownloader> downloaders) {
        for (IDownloader downloader : downloaders) {
            if (!waitForDownloadBooks.contains(downloader) && !downloadingBooks.containsKey(downloader.getDownloaderId())) {
                waitForDownloadBooks.add(downloader);
            } else if (downloadingBooks.containsKey(downloader.getDownloaderId())) {
                DownloadUnit downloadUnit = downloadingBooks.get(downloader.getDownloaderId());
                downloadUnit.getDownloader().addDownloaderSelf(downloader);
            }
        }
        this.addDownloaderToScheduled();
    }

    /**
     * 移除指定的下载者
     *
     * @param downloaders
     */
    public synchronized void removeDownloader(IDownloader... downloaders) {
        for (IDownloader downloader : downloaders) {
            String id = downloader.getDownloaderId();
            if (downloadingBooks.containsKey(id)) {
                DownloadUnit downloadUnit = downloadingBooks.remove(id);
                downloadUnit.getHttpHandler().cancel();
            } else if (waitForDownloadBooks.contains(downloader)) {
                waitForDownloadBooks.remove(downloader);
            }
        }
        this.addDownloaderToScheduled();
    }

    /**
     * 移除指定的下载者
     *
     * @param downloaders
     */
    public synchronized void removeDownloader(LinkedList<IDownloader> downloaders) {
        for (IDownloader downloader : downloaders) {
            String id = downloader.getDownloaderId();
            if (downloadingBooks.containsKey(id)) {
                DownloadUnit downloadUnit = downloadingBooks.remove(id);
                downloadUnit.getHttpHandler().cancel();
            } else if (waitForDownloadBooks.contains(downloader)) {
                waitForDownloadBooks.remove(downloader);
            }
        }
        this.addDownloaderToScheduled();
    }


    /**
     * 停止全部的下载
     */
    public synchronized void removeAllDownloader() {
        waitForDownloadBooks.clear();
        Set<Map.Entry<String, DownloadUnit>> entries = downloadingBooks.entrySet();
        for (Map.Entry<String, DownloadUnit> entry : entries) {
            DownloadUnit value = entry.getValue();
            value.getHttpHandler().cancel();
        }
        downloadingBooks.clear();
    }

    /**
     * 下载调度策略
     */
    private synchronized void addDownloaderToScheduled() {
        while (this.downloadingBooks.size() < this.maxDownloadingNum) {
            /**
             * 如果当前正在下载的数量小于最大下载数量，获取待下载队列第一个下载
             */
            IDownloader downloader = this.waitForDownloadBooks.poll();
            if (downloader != null) {
                /**
                 * 待下载队列不为空就添加到下载队列
                 */
                this.downloadingBooks.put(downloader.getDownloaderId(), new DownloadUnit(downloader, this.startDownloadTask(downloader)));
            } else {
                /**
                 * 待下载队列是空就终止
                 */
                break;
            }
        }
    }


    /**
     * 启动下载任务
     *
     * @param downloader
     * @return
     */
    private HttpHandler startDownloadTask(final IDownloader downloader) {
        if (!new File(downloader.getLocalUri()).exists()) {
            new File(downloader.getLocalUri()).mkdirs();
        }
        HttpUtils http = new HttpUtils();
        RequestParams requestParams;
        if (downloader.getRequestParams() != null) {
            requestParams = downloader.getRequestParams();
        } else {
            requestParams = globalRequestParams;
        }
        HttpHandler<File> download = http.download(downloader.getRemoteUri(), downloader.getLocalUri() + File.separator + downloader.getSaveName(), requestParams,
                true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {
                    long total;
                    @Override
                    public void onStart() {
                        downloader.onDownloadStart();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        this.total = total;
                        downloader.onDownloadProgress(total, current);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        if (responseInfo.result.length() < this.total){
                            onFailure(new HttpException("本地文件数据量(" + responseInfo.result.length() + ")小于网络文件数据量(" + this.total + ")"),"本地文件数据量(" + responseInfo.result.length() + ")小于网络文件数据量(" + this.total + ")");
                        }else{
                            downloader.onDownloadFinish(responseInfo);
                            finishDownloader(downloader);
                        }
                    }


                    @Override
                    public void onFailure(HttpException error, String msg) {
                        downloader.onDownloadError(error, msg);
                        finishDownloader(downloader);
                    }

                    @Override
                    public void onCancelled() {
                        super.onCancelled();
                        downloader.onCancelled();
                        finishDownloader(downloader);
                    }
                });
        return download;
    }

    /**
     * 图书下载完成或者出现异常导致终止或者触发取消操作
     * 执行该方法进行再次调度
     */
    private void finishDownloader(IDownloader iDownloader){
        downloadingBooks.remove(iDownloader.getDownloaderId());
        addDownloaderToScheduled();
    }

    /**
     * 下载单元
     */
    private class DownloadUnit {
        private IDownloader downloader;
        private HttpHandler httpHandler;

        public DownloadUnit() {
        }

        public DownloadUnit(IDownloader downloader) {
            this.downloader = downloader;
        }

        public DownloadUnit(IDownloader downloader, HttpHandler httpHandler) {
            this.downloader = downloader;
            this.httpHandler = httpHandler;
        }

        public IDownloader getDownloader() {
            return downloader;
        }

        public void setDownloader(IDownloader downloader) {
            this.downloader = downloader;
        }

        public HttpHandler getHttpHandler() {
            return httpHandler;
        }

        public void setHttpHandler(HttpHandler httpHandler) {
            this.httpHandler = httpHandler;
        }
    }
}

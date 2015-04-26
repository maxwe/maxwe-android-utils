package org.maxwe.android.utils.views.sample.activities.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.maxwe.android.utils.download.DownloadInfo;
import org.maxwe.android.utils.download.DownloadManager;
import org.maxwe.android.utils.views.sample.R;

import java.io.File;

/**
 * Created by danhantao on 15/4/24.
 * Email:danhantao@yeah.net
 * Description for android-utils-parent
 */
public class TestActivity1 extends Activity {
    private static final String TAG = TestActivity1.class.getSimpleName();
    private String URL =
            "http://119.80.160.54:9090/data8/7/8/6e/8/c568fe83b8fc2cee289f636ae2786e87/dl.baofeng.com/Baofeng5-5.47.0331.exe";
    private TextView label;
    private ProgressBar progressBar;
    private Button stopButton;
    private String targetPath;

    private Button cleanButton;
    private Button jumpButton;

    // 下载工具类
    private DownloadManager downloadManager;

    // 对应的下载信息
    private DownloadInfo downloadInfo;

    private long contentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mx_download_test_activity1);
        File file = TestActivity1.this.getExternalFilesDir("");
        targetPath = file.getAbsolutePath();
        label = (TextView) findViewById(R.id.download_label2);
        progressBar = (ProgressBar) findViewById(R.id.download_pb2);
        stopButton = (Button) findViewById(R.id.download_stop_btn2);
        cleanButton = (Button) findViewById(R.id.cleanButton2);
        jumpButton = (Button) findViewById(R.id.jumpButton2);

        // 下载
        downloadManager = GlobalDataCacheMemory.INSTANCE.getDownloadManager(getApplicationContext());
        contentId = 100l; //文件的唯一标识
        downloadInfo = downloadManager.getDownloadInfo(contentId);
        // 新添加一个下载
        if (downloadInfo == null) {
        } else {
            HttpHandler.State state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                case LOADING:
                    stopButton.setText("暂停");
                    break;
                case SUCCESS:
                    break;
                case CANCELLED:
                case FAILURE:
                    stopButton.setText("下载");
                    break;
                default:
                    break;
            }
            Log.i(TAG, "downloadInfo:" + downloadInfo.toString());
            int progressbar = (int) (downloadInfo.getProgress() * 100.0f / downloadInfo.getFileLength());
            label.setText(progressbar + "");
            Log.i(TAG, "进度为:" + progressbar);
            progressBar.setProgress(progressbar);
            if (downloadInfo.getState().equals(HttpHandler.State.LOADING)) {
                HttpHandler<File> handler = downloadInfo.getHandler();
                if (handler != null) {
                    downloadManager.changeDownloadCallBack(downloadInfo,new DownloadRequestCallBack(contentId, progressBar, stopButton));
                }
            }
        }

        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 删除数据库
                DownloadInfo dLoadInfo = downloadManager.getDownloadInfo(contentId);
                if (dLoadInfo != null) {
                    try {
                        downloadManager.removeDownload(dLoadInfo);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                // 删除文件
                File file1 = new File(targetPath + "/" + "1.apk");
                if (file1.exists()) {
                    file1.delete();
                }
                progressBar.setProgress(0);
                label.setText(0+"");
            }
        });


        jumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestActivity1.this.finish();
                startActivity(new Intent(TestActivity1.this, TestActivity2.class));
            }
        });

        // 停止
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadInfo = downloadManager.getDownloadInfo(contentId);
                if (downloadInfo == null) {
                    try {
                        downloadManager
                                .addNewDownload(contentId, URL, "111", targetPath + "/" + "1.apk", true, false, new DownloadRequestCallBack(contentId, progressBar, stopButton));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                HttpHandler.State state = downloadInfo.getState();
                switch (state) {
                    case WAITING:
                    case STARTED:
                    case LOADING:
                        try {
                            downloadManager.stopDownload(downloadInfo);
                            Log.i(TAG, downloadInfo.toString());
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        break;
                    case CANCELLED:
                    case FAILURE:
                        try {
                            downloadManager.resumeDownload(downloadInfo, new DownloadRequestCallBack(contentId, progressBar, stopButton));
                            Log.i(TAG, downloadInfo.toString());
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

    }

    class DownloadRequestCallBack extends RequestCallBack<File> {
        long contentId;
        View statusView;
        View progressbar;

        private void refreshView() {
            DownloadInfo dLoadInfo = downloadManager.getDownloadInfo(contentId);
            HttpHandler.State state = dLoadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                case LOADING:
                    ((Button) statusView).setText("暂停");
                    break;
                case SUCCESS:
                    break;
                case CANCELLED:
                case FAILURE:
                    ((Button) statusView).setText("下载");
                    break;
                default:
                    break;
            }
        }

        public DownloadRequestCallBack(long contentId, View progressbar, View statusView) {
            this.contentId = contentId;
            this.statusView = statusView;
            this.progressbar = progressbar;
        }

        @Override
        public void onSuccess(ResponseInfo<File> responseInfo) {
            Log.i(TAG, "onSuccess");
            refreshView();
            // 获取成功id
            DownloadInfo dLoadInfo = downloadManager.getDownloadInfo(contentId);
            try {
                if (dLoadInfo != null) {
                    downloadManager.removeDownload(dLoadInfo);
                } else {

                }
                // 下载成功
            } catch (DbException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            refreshView();
            Log.i(TAG, "下载失败:" + msg);
        }

        @Override
        public void onCancelled() {
            refreshView();
            Log.i(TAG, "onCancelled");
        }

        @Override
        public void onLoading(long total, long current, boolean isUploading) {
            refreshView();
            Log.i(TAG, "文件下载中,total:" + total + " current:" + current + " " + isUploading);
            float pro = (current * 100.0f) / total;
            ((ProgressBar) progressbar).setProgress((int) pro);
            label.setText((int) pro + "");
            Log.i(TAG, "onLoading进度为:" + (int) pro);
        }

        @Override
        public void onStart() {
            refreshView();
            Log.i(TAG, "onStart");
        }
    }
}

package org.maxwe.android.utils.sample.downloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import org.maxwe.android.utils.download.DownloadManager;
import org.maxwe.android.utils.download.IDownloader;
import org.maxwe.android.utils.sample.R;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2015-08-20 11:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class DownloaderActivity extends Activity implements View.OnClickListener{

    private static final DownloadManager downloadManager = DownloadManager.getDownloadManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.act_downloader);
        downloadManager.setMaxDownloadingNum(2);
    }

    @Override
    public void onClick(View v) {
        String s = v.getTag().toString();
        int id = v.getId();
        switch (id){
            case R.id.act_download_1_1:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_1", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_1", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }
                break;
            case R.id.act_download_1_2:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_1", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_1", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }

                break;
            case R.id.act_download_1_3:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_1", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_1", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }
                break;
            case R.id.act_download_1_4:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_1", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_1", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }

                break;
            case R.id.act_download_1_5:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_1", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_1", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }

                break;
            case R.id.act_download_2_1:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_2", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_2", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }
                break;
            case R.id.act_download_2_2:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_2", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_2", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }
                break;
            case R.id.act_download_2_3:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_2", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_2", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }
                break;
            case R.id.act_download_3_1:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_3", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_3", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }
                break;
            case R.id.act_download_3_2:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_3", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_3", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }

                break;
            case R.id.act_download_3_3:
                if ("0".equals(s)){
                    downloadManager.addDownloader(createDownloader("book_id_3", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    v.setTag("1");
                    ((Button)v).setText("取消");
                }else if ("1".equals(s)){
                    v.setTag("0");
                    downloadManager.removeDownloader(createDownloader("book_id_3", (ProgressBar) ((LinearLayout) v.getParent()).getChildAt(0)));
                    ((Button)v).setText("下载");
                }
                break;
        }
    }

    private IDownloader createDownloader(String id,ProgressBar progressBar){
        return new MyDownloader(id,progressBar);
    }

    class MyDownloader implements IDownloader{
        private LinkedList<ProgressBar> progressBars = new LinkedList<ProgressBar>();

        private String id;
        private ProgressBar progressBar;

        public MyDownloader(String id,ProgressBar progressBar){
            this.id = id;
            this.progressBar = progressBar;
            this.progressBars.add(progressBar);
        }

        @Override
        public boolean equals(Object obj) {
            if (((IDownloader)obj).getDownloaderId().equals(this.getDownloaderId())){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public String getDownloaderId() {
            return id;
        }

        @Override
        public String getRemoteUri() {
//            return "http://mqh.icloud-media.com:8080/mqh/uploads/enterprise/000/000/037/resources/000/000/914/注册执业销售经理V2.0.zip";
            return "http://mqh.icloud-media.com:8080/mqh/uploads/enterprise/000/000/037/resources/000/000/905/产品画册-奥迪A7V2.0.zip";
        }

        @Override
        public String getLocalUri() {
            return "/mnt/sdcard/test";
        }

        @Override
        public String getSaveName() {
            return id + ".zip";
        }

        @Override
        public RequestParams getRequestParams() {
            return null;
        }

        @Override
        public void addDownloaderSelf(IDownloader iDownloader) {
            this.progressBars.addAll(((MyDownloader)iDownloader).getProgressBars());
        }

        @Override
        public void onDownloadStart() {
            System.out.println("================start==================");
        }

        @Override
        public void onDownloadProgress(long total, long current) {
            for (ProgressBar progressBar:progressBars){
                progressBar.setMax((int)total);
                progressBar.setProgress((int) current);
            }
            System.out.println("================progress==================" + current + " / " + total);
        }

        @Override
        public void onDownloadError(HttpException error, String msg) {
            System.out.println("================error==================" + msg);
            error.printStackTrace();
        }

        @Override
        public void onDownloadFinish(ResponseInfo<File> responseInfo) {
            System.out.println("================finish==================" + responseInfo.contentLength);
        }

        @Override
        public void onCancelled() {
            System.out.println("================cancel==================");
        }

        public LinkedList<ProgressBar> getProgressBars() {
            return progressBars;
        }
    }
}

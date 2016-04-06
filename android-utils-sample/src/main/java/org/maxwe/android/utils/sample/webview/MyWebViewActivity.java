package org.maxwe.android.utils.sample.webview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by Pengwei Ding on 2016-04-06 16:08.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MyWebViewActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
//        webView.loadUrl("https://github.com/QQ1350995917/b-f-setting/blob/master/README.md");
        webView.loadUrl("file:///android_asset/test.md");
        this.setContentView(webView);
    }
}

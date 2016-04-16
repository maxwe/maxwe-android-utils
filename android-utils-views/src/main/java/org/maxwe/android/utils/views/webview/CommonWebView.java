package org.maxwe.android.utils.views.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * Created by Pengwei Ding on 2016-04-15 16:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CommonWebView extends WebView {

    public CommonWebView(Context context) {
        super(context);
        this.init();
    }

    public CommonWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public CommonWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init(){
        this.loadUrl("file:///android_asset/common_web_view.html");
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);

        HitTestResult result = getHitTestResult();

        MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        };

        menu.setHeaderTitle(result.getExtra());
        menu.addSubMenu(0, 0, 0, "Save Image");
        menu.addSubMenu(0, 1, 1, "View Image");

        menu.setQwertyMode(false);


        if (result.getType() == HitTestResult.IMAGE_TYPE ||
                result.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            // Menu options for an image.
            //set the header title to the image url
            menu.setHeaderTitle(result.getExtra());
            menu.add(0, 0, 0, "Save Image").setOnMenuItemClickListener(handler);
            menu.add(0, 1, 1, "View Image").setOnMenuItemClickListener(handler);
        } else if (result.getType() == HitTestResult.ANCHOR_TYPE ||
                result.getType() == HitTestResult.SRC_ANCHOR_TYPE) {
            // Menu options for a hyperlink.
            //set the header title to the link url
            menu.setHeaderTitle(result.getExtra());
            menu.add(0, 0, 0, "Save Link").setOnMenuItemClickListener(handler);
            menu.add(0, 1, 1, "Share Link").setOnMenuItemClickListener(handler);
        }
    }

}

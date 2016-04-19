package org.maxwe.android.utils.views.webview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by Pengwei Ding on 2016-04-15 16:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CommonWebView extends WebView {

    public CommonWebView(Context context) {
        super(context.getApplicationContext());
        this.init();
    }

    public CommonWebView(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
        this.init();
    }

    public CommonWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context.getApplicationContext(), attrs, defStyle);
        this.init();
    }

    private void init(){
        this.loadUrl("file:///android_asset/common_web_view.html");
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        CustomizedSelectActionModeCallback customizedSelectActionModeCallback = new CustomizedSelectActionModeCallback(
                callback);
        return super.startActionMode(customizedSelectActionModeCallback);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public class CustomizedSelectActionModeCallback implements ActionMode.Callback {
        private ActionMode.Callback callback;


        public CustomizedSelectActionModeCallback(ActionMode.Callback callback) {
            this.callback = callback;
        }


        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return callback.onCreateActionMode(mode, menu);
        }


        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return callback.onPrepareActionMode(mode, menu);
        }



        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item == null || TextUtils.isEmpty(item.getTitle())) {
                return callback.onActionItemClicked(mode, item);
            }
            if (!item.getTitle().toString().contains("搜索")
                    && !item.getTitle().toString().contains("search")) {
                return callback.onActionItemClicked(mode, item);
            }
            loadUrl("javascript:window.search.show(window.getSelection().toString());");
            clearFocus();
            return true;
        }


        @Override
        public void onDestroyActionMode(ActionMode mode) {
            callback.onDestroyActionMode(mode);
        }
    }


}

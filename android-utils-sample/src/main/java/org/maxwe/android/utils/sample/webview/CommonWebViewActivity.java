package org.maxwe.android.utils.sample.webview;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;

import org.maxwe.android.utils.sample.R;
import org.maxwe.android.utils.views.webview.CommonWebView;

/**
 * Created by Pengwei Ding on 2016-04-15 16:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CommonWebViewActivity extends Activity {
    private CommonWebView commonWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.commonWebView = new CommonWebView(this);
        this.setContentView(commonWebView);

    }

    private ActionMode mActionMode = null;
    @Override
    public void onActionModeStarted(ActionMode mode) {
        if (mActionMode == null) {
            mActionMode = mode;
            Menu menu = mode.getMenu();
            menu.clear();
            mode.getMenuInflater().inflate(R.menu.ym_epub_web_view_selection, menu);
        }
        super.onActionModeStarted(mode);
    }

    public void onContextualMenuItemClicked(MenuItem item) {
        int itemId = item.getItemId();
        if (R.id.ym_epub_id_menu_selection_copy == itemId) {

        } else if (R.id.ym_epub_id_menu_selection_digest == itemId) {

        } else if (R.id.ym_epub_id_menu_selection_note == itemId) {

        } else if (R.id.ym_epub_id_menu_selection_share == itemId) {

        }

        if (mActionMode != null) {
            mActionMode.finish();
        }

    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        mActionMode = null;
        super.onActionModeFinished(mode);
    }
}

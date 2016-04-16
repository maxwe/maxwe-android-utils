package org.maxwe.android.utils.sample.webview;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
        registerForContextMenu(commonWebView);
        this.menuInflater = new MenuInflater(this);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        System.out.println();
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }
    private MenuInflater menuInflater;
    private ActionMode mActionMode = new ActionMode() {

        @Override
        public void setTitle(CharSequence title) {

        }

        @Override
        public void setTitle(int resId) {

        }

        @Override
        public void setSubtitle(CharSequence subtitle) {

        }

        @Override
        public void setSubtitle(int resId) {

        }

        @Override
        public void setCustomView(View view) {

        }

        @Override
        public void invalidate() {

        }

        @Override
        public void finish() {

        }

        @Override
        public Menu getMenu() {
            return new Menu() {
                @Override
                public MenuItem add(CharSequence title) {
                    return null;
                }

                @Override
                public MenuItem add(int titleRes) {
                    return null;
                }

                @Override
                public MenuItem add(int groupId, int itemId, int order, CharSequence title) {
                    return null;
                }

                @Override
                public MenuItem add(int groupId, int itemId, int order, int titleRes) {
                    return null;
                }

                @Override
                public SubMenu addSubMenu(CharSequence title) {
                    return null;
                }

                @Override
                public SubMenu addSubMenu(int titleRes) {
                    return null;
                }

                @Override
                public SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title) {
                    return null;
                }

                @Override
                public SubMenu addSubMenu(int groupId, int itemId, int order, int titleRes) {
                    return null;
                }

                @Override
                public int addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics, Intent intent, int flags, MenuItem[] outSpecificItems) {
                    return 0;
                }

                @Override
                public void removeItem(int id) {

                }

                @Override
                public void removeGroup(int groupId) {

                }

                @Override
                public void clear() {

                }

                @Override
                public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {

                }

                @Override
                public void setGroupVisible(int group, boolean visible) {

                }

                @Override
                public void setGroupEnabled(int group, boolean enabled) {

                }

                @Override
                public boolean hasVisibleItems() {
                    return false;
                }

                @Override
                public MenuItem findItem(int id) {
                    return null;
                }

                @Override
                public int size() {
                    return 0;
                }

                @Override
                public MenuItem getItem(int index) {
                    return null;
                }

                @Override
                public void close() {

                }

                @Override
                public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
                    return false;
                }

                @Override
                public boolean isShortcutKey(int keyCode, KeyEvent event) {
                    return false;
                }

                @Override
                public boolean performIdentifierAction(int id, int flags) {
                    return false;
                }

                @Override
                public void setQwertyMode(boolean isQwerty) {

                }
            };
        }

        @Override
        public CharSequence getTitle() {
            return "TEST";
        }

        @Override
        public CharSequence getSubtitle() {
            return "TEST";
        }

        @Override
        public View getCustomView() {
            LinearLayout linearLayout = new LinearLayout(CommonWebViewActivity.this);
            linearLayout.setBackgroundColor(Color.RED);
            return linearLayout;
        }


        @Override
        public MenuInflater getMenuInflater() {
            return menuInflater;
        }
    };

    @Override
    public void onActionModeStarted(ActionMode mode) {
//        if (mActionMode == null) {
//            mActionMode = mode;
//            Menu menu = mode.getMenu();
//            menu.clear();
//            mode.getMenuInflater().inflate(R.menu.ym_epub_web_view_selection, menu);
//        }
        super.onActionModeStarted(mActionMode);
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

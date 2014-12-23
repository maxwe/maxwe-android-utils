package org.maxwe.android.views.treelist;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.maxwe.json.JsonArray;
import org.maxwe.json.JsonObject;

/**
 * Created by dingpengwei on 12/24/14.
 */
public class TreeNode extends LinearLayout implements View.OnClickListener, TreeListConstants {

    private JsonObject nodeItem;
    private TextView textView;
    private LinearLayout linearLayout;

    public TreeNode(Context context) {
        super(context);
        this.initView();
    }

    public TreeNode(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }

    public TreeNode(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initView();
    }

    private void initView() {
        this.setOrientation(LinearLayout.VERTICAL);
        this.textView = new TextView(this.getContext());
        this.textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.textView.setTextSize(20);
        this.textView.setBackgroundColor(Color.GREEN);
        this.linearLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(45, 0, 0, 0);
        this.linearLayout.setLayoutParams(layoutParams);
        this.linearLayout.setOrientation(LinearLayout.VERTICAL);
        this.linearLayout.setVisibility(View.GONE);
        this.textView.setOnClickListener(this);
        this.addView(this.textView);
        this.addView(this.linearLayout);
    }

    public void bindDataToView() {
        this.textView.setText(this.nodeItem.getString(KEY_CITY_NAME));
    }

    @Override
    public void onClick(View v) {
        if (!this.nodeItem.hasKey(KEY_NODE_LIST)) {
            this.nodeItem.set(KEY_NODE_LIST, this.getSubNodeList(this.nodeItem.getString(KEY_CITY_ID)));
        }

        if (!this.nodeItem.hasKey(KEY_UNFOLD)) {
            this.nodeItem.set(KEY_UNFOLD, KEY_UNFOLD_TRUE);
            this.linearLayout.setVisibility(View.VISIBLE);
            this.bindSubNodeListToView();
        } else if (this.nodeItem.getString(KEY_UNFOLD).equals(KEY_UNFOLD_TRUE)) {
            this.nodeItem.set(KEY_UNFOLD, KEY_UNFOLD_FALSE);
            this.linearLayout.setVisibility(View.GONE);
        } else if (this.nodeItem.getString(KEY_UNFOLD).equals(KEY_UNFOLD_FALSE)) {
            this.linearLayout.setVisibility(View.VISIBLE);
            this.nodeItem.set(KEY_UNFOLD, KEY_UNFOLD_TRUE);
            this.bindSubNodeListToView();
        }
    }

    private void bindSubNodeListToView() {
        JsonArray jsonArray = this.nodeItem.getJsonArray(KEY_NODE_LIST);
        int lenght = jsonArray.getLenght();
        this.linearLayout.removeAllViews();
        for (int i = 0; i < lenght; i++) {
            TreeNode treeNode = new TreeNode(this.getContext());
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            treeNode.setNodeItem(jsonObject).bindDataToView();
            this.linearLayout.addView(treeNode);
        }
    }


    private JsonArray getSubNodeList(String pid) {
        TreeData treeData = new TreeData(this.getContext());
        return treeData.getTreeNode(pid);
    }

    public TreeNode setNodeItem(JsonObject nodeItem) {
        this.nodeItem = nodeItem;
        return this;
    }

    public TextView getTextView() {
        return textView;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }
}

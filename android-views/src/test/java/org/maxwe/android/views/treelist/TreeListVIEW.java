package org.maxwe.android.views.treelist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import org.maxwe.json.JsonArray;
import org.maxwe.json.JsonObject;

/**
 * Created by dingpengwei on 12/19/14.
 */
public class TreeListView extends ViewGroup implements TreeListConstants{

    private JsonObject jsonObject;
    private int offsetX;
    private int offsetY;

    public TreeListView(Context context) {
        super(context);
        this.initView();
    }

    public TreeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }

    public TreeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initView();
    }

    private void initView(){
        JsonArray nodeList = this.jsonObject.getJsonArray(this.NODE_LIST);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}

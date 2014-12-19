package org.maxwe.android.views.treelist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import org.maxwe.android.views.R;
import org.maxwe.json.JsonArray;
import org.maxwe.json.JsonObject;

/**
 * Created by dingpengwei on 12/19/14.
 */
public class TreeListView extends ViewGroup implements TreeListConstants{

    private JsonObject jsonObject;
    private OnNoedClickerListener onNoedClickerListener;
    private Context context;

    private int offsetX;
    private int offsetY;

    public TreeListView(Context context) {
        super(context);
        this.init(context);
    }

    public TreeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public TreeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    private void init(Context context){
        this.context = context;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        JsonArray nodeList = this.jsonObject.getJsonArray(this.KEY_NODE_LIST);
        int lenght = nodeList.getLenght();
        for(int i=0;i<lenght;i++){
            TextView textView = new TextView(this.context);
            textView.setText(nodeList.getJsonObject(i).getString(KEY_NAME));
            textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            this.addView(textView);
            textView.layout(this.offsetX, this.offsetY = this.offsetY + 20, this.offsetX + 100, this.offsetY + 20);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public TreeListView setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
        return this;
    }

    public TreeListView setOnNoedClickerListener(OnNoedClickerListener onNoedClickerListener) {
        this.onNoedClickerListener = onNoedClickerListener;
        return this;
    }

    public interface OnNoedClickerListener{
        public void onNodeClick(JsonArray jsonArray);
    }
}

package org.maxwe.android.views.treelist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import org.maxwe.json.Json;
import org.maxwe.json.JsonArray;
import org.maxwe.json.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by dingpengwei on 12/24/14.
 */
public class TreeListView extends HorizontalScrollView implements TreeListConstants{
    private String fileName = "treelist.sql";
    private JsonArray nodeList;
    private ScrollView verticalScrollView;
    private LinearLayout nodeRoot;

    public TreeListView(Context context) {
        super(context);
        this.init();
    }

    public TreeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public TreeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init(){
        try {
            TreeData treeData = new TreeData(this.getContext());
            if(treeData.getConuter() < 1){
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.getResources().getAssets().open(fileName)));
                String sql = null;
                JsonArray sqls = Json.createJsonArray();
                while((sql = reader.readLine()) != null){
                    sqls.push(sql);
                }
                reader.close();
                treeData.initBySql(sqls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.verticalScrollView = new ScrollView(this.getContext());
        this.nodeRoot = new LinearLayout(this.getContext());
        this.nodeRoot.setOrientation(LinearLayout.VERTICAL);
        this.verticalScrollView.addView(this.nodeRoot);
        this.addView(this.verticalScrollView);

        this.nodeList = this.getTreeNode("0");
        int lenght = this.nodeList.getLenght();
        for(int i=0;i<lenght;i++){
            TreeNode treeNode = new TreeNode(this.getContext());
            JsonObject jsonObject = this.nodeList.getJsonObject(i);
            treeNode.setNodeItem(jsonObject).bindDataToView();
            this.nodeRoot.addView(treeNode);
        }
    }


    private JsonArray getTreeNode(String pid){
        TreeData treeData = new TreeData(this.getContext());
        return treeData.getTreeNode(pid);
    }
}

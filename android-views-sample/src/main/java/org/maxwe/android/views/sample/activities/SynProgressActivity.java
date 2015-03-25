package org.maxwe.android.views.sample.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.maxwe.android.views.sample.R;
import org.maxwe.android.views.synprogress.MarchListener;
import org.maxwe.android.views.synprogress.MarchManager;
import org.maxwe.android.views.synprogress.Marching;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Created by dingpengwei on 3/18/15.
 */
public class SynProgressActivity extends Activity implements View.OnClickListener {

    private int threadId = 0;
    private int viewId = 0;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout linearLayout;
    private MarchManager instance = MarchManager.getInstance();
    private Executor executor = Executors.newFixedThreadPool(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.horizontalScrollView = new HorizontalScrollView(this);
        this.setContentView(this.horizontalScrollView);
        this.linearLayout = new LinearLayout(this);
        this.linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.horizontalScrollView.addView(this.linearLayout);



        Button addThread = new Button(this);
        addThread.setText("添加线程");
        addThread.setId(R.id.bt_add_thread);
        addThread.setOnClickListener(this);
        addThread.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.linearLayout.addView(addThread);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_add_thread) {
            LinearLayout linearLayout1 = new LinearLayout(this);
            linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            Button button = new Button(this);
            button.setId(R.id.bt_add_view);
            button.setText("添加进度");
            button.setOnClickListener(this);
            linearLayout1.addView(button);
            this.linearLayout.addView(linearLayout1);

            String threadId = "thread" + this.threadId;
            Marching marching = new Marching(threadId);
            instance.addMarching(threadId, marching);
            marching.executeOnExecutor(executor,threadId);
            linearLayout1.setTag(threadId);
            this.threadId++;
        } else if (id == R.id.bt_add_view) {
            LinearLayout parent = (LinearLayout) view.getParent();
            String threadId = (String) parent.getTag();
            Marching marching = instance.getMarching(threadId);
            final TextView textView = new TextView(this);
            String viewId = "viewId-" + threadId + "-" + this.viewId;

            textView.setText(viewId);
            parent.addView(textView);
            if (marching != null){
                marching.registerMarchListener(viewId, new MarchListener(textView) {
                    @Override
                    public void onMarchPre() {

                    }

                    @Override
                    public void onMarching(int progress) {
                        ((TextView)this.view).setText(progress + "");
                    }

                    @Override
                    public void onMarchEnd(String id) {
                        MarchManager.getInstance().removeMarching(id);
                    }

                    @Override
                    public void onMarchError() {

                    }
                });
            }
            this.viewId ++;
        }
    }
}

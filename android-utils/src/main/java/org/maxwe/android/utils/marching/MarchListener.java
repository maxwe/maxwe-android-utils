package org.maxwe.android.utils.marching;

import android.view.View;

/**
 * Created by dingpengwei on 3/18/15.
 */
public abstract class MarchListener {
    protected View view;
    public String marchListenerId = null;
    public MarchListener(View view){
        this.view = view;
    }
    public abstract void onMarchPre();
    public abstract void onMarching(int progress);
    public abstract void onMarchEnd(String id);
    public abstract void onMarchError();
}

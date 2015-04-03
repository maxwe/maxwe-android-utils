package org.maxwe.android.utils.marching;

import android.view.View;
import org.maxwe.json.JsonObject;

/**
 * Created by dingpengwei on 3/18/15.
 */
public abstract class MarchListener {
    protected String KEY_PROGERSS = "PROGRESS";
    protected String KEY_MARCHLISTENER_ID = "ID";
    protected View view;
    public MarchListener(View view){
        this.view = view;
    }

    public String getKEY_PROGERSS() {
        return KEY_PROGERSS;
    }

    public String getKEY_MARCHLISTENER_ID() {
        return KEY_MARCHLISTENER_ID;
    }

    public abstract void onMarchPre(JsonObject jsonObject);
    public abstract void onMarching(JsonObject jsonObject);
    public abstract void onMarchEnd(JsonObject jsonObject);
    public abstract void onMarchError(JsonObject jsonObject);
}

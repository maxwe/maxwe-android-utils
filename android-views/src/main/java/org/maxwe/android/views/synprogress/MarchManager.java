package org.maxwe.android.views.synprogress;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by dingpengwei on 3/18/15.
 */
public class MarchManager {


    private static volatile MarchManager marchManager;
    private MarchManager(){}
    public static MarchManager getInstance(){
        if (marchManager == null){
            synchronized (MarchManager.class){
                if (marchManager == null){
                    marchManager = new MarchManager();
                }
            }
        }
        return marchManager;
    }

    private static ConcurrentMap<String,Marching> marchingConcurrentMap = new ConcurrentHashMap<>();

    public void addMarching(String id,Marching marching){
        marchingConcurrentMap.put(id,marching);
    }

    public boolean removeMarching(String id){
        boolean result = false;
        Marching marching = marchingConcurrentMap.get(id);
        if (marching != null){
            boolean cancel = marching.cancel(true);
            if (cancel){
                marchingConcurrentMap.remove(id);
                result = true;
            }
        }
        return result;
    }

    public Marching getMarching(String id){
        return marchingConcurrentMap.get(id);
    }
}

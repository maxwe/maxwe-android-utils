package org.maxwe.android.utils.marching;

import android.os.AsyncTask;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by dingpengwei on 3/16/15.
 *
 * String：启动任务执行的输入参数的类型。
 * Integer：后台任务完成进度值的类型。
 * Boolean：后台执行任务完成后返回结果的类型。
 */
public abstract class Marching extends AsyncTask<String,Integer,Boolean>{
    public enum MarchingStatus{
        PRE,ING,END
    }
    private String id;
    private MarchingStatus marchingStatus;
    private ConcurrentMap<String,MarchListener> marchListenerConcurrentMap = new ConcurrentHashMap<>();

    public Marching(String id){
        this.id = id;
    }

    public void registerMarchListener(String id,MarchListener marchListener){
        this.marchListenerConcurrentMap.put(id, marchListener);
    }
    public void unRegisterMarchListener(String id){
        this.marchListenerConcurrentMap.remove(id);
    }

    /**
     * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
     */
    @Override
    protected abstract Boolean doInBackground(String... strings);

    /**
     * 运行在UI线程中，在调用doInBackground()之前执行
     */
    @Override
    protected void onPreExecute() {
        this.marchingStatus = MarchingStatus.PRE;
    }

    /**
     * 运行在ui线程中，在doInBackground()执行完毕后执行
     */
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        this.marchingStatus = MarchingStatus.END;
        Set<Map.Entry<String, MarchListener>> entries = this.marchListenerConcurrentMap.entrySet();
        for (Map.Entry<String,MarchListener> entry : entries){
            MarchListener value = entry.getValue();
            // System.out.println("线程:" + this.id + " 正在更新:" + value.marchListenerId + " 当前进度:" + values[0]);
            value.onMarchEnd(id);
        }
    }

    /**
     * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        Set<Map.Entry<String, MarchListener>> entries = this.marchListenerConcurrentMap.entrySet();
        for (Map.Entry<String,MarchListener> entry : entries){
            MarchListener value = entry.getValue();
            value.onMarching(values[0]);
        }
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        this.marchingStatus = MarchingStatus.END;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public MarchingStatus getMarchingStatus() {
        return marchingStatus;
    }
}

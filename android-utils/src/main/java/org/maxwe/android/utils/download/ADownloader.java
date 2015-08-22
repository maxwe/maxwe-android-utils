package org.maxwe.android.utils.download;

/**
 * Created by Pengwei Ding on 2015-08-06 17:51.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 默认下载者实现比较器
 */
public abstract class ADownloader implements IDownloader,Comparable {
    protected String id;

    @Override
    public String getDownloaderId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ADownloader && this.id != null){
            if (this.id.equals(((ADownloader) obj).id)){
                return true;
            }
        }
        return super.equals(obj);
    }

}

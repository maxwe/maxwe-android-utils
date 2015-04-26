package org.maxwe.android.utils.views.sample.activities.download;

import android.content.Context;
import org.maxwe.android.utils.download.DownloadManager;
import org.maxwe.android.utils.download.DownloadService;

/**
 * Created by danhantao on 15/4/24.
 * Email:danhantao@yeah.net
 * Description for android-utils-parent
 */
public enum  GlobalDataCacheMemory {
  INSTANCE;
  public DownloadManager getDownloadManager(Context context){
    return DownloadService.getDownloadManager(context);
  }
}

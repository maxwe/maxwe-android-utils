package org.maxwe.android.utils.download;

import com.lidroid.xutils.db.annotation.Transient;
import com.lidroid.xutils.http.HttpHandler;

import java.io.File;

/**
 * 下载相关信息
 */
public class DownloadInfo {
  public DownloadInfo() {
  }

  private long id; //唯一标识
  private String downloadUrl; // 下载url
  private String fileName; // 文件名
  private String fileSavePath; // 文件存储路径
  private long progress; // 进度
  private long fileLength; // 文件长度
  private boolean autoResume;
  private boolean autoRename;
  private long downInfoId; // 下载的唯一id

  public long getDownInfoId() {
    return downInfoId;
  }

  public void setDownInfoId(long downInfoId) {
    this.downInfoId = downInfoId;
  }


  @Transient
  private HttpHandler<File> handler;
  private HttpHandler.State state;

  public HttpHandler.State getState() {
    return state;
  }

  public void setState(HttpHandler.State state) {
    this.state = state;
  }

  public long getProgress() {
    return progress;
  }

  public void setProgress(long progress) {
    this.progress = progress;
  }

  public long getFileLength() {
    return fileLength;
  }

  public void setFileLength(long fileLength) {
    this.fileLength = fileLength;
  }

  public boolean isAutoResume() {
    return autoResume;
  }

  public void setAutoResume(boolean autoResume) {
    this.autoResume = autoResume;
  }

  public boolean isAutoRename() {
    return autoRename;
  }

  public void setAutoRename(boolean autoRename) {
    this.autoRename = autoRename;
  }

  public HttpHandler<File> getHandler() {
    return handler;
  }

  public void setHandler(HttpHandler<File> handler) {
    this.handler = handler;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileSavePath() {
    return fileSavePath;
  }

  public void setFileSavePath(String fileSavePath) {
    this.fileSavePath = fileSavePath;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof DownloadInfo))
      return false;

    DownloadInfo that = (DownloadInfo) o;

    if (downInfoId != that.downInfoId)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }

  @Override
  public String toString() {
    return "contentId:"+getDownInfoId()+" id:" + getId() + " downloadUrl:" + getDownloadUrl() + " fileName:" + getFileName()
        + " fileSavePath:" + getFileSavePath() + " fileLength:" + getFileLength() + " progress:"
        + progress;
  }
}

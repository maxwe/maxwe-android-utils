package org.maxwe.android.utils.sample.download;

import org.maxwe.android.utils.download.DownloadManager;
import org.maxwe.android.utils.download.IDownloader;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2015-08-10 17:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 下载管理器测试
 */
public class DownTest {

    public void downTest(){
        Book book01 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0000.zip");
        Book book02 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0001.zip");
        Book book03 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0002.zip");
        Book book04 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0003.zip");
        Book book05 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0004.zip");
        Book book06 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0005.zip");
        Book book07 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0006.zip");
        Book book08 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0007.zip");
        Book book09 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0008.zip");
        Book book10 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0009.zip");
        Book book11 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0010.zip");
        Book book12 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0011.zip");
        Book book13 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0012.zip");
        Book book14 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0013.zip");
        Book book15 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0014.zip");
        Book book16 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0015.zip");
        Book book17 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0016.zip");
        Book book18 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0017.zip");
        Book book19 = new Book("http://123.57.205.126/mqh/uploads/enterprise/000/000/013/resources/000/000/554/%E9%A6%99%E7%86%8F%E5%8D%95%E6%96%B9%E7%B2%BE%E6%B2%B9%EF%BC%88%E4%BA%94%EF%BC%89.zip", "~/Downloads/tmp", "0018.zip");


        LinkedList<IDownloader> iDownloaders = new LinkedList<>();
        iDownloaders.add(book01);
        iDownloaders.add(book02);
        iDownloaders.add(book03);
        iDownloaders.add(book04);
        iDownloaders.add(book05);
        iDownloaders.add(book06);
        iDownloaders.add(book07);
        iDownloaders.add(book08);
        iDownloaders.add(book09);
        iDownloaders.add(book10);
        iDownloaders.add(book11);
        iDownloaders.add(book12);
        iDownloaders.add(book13);
        iDownloaders.add(book14);
        iDownloaders.add(book15);
        iDownloaders.add(book16);
        iDownloaders.add(book17);
        iDownloaders.add(book18);
        iDownloaders.add(book19);

        DownloadManager.getDownloadManager().addDownloader(iDownloaders);
    }

}

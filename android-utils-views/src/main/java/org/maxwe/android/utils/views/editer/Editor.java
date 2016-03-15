package org.maxwe.android.utils.views.editer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by Pengwei Ding on 2016-03-15 13:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Editor extends WebView implements View.OnLongClickListener{

    public Editor(Context context) {
        super(context);
        this.init();
    }

    public Editor(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public Editor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init(){
        this.setOnLongClickListener(this);
        this.getSettings().setDefaultTextEncodingName("utf-8");
        this.loadData("<!DOCTYPE html>\n" +
                "<html style=\"height: 100%\">\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body style=\"height: 100%\">\n" +
                "<textarea style=\"width: 100%;height: 100%;\">这是一行文字</textarea>\n" +
                "</body>\n" +
                "</html>", "text/html; charset=UTF-8", null);
    }

    @Override
    public boolean onLongClick(View view) {
        return true;
    }
}

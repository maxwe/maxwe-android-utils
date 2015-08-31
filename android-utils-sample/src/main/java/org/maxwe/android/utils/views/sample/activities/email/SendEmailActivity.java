package org.maxwe.android.utils.views.sample.activities.email;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.maxwe.android.utils.email.EmailSender;
import org.maxwe.android.utils.views.sample.R;

/**
 * Created by Pengwei Ding on 2015-08-31 17:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SendEmailActivity extends Activity implements View.OnClickListener{

    EditText et_act_email_text;
    Button bt_act_sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.act_email);

        this.et_act_email_text = (EditText) this.findViewById(R.id.et_act_email_text);
        this.bt_act_sender = (Button) this.findViewById(R.id.bt_act_sender);
        this.bt_act_sender.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EmailSender.defaultEmailSender(et_act_email_text.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

package org.maxwe.android.utils;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Pengwei Ding on 2015-08-31 14:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class EmailSender extends TestCase {

    @Test
    public void emailSender() throws Exception {
        org.maxwe.android.utils.email.EmailSender.defaultEmailSender("test");
    }

}

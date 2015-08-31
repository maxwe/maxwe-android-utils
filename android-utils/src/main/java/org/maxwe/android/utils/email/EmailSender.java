package org.maxwe.android.utils.email;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Pengwei Ding on 2015-08-31 13:43.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 指定邮箱账号发送邮件
 */
public class EmailSender {

    public static void main(String[] args) throws Exception{
        defaultEmailSender("hello world");
    }

    public static void defaultEmailSender(String message) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.sina.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect("smtp.sina.com", "yun_chang_yue@sina.com", "Retech654321");
        //4、创建邮件
        MimeMessage simpleMail = createSimpleMail(session, message);
        //5、发送邮件
        ts.sendMessage(simpleMail, simpleMail.getAllRecipients());
        ts.close();
    }

    private static MimeMessage createSimpleMail(Session session,String message) throws Exception {
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //指明邮件的发件人
        mimeMessage.setFrom(new InternetAddress("yun_chang_yue@sina.com"));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("app@retechcorp.com"));
        //邮件的标题
        mimeMessage.setSubject("云畅阅-Android客户端-意见反馈");
        //邮件的文本内容
        mimeMessage.setContent(message, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return mimeMessage;
    }

}

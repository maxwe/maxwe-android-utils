package org.maxwe.android.utils.email;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
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

    private static int port = 25;  //smtp协议使用的是25号端口
    private static String server = "smtp.sina.com"; // 发件人邮件服务器
    private static String user = "yun_chang_yue@sina.com";   // 使用者账号
    private static String password = "Retech654321"; //使用者密码
    private static String toEmail = "app@retechcorp.com";
    private static String title = "云畅阅-Android客户端-意见反馈";

    public static void defaultEmailSender(String message) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", server);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.auth", "true");
        Transport transport = null;
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage msg = new MimeMessage(session);

        transport = session.getTransport("smtp");
        transport.connect(server, user, password);    //建立与服务器连接
        msg.setSentDate(new Date());
        InternetAddress fromAddress = null;
        fromAddress = new InternetAddress(user);
        msg.setFrom(fromAddress);
        InternetAddress[] toAddress = new InternetAddress[1];
        toAddress[0] = new InternetAddress(toEmail);
        msg.setRecipients(Message.RecipientType.TO, toAddress);
        msg.setSubject(title, "UTF-8");            //设置邮件标题
        MimeMultipart multi = new MimeMultipart();   //代表整个邮件邮件
        BodyPart textBodyPart = new MimeBodyPart();  //设置正文对象
        textBodyPart.setText(message);                  //设置正文
        multi.addBodyPart(textBodyPart);             //添加正文到邮件
        msg.setContent(multi);                      //将整个邮件添加到message中
        msg.saveChanges();
        transport.sendMessage(msg, msg.getAllRecipients());  //发送邮件
        transport.close();
    }
}

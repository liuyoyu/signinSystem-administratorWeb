package com.blog.yongyu.demo.Utils;

import lombok.extern.log4j.Log4j2;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * 邮件工具类
 */
@Log4j2
public class EmailUtils {
//    private static final String account = "yoyu126@126.com";
//    private static final String pwd = "liu111";//授权密码
//    private static final String EAMIL_HOST = "smtp.126.com";
    private static final String account = "heling_fzu@163.com";
    private static final String pwd = "heling231231";//授权密码
    private static final String TITLE = "签到start邮箱验证";
    private static final String EAMIL_HOST = "smtp.163.com";
    private static final String EAMIL_PORT = 465 + "";
    private static final Integer CODE_LENGTH = 25;

    /**
     * 发送验证邮件
     *
     * @param receiver
     */
    public static String editEmail(String receiver){
        String code = getRandomCode();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String text = "<h1>亲爱的用户：</h1>" +
                "<h2>感谢您使用我们的签到Start平台邮箱验证功能</h2>" +
                "<p>您本次注册验证码为: " + code +
                "</p><small>若非本人操作，请忽略本条邮件。祝，生活愉快</small><br><br>"+sdf.format(new Date())+
                "（本邮件由系统自动发出，请勿回复）";
        try {
            EmailUtils.sendEmail(receiver, text);
        } catch (Exception e) {
            e.printStackTrace();
            code = "";
        } finally {
            return code;
        }
    }

    public static String getRandomCode() {
        String strcode = "abcdefghijklnmopqrstuvwxykABCDEFGHIJKLNMOPQRSTUVWXYZ0123456789";
        String code = "";
        Random rand = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code += strcode.charAt(rand.nextInt(strcode.length()));
        }
        return code;
    }

    /**
     * 发送邮件
     * @param receiver
     * @param code
     * @throws Exception
     */
    public static void sendEmail(String receiver, String code) throws Exception {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", EAMIL_HOST);
        properties.put("mail.smtp.port", EAMIL_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.connectiontiomeout", 3000);
//        properties.put("mail.smtp.timeout", 3000);
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, pwd);
            }
        });
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress(account));
        //抄送给自己，避免error:554DT:SPM
        message.addRecipient(Message.RecipientType.CC, new InternetAddress(account));
        //设置收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        //设置主题
        message.setSubject(TITLE);
        //设置内容
        message.setContent(code, "text/html;charset=UTF-8");
        //发送邮件
        Transport.send(message);
    }

}

package org.qydata.tools;

import org.apache.commons.mail.HtmlEmail;

/**
 * Created by jonhn on 2017/2/15.
 */
public class SendEmail {

    /**
     * 发送邮件
     * @param to 收件人邮箱地址
     * @param data 发送的内容
     */
    public static boolean sendMail(String to, String data) {
        try {
            HtmlEmail email = new HtmlEmail   ();

            email.setTLS(true);
            //发送主机服务器
            email.setHostName("smtp.mxhichina.com");
            //登陆邮件服务器的用户名和密码
            email.setAuthentication("member@qianyandata.com", "92f!x.8fce0e65");
            //发送人
            email.setFrom("member@qianyandata.com");
            //接收人
            email.addTo(to);
            //标题
            email.setSubject("上行消息");
            //编码
            email.setCharset("utf-8");
            //内容
            email.setTextMsg(data);
            //发送
            email.send();
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}



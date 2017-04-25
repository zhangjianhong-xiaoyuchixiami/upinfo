package org.qydata.controller;

import org.qydata.service.UpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jonhn on 2017/4/24.
 */
@Controller
public class UpInfoController {

    @Autowired private UpInfoService upInfoService;

    /**
     * 被动接受上行消息
     * @param msgid 上行短信的唯一编号
     * @param sp_code 上行短信的目标地址
     * @param src_mobile 发送上行短信的手机号
     * @param msg_content 上行短信内容的URLEncode编码
     * @param recv_time 网关接收到上行短信的时间
     */
    @RequestMapping("/up-info")
    public void receiveUpInfo(String msgid,String sp_code,String src_mobile,String msg_content,String recv_time){
        boolean flag =  upInfoService.insertUpInfo(msgid, sp_code, src_mobile, msg_content, recv_time);
    }

}

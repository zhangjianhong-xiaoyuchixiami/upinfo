package org.qydata.controller;

import org.qydata.service.UpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jonhn on 2017/4/24.
 */
@Controller
public class UpInfoController {

    @Autowired private UpInfoService upInfoService;

    /**
     * 被动接受上行消息
     * @param msg_id 上行短信的唯一编号
     * @param sp_code 上行短信的目标地址
     * @param src_mobile 发送上行短信的手机号
     * @param msg_content 上行短信内容的URLEncode编码
     * @param recv_time 网关接收到上行短信的时间
     */
    @RequestMapping("/passive/up-info")
    @ResponseBody
    public String passiveReceiveUpInfo(String msg_id,String sp_code,String src_mobile,String msg_content,String recv_time){
        boolean flag =  upInfoService.passiveReceiveUpInfo(msg_id, sp_code, src_mobile, msg_content, recv_time);
        if (flag){
            return "200";
        }
        return "500";
    }

    /**
     * 主动接受上行消息
     * @throws Exception
     */
    @RequestMapping("/active/up-info")
    @ResponseBody
    public  String activeReceiveUpInfo()throws Exception{
        boolean flag = upInfoService.activeReceiveUpInfo();
        if (flag){
            return "200";
        }
        return "500";
    }

}

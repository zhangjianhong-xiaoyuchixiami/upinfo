package org.qydata.mapper;

/**
 * Created by jonhn on 2017/4/24.
 */
public interface UpInfoMapper {

    /**
     * 被动接受上行消息
     * @param msgid 上行短信的唯一编号
     * @param sp_code 上行短信的目标地址
     * @param src_mobile 发送上行短信的手机号
     * @param msg_content 上行短信内容的URLEncode编码
     * @param recv_time 网关接收到上行短信的时间
     */
    public boolean insertUpInfo(String msgid,String sp_code,String src_mobile,String msg_content,String recv_time);


}

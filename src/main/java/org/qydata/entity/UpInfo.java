package org.qydata.entity;

import java.io.Serializable;

/**
 * Created by jonhn on 2017/5/24.
 */
public class UpInfo implements Serializable{

    private String msg_id;
    private String sp_code;
    private String src_mobile;
    private String msg_content;
    private String recv_time;

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getSp_code() {
        return sp_code;
    }

    public void setSp_code(String sp_code) {
        this.sp_code = sp_code;
    }

    public String getSrc_mobile() {
        return src_mobile;
    }

    public void setSrc_mobile(String src_mobile) {
        this.src_mobile = src_mobile;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getRecv_time() {
        return recv_time;
    }

    public void setRecv_time(String recv_time) {
        this.recv_time = recv_time;
    }
}

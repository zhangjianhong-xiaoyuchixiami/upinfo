package org.qydata.service.impl;

import org.qydata.mapper.UpInfoMapper;
import org.qydata.service.UpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by jonhn on 2017/4/24.
 */
@Service
public class UpInfoServiceImpl implements UpInfoService ,Serializable{

    @Autowired
    private UpInfoMapper upInfoMapper;

    @Override
    public boolean insertUpInfo(String msgid, String sp_code, String src_mobile, String msg_content, String recv_time) {
        return upInfoMapper.insertUpInfo(msgid, sp_code, src_mobile, msg_content, recv_time);
    }
}

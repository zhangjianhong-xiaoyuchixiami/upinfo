package org.qydata.mapper;

import org.qydata.entity.UpInfo;

/**
 * Created by jonhn on 2017/4/24.
 */
public interface UpInfoMapper {

    /**
     * 被动接受上行消息
     * @param upInfo
     */
    public boolean insertUpInfo(UpInfo upInfo);


}

package org.qydata.service.impl;

import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.qydata.entity.UpInfo;
import org.qydata.mapper.UpInfoMapper;
import org.qydata.service.UpInfoService;
import org.qydata.tools.SendEmail;
import org.qydata.tools.http.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonhn on 2017/4/24.
 */
@Service
public class UpInfoServiceImpl implements UpInfoService ,Serializable{

    @Autowired
    private UpInfoMapper upInfoMapper;

    @Override
    public boolean passiveReceiveUpInfo(String msg_id, String sp_code, String src_mobile, String msg_content, String recv_time) {
        try{
            System.out.println("执行写库操作");
            UpInfo upInfo = new UpInfo();
            upInfo.setMsg_id(msg_id);
            upInfo.setSp_code(sp_code);
            upInfo.setSrc_mobile(src_mobile);
            upInfo.setMsg_content(msg_content);
            upInfo.setRecv_time(recv_time);
            upInfoMapper.insertUpInfo(upInfo);
            System.out.println("执行写库操作成功");
            return true;
        }catch (Exception e){
            try {
                System.out.println("执行写文件操作");
                String dataFile = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "写入：msg_id=" + msg_id + ";sp_code=" + sp_code + ";src_mobile=" + src_mobile + ";msg_content=" + msg_content + ";recv_time=" + recv_time;
                File file = new File("/data/upinfo_log/upinfo.txt");
                //File file = new File("E:\\data\\upinfo_log\\upinfo.txt");
                //File file = new File("E:\\upinfo.txt");
                if(!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(dataFile + "\r\n");
                bw.close();
                System.out.println("执行写文件操作成功");
                e.printStackTrace();
                return true;
            } catch (IOException e1) {
                System.out.println("执行发邮件操作");
                String dataEmail = "msg_id=" + msg_id + ";sp_code=" + sp_code + ";src_mobile=" + src_mobile + ";msg_content=" + msg_content + ";recv_time=" + recv_time;
                SendEmail.sendMail("18945927283@163.com", dataEmail);
                System.out.println("执行发邮件操作成功");
                e1.printStackTrace();
                return true;
            }
        }
    }

    @Override
    public boolean activeReceiveUpInfo() throws Exception {
        //获取Token
        String uri = "http://43.243.130.33:8860/getToken";
        String cust_code = "300467";
        Map<String,Object> mapToken = new HashMap<>();
        mapToken.put("cust_code",cust_code);
        String token = HttpClientUtil.doPost(uri,new Gson().toJson(mapToken).toString());
        JSONObject jsonObject = JSONObject.fromObject(token);


        String token_id = jsonObject.getString("token_id");  //10
        String token_value = jsonObject.getString("token");  //20
        String password = "L4RVJGPK5D";
        String sign = DigestUtils.md5Hex(token_value+password);

        //查询
        String uriQuery = "http://43.243.130.33:8860/getMO";
        Map<String,Object> mapMessage = new HashMap<>();
        mapMessage.put("cust_code",cust_code);
        mapMessage.put("token_id",token_id);
        mapMessage.put("sign",sign);
        String upMessage = HttpClientUtil.doPost(uriQuery,new Gson().toJson(mapMessage).toString());

        JSONArray jsonArray = JSONArray.fromObject(upMessage);
        for (int j=0; j<jsonArray.size();j++){
            JSONObject myjsonObject = jsonArray.getJSONObject(j);
            UpInfo upInfo = (UpInfo) myjsonObject.toBean(jsonObject,UpInfo.class);
            System.out.println(upInfo.getMsg_id());
            System.out.println(upInfo.getSp_code());
            System.out.println(upInfo.getSrc_mobile());
            System.out.println(upInfo.getMsg_content());
            System.out.println(upInfo.getRecv_time());
            upInfoMapper.insertUpInfo(upInfo);
            return true;
        }
        return false;
    }
}

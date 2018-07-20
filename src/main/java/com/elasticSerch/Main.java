package com.elasticSerch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;


public class Main{

    public static void main(String[] args){

        try {

            //利用map的方式建立json数据
            Map<String, Object> json = new HashMap<String, Object>();
            json.put("user","kimchy02");
            json.put("postDate","2013-01-30");
            json.put("message","trying out Elasticsearch");


            //连接ES数据库，tcp方式，端口默认为9300
            byte[] addr = new byte[]{127,0,0,1};
            TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByAddress(addr), 9300));

            //建立索引
            //            GetResponse getResponse = client.prepareGet("twitter", "tweet").get();
            GetResponse getResponse = client.prepareGet("twitter", "tweet", "1").get();

            //插入一条新记录
            IndexResponse indexResponse = client.prepareIndex("twitter", "tweet", "1")
                    .setSource(json
                    )
                    .get();

            //删除id为1 的记录
            client.prepareDelete("twitter","tweet","1").get();
            String strTmp2 = getResponse.toString();
            System.out.println(strTmp2);
            getResponse = client.prepareGet("twitter", "tweet", "1").get();
            strTmp2 = getResponse.toString();
            System.out.println(strTmp2);

            //更新id为2的记录
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index("twitter").type("tweet").id("2").doc(json);
            client.update(updateRequest).get();

            //第二种更新方式
            //client.prepareUpdate("twitter", "tweet", "1").setDoc(json).get();

            //获取id为2的数据
            getResponse = client.prepareGet("twitter", "tweet", "2").get();
            strTmp2 = getResponse.toString();
            System.out.println(strTmp2);
            client.close();
        }
        catch(Exception e)
        {
            //
        }

    }


}

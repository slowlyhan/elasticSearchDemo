package com.elasticSerch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public class elasticSerchTest {

    private TransportClient client;
    @Before
    public void before(){

    }
    @Test
    public void CreateList() throws UnknownHostException {


        client = this.getClient();
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user","kimchy");
        json.put("postDate","2013-01-30");
        json.put("message","trying out Elasticsearch");
        IndexResponse response = client.prepareIndex("fendo", "fendo date")
                        .setSource(json)
                        .get();
        System.out.println(response.getResult());
    }
    public TransportClient getClient(){
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(
                            new InetSocketTransportAddress(InetAddress.getByName("localhost"),
                                    9300));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  client;
    }


}

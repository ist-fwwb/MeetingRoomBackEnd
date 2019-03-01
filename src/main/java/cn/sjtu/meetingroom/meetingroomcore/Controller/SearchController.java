package cn.sjtu.meetingroom.meetingroomcore.Controller;

import cn.sjtu.meetingroom.meetingroomcore.Domain.SearchResult;
import cn.sjtu.meetingroom.meetingroomcore.Util.Util;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/search")
public class SearchController {
    @GetMapping(name = "")
    public SearchResult search(@RequestParam(name = "request") String request){
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "Pzy19980525"));

        RestClient restClient = RestClient.builder(new HttpHost(Util.ElasticHost, 9200)) .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                })
                .build();
        JSONObject query = createJSONQuery(request);
        try {
            HttpEntity entity = new StringEntity(query.toString(),  ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest("GET", "/meetingroom/_search", Collections.<String, String>emptyMap(), entity);
            String requestRes = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(requestRes);
            JSONObject totalHints = jsonObject.getJSONObject("hits");
            return SearchResult.crete(totalHints);
        } catch (Exception e) {
            e.printStackTrace();
            return SearchResult.createNullSearchResult();
        }
    }

    JSONObject createJSONQuery(String request){
        JSONObject query = createJSONObject();
        JSONObject bool = createJSONObject();
        JSONObject should = createJSONObject();
        JSONArray conditions = new JSONArray();
        conditions.add(createMatchJSONObject("name", request));
        conditions.add(createMatchJSONObject("title", request));
        conditions.add(createMatchJSONObject("note", request));
        conditions.add(createMatchJSONObject("heading", request));
        conditions.add(createMatchJSONObject("description", request));
        conditions.add(createMatchJSONObject("location", request));
        should.put("should", conditions);
        bool.put("bool", should);
        query.put("query", bool);
        return query;
    }

    JSONObject createJSONObject(){
        return new JSONObject();
    }

    JSONObject createMatchJSONObject(String key, String value){
        JSONObject res = createJSONObject();
        JSONObject condition = createJSONObject();
        condition.put(key, value);
        res.put("match", condition);
        return res;
    }
}

package me.leoata.util;

import lombok.Getter;
import me.leoata.MCAuthPlugin;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

public class HTTPUtil {

    @Getter
    private static final String API_URL = "https://immense-sea-29739.herokuapp.com/";

    public static String httpGet(String path, String body) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGetWithEntity httpGet = new HttpGetWithEntity();
            if (body != null)
                httpGet.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
            httpGet.setURI(URI.create(API_URL + path));
            return processHttpClient(httpClient, httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param request is in the format "{\"key\": \"value\"}"
     * @return
     */
    public static String httpPost(String path, String request) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_URL + path);
            httpPost.setEntity(new StringEntity(request, ContentType.APPLICATION_JSON));
            return processHttpClient(httpClient, httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String processHttpClient(HttpClient httpClient, HttpUriRequest request) throws IOException {
        return httpClient.execute(request, httpResponse -> {
            int status = httpResponse.getStatusLine().getStatusCode();
            MCAuthPlugin.getInstance().getLogger().info("Status: " + status);
            if (status < 200 || status >= 300) {
                return status + "";
            }
            HttpEntity entity = httpResponse.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        });
    }
}

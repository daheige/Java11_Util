package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class HttpUtil {

    /**
     * 创建连接的超时时间
     */
    static final long connectTimeout = 5000;

    /**
     * 请求的超时时间
     */
    static final long readTimeout = 5000;

    /**
     * Http请求类型
     */
    static final HttpClient.Version version = HttpClient.Version.HTTP_2;

    /**
     * HttpClient实体
     */
    static final HttpClient httpClient = newHttpClient();


    /**
     * 获取HttpClient实体
     */
    static HttpClient newHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(connectTimeout))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .version(version)
                .build();
    }


    /**
     * 创建Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 创建Get请求的Builder
     * @param url
     * @return
     */
    public static Builder getUrl(String url) {
        return newBuilder().getUrl(url);
    }

    /**
     * 创建Post请求的Builder
     * @param url
     * @param body
     * @return
     */
    public static Builder postUrl(String url, String body) {
        return newBuilder().postUrl(url, body);

    }

    /**
     * 自定义的HttpRequest.Builder类
     */
    public static class Builder {

        /**
         * 内部封装的HttpRequest.Builder
         */
        static HttpRequest.Builder builder = HttpRequest.newBuilder().timeout(Duration.ofMillis(readTimeout));

        /**
         * 设置请求路径
         * @param url
         * @return
         */
        Builder getUrl(String url) {
            builder.uri(URI.create(url))
                    .GET();
            return this;
        }

        /**
         * 设置请求路径，并设置POST请求的Body
         * @param url
         * @param body
         * @return
         */
        Builder postUrl(String url, String body) {
            builder.uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(body));
            return this;
        }

        /**
         * 设置请求的Header
         * @param header
         * @return
         */
        public Builder header(Header header) {
            if (header == null) return this;
            header.getHeader().forEach((k, v) -> builder.header(k, v));
            return this;
        }

        /**
         * 构建完成，返回HttpRequest
         * @return
         */
        public HttpRequest build() {
            return builder.build();
        }

        /**
         * 同步请求，返回HttpResponse。  如果需要获取值，则调用httpResponse.body()
         * @return
         * @throws IOException
         * @throws InterruptedException
         */
        public HttpResponse sendSync() throws IOException, InterruptedException {
            return httpClient.send(build(), HttpResponse.BodyHandlers.ofString());
        }

        /**
         * 异步请求， 返回CompletableFuture。  如果需要获取值，则调用completableFuture.get()
         * @return
         */
        public CompletableFuture<String> sendAsync() {
            return httpClient.sendAsync(build(), HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body);
        }


    }


    /**
     * 创建Header.  默认content-type为json格式
     */
    public static Header newHeader() {
        return new Header(application_Json);
    }

    /**
     * 创建Header, 支持自定义contentType
     * @param contentType
     * @return
     */
    public static Header newHeader(String contentType) {
        return new Header(contentType);
    }

    /**
     * JSON类型的Application
     */
    public static final String application_Json = "application/json";

    /**
     * 表单类型的Application
     */
    public static final String application_From = "application/x-www-form-urlencoded";

    /**
     * 自定义的HttpRequest.Header类
     */
    public static class Header {

        /**
         * 自定义的header集
         */
        static Map<String, String> header = new HashMap<>();

        /**
         * 构建该类需要指定Content-Type
         */
        Header(String contentType) {
            header.put("Content-Type", contentType);
        }

        /**
         * 设置Cookie值
         * @param cookie
         * @return
         */
        public Header cookie(String cookie) {
            header.put("Cookie", cookie);
            return this;
        }

        /**
         * 设置contentType值
         * @param contentType
         * @return
         */
        public Header contentType(String contentType) {
            header.put("Content-Type", contentType);
            return this;
        }

        /**
         * 设置header值， 可以自定义Key,Value
         * @param key
         * @param value
         * @return
         */
        public Header put(String key, String value) {
            header.put(key, value);
            return this;
        }

        /**
         * 获取Header中的header对象
         * @return
         */
        public Map<String, String> getHeader() {
            return header;
        }
    }






}

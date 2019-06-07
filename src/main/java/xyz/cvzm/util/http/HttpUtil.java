package xyz.cvzm.util.http;

import xyz.cvzm.util.http.enums.ContentType;
import xyz.cvzm.util.http.enums.Method;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * Http Util
 *  减小封装 HttpClient, HttpRequest, Header 的繁琐度
 *
 * @author C.v.
 */
public class HttpUtil {

    /**
     * 创建连接的超时时间 单位(ms)
     */
    private static final long CONNECT_TIMEOUT = 1000 * 5;

    /**
     * 请求的超时时间 单位(ms)
     */
    private static final long READ_TIMEOUT = 1000 * 5;

    /**
     * Http请求类型
     *  默认Http/2，不兼容时会自动降级
     */
    private static final HttpClient.Version VERSION = HttpClient.Version.HTTP_2;

    /**
     * 内置 HttpClient 实体
     *  公用的目的是为了减少 HttpClient 占用的内存，目前没发现性能影响。
     *  如遇瓶颈。可考虑上 HttpClient 池
     */
    private static final HttpClient HTTP_CLIENT;

    static {
        HTTP_CLIENT = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(CONNECT_TIMEOUT))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .version(VERSION)
                .build();
    }


    /**
     * 创建 Builder
     * @return Builder
     */
    private static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 创建自定义请求方法的Builder
     * @param url url
     * @param method 自定义 method 或者 {@link Method}
     * @return builder
     */
    public static Builder methodUrl(String url, String method) {
        return newBuilder().url(url).method(method);
    }

    /**
     * 创建 GET 请求的Builder
     * @param url url
     * @return builder
     */
    public static Builder getUrl(String url) {
        return methodUrl(url, Method.GET.getMethod());
    }

    /**
     * 创建 POST 请求的Builder
     * @param url url
     * @return builder
     */
    public static Builder postUrl(String url) {
        return methodUrl(url, Method.POST.getMethod());

    }

    /**
     * 创建 PUT 请求的Builder
     * @param url url
     * @return builder
     */
    public static Builder putUrl(String url) {
        return methodUrl(url, Method.PUT.getMethod());
    }

    /**
     * 创建 DELETE 请求的Builder
     * @param url url
     * @return builder
     */
    public static Builder deleteUrl(String url) {
        return methodUrl(url, Method.DELETE.getMethod());
    }


    /**
     * 自定义的HttpRequest.Builder类
     */
    public static class Builder {

        /**
         * 内置 HttpRequest.Builder
         */
        private static HttpRequest.Builder builder = HttpRequest.newBuilder()
                                                .timeout(Duration.ofMillis(READ_TIMEOUT));

        /**
         * 请求header中 Content-Type 的 Key
         */
        private static final String CONTENT_TYPE = "Content-Type";

        /**
         * 请求header中 Cookie 的 Key
         */
        private static final String COOKIE = "Cookie";

        /**
         * 内置 请求方法， 默认GET请求
         */
        private String method = Method.GET.getMethod();

        /**
         * 内置 请求body
         */
        private HttpRequest.BodyPublisher requestBodyPublisher = HttpRequest.BodyPublishers.noBody();

        /**
         * 设置超时时间
         *  由于builder是公共的，timeout设置后全局生效
         * @param timeout 超时时间 (ms)
         * @return builder
         */
        public Builder timeout(long timeout) {
            builder.timeout(Duration.ofMillis(timeout));
            return this;
        }

        /**
         * 设置是否再请求前先 检查服务器是否可以基于请求的首部处理请求
         * @param enable true-检查 false-不检查
         * @return builder
         */
        public Builder expectContinue(boolean enable) {
            builder.expectContinue(enable);
            return this;
        }

        /**
         * 设置请求路径
         * @param url url
         * @return builder
         */
        private Builder url(String url) {
            builder.uri(URI.create(url));
            return this;
        }

        /**
         * 设置 请求方法
         * @param method 请求方法
         * @return builder
         */
        private Builder method(String method) {
            this.method = method;
            return this;
        }

        /**
         * 设置 请求bodyPublisher
         * @param bodyPublisher body
         * @return builder
         */
        public Builder requestBodyPublisher(HttpRequest.BodyPublisher bodyPublisher) {
            this.requestBodyPublisher = bodyPublisher;
            return this;
        }

        /**
         * 设置 request String body
         * @param body String body
         * @return builder
         */
        public Builder requestStringBody(String body) {
            if (body != null)
                this.requestBodyPublisher = HttpRequest.BodyPublishers.ofString(body);
            return this;
        }

        /**
         * 设置请求的Header
         * @return builder
         */
        public Builder header(String name, String value) {
            builder.header(name, value);
            return this;
        }

        /**
         * 设置headers
         * @param headers headers
         * @return builder
         */
        public Builder headers(String... headers) {
            builder.headers(headers);
            return this;
        }

        /**
         * 设置header Cookie值
         * @param cookie cookie
         * @return builder
         */
        public Builder cookie(String cookie) {
            builder.header(COOKIE, cookie);
            return this;
        }

        /**
         * 设置header contentType值
         * @param contentType Content-Type
         * @return builder
         */
        public Builder contentType(String contentType) {
            builder.header(CONTENT_TYPE, contentType);
            return this;
        }

        /**
         * 设置header ContentType枚举值
         * @param contentType {@link ContentType}
         * @return builder
         */
        public Builder contentType(ContentType contentType) {
            return contentType(contentType.getType());
        }

        /**
         * 完成构建
         * @return HttpRequest
         */
        private HttpRequest build() {
            return builder
                    .method(this.method, this.requestBodyPublisher)
                    .build();
        }


        /**
         * 内置 响应bodyHandler
         */
        private HttpResponse.BodyHandler<String> responseBodyHandler = HttpResponse.BodyHandlers.ofString();

        /**
         * 设置 响应bodyHandler
         * @param responseBodyHandler responseBodyHandler
         * @return builder
         */
        public Builder responseBodyHandler(HttpResponse.BodyHandler<String> responseBodyHandler) {
            this.responseBodyHandler = responseBodyHandler;
            return this;
        }

        /**
         * 同步请求
         * @return HttpResponse
         * @throws IOException IOException
         * @throws InterruptedException InterruptedException
         */
        public HttpResponse send() throws IOException, InterruptedException {
            return HTTP_CLIENT
                    .send(build(), responseBodyHandler);
        }

        /**
         * 异步请求.  通过completableFuture.get()即可获取HttpResponse
         * @return CompletableFuture
         */
        public CompletableFuture<HttpResponse<String>> sendAsync() {
            return HTTP_CLIENT
                    .sendAsync(build(), responseBodyHandler);
        }

    }



}

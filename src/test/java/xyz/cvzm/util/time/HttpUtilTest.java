package xyz.cvzm.util.time;

import org.junit.Test;
import xyz.cvzm.util.http.HttpUtil;
import xyz.cvzm.util.http.enums.ContentType;
import xyz.cvzm.util.http.enums.Method;



public class HttpUtilTest {

    private String getUrl = "http://yunlsp.com/";

    /**
     * get请求(同步)
     */
    @Test
    public void getSync() throws Exception {
        var response = HttpUtil.getUrl(getUrl).send();
        assert response.statusCode() == 200 : "get 同步请求失败";
    }

    /**
     * get请求(异步), 在调用get()方法前，线程不会阻塞
     */
    @Test
    public void getAsync() throws Exception {
        var response = HttpUtil.getUrl(getUrl)
                .sendAsync()
                .get();
        assert response.statusCode() == 200 : "get 异步请求失败";

    }

    /**
     * get请求(异步)  设置cookie
     */
    @Test
    public void get3() throws Exception {
        var cookie = "cookie";

        var response = HttpUtil.getUrl(getUrl).cookie(cookie).sendAsync().get();

        var optional = response.request().headers().firstValue("Cookie");

        assert optional.isPresent() : "设置cookie失败";
        assert optional.get().equals(cookie) : "cookie不一致";
    }

    /**
     * get请求(异步)  设置自定义herder
     */
    @Test
    public void get4() throws Exception {
        var name = "name";
        var value = "value";

        var response = HttpUtil.getUrl(getUrl).header(name, value).sendAsync().get();

        var optional = response.request().headers().firstValue(name);

        assert optional.isPresent(): "设置herder失败";
        assert optional.get().equals(value): "herder不匹配";
    }

    /**
     * get请求(异步)  设置Content-Type
     */
    @Test
    public void get5() throws Exception {

        var response = HttpUtil.getUrl(getUrl).contentType(ContentType.MULTIPART).sendAsync().get();

        var optional = response.request().headers().firstValue("Content-Type");

        assert optional.isPresent(): "设置 Content-Type 失败";
        assert optional.get().equals(ContentType.MULTIPART.getType()): "Content-Type 不匹配";
    }

    /**
     * get请求(异步) 设置请求超时时间
     */
    @Test
    public void get6() throws Exception {
//        try {
//            HttpUtil.getUrl(getUrl).timeout(1).sendAsync().get();
//            assert true : "设置timeout失败";
//        } catch (ExecutionException e) {
//            // success
//        }
    }


    /**
     * post请求(异步)
     */
    @Test
    public void post1() throws Exception {
        var response = HttpUtil.postUrl(getUrl).sendAsync().get();

        assert Method.POST.getMethod().equals(response.request().method()) : "post 请求方式设置失败";
    }

    /**
     * post请求(异步) 设置参数
     */
    @Test
    public void post2() throws Exception {
        var body = "{\"a\":11}";
        var response = HttpUtil.postUrl(getUrl).requestStringBody(body).sendAsync().get();
        assert response.request().bodyPublisher().orElseThrow().contentLength() == body.length() : "post 请求参数设置失败";
    }

    /**
     * put请求
     */
    @Test
    public void put() throws Exception {
        var response = HttpUtil.putUrl(getUrl).sendAsync().get();

        assert Method.PUT.getMethod().equals(response.request().method()) : "put 请求方式设置失败";
    }

    /**
     * delete请求
     */
    @Test
    public void delete() throws Exception {
        var response = HttpUtil.deleteUrl(getUrl).send();

        assert Method.DELETE.getMethod().equals(response.request().method()) : "delete 请求方式设置失败";
    }

}

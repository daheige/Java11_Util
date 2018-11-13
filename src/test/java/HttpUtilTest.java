import org.junit.Test;
import util.HttpUtil;

public class HttpUtilTest {


    /**
     * get请求(同步)
     */
    @Test
    public void get1() throws Exception{
        var get = HttpUtil.getUrl("https://www.baidu.com")
                .sendSync();
        System.out.println(get.body());
    }

    /**
     * get请求(异步), 在调用get()方法前，线程不会阻塞
     */
    @Test
    public void get2() throws Exception{
        var get = HttpUtil.getUrl("https://www.baidu.com")
                .sendAsync();
        System.out.println(get.get());
    }

    /**
     * get请求(异步)  设置cookie
     */
    @Test
    public void get3() throws Exception{
        var get = HttpUtil.getUrl("https://www.baidu.com?a=1")
                .header(HttpUtil.newHeader()
                        .cookie("cookie"))
                .sendAsync();
        System.out.println(get.get());
    }

    /**
     * get请求(异步)  设置自定义herder
     */
    @Test
    public void get4() throws Exception{
        var get = HttpUtil.getUrl("https://www.baidu.com")
                .header(HttpUtil.newHeader()
                        .put("key", "value"))
                .sendAsync();
        System.out.println(get.get());
    }


    /**
     * post请求(异步)  JSON提交
     */
    @Test
    public void post1() throws Exception{
        var post = HttpUtil.postUrl("url", "{\"a\":1}")
//                .header(HttpUtil.newHeader() //和下行效果一样， contentType默认为JSON类型
                .header(HttpUtil.newHeader(HttpUtil.application_Json))
                .sendAsync();
        System.out.println(post.get());
    }

    /**
     * post请求(异步)  表单提交
     */
    @Test
    public void post2() throws Exception{
        var post = HttpUtil.postUrl("url", "a=1&b=2")
                .header(HttpUtil.newHeader(HttpUtil.application_From))
                .sendAsync();
        System.out.println(post.get());
    }


}

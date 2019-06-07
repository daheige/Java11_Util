package xyz.cvzm.util.date;

import org.junit.Test;
import xyz.cvzm.util.date.DateUtil;

public class DateUtilTest {


    /**
     * 日期转Date
     */
    @Test
    public void test1(){
        var dateString = "2018-01-01";
        var format = "yyyy-MM-dd";
        var date = DateUtil.parseDate(dateString, format);
        assert dateString.equals(DateUtil.parseString(date, format)) : "转换失败";
    }

    /**
     * 获取指定年月日的开始时间
     */
    @Test
    public void test3(){
        var date = DateUtil.getDayFirstDay(2018, 1,1);

        assert "2018-01-01".equals(DateUtil.parseString(date, "yyyy-MM-dd")) : "获取失败";
    }

    /**
     * 将时间戳或者Date的时分秒归零
     */
    @Test
    public void test4(){
        var dateString = "2018-01-01 11:11:11";
        var format = "yyyy-MM-dd hh:mm:ss";
        var date = DateUtil.parseDate(dateString, format);

        var compressDate = DateUtil.compressTime(date);

        assert "2018-01-01".equals(DateUtil.parseString(compressDate, "yyyy-MM-dd")) : "处理失败";
    }

    /**
     * 获取指定天前或者天后的开始日期
     */
    @Test
    public void test5() {
        var dateString = "2018-01-01";
        var format = "yyyy-MM-dd";
        var date = DateUtil.parseDate(dateString, format);

        var addDate = DateUtil.getDateByAddDay(date, 1);
        assert "2018-01-02".equals(DateUtil.parseString(addDate, format)) : "增加天数失败";

    }


}

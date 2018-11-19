import org.junit.Test;
import util.DateUtil;

import java.util.Date;

public class DateUtilTest {


    /**
     * 日期转Date
     */
    @Test
    public void test1(){
        //日期与日期需要匹配，否则会返回null
        Date date = DateUtil.parseDate("2018-1-1", "yyyy-MM-dd");
        System.out.println(date);

    }

    /**
     * 时间戳或Date转日期
     */
    @Test
    public void test2(){
        System.out.println(DateUtil.parseString(new Date()));
        System.out.println(DateUtil.parseString(new Date().getTime()));
        System.out.println(DateUtil.parseString(new Date().getTime() / 1000));
        System.out.println(DateUtil.parseString(new Date(), "yyyy-MM-dd"));
    }

    /**
     * 获取指定年月日的开始时间
     */
    @Test
    public void test3(){
        Date date = DateUtil.getDayFirstDay(2018, 1,1);
        System.out.println(date);
        System.out.println(DateUtil.parseString(date));
    }

    /**
     * 将时间戳或者Date的时分秒归零
     */
    @Test
    public void test4(){
        Date date = DateUtil.compressTime(new Date());
        System.out.println(date);
        System.out.println(DateUtil.parseString(date));

        Date date1 = DateUtil.compressTime(new Date().getTime());
        System.out.println(DateUtil.parseString(date1));
    }

    /**
     * 获取指定天前或者天后的开始日期
     */
    @Test
    public void test5() {
        Date date = DateUtil.getDateByAddDay(new Date(), 1);
        System.out.println(DateUtil.parseString(date));

        Date date1 = DateUtil.getDateByAddDay(new Date(), -1);
        System.out.println(DateUtil.parseString(date1));

        Date date2 = DateUtil.getDateByAddDay(new Date(), -1, -1, -1);
        System.out.println(DateUtil.parseString(date2));

    }


}

package com.tth.service.util.common;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: tth
 * Date: 2022/11/18
 * Time: 10:59
 * Description: 注释
 */
public class MathUtil {
    /**
     * 提供精確的加法運算
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供了精確的減法運算
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供了精確的乘法運算
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供了(相對)精確的除法運算，當發生除不儘的情況時，精確到
     * 小數點以後１10位
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, 5);
    }

    /**
     * 提供了(相對)精確的除法運算，當發生除不儘的情況時，由scale參數指定
     * 精度，以後的數字四捨五入
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供了精確的小數位四捨五入處理
     */

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }


    /**
     * 单精度转换为双精度
     *
     * @param f
     * @return
     */
    public static double floToDou(Float f) {
        BigDecimal b = new BigDecimal(String.valueOf(f));
        double doubleValue = b.doubleValue();
        return doubleValue;
    }


    /**
     * int转为double
     *
     * @param f
     * @return
     */
    public static double intToDou(Integer f) {
        BigDecimal b = new BigDecimal(String.valueOf(f));
        double doubleValue = b.doubleValue();
        return doubleValue;
    }


    /**
     * int转为float
     *
     * @param i
     * @return
     */
    public static Float intToFlo(Integer i) {
        String format = new BigDecimal(String.valueOf(i)).toString();

        float rr = Float.valueOf(format);
        return rr;
    }


    public static Integer RandomWithFour() {
        return new Random().nextInt(9999);
    }

    public static String RandomWithFourString() {
        return String.format("%04d", new Random().nextInt(9999));
    }

    public static void main(String[] args) {
        Long time1 = System.currentTimeMillis();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long time2 = System.currentTimeMillis();
        System.out.println("时间差：" + (time2 - time1) / 1000);

        //时间差
//        String time1 = DateUtil.formatSecondByTimestamp(System.currentTimeMillis());
//        System.out.println("time1 = " + time1);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        String time2 = DateUtil.formatSecondByTimestamp(System.currentTimeMillis());
//        System.out.println("time2 = " + time2);
//        Integer second = new DateUtil().dateDifferentToSecond(time1, time2);
//        System.out.println("second = " + second);

//        ConcurrentMapCacheUtil cacheUtil = new ConcurrentMapCacheUtil();
//        cacheUtil.set("tth", "hello", 2000);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        cacheUtil.set("tth", "world", 2000);
//        cacheUtil.set("tth2", "world2", 2000);
//        Object tth = cacheUtil.get("tth");
//        System.out.println("judge : "+ ObjectUtils.isEmpty(tth));
//        ConcurrentMap<String, CacheObject> all = cacheUtil.getAll();
//        all.keySet().stream().forEach(System.out::println);
//        all.values().stream().forEach(val-> System.out.println(val.getValue()));

//        String st = "20210708/1.jpg";
//        String encode = Base64Utils.encode(st);
//        System.out.println("encode = " + encode);

//        String msg = "0_御2行业进阶版_绝缘子";
//        System.out.println(msg.substring(0,msg.indexOf("_")));
//        System.out.println(msg.substring(msg.indexOf("_")+1,msg.lastIndexOf("_")));
//        System.out.println(msg.substring(msg.lastIndexOf("_")+1));

//        Long ts1 = System.currentTimeMillis();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Long ts2 = System.currentTimeMillis();
//        System.out.println("时间差: "+(ts2-ts1)/(1000));
//
//
//        System.out.println(intToDou(1));

//        String msg = "您的注册码为%s,谢谢注册!";
//        System.out.println("Math.random得到小数");
//        System.out.println(Math.round((Math.random() + 1) * 1000));
//        System.out.println("Random");
//        System.out.println(new Random().nextInt(9999));
//        System.out.println("字符串前面补0的话就这样String.format");
//        System.out.println(String.format("%04d", new Random().nextInt(9999)));
//        System.out.println(add(1.2321231, 3.7865765));
//        System.out.println(sub(6.4523423, 1.2321231));
//        System.out.println(mul(6.4523423, 3.7865765));
//        System.out.println(div(6.4523423, 3.7865765, 5));
//        System.out.println(round(3.7865765, 5));
//
//
//        float f = 127.1f;
//        BigDecimal b = new BigDecimal(String.valueOf(f));
//        double d = b.doubleValue();
//        double toDou = floToDou(f);
//
//        System.out.println("转换: "+d);
//        System.out.println("方法转换: "+toDou);
//
//
//        System.out.println(intToFlo(1));
    }
}


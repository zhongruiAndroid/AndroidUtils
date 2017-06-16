package com.github.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/10/19.
 */
public class StringUtils {
    /**
     * 列表有100条数据，第一条数据就显示 001
     * @param dataCount 数据总数
     * @param position  当前数据下标
     * @return
     */
    public static String getStringLength(int dataCount, int position) {
        int countLength = String.valueOf(dataCount).length();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < countLength - String.valueOf(position + 1).length(); i++) {
            stringBuffer.append("0");
        }
        return stringBuffer.toString();
    }
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }
    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }




    private static final Pattern REG_UNICODE = Pattern.compile("[0-9A-Fa-f]{4}");
    private static final Pattern EN_CODE = Pattern.compile("[A-Za-z]{4}");
    public static String unicodeToString(String str) {
        StringBuilder sb = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c1 = str.charAt(i);
            if (c1 == '\\' && i < len - 1) {
                char c2 = str.charAt(++i);
                if (c2 == 'u' && i <= len - 5) {
                    String tmp = str.substring(i + 1, i + 5);
                    Matcher matcher = REG_UNICODE.matcher(tmp);
                    if (matcher.find()) {
                        sb.append((char) Integer.parseInt(tmp, 16));
                        i = i + 4;
                    } else {
                        sb.append(c1).append(c2);
                    }
                } else {
                    sb.append(c1).append(c2);
                }
            } else {
                sb.append(c1);
            }
        }
        return sb.toString();
    }

    /**
     * Convert the whole String object.
     *
     * @param str
     * @return
     */
    public static String stringToUnicode(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String tmpStr = Integer.toHexString(str.charAt(i));
            if (tmpStr.length() < 4) {
                sb.append("\\u00");
            } else {
                sb.append("\\u");
            }
            sb.append(tmpStr);
        }
        return sb.toString();
    }

    /**
     * Just convert Chinese String
     *
     * @param str
     * @return
     */
    public static String chinese2Unicode(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String tmpStr = Integer.toHexString(str.charAt(i));

            if (tmpStr.length() < 4) {
                sb.append(str.charAt(i));
            } else {
                sb.append("\\u");
                sb.append(tmpStr);
            }
        }
        return sb.toString();
    }


    /**
     *判断字符串属于数字还是字母
     * @param txt
     * @return 0数字   1字母   2汉字  -1混合  -2为null -3为空字符串
     */
    public static int getStringType(String txt){
        if(txt==null){
            return -2;
        }else if(txt.trim().length()==0){
            return -3;
        }
        txt=txt.trim();
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(txt);
        if(m.matches() ){
            return 0;
        }
        p=Pattern.compile("[a-zA-Z]*");
        m=p.matcher(txt);
        if(m.matches()){
            return 1;
        }
        p=Pattern.compile("[\u4e00-\u9fa5]*");
        m=p.matcher(txt);
        if(m.matches()){
            return 2;
        }
        return -1;
    }
    public static void main(String[]args){
        System.out.println(getStringType("阿"));
        System.out.println("S12".toUpperCase());
        String a="啊Sa1sd";
        System.out.println("==" + a);
        a.toLowerCase();
        System.out.println("==" + a);
        System.out.println("啊Sa1sd".toLowerCase());


        BigDecimal deSource = new BigDecimal(2.52);


        BigDecimal bigDecimal = deSource.setScale(1, BigDecimal.ROUND_HALF_UP);
        System.out.println(bigDecimal+"");

    }

    /**
     * 保留小数点后几位，不四舍五入
     * @param d
     */
    public static double keepDecimal(double d,int num){
        String format="#0";
        for (int i = 0; i < num; i++) {
            if(i==0){
                format=format+".#";
            }else{
                format=format+"#";
            }
        }
        DecimalFormat formater = new DecimalFormat(format);
        formater.setRoundingMode(RoundingMode.FLOOR);
        double result = Double.parseDouble(formater.format(d));
        return  result;
    }
    public static double keepDecimal(double d){
        return keepDecimal(d,2);
    }

    /**
     *
     * @param d
     * @param num 小数点后几位
     * @return
     */
    public static String round(double d,int num){
        String result = String.format("%."+num+"f", d);
        return result;
    }
    public static String round(double d){
        return round(d,1);
    }
    public static BigDecimal roundForBigDecimal(double d,int num){
        return new BigDecimal(d).setScale(num, BigDecimal.ROUND_HALF_UP);
    }
    public static BigDecimal roundForBigDecimal(double d){
        return roundForBigDecimal(d,1);
    }

    /**
     *
     * @param str 原字符串
     * @param modelStr 需要计算下标的字符
     * @param count 计算出现第几次的下标
     * @return
     */
    public static int getFromIndex(String str, String modelStr, Integer count) {
        //对子字符串进行匹配
        Matcher slashMatcher = Pattern.compile(modelStr).matcher(str);
        int index = 0;
        //matcher.find();尝试查找与该模式匹配的输入序列的下一个子序列
        while(slashMatcher.find()) {
            index++;
            //当modelStr字符第count次出现的位置
            if(index == count){
                break;
            }
        }
        //matcher.start();返回以前匹配的初始索引。
        return slashMatcher.start();
    }
}

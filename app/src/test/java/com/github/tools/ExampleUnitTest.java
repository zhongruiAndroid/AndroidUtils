package com.github.tools;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
       /* String s = "清山";
        byte[] b = s.getBytes("gbk");//编码
        String sa = new String(b, "utf-8");//解码:用什么字符集编码就用什么字符集解码
        System.out.println(sa);*/

        String str = "清山清山";
        byte[]  ab = str.getBytes("utf-8");//编码
        String asa = new String(ab, "gbk");//解码
        System.err.println(asa);
    }

    @Test
    public void a() throws Exception {
        String str="中文";
        System.out.println("======"+str.getBytes());
        System.out.println("======"+str.getBytes("utf-8"));
        System.out.println("======"+"中".getBytes("utf-8"));
        System.out.println("======"+"文".getBytes("utf-8"));
        System.out.println("======"+str);


    }
}
package com.haitaoit.chengquan;

import com.github.androidtools.AES;

import org.junit.Test;

import java.util.Calendar;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        /*String asf = AES.decode("7412ECF3CA29F73517D58FCDB867841BD7B8D06D182FD06BA9CCD50A3F1FDE78FB24F5F5123B836793470676B45B64D4");
        String asf2 = AES.encode("");
        System.out.println(asf);
        System.out.println(asf2);*/
        System.out.println(AES.encode("hai-tao"));
        String aa = AES.decode("C7DF9CB823A610988EFA171E2517DB84");
        System.out.println(aa);
        Calendar calendar=Calendar.getInstance();
        calendar.set(2017,6,20);
        long timeInMillis = calendar.getTimeInMillis();

        Calendar calendar1=Calendar.getInstance();
        long timeInMillis1 = calendar1.getTimeInMillis();

        System.out.println(timeInMillis-timeInMillis1);
        System.out.println(getTime());

    }
    public boolean getTime(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(2017,6,20);
        long timeInMillis = calendar.getTimeInMillis();

        Calendar calendar1=Calendar.getInstance();
        long timeInMillis1 = calendar1.getTimeInMillis();
        long l = timeInMillis - timeInMillis1;
        return l<0;
    }
}
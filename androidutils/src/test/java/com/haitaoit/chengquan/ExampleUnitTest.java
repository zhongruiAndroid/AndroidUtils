package com.haitaoit.chengquan;

import com.github.androidtools.AES;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String asf = AES.decode("7412ECF3CA29F73517D58FCDB867841BD7B8D06D182FD06BA9CCD50A3F1FDE78FB24F5F5123B836793470676B45B64D4");
        String asf2 = AES.encode("");
        System.out.println(asf);
        System.out.println(asf2);

    }
}
package com.github.tools;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/11/16.
 */
public class ColorUtils {
    /**
     * 根据string获取color
     * @param colorStr #ffffff
     * @return
     */
    public static int getColor(String colorStr){
        try {
            return Color.parseColor(colorStr);
        }catch (Exception e){
            return Color.parseColor("#ffffff");
        }
    }
}

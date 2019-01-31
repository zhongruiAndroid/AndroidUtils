package com.github.androidtools;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.InputStream;

public class BitmapUtils {
    public static Bitmap createBitmapFromView(View view, int leftCrop, int topCrop, int rightCrop, int bottomCrop){
        Bitmap originBitmap = createBitmapFromView(view,1);
        if (originBitmap == null) {
            return null;
        }
        Bitmap cutBitmap=createBitmapSafely(view.getWidth()-leftCrop-rightCrop,view.getHeight()-topCrop-bottomCrop,Bitmap.Config.ARGB_8888,1);
        if(cutBitmap!=null){
            Canvas canvas = new Canvas(cutBitmap);
            canvas.drawColor(Color.WHITE);
            Rect originRect=new Rect(leftCrop,topCrop,view.getWidth()-rightCrop,view.getHeight()-bottomCrop);
            RectF cutRect=new RectF(0,0,view.getWidth()-rightCrop-leftCrop,view.getHeight()-bottomCrop-topCrop);
            canvas.drawBitmap(originBitmap,originRect,cutRect,null);
            canvas.setBitmap(null);
            canvas=null;
            originBitmap.recycle();
            return cutBitmap;
        }
        return cutBitmap;
    }
    public static Bitmap createBitmapFromView(View view){
        return createBitmapFromView(view,1);
    }
    public static Bitmap createBitmapFromView(View view,float scale){
        if(scale<=0){
            Log.e("screenshots","bitmap scale size not less than 0");
        }
        if(view instanceof ImageView){
            Drawable drawable = ((ImageView) view).getDrawable();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return bitmap;
        }
        view.clearFocus();
        Bitmap bitmap=createBitmapSafely(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888,1);
        if(bitmap!=null){
            Canvas canvas=new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            canvas.scale(scale, scale);
            view.draw(canvas);
            canvas.setBitmap(null);
            canvas=null;
            return bitmap;
        }
        return bitmap;
    }
    private static Bitmap createBitmapSafely(int width, int height, @NonNull Bitmap.Config config, int retryCount){
        try {
            return Bitmap.createBitmap(width,height,config);
        }catch (OutOfMemoryError error){
            error.printStackTrace();
            if(retryCount>0){
                System.gc();
                return createBitmapSafely(width,height,config,retryCount-1);
            }
        }
        return null;
    }


    /*******************************************************************************************************/
    /**
     * 对图片进行压缩，主要是为了解决控件显示过大图片占用内存造成OOM问题,一般压缩后的图片大小应该和用来展示它的控件大小相近.
     *
     * @param context 上下文
     * @param resId 图片资源Id
     * @param reqWidth 期望压缩的宽度
     * @param reqHeight 期望压缩的高度
     * @return 压缩后的图片
     */
    public static Bitmap compressBitmap(Context context, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmap(BitmapFactory.decodeResource(context.getResources(), resId, options),reqWidth,reqHeight);
    }
    public static Bitmap compressBitmap(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmap(BitmapFactory.decodeFile(pathName, options),reqWidth,reqHeight);
    }
    public static Bitmap compressBitmap(byte[] data, int offset, int length, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, offset,length, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmap(BitmapFactory.decodeByteArray(data, offset,length, options),reqWidth,reqHeight);
    }
    public static Bitmap compressBitmap(FileDescriptor fd, Rect outPadding, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, outPadding, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmap(BitmapFactory.decodeFileDescriptor(fd, outPadding, options),reqWidth,reqHeight);
    }
    public static Bitmap compressBitmap(Resources res, TypedValue value,InputStream is, Rect pad, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResourceStream(res, value,is,pad, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmap(BitmapFactory.decodeResourceStream(res, value,is,pad, options),reqWidth,reqHeight);
    }
    public static Bitmap compressBitmap(InputStream is, Rect outPadding, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, outPadding, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmap(BitmapFactory.decodeStream(is, outPadding, options),reqWidth,reqHeight);
    }
    /*******************************************************************************************************/


    /*******************************************************************************************************/
    public static Bitmap compressBitmapForHeight(Context context, int resId,  int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);

        final int height = options.outHeight;
//        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight ) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = heightRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForHeight(BitmapFactory.decodeResource(context.getResources(), resId, options),reqHeight);
    }
    public static Bitmap compressBitmapForHeight(String pathName,int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        final int height = options.outHeight;
//        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight ) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = heightRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForHeight(BitmapFactory.decodeFile(pathName, options),reqHeight);
    }
    public static Bitmap compressBitmapForHeight(byte[] data, int offset, int length,int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data,offset,length, options);

        final int height = options.outHeight;
//        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight ) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = heightRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForHeight(BitmapFactory.decodeByteArray(data,offset,length, options),reqHeight);
    }
    public static Bitmap compressBitmapForHeight(FileDescriptor fd, Rect outPadding,int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,outPadding, options);

        final int height = options.outHeight;
//        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight ) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = heightRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForHeight(BitmapFactory.decodeFileDescriptor(fd,outPadding, options),reqHeight);
    }
    public static Bitmap compressBitmapForHeight(Resources res, TypedValue value,InputStream is, Rect pad,int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResourceStream(res,value,is,pad, options);

        final int height = options.outHeight;
//        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight ) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = heightRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForHeight(BitmapFactory.decodeResourceStream(res,value,is,pad, options),reqHeight);
    }
    public static Bitmap compressBitmapForHeight(InputStream is, Rect outPadding,int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is,outPadding, options);

        final int height = options.outHeight;
//        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight ) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = heightRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForHeight(BitmapFactory.decodeStream(is,outPadding,  options),reqHeight);
    }
    /*******************************************************************************************************/


    /*******************************************************************************************************/
    public static Bitmap compressBitmapForWidth(Context context, int resId, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);

//        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if ( width > reqWidth) {
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize =  widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForWidth(BitmapFactory.decodeResource(context.getResources(), resId, options),reqWidth);
    }
    public static Bitmap compressBitmapForWidth(String pathName, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

//        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if ( width > reqWidth) {
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize =  widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForWidth(BitmapFactory.decodeFile(pathName, options),reqWidth);
    }
    public static Bitmap compressBitmapForWidth(byte[] data, int offset, int length, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data,offset,length, options);

//        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if ( width > reqWidth) {
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize =  widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForWidth(BitmapFactory.decodeByteArray(data,offset,length, options),reqWidth);
    }
    public static Bitmap compressBitmapForWidth(FileDescriptor fd, Rect outPadding, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,outPadding, options);

//        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if ( width > reqWidth) {
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize =  widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForWidth(BitmapFactory.decodeFileDescriptor(fd,outPadding, options),reqWidth);
    }
    public static Bitmap compressBitmapForWidth(Resources res, TypedValue value,InputStream is, Rect pad, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResourceStream(res,value,is,pad, options);

//        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if ( width > reqWidth) {
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize =  widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForWidth(BitmapFactory.decodeResourceStream(res,value,is,pad, options),reqWidth);
    }
    public static Bitmap compressBitmapForWidth(InputStream is, Rect outPadding, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is,outPadding, options);

//        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if ( width > reqWidth) {
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize =  widthRatio;
        }
        options.inSampleSize = inSampleSize;
        // 使用计算得到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return scaleBitmapForWidth(BitmapFactory.decodeStream(is,outPadding, options),reqWidth);
    }
    /*******************************************************************************************************/


    /*******************************************************************************************************/
    public static Bitmap compressBitmapForScale(Context context, int resId,int scaleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        if (scaleSize>1) {
            options.inSampleSize = scaleSize;
        }else{
            options.inSampleSize = 1;
        }
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }
    public static Bitmap compressBitmapForScale(String pathName,int scaleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        if (scaleSize>1) {
            options.inSampleSize = scaleSize;
        }else{
            options.inSampleSize = 1;
        }
        return BitmapFactory.decodeFile(pathName, options);
    }
    public static Bitmap compressBitmapForScale(byte[] data, int offset, int length,int scaleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        if (scaleSize>1) {
            options.inSampleSize = scaleSize;
        }else{
            options.inSampleSize = 1;
        }
        return BitmapFactory.decodeByteArray(data,offset,length, options);
    }
    public static Bitmap compressBitmapForScale(FileDescriptor fd, Rect outPadding,int scaleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        if (scaleSize>1) {
            options.inSampleSize = scaleSize;
        }else{
            options.inSampleSize = 1;
        }
        return BitmapFactory.decodeFileDescriptor(fd,outPadding, options);
    }
    public static Bitmap compressBitmapForScale(Resources res, TypedValue value,InputStream is, Rect pad,int scaleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        if (scaleSize>1) {
            options.inSampleSize = scaleSize;
        }else{
            options.inSampleSize = 1;
        }
        return BitmapFactory.decodeResourceStream(res,value,is,pad, options);
    }
    public static Bitmap compressBitmapForScale(InputStream is, Rect outPadding,int scaleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        /*
         * 第一次解析时，inJustDecodeBounds设置为true，
         * 禁止为bitmap分配内存，虽然bitmap返回值为空，但可以获取图片大小
         */
        if (scaleSize>1) {
            options.inSampleSize = scaleSize;
        }else{
            options.inSampleSize = 1;
        }
        return BitmapFactory.decodeStream(is,outPadding, options);
    }
    /*******************************************************************************************************/


    /*******************************************************************************************************/
    public static Bitmap scaleBitmapForWidth(Bitmap bitmap,int newWidth){
        float initScale=newWidth*1.0f/bitmap.getWidth();
        Matrix matrix=new Matrix();
        matrix.postScale(initScale,initScale);

        int newHeight=(int)(bitmap.getHeight()*initScale);
        Bitmap newBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(newBitmap);
        canvas.drawBitmap(bitmap,matrix,null);
        return newBitmap;
//        return Bitmap.createBitmap(bitmap,0,0,newWidth,(int)(bitmap.getHeight()*initScale),matrix,true);
    }
    public static Bitmap scaleBitmapForHeight(Bitmap bitmap,int newHeight){
        float initScale=newHeight*1.0f/bitmap.getHeight();
        Matrix matrix=new Matrix();
        matrix.postScale(initScale,initScale);

        int newWidth=(int)(bitmap.getWidth()*initScale);
        Bitmap newBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(newBitmap);
        canvas.drawBitmap(bitmap,matrix,null);
        return newBitmap;
//        return Bitmap.createBitmap(bitmap,0,0,(int)(bitmap.getWidth()*initScale),newHeight,matrix,true);
    }
    public static Bitmap scaleBitmap(Bitmap bitmap,int newWidth,int newHeight){
        float initScale=1;
        float initTranslateX;
        float initTranslateY;
        if(bitmap.getWidth()*1.0f/bitmap.getHeight()>newWidth*1.0f/newHeight){
            initScale=newWidth*1.0f/bitmap.getWidth();
            initTranslateX=0;
            initTranslateY=(newHeight-bitmap.getHeight()*initScale)/2;
        }else{
            initScale=newHeight*1.0f/bitmap.getHeight();
            initTranslateX=(newWidth-bitmap.getWidth()*initScale)/2;
            initTranslateY=0;
        }
        Matrix matrix=new Matrix();
        matrix.postScale(initScale,initScale);
        matrix.postTranslate(initTranslateX,initTranslateY);
        Bitmap newBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(newBitmap);
        canvas.drawBitmap(bitmap,matrix,null);
        return newBitmap;
//        return Bitmap.createBitmap(bitmap,0,0,newWidth,newHeight,matrix,true);
    }
    /*******************************************************************************************************/

    public static int[] getBitmapSize(Resources res, int id){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res,id,options);
        return new int[]{options.outWidth,options.outHeight};
    }
    public static int[] getBitmapSize(byte[] data, int offset, int length){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeByteArray(data,offset,length,options);
        return new int[]{options.outWidth,options.outHeight};
    }
    public static int[] getBitmapSize(String pathName){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(pathName,options);
        return new int[]{options.outWidth,options.outHeight};
    }
    public static int[] getBitmapSize(FileDescriptor fd, Rect outPadding){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFileDescriptor(fd,outPadding,options);
        return new int[]{options.outWidth,options.outHeight};
    }
    public static int[] getBitmapSize(Resources res, TypedValue value,InputStream is, Rect pad){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResourceStream(res,value,is,pad,options);
        return new int[]{options.outWidth,options.outHeight};
    }
    public static int[] getBitmapSize(InputStream is, Rect outPadding){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeStream(is,outPadding,options);
        return new int[]{options.outWidth,options.outHeight};
    }

    /**
     * 创建一张指定大小的纯色图片，支持圆角
     *
     * @param resources    Resources对象，用于创建BitmapDrawable
     * @param width        图片的宽度
     * @param height       图片的高度
     * @param cornerRadius 图片的圆角，不需要则传0
     * @param filledColor  图片的填充色
     * @return 指定大小的纯色图片
     */
    public static BitmapDrawable createDrawableWithSize(Resources resources, int width, int height, int cornerRadius, @ColorInt int filledColor) {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        if (filledColor == 0) {
            filledColor = Color.TRANSPARENT;
        }

        if (cornerRadius > 0) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(filledColor);
            canvas.drawRoundRect(new RectF(0, 0, width, height), cornerRadius, cornerRadius, paint);
        } else {
            canvas.drawColor(filledColor);
        }
        return new BitmapDrawable(resources, output);
    }

    /**
     * 设置Drawable的颜色
     * <b>这里不对Drawable进行mutate()，会影响到所有用到这个Drawable的地方，如果要避免，请先自行mutate()</b>
     */
    public static ColorFilter setDrawableTintColor(Drawable drawable, @ColorInt int tintColor) {
        LightingColorFilter colorFilter = new LightingColorFilter(Color.argb(255, 0, 0, 0), tintColor);
        if(drawable != null){
            drawable.setColorFilter(colorFilter);
        }
        return colorFilter;
    }

    /**
     * 由一个drawable生成bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null)
            return null;
        else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();

        if (!(intrinsicWidth > 0 && intrinsicHeight > 0))
            return null;

        try {
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                    : Bitmap.Config.RGB_565;
            Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建一张渐变图片，支持韵脚。
     *
     * @param startColor 渐变开始色
     * @param endColor   渐变结束色
     * @param radius     圆角大小
     * @param centerX    渐变中心点 X 轴坐标
     * @param centerY    渐变中心点 Y 轴坐标
     * @return 返回所创建的渐变图片。
     */
    @TargetApi(16)
    public static GradientDrawable createCircleGradientDrawable(@ColorInt int startColor,
                                                                @ColorInt int endColor, int radius,
                                                                @FloatRange(from = 0f, to = 1f) float centerX,
                                                                @FloatRange(from = 0f, to = 1f) float centerY) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{
                startColor,
                endColor
        });
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setGradientRadius(radius);
        gradientDrawable.setGradientCenter(centerX, centerY);
        return gradientDrawable;
    }


    /**
     * 动态创建带上分隔线或下分隔线的Drawable。
     *
     * @param separatorColor 分割线颜色。
     * @param bgColor        Drawable 的背景色。
     * @param top            true 则分割线为上分割线，false 则为下分割线。
     * @return 返回所创建的 Drawable。
     */
    public static LayerDrawable createItemSeparatorBg(@ColorInt int separatorColor, @ColorInt int bgColor, int separatorHeight, boolean top) {

        ShapeDrawable separator = new ShapeDrawable();
        separator.getPaint().setStyle(Paint.Style.FILL);
        separator.getPaint().setColor(separatorColor);

        ShapeDrawable bg = new ShapeDrawable();
        bg.getPaint().setStyle(Paint.Style.FILL);
        bg.getPaint().setColor(bgColor);

        Drawable[] layers = {separator, bg};
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        layerDrawable.setLayerInset(1, 0, top ? separatorHeight : 0, 0, top ? 0 : separatorHeight);
        return layerDrawable;
    }


    /////////////// VectorDrawable /////////////////////

    public static Drawable getVectorDrawable(Context context, @DrawableRes int resVector) {
        try {
            return AppCompatResources.getDrawable(context, resVector);
        } catch (Exception e) {
            Log.d("BitmapUtils", "Error in getVectorDrawable. resVector=" + resVector + ", resName=" + context.getResources().getResourceName(resVector) + e.getMessage());
            return null;
        }
    }

    public static Bitmap vectorDrawableToBitmap(Context context, @DrawableRes int resVector) {
        Drawable drawable = getVectorDrawable(context, resVector);
        if (drawable != null) {
            Bitmap b = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
            drawable.draw(c);
            return b;
        }
        return null;
    }
}

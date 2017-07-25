package com.github.androidtools;

/**
 * Created by Administrator on 2016/9/28.
 */
public class GsonUtils {
    public void toObj(){
//        new Gson().fromJson(str, new TypeToken<List<DyshowlistBean>>(){}.getType());
    }
    /*private static GsonUtils gsonUtils;
    private static Gson gson;
    private GsonUtils() {
        gson=new Gson();
    }
    public static GsonUtils getInstance(){
        if(gson==null){
            synchronized (GsonUtils.class){
                if(gsonUtils==null){
                    gsonUtils=new GsonUtils();
                }
            }
        }
        return gsonUtils;
    }
    public static Gson getGson(){
        return getInstance().gson;
    }
    public static <T> T jsonToObject(String string,Class<T> clazz){
        return getInstance().gson.fromJson(string, clazz);
    }
    public static <T extends Object> T jsonToObject(String string,Type type){
        return getInstance().gson.fromJson(string, type);
    }

    public static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }
            public Type[] getActualTypeArguments() {
                return args;
            }
            public Type getOwnerType() {
                return null;
            }
        };
    }
    public static Object toObj(String string,Class clazz,Class claxx) {
        ParameterizedType pType = GsonUtils.type(clazz,claxx);
        return new Gson().fromJson(string, pType);
    }
    public static Object toObj(String string,Class clzz) {
        return new Gson().fromJson(string,clzz);
    }*/
}

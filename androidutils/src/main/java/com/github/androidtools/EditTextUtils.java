package com.github.androidtools;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * @createBy r-zhong
 * @time 2018-12-04 13:26
 */
public class EditTextUtils {
    public static void simpleNumberSpace(final EditText editText) {
        /*固定3,4,4格式*/
        editText.addTextChangedListener(new TextWatcher() {
            boolean flag;

            public Resources getResources() {
                return editText.getResources();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (flag) {
                    return;
                }
                int selection = editText.getSelectionEnd();
                if (s != null && s.length() > 0) {
                    String str = s.toString().replace(getResources().getString(R.string.strSpace), "");
                    //3    4    4
                    //<=3  <=7  <=length
                    //0-3  3-7 7-length
                    String result = null;
                    if (str.length() <= 3) {
                        result = str.toString();
                    } else if (str.length() <=7) {
                        String s1 = str.substring(0, 3);
                        String s2 = str.substring(3, str.length());
                        result = s1 + getResources().getString(R.string.strSpace) + s2;
                        if (before == 0) {
                            //输入文本
                            selection++;
                        }
                    } else {
                        String s1 = str.substring(0, 3);
                        String s2 = str.substring(3, 7);
                        String s3 = str.substring(7, str.length());
                        result = s1 + getResources().getString(R.string.strSpace) + s2 + getResources().getString(R.string.strSpace) + s3;
                        if (before == 0) {
                            //输入文本
                            selection++;
                        }
                    }
                    if (result.equals(s.toString())) {
                        return;
                    }

                    flag = true;
                    editText.setText(result);
                    flag = false;

                    if (selection >= result.length()) {
                        selection = result.length();
                    }
                    editText.setSelection(selection);
                }
            }


        });
    }

    public void numberSpace(final EditText editText) {
        numberSpace(editText, 3, 4, 4);
    }

    public static void numberSpace(final EditText editText, final int... numberType) {
        numberSpace(editText,editText.getContext().getResources().getString(R.string.strSpace),numberType);
    }
    public static void numberSpace(final EditText editText,final String strSpace, final int... numberType) {
        editText.addTextChangedListener(new TextWatcher() {
            boolean flag;
            int[]params=numberType;
            /*获取次数，用来for循环拼接*/
            public int sum(int index){
                if(index==0){
                    return params[0];
                }else {
                    return params[index]+sum(index-1);
                }
            }
            /*获取字符串长度小于某个数的对应下标*/
            public   int getNum(String str){
                int index=0;
                for (int i = 0; i < params.length; i++) {
                    if(i==params.length-1){
                        index=i;
                        break;
                    }else if(str.length()<=sum(i)){
                        index=i;
                        break;
                    }
                }
                return index;
            }

            public Resources getResources() {
                return editText.getResources();
            }

            public String getSpaceStr(){
                return  strSpace;
//                return  getResources().getString(R.string.strSpace);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (flag || numberType.length <= 1) {
                    return;
                }
                int selection = editText.getSelectionEnd();
                if (s != null && s.length() > 0) {
                    String str = s.toString().replace(getSpaceStr(), "");
                    //3    4    4
                    //<=3  <=7  <=length
                    //0-3  3-7 7-length


                    StringBuilder builder = new StringBuilder();

                    builder.append(str.substring(0,params[0]<=str.length()?params[0]:str.length()));
                    builder.append(getSpaceStr());
                    boolean isNeedAddSelection=false;
                    //获取到的下标是多少就循环拼接多少次
                    for (int i = 1; i < getNum(str)+1; i++) {
                        if(i==params.length-1){
                            builder.append(str.substring(sum(i-1),str.length()));
                        }else{
                            builder.append(str.substring(sum(i-1),sum(i)<=str.length()?sum(i):str.length()));
                        }
                        builder.append(getSpaceStr());
                        isNeedAddSelection=true;
                    }
                    if(before==0&&isNeedAddSelection){
                        //输入文本
                        selection++;
                    }

                    builder.deleteCharAt(builder.length() - 1);

                    String result=builder.toString();

                    flag=true;
                    editText.setText(result);
                    flag=false;
                    if(selection>=result.length()){
                        selection=result.length();
                    }
                    editText.setSelection(selection);
                }
            }
        });
    }
}

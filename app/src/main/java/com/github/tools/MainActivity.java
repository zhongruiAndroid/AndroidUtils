package com.github.tools;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.tv_select)
    TextView tv_select;
    @BindView(R.id.tv_data)
    TextView tv_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase.loadLibs(this);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

    }
    @OnClick({R.id.tv_save,R.id.tv_select})
    public void onClick(View view){
        switch (view.getId()){
            case  R.id.tv_save:
                DBManager dbManager = DBManager.newInstance(this);
                dbManager.addData(1,"第一个");
                break;
            case R.id.tv_select:
                DBManager dbManager1 = DBManager.newInstance(this);
                String s = dbManager1.selectData(1);
                tv_data.setText(s);
                break;
        }
    }
}

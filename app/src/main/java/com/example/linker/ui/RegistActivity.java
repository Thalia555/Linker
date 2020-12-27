package com.example.linker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.linker.DB.SQLFunction;
import com.example.linker.R;
import com.example.linker.bean.Admin;
import com.example.linker.bean.User;
import com.example.linker.util.SPUtils;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username;
    EditText password;
    EditText repassword;
    SQLFunction sqlFunction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        findViewById(R.id.registbtn).setOnClickListener(this);
        sqlFunction=new SQLFunction();
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registbtn:
                nullCheck();
              if (!password.getText().toString().equals(repassword.getText().toString())){
                    AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                            .setTitle("提示")//标题
                            .setMessage("两次密码输入不一致")//内容
                            .setIcon(R.mipmap.ic_launcher)//图标
                            .create();
                    alertDialog1.show();
                    return;
                }else {
                  Admin admin=new Admin();
                  admin.setUsername(username.getText().toString());
                  admin.setPassword(password.getText().toString());
                  sqlFunction.regist(this,admin);
                  finish();
                }
                break;
        }
    }
    private void nullCheck(){
        StringBuffer str=new StringBuffer();

        if(username.getText().toString().equals("")){
            str.append("账号/");
        }
        if(password.getText().toString().equals("")){
            str.append("密码/");
        }
        if(repassword.getText().toString().equals("")){
            str.append("确认密码/");
        }
        if(str.toString().equals(null)) {
            AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                    .setTitle("提示")//标题
                    .setMessage(str+"不能为空")//内容
                    .setIcon(R.mipmap.ic_launcher)//图标
                    .create();
            alertDialog1.show();
        }
    }

}
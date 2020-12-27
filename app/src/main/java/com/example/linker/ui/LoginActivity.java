package com.example.linker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.linker.R;
import com.example.linker.DB.SQLFunction;
import com.example.linker.bean.Admin;
import com.example.linker.ui.MianActivity;
import com.example.linker.ui.RegistActivity;
import com.example.linker.util.SPUtils;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username;
    EditText password;
    SQLFunction sqlFunction;
    String status="否";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
           /*
        初始化数据库
         */
        SQLFunction.initTable(LoginActivity.this);
        findViewById(R.id.loginbtn).setOnClickListener(this);
        findViewById(R.id.registbtn).setOnClickListener(this);
        findViewById(R.id.yes).setOnClickListener(this);
        findViewById(R.id.no).setOnClickListener(this);
        username=findViewById(R.id.username);
        password=findViewById(R.id.userpwd);
        sqlFunction=new SQLFunction();
        String username=SPUtils.getString(this,"username","");
        String password=SPUtils.getString(this,"password","");
        Admin admin=new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        if(sqlFunction.login(this,admin)){
            finish();
            Intent intent=new Intent(this,MianActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginbtn:
                //判断账号密码是否是admin 是的话进入主页面不是的话用一个弹框提示
                String usernameStr=username.getText().toString();
                String passwordStr=password.getText().toString();
                Admin admin=new Admin();
                admin.setUsername(usernameStr);
                admin.setPassword(passwordStr);
                if(usernameStr.equals("")||passwordStr.equals("")){
                    AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                            .setTitle("提示")//标题
                            .setMessage("账号密码不能为空")//内容
                            .setIcon(R.mipmap.ic_launcher)//图标
                            .create();
                    alertDialog1.show();
                }else{
                    if(sqlFunction.login(this,admin)){
                        if(status.equals("是")){
                            SPUtils.putString(this,"username",admin.getUsername());
                            SPUtils.putString(this,"password",admin.getPassword());
                        }else{
                            SPUtils.putString(this,"username","");
                            SPUtils.putString(this,"password","");
                        }
                        Intent intent=new Intent(this,MianActivity.class);
                        startActivity(intent);
                        Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
                    }else{
                        AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                                .setTitle("提示")//标题
                                .setMessage("账号密码输入不正确")//内容
                                .setIcon(R.mipmap.ic_launcher)//图标
                                .create();
                        alertDialog1.show();
                    }
                }
                break;
            case R.id.registbtn:
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.yes:
                status="是";
                break;
            case R.id.no:
                status="否";
                break;
        }
    }
}
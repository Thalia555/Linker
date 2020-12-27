package com.example.linker.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.lang.UScript;

import com.example.linker.bean.Admin;
import com.example.linker.bean.User;

import java.util.ArrayList;

public class DBManager {
    String TAG = "DBManger";
    DBHelper helper;
    SQLiteDatabase sqLiteDatabase;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        sqLiteDatabase = helper.getReadableDatabase();
    }

    /**
     * execSql()方法可以执行 Insert,update,delete语句
     * 实现对数据库的增删改功能
     * sql为操作语句 bindArgs为操作传递参数
     */
    //增刪改
    public boolean updateSQLite(String sql) {
        boolean isSuccess = false;
        try {
            sqLiteDatabase.execSQL(sql);
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((sqLiteDatabase != null)) {
                sqLiteDatabase.close();
            }
        }
        return isSuccess;
    }

    /**
     * 登录
     */
    public ArrayList<User> queryUserByName(String name) {
        //查询全部数据
        Cursor cursor = sqLiteDatabase.query("info", new String[]{"id", "telephone", "qq", "email"}, "name=?", new String[]{name}, null, null, null, null);
        ArrayList<User> list = new ArrayList<User>();
        if (cursor.getCount() > 0) {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String telephone = cursor.getString(cursor.getColumnIndex("telephone"));
                String qq = cursor.getString(cursor.getColumnIndex("qq"));
                String email = cursor.getString(cursor.getColumnIndex("email"));

                User user = new User();
                user.setId(Integer.parseInt(id));
                user.setName(name);
                user.setEmail(email);
                user.setTelephone(telephone);
                user.setQq(qq);
                list.add(user);
                //移动到下一位
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    /**
     * 登录
     */
    public ArrayList<Admin> login(String username) {
        //查询全部数据
        Cursor cursor = sqLiteDatabase.query("admin", new String[]{"id", "password"}, "username=?", new String[]{username}, null, null, null, null);
        ArrayList<Admin> list = new ArrayList<Admin>();
        if (cursor.getCount() > 0) {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String password = cursor.getString(cursor.getColumnIndex("password"));

                Admin admin = new Admin();
                admin.setId(Integer.parseInt(id));
                admin.setUsername(username);
                admin.setPassword(password);
                list.add(admin);
                //移动到下一位
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    /**
     * 查询所有的人信息
     */
    public ArrayList<User> queryAllUserData() {
        //查询全部数据
        Cursor cursor = sqLiteDatabase.query("info", null, null, null, null, null, null, null);
        ArrayList<User> list = new ArrayList<User>();
        if (cursor.getCount() > 0) {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String telephone = cursor.getString(cursor.getColumnIndex("telephone"));
                String qq = cursor.getString(cursor.getColumnIndex("qq"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                User user = new User();
                user.setId(Integer.parseInt(id));
                user.setName(name);
                user.setTelephone(telephone);
                user.setQq(qq);
                user.setEmail(email);
                list.add(user);
                //移动到下一位
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }


}

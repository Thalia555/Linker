package com.example.linker.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.linker.bean.Admin;
import com.example.linker.bean.User;

import java.util.ArrayList;

public class SQLFunction {

    static DBHelper helper;
    SQLiteDatabase sqLiteDatabase;

    public static void initTable(Context context) {
        helper = new DBHelper(context);
        helper.getReadableDatabase();
    }

    /**
     * 增加用户
     *
     * @param context
     */
    public static void addtUser(Context context, User user) {
        DBManager sqlManager = new DBManager(context);
        helper = new DBHelper(context);
        helper.getWritableDatabase();
        String sql = "insert into info (name,telephone,qq,email) values("
                +"'"+ user.getName() +"'"+ ","+"'"+ user.getTelephone()+"'" +"," +"'"+ user.getQq()+"'" +","+ "'"+user.getEmail()+ "'"+");";
        sqlManager.updateSQLite(sql);
    }
    /**
     * 增加用户
     *
     * @param context
     */
    public static void regist(Context context, Admin admin) {
        DBManager sqlManager = new DBManager(context);
        helper = new DBHelper(context);
        helper.getWritableDatabase();
        String sql = "insert into admin (username,password) values("
               +"'"+ admin.getUsername()+"'" +","+ "'"+admin.getPassword()+ "'"+");";
        sqlManager.updateSQLite(sql);
    }
    /**
     * 删除用户
     *
     * @param context
     */
    public static void deleterUser(Context context, User user) {
        DBManager sqlManager = new DBManager(context);
        helper = new DBHelper(context);
        helper.getWritableDatabase();
        String sql = "delete from info where id="+user.getId();
        sqlManager.updateSQLite(sql);
    }

    /**
     * 修改用户
     *
     * @param context
     */
    public static void update(Context context, User user) {
        DBManager sqlManager = new DBManager(context);
        helper = new DBHelper(context);
        helper.getWritableDatabase();
        String sql = "update info set name=" + "'"+user.getName() + "'"+ ",telephone=" + "'"+ user.getTelephone()
                +  "'"+",qq=" +"'"+user.getQq()+"'"+ ",email=" + "'"+ user.getEmail() + "'"+ " where id=" +user.getId();
        Log.d("update",sql);
        sqlManager.updateSQLite(sql);
    }
    /*
      查询所有用户的信息    */
    public static ArrayList<User> selectAllUser(Context context) {
        DBManager sqlManager = new DBManager(context);
        ArrayList<User> list = new ArrayList<User>();
        String sql = "select * from info ";
        list = sqlManager.queryAllUserData();
        return list;
    }
    /*
     查询根据姓名查询信息    */
    public static ArrayList<User> queryUserByName(Context context,String  name) {
        DBManager sqlManager = new DBManager(context);
        ArrayList<User> list = new ArrayList<User>();
        list = sqlManager.queryUserByName(name);
        return list;
    }

    /*
    查询根据姓名查询信息    */
    public static boolean login(Context context,Admin admin) {
        DBManager sqlManager = new DBManager(context);
        Boolean flag=false;
        ArrayList<Admin> list = new ArrayList<Admin>();
        list = sqlManager.login(admin.getUsername());
        for (int i=0;i<list.size();i++){
            if(list.get(0).getPassword().equals(admin.getPassword())){
                flag=true;
            }
        }
        return flag;
    }
}
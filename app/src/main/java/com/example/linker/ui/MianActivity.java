package com.example.linker.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.linker.DB.SQLFunction;
import com.example.linker.R;
import com.example.linker.bean.User;

import java.util.ArrayList;

public class MianActivity extends AppCompatActivity implements View.OnClickListener {
    ViewHolderAdapter adapter;
    private SQLFunction sqlFunction;
    private ArrayList<User> userList;
    private ListView listview;
    private Activity activity;
    TextView name;
    TextView telephone;
    TextView qq;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.select).setOnClickListener(this);
        findViewById(R.id.user).setOnClickListener(this);
        name=findViewById(R.id.name);
        telephone=findViewById(R.id.telephone);
        qq=findViewById(R.id.qq);
        email=findViewById(R.id.email);
        listview=findViewById(R.id.listview);
        sqlFunction=new SQLFunction();
        userList=sqlFunction.selectAllUser(this);
        adapter=new ViewHolderAdapter(this,userList);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //设置ContextMenu,长按listitem时触发
        listview.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                //menu.setHeaderTitle("选择操作");
                menu.add(0, 1, 0, "修改");
                menu.add(0, 2, 0, "删除");

            }

        });

    }
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //获取点击的item的id
        int position = info.position;
        final int id;
        switch (item.getItemId()) {
            case 1:
                View view = getLayoutInflater().inflate(R.layout.half_dialog_view, null);
                User user=userList.get(position);
                 id=userList.get(position).getId();
                final EditText nameText = (EditText) view.findViewById(R.id.name);
                final EditText telText = (EditText) view.findViewById(R.id.telephone);
                final EditText qqText = (EditText) view.findViewById(R.id.qq);
                final EditText emailText = (EditText) view.findViewById(R.id.email);
                nameText.setText(user.getName());
                telText.setText(user.getTelephone());
                qqText.setText(user.getQq());
                emailText.setText(user.getEmail());
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)//设置标题的图片
                        .setTitle("修改")//设置对话框的标题
                        .setView(view)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                        if(nullCheck(nameText.getText().toString(),telText.getText().toString(),
                                qqText.getText().toString(),emailText.getText().toString())){
                            User user=new User();
                            user.setId(id);
                            user.setName(nameText.getText().toString());
                            user.setTelephone(telText.getText().toString());
                            user.setQq(qqText.getText().toString());
                            user.setEmail(emailText.getText().toString());
                            Log.d("aa",user.getName().toString());
                            sqlFunction.update(activity,user);
                            userList=sqlFunction.selectAllUser(activity);
                            adapter.notifyDataSetChanged();
                        }
                            }
                        }).create();
                dialog.show();





                return true;
            case 2:
                User user2=new User();
                user2.setId(userList.get(position).getId());
                sqlFunction.deleterUser(this,user2);
                userList=sqlFunction.selectAllUser(this);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                final String namestr=name.getText().toString();
                String telstr=telephone.getText().toString();
                String qqstr=qq.getText().toString();
                String emailstr=email.getText().toString();
                if(nullCheck(namestr,telstr,qqstr,emailstr)){
                    User user=new User();
                    user.setName(namestr);
                    user.setTelephone(telstr);
                    user.setQq(qqstr);
                    user.setEmail(emailstr);
                    sqlFunction.addtUser(this,user);
                    userList=sqlFunction.selectAllUser(this);
                    adapter.notifyDataSetChanged();
                }

                break;
            case R.id.select:
                View view = getLayoutInflater().inflate(R.layout.half_dialog_vie2, null);
                final EditText nameText = (EditText) view.findViewById(R.id.name);

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)//设置标题的图片
                        .setTitle("查询")//设置对话框的标题
                        .setView(view)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name=nameText.getText().toString();
                                Log.d("namename",name);
                                if(name.equals(null)){
                                    Log.d("namename1",name);

                                    userList=sqlFunction.selectAllUser(activity);
                                }else{
                                    Log.d("namename2",name);

                                    userList=sqlFunction.queryUserByName(activity,name);

                                }
                                 adapter.notifyDataSetChanged();

                            }
                        }).create();
                dialog.show();

                break;
            case R.id.user:

                Intent intent2=new Intent("com.example.broadcasttest.MY_BROADCAST");
                intent2.addFlags(0x01000000);
                sendOrderedBroadcast(intent2,null);
                finish();
                break;
        }
    }

    private boolean nullCheck(String name,String telephone,String qq,String email){
        boolean flag=true;
        StringBuffer str=new StringBuffer();

        if(name.equals("")){
            str.append("姓名/");
        }
        if(telephone.equals("")){
            str.append("号码/");
        }
        if(qq.equals("")){
            str.append("qq/");
        }
        if(email.equals("")){
            str.append("邮箱/");
        }
        if(!str.toString().equals("")) {
            AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                    .setTitle("提示")//标题
                    .setMessage(str+"不能为空")//内容
                    .setIcon(R.mipmap.ic_launcher)//图标
                    .create();
            alertDialog1.show();
            flag=false;
        }
        return  flag;
    }

    public  class ViewHolderAdapter extends BaseAdapter {

        private LayoutInflater mlayoutInflater;

        public  ViewHolderAdapter(Context context, ArrayList<User> data){
            userList=data;
            mlayoutInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public Object getItem(int position) {
            return userList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            MianActivity.ViewHolderAdapter.ViewHolder holder=null;
            //判断是否缓存
            if(convertView==null){
                holder=new MianActivity.ViewHolderAdapter.ViewHolder();
                //通过LayoutInflater实例化布局
                convertView=mlayoutInflater.inflate(R.layout.viewholder_item,null);
                holder.name=convertView.findViewById(R.id.name);
                holder.telephone=convertView.findViewById(R.id.telephone);
                holder.qq=convertView.findViewById(R.id.qq);
                holder.email=convertView.findViewById(R.id.email);

                convertView.setTag(holder);
            }else{
                //通过tag找到缓存的布局
                holder= (MianActivity.ViewHolderAdapter.ViewHolder) convertView.getTag();
            }
            holder.name.setText("姓名:"+userList.get(i).getName());
            holder.telephone.setText("电话:"+userList.get(i).getTelephone());
            holder.qq.setText("qq号:"+userList.get(i).getQq());
            holder.email.setText("邮箱:"+userList.get(i).getEmail());
            return convertView;

        }

        public  final class ViewHolder{
            public TextView name;
            public TextView telephone;
            public TextView qq;
            public TextView email;
        }

    }


}
package com.example.linker.ui;
import com.example.linker.R;
import com.example.linker.util.SPUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookiePolicy;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        imageView=findViewById(R.id.image);
        findViewById(R.id.notification).setOnClickListener(this);
        findViewById(R.id.exit).setOnClickListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadNetImage();
            }
        }).start();
    }
Handler handler=new Handler();
    private Bitmap loadNetImage(){
        String url="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2F201304%2F18%2F001339jv88x0qs06vo3qq6.jpg&refer=http%3A%2F%2Fattachments.gfan.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611659400&t=78a277d8e94543429fbb8f17a3bbb3ab";
        HttpClient httpClient = new DefaultHttpClient();//创建一个默认的httpclient
//        HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        HttpGet httpGet = new HttpGet(url);//创建get请求
        InputStream is = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);//执行请求
            HttpEntity httpEntity = httpResponse.getEntity();//得到响应内容
            BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
            is = bufferedHttpEntity.getContent();
            if (is != null) {
                final Bitmap bitmap = BitmapFactory.decodeStream(is);//转为bitmap
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                                imageView.setImageBitmap(bitmap);
                        }
                    });
                return bitmap;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if (is != null) {
                    is.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notification:
                Intent intent=new Intent(this,LoginActivity.class);
//                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
                NotificationManagerCompat manager= NotificationManagerCompat.from(this);
                String channelId=creatrNotificationChannel("my_channel_ID","my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
                androidx.core.app.NotificationCompat.Builder  notification = new  androidx.core.app.NotificationCompat.Builder(this,channelId)
                        .setContentTitle("这是标题")
                        .setContentText("这是内容")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{0,1000,1000,1000})
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("我是Linker的通知"))
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory
//                        .decodeResource(getResources(),R.drawable.strawberry_pic)))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true);

                manager.notify(1,notification.build());
                break;
            case R.id.exit:
                SPUtils.putString(this,"username","");
                SPUtils.putString(this,"password","");
                startActivity(new Intent(this,LoginActivity.class));
                    finish();

                break;
        }
    }
    //通知的创建渠道
    private  String creatrNotificationChannel(String channelID,String channelNAME,int level){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel=new NotificationChannel(channelID,channelNAME,level);
            manager.createNotificationChannel(channel);
            return channelID;
        }else{
            return null;
        }
    }
}
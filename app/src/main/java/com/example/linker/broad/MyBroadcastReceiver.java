package com.example.linker.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.linker.ui.UserActivity;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/12 15:26
 * <br/>
 *
 * @since
 */
public  class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        context.startActivity(new Intent(context, UserActivity.class));
    }
}

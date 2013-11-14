package com.example.mychat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) 
	    { 
			PushService.actionStart(context.getApplicationContext());
			System.out.println("1111111111111");
//			PushService.actionStart(context.getApplicationContext());
//	      Intent newIntent = new Intent(context, PushService.class); 
////	      newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
//	      context.startService(newIntent);       
	    } 
		
	}

}

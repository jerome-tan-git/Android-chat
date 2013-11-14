package com.example.mychat;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MyHandler extends Handler {
	private ChatService service;
	public MyHandler(ChatService _service)
	{
		this.service = _service;
	}
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if (msg.what == 1) {
			System.out.println("Get message: "+msg.obj);
			
			NotificationManager nm = (NotificationManager)service.getSystemService(Context.NOTIFICATION_SERVICE);               
			Notification n = new Notification(R.drawable.icon, "Hello,there!", System.currentTimeMillis());              
			n.flags = Notification.FLAG_AUTO_CANCEL;                
			Intent i = new Intent(this.service, MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);           
			//PendingIntent
			PendingIntent contentIntent = PendingIntent.getActivity(
					this.service, 
			        R.string.app_name, 
			        i, 
			        PendingIntent.FLAG_UPDATE_CURRENT);
			                 
			n.setLatestEventInfo(
					this.service,
			        "Hello,there!", 
			        "我"+(msg.obj), 
			        contentIntent);
			nm.notify(R.string.app_name, n);
//			// ================
			
			
			Intent intent1 = new Intent();
			intent1.setAction("android.intent.action.MY_RECEIVER");
		    intent1.putExtra("msg", ""+msg.obj);
		    this.service.sendBroadcast(intent1);

		} else if (msg.what == 2) {
			System.out.println("连接成功");
//			Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT)
//					.show();
			try {
				this.service.getClient().subscribe(this.service.getMyTopic(), 1);
				System.out.println("Is connect: " + this.service.getClient().isConnected());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (msg.what == 3) {
			System.out.println("连接失败，系统正在重连");
//			Toast.makeText(MainActivity.this, "连接失败，系统正在重连", Toast.LENGTH_SHORT)
//					.show();
		}
	}
}

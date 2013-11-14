package com.example.mychat;




import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;



import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class ChatService extends Service {
	private boolean exit;
	private String host = "tcp://192.168.103.18:1883";
	private static final String TAG = "MyService";
	private String userName = "system";
	private String passWord = "123456";


	private Handler handler;

	private MqttClient client;

	private String myTopic = "test/topic";
	public MqttClient getClient() {
		return client;
	}

	public void setClient(MqttClient client) {
		this.client = client;
	}

	private MqttConnectOptions options;

	public String getMyTopic() {
		return myTopic;
	}

	public void setMyTopic(String myTopic) {
		this.myTopic = myTopic;
	}

	private ScheduledExecutorService scheduler;
	@Override
	public IBinder onBind(Intent arg0) {

		return null;
		
	}
	@Override
	public void onStart(Intent intent, int startId) {
//		System.out.println("a");
//		this.exit = false;
//        new Thread(new Runnable()
//        {
//
//			@Override
//			public void run() {
//				Intent intent1 = new Intent();
//				int count = 0;
//				while(!exit)
//				{
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//			        intent1.setAction("android.intent.action.MY_RECEIVER");
//			        intent1.putExtra("msg", ""+(count++));
//			        sendBroadcast(intent1);
//
//				}
//			}
//        	
//        }).start();
		super.onStart(intent, startId);
		System.out.println("client: " + this.client);
		if (this.client == null)
		{
			this.init();
		}
		
		this.handler = new MyHandler(this);
		this.startReconnect();
	}
	
	@Override
	public void onDestroy() {
		this.exit = true;
		System.out.println("1111111111111111111111111111111111");
//		try {
//			if (client != null)
//			{
//				client.close();
//				scheduler.shutdown();
//			}
//		} catch (MqttException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
 
	}
	
	
	
	private void startReconnect() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("In reconnection....  " + getClient().isConnected());
				if (!getClient().isConnected()) {
					System.out.println("Do reconnection....");
					connect();
				}
				
			}
		}, 0 * 1000, 3 * 1000, TimeUnit.MILLISECONDS);
	}
	
	private void connect() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("In connecting ......");
					client.connect(options);
//					client.connect();
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}
	
	private void init() {
		try {
			System.out.println("In init ......");
			client = new MqttClient(host, "test", new MemoryPersistence());
			options = new MqttConnectOptions();
			options.setCleanSession(true);
			options.setUserName(userName);
			options.setPassword(passWord.toCharArray());
			options.setConnectionTimeout(10);
			options.setKeepAliveInterval(20);
			client.setCallback(new MqttCallback() {
				@Override
				public void connectionLost(Throwable cause) {
					Log.e(TAG, "connectionLost----------1");
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					Log.e(TAG, "deliveryComplete---------"
							+ token.isComplete());
				}

				@Override
				public void messageArrived(String topicName, MqttMessage message)
						throws Exception {
					Log.e(TAG, "messageArrived----------");
					Message msg = new Message();
					msg.what = 1;
					msg.obj = topicName + "---" + message.toString();
					handler.sendMessage(msg);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

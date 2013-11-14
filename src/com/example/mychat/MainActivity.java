package com.example.mychat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.mqtt.IMqttClient;
import com.ibm.mqtt.MqttClient;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	private String[] goodsNames = {};
	private ListView listView;
	private List<Map<String, Object>> listItems;
	private ListViewAdapter listViewAdapter;
	private Button add, startS, stopS;
	private EditText msg;
	private MyReceiver receiver;
	private IntentFilter filter;
	private Intent intentService;

	// get message

	@Override
	protected void onStart() {
		super.onStart();
		// 绑定Service

	}

	@Override
	public void onDestroy() {
		this.unregisterReceiver(receiver);
		// this.stopService(this.intentService);
		super.onDestroy();
	}

//	@Override
	protected void onPause()
	{
		System.out.println("Is paused: .............");
		super.onPause();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.list_goods); 
		listItems = getListItems();
		listViewAdapter = new ListViewAdapter(this, listItems);
		listView.setAdapter(listViewAdapter);
		this.add = (Button) findViewById(R.id.button_chat);
		this.msg = (EditText) findViewById(R.id.editText1);
		this.startS = (Button) findViewById(R.id.button2);
		this.stopS = (Button) findViewById(R.id.button3);
		receiver = new MyReceiver();
		filter = new IntentFilter();
		filter.addAction("android.intent.action.MY_RECEIVER");
		registerReceiver(receiver, filter);

		this.add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent intent1 = new Intent();
				intent1.setAction("android.intent.action.MY_CALLBACK_RECEIVER");
				intent1.putExtra("msg", "" + msg.getText().toString());
				sendBroadcast(intent1);
				msg.setText("");
			}

		});

		this.startS.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				PushService.actionStart(getApplicationContext());
				System.out.println("Start");
			}

		});
		this.stopS.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				PushService.actionStop(getApplicationContext());
				System.out.println("Stop");
			}

		});

	}

	// protected
	public void refreshText(String _text, String _msgID) {
		addObject(_text);
		listViewAdapter.notifyDataSetChanged();
		
//		Intent intent1 = new Intent();
//		intent1.setAction("android.intent.action.MY_CALLBACK_RECEIVER");
//		intent1.putExtra("msg", "Added");
//		intent1.putExtra("ID", _msgID);
//		sendBroadcast(intent1);
	}

	private void addObject(String _text) {
		// this.
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "物品名称：");
		map.put("info", _text);
		listItems.add(0, map);
	}

	private List<Map<String, Object>> getListItems() {
		listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < goodsNames.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("title", "物品名称："); // 物品标题
			map.put("info", goodsNames[i]);
			listItems.add(map);
		}
		return listItems;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
					+ intent.getStringExtra("msg"));
			
			refreshText(intent.getStringExtra("msg"), intent.getStringExtra("ID"));
		}
	}
}

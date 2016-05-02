package com.buaa.tezlikai.mybaidumapdemo;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.mapapi.SDKInitializer;
import com.buaa.tezlikai.mybaidumapdemo.util.Utils;

public class DemoListActivity extends ListActivity {

	private BroadcastReceiver receiver;
	private ClassAndName[] datas = {
			new ClassAndName(HelloBaiduMapActivity.class, "HelloBaiduMap")
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerSDKCheckReceiver();
		ArrayAdapter<ClassAndName> adapter = 
				new ArrayAdapter<ClassAndName>(this, android.R.layout.simple_list_item_1, datas);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		ClassAndName classAndName = (ClassAndName) l.getItemAtPosition(position);
		startActivity(new Intent(this, classAndName.clazz));
	}
	
	
	class ClassAndName {
		public Class<?> clazz;
		public String name;
		public ClassAndName(Class<?> cls, String name) {
			this.clazz = cls;
			this.name = name;
		}
		@Override
		public String toString() {
			return name;
		}
	}

	public void registerSDKCheckReceiver(){
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR.equals(action)){
					Utils.showToast(DemoListActivity.this,"网络错误");
				}else if (SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR.equals(action)){
					Utils.showToast(DemoListActivity.this,"Key验证错误");
				}
			}
		};


		IntentFilter filter = new IntentFilter();
		//监听网络错误
		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		//监听百度地图sdk的key是否正确
		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}
}

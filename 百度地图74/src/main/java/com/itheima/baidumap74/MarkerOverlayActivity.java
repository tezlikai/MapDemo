package com.itheima.baidumap74;

import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.MapViewLayoutParams.ELayoutMode;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * 标志覆盖物
 */
public class MarkerOverlayActivity extends BaseActivity {

	private View pop;
	private TextView tv_title;

	@Override
	public void init() {
		initMarker();
		baiduMap.setOnMarkerClickListener(mOnMarkerClickListener);//点击监听器
		baiduMap.setOnMarkerDragListener(mOnMarkerDragListener);//拖动的监听器
	}

	/** 标志拖动监听器 */
	OnMarkerDragListener mOnMarkerDragListener = new OnMarkerDragListener() {

		/** 标志开始拖动 */
		@Override
		public void onMarkerDragStart(Marker marker) {//market封装的当前位置的信息
			mapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
		}

		/** 标志拖动结束 */
		@Override
		public void onMarkerDragEnd(Marker marker) {
			mapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
		}

		/** 标志正在拖动 */
		@Override
		public void onMarkerDrag(Marker marker) {
			mapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
		}
	};

	OnMarkerClickListener mOnMarkerClickListener = new OnMarkerClickListener() {

		@Override
		public boolean onMarkerClick(Marker marker) {
			// 显示一个泡泡
			if (pop == null) {
				pop = View.inflate(MarkerOverlayActivity.this, R.layout.pop, null);
				tv_title = (TextView) pop.findViewById(R.id.tv_title);
				mapView.addView(pop, createLayoutParams(marker.getPosition()));
			} else {//改变一下位置
				mapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
			}
			tv_title.setText(marker.getTitle());
			return true;
		}
	};

	/** 初始化标志 */
	private void initMarker() {
		MarkerOptions options = new MarkerOptions();
		BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_eat);
		options.position(hmPos)		// 位置
				.title("黑马")		// title
				.icon(icon)			// 图标
				.draggable(true);	// 设置图标可以拖动

		baiduMap.addOverlay(options);

		// 添加一个向北的标志
		options = new MarkerOptions().icon(icon)
				.title("向北")
				.position(new LatLng(hmPos.latitude + 0.001, hmPos.longitude))
				.draggable(true);
		baiduMap.addOverlay(options);

		// 添加一个向东的标志
		options = new MarkerOptions().icon(icon)
				.title("向东")
				.position(new LatLng(hmPos.latitude, hmPos.longitude + 0.001))
				.draggable(true);
		baiduMap.addOverlay(options);

		// 添加一个向西南的标志
		options = new MarkerOptions().icon(icon)
				.title("向西南")
				.position(new LatLng(hmPos.latitude - 0.001, hmPos.longitude - 0.001))
				.draggable(true);
		baiduMap.addOverlay(options);
	}

	/**
	 * 创建一个布局参数
	 * @param position
	 */
	private MapViewLayoutParams createLayoutParams(LatLng position) {
		MapViewLayoutParams.Builder buidler = new MapViewLayoutParams.Builder();
		buidler.layoutMode(ELayoutMode.mapMode);	// 指定坐标类型为经纬度
		buidler.position(position);		// 设置标志的位置
		buidler.yOffset(-25);			// 设置View往上偏移，以免挡住标志
		MapViewLayoutParams params = buidler.build();
		return params;
	}

}

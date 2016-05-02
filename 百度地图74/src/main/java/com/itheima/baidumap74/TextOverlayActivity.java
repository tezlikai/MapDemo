package com.itheima.baidumap74;

import com.baidu.mapapi.map.TextOptions;

public class TextOverlayActivity extends BaseActivity {

	@Override
	public void init() {
		TextOptions options = new TextOptions();
		options.position(hmPos)			// 位置
				.text("北京航空航天大学研究院")			// 文字内容
				.fontSize(20)			// 文字大小
				.fontColor(0XFF000000)	// 文字颜色
				.bgColor(0X55FF0000)	// 背景颜色
				.rotate(30);			// 旋转
		baiduMap.addOverlay(options);
	}

}

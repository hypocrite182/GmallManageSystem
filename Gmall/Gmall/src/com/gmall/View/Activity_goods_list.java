package com.gmall.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.gmall.R;
import com.gmall.Controllers.GoodController;
import com.gmall.Models.Good;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;
/*
 * Adapter适配器
 * 动态按分类查询发布的物品
 * 复用Activity_activity_adapter.xml布局
 */
public class Activity_goods_list extends Activity {
	private ListView mListView;
	private GoodController gcon;
	private List<Good> list;
	private String []text;
	private TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_goods_list);
		
		title=(TextView)findViewById(R.id.list_title);
		text=getIntent().getStringArrayExtra("text");
		mListView=(ListView)findViewById(R.id.goods_list);

		init(text[0]);
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		for (Good good : list) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("path",good.getPhotoPath());
			data.put("name", good.getName());
			data.put("money", good.getMoney());
			data.put("type", good.getTypeName());
			map.add(data);
		}
		SimpleAdapter sa = new SimpleAdapter(getApplicationContext(), map, R.layout.activity_activity_adapter,
				new String[] { "path", "name", "money", "type" },
				new int[] { R.id.goods_img, R.id.goods_name, R.id.goods_price, R.id.goods_type });
		sa.setViewBinder(new ViewBinder() {
			// 动态绑定图片
			@Override
			public boolean setViewValue(View view, Object data, String textRepresentation) {
				if ((view instanceof ImageView) && (data instanceof Bitmap)) {
					ImageView v = (ImageView) view;
					v.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		mListView.setAdapter(sa);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int goods_id=list.get(position).getId();
				Intent intent=new Intent();
				intent.putExtra("id", goods_id);
				intent.setClass(Activity_goods_list.this, Activity_goods_detils.class);
				startActivity(intent);
			}
		});
	}

	private void init(String text) {
		int flag=0;
		if(text.equals("search")) {//搜索
			flag=1;
		}
		else if(text.equals("all")){//全部分类
			flag=2;
		}
		else if(text.equals("life")){//生活百货
			flag=3;
		}
		else if(text.equals("phone")){//数码产品
			flag=4;
		}
		else if(text.equals("cloth")){//服装
			flag=5;
		}
		else if(text.equals("cos")){//美妆
			flag=6;
		}
		else if(text.equals("toy")){//玩具
			flag=7;
		}
		else if(text.equals("home")){//房屋租赁
			flag=8;
		}
		else if(text.equals("game")){//游戏交易
			flag=9;
		}
		else if(text.equals("study")){//学习用品
			flag=10;
		}
	
		switch(flag){
			case 1:
				gcon = new GoodController(this.getApplicationContext());
				list = gcon.searchGoods(this.text[1]);
				title.setText("查询结果");
				break;
			case 2:
				gcon = new GoodController(this.getApplicationContext());
				list = gcon.getAllGoods();
				title.setText(this.text[1]);
				break;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				gcon = new GoodController(this.getApplicationContext());
				list = gcon.getTypeOfGoods(this.text[1]);
				title.setText(this.text[1]);
				break;
			default:
				break;
		}
		
	}
}

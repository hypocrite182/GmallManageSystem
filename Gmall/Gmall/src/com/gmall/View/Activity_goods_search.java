package com.gmall.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.gmall.R;
import com.gmall.Controllers.GoodController;
import com.gmall.Models.Good;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
/*
 * Ê×Ò³
 */
public class Activity_goods_search extends Fragment {
	private View vView;
	private List<Good> list;
	private GoodController gcon;
	private ListView mListView;
	private ImageView img;
	private ImageView search_goods;
	private EditText search_text;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_activity_goods_search, null);
		mListView = (ListView) vView.findViewById(R.id.list);
		img = (ImageView) vView.findViewById(R.id.goods_img);
		search_text = (EditText) vView.findViewById(R.id.search_text);
		search_goods = (ImageView) vView.findViewById(R.id.search_goods);
		search_goods.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("null")
			@Override
			public void onClick(View v) {
				String[] text = new String[2];
				text[0] = "search";
				text[1] = search_text.getText().toString();
				Intent intent = new Intent();
				intent.setClass(getActivity(), Activity_goods_list.class);
				intent.putExtra("text", text);
				startActivity(intent);

			}
		});
		init();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		for (Good good : list) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("path", good.getPhotoPath().equals("") ? null : BitmapFactory.decodeFile(good.getPhotoPath()));
			data.put("name", good.getName());
			data.put("money", good.getMoney());
			data.put("type", good.getTypeName());
			map.add(data);
		}
		SimpleAdapter sa = new SimpleAdapter(this.getActivity(), map, R.layout.activity_activity_adapter,
				new String[] { "path", "name", "money", "type" },
				new int[] { R.id.goods_img, R.id.goods_name, R.id.goods_price, R.id.goods_type });
		sa.setViewBinder(new ViewBinder() {
			// ¶¯Ì¬°ó¶¨Í¼Æ¬
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
				int goods_id = list.get(position).getId();
				Intent intent = new Intent();
				intent.putExtra("id", goods_id);
				intent.setClass(getActivity(), Activity_goods_detils.class);
				startActivity(intent);
			}

		});

		return vView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void init() {
		gcon = new GoodController(this.getActivity());
		list = gcon.getAllGoods();
	}
}

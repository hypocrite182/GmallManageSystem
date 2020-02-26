package com.gmall.View;

import android.support.v4.app.Fragment;

import java.util.List;

import com.example.gmall.R;
import com.gmall.Controllers.GoodController;
import com.gmall.Models.Good;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * 物品分类页
 * 按照不同分类查询相应货物
 * 跳转到相应的货物类别页面
 */
public class GoodsTypeActivity extends Fragment {
	private ImageView allType;
	private TextView allText;
	private ImageView lifeType;
	private TextView lifeText;
	private ImageView phoneType;
	private TextView phoneText;
	private ImageView clothType;
	private TextView clothText;
	private ImageView cosType;
	private TextView cosText;
	private ImageView toyType;
	private TextView toyText;
	private ImageView homeType;
	private TextView homeText;
	private ImageView gameType;
	private TextView gameText;
	private ImageView studyType;
	private TextView studyText;
	private String[] text = new String[2];
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_goods_type, null);
		allType=(ImageView)view.findViewById(R.id.type_all);
		allText=(TextView)view.findViewById(R.id.text_all);
		lifeType=(ImageView)view.findViewById(R.id.type_live);
		lifeText=(TextView)view.findViewById(R.id.text_life);
		phoneType=(ImageView)view.findViewById(R.id.type_phone);
		phoneText=(TextView)view.findViewById(R.id.text_phone);
		clothType=(ImageView)view.findViewById(R.id.type_clothing);
		clothText=(TextView)view.findViewById(R.id.text_cloth);
		cosType=(ImageView)view.findViewById(R.id.type_cos);
		cosText=(TextView)view.findViewById(R.id.text_cos);
		toyType=(ImageView)view.findViewById(R.id.type_toy);
		toyText=(TextView)view.findViewById(R.id.text_toy);
		homeType=(ImageView)view.findViewById(R.id.type_home);
		homeText=(TextView)view.findViewById(R.id.text_home);
		gameType=(ImageView)view.findViewById(R.id.type_game);
		gameText=(TextView)view.findViewById(R.id.text_game);
		studyType=(ImageView)view.findViewById(R.id.type_book);
		studyText=(TextView)view.findViewById(R.id.text_study);
		typeOnClickListener listener=new typeOnClickListener();
		allType.setOnClickListener(listener);
		allText.setOnClickListener(listener);
		lifeType.setOnClickListener(listener);
		lifeText.setOnClickListener(listener);
		phoneType.setOnClickListener(listener);
		phoneText.setOnClickListener(listener);
		clothType.setOnClickListener(listener);
		clothText.setOnClickListener(listener);
		cosType.setOnClickListener(listener);
		cosText.setOnClickListener(listener);
		toyType.setOnClickListener(listener);
		toyType.setOnClickListener(listener);
		homeType.setOnClickListener(listener);
		homeText.setOnClickListener(listener);
		gameType.setOnClickListener(listener);
		gameText.setOnClickListener(listener);
		studyType.setOnClickListener(listener);
		studyText.setOnClickListener(listener);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	class typeOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			int id=v.getId();
			switch(id) {
			case R.id.type_all:
			case R.id.text_all:	
				text[0]="all";
				text[1]="全部分类";
				Intent intent=new Intent();
				intent.setClass(getActivity(),Activity_goods_list.class);
				intent.putExtra("text", text);
				startActivity(intent);
				break;
			case R.id.type_live:
			case R.id.text_life:
				text[0]="life";
				text[1]="生活百货";
				Intent intent1=new Intent();
				intent1.setClass(getActivity(),Activity_goods_list.class);
				intent1.putExtra("text", text);
				startActivity(intent1);
				break;
			case R.id.type_phone:
			case R.id.text_phone:
				text[0]="phone";
				text[1]="数码产品";
				Intent intent2=new Intent();
				intent2.setClass(getActivity(),Activity_goods_list.class);
				intent2.putExtra("text", text);
				startActivity(intent2);
				break;
			case R.id.type_clothing:
			case R.id.text_cloth:
				text[0]="cloth";
				text[1]="服装";
				Intent intent3=new Intent();
				intent3.setClass(getActivity(),Activity_goods_list.class);
				intent3.putExtra("text", text);
				startActivity(intent3);
				break;
			case R.id.type_cos:
			case R.id.text_cos:
				text[0]="cos";
				text[1]="美装";
				Intent intent4=new Intent();
				intent4.setClass(getActivity(),Activity_goods_list.class);
				intent4.putExtra("text", text);
				startActivity(intent4);
				break;
			case R.id.type_toy:
			case R.id.text_toy:
				text[0]="toy";
				text[1]="玩具";
				Intent intent5=new Intent();
				intent5.setClass(getActivity(),Activity_goods_list.class);
				intent5.putExtra("text", text);
				startActivity(intent5);
				break;
			case R.id.type_home:
			case R.id.text_home:
				text[0]="home";
				text[1]="房屋租赁";
				Intent intent6=new Intent();
				intent6.setClass(getActivity(),Activity_goods_list.class);
				intent6.putExtra("text", text);
				startActivity(intent6);
				break;
			case R.id.type_game:
			case R.id.text_game:
				text[0]="game";
				text[1]="游戏交易";
				Intent intent7=new Intent();
				intent7.setClass(getActivity(),Activity_goods_list.class);
				intent7.putExtra("text", text);
				startActivity(intent7);
				break;
			case R.id.type_book:
			case R.id.text_study:
				text[0]="study";
				text[1]="学习用品";
				Intent intent8=new Intent();
				intent8.setClass(getActivity(),Activity_goods_list.class);
				intent8.putExtra("text", text);
				startActivity(intent8);
				break;
			}
		}
	
	}

}

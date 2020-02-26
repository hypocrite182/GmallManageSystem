package com.gmall.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.example.gmall.R;
import com.gmall.Utils.GetPhotoFromPhotoAlbum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
/*
 * 导航栏
 */
public class MainActivity extends FragmentActivity implements OnClickListener {
	private Activity_goods_search home;
	private Activity_goods_release add;
	private GoodsTypeActivity type;
	private Activity_user use;
	private RadioButton r1;
	private RadioButton r2;
	private RadioButton r3;
	private RadioButton r4;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			id = getIntent().getIntExtra("id", 1);
			switch (id) {
			case 1:
				if (home != null)
					removeFragment(home);
				home = new Activity_goods_search();
				addFragment(home);
				showFragment(home);
				break;
			case 2:
				if (add != null)
					removeFragment(add);
				add = new Activity_goods_release();
				addFragment(add);
				showFragment(add);
				break;
			case 3:
				if (type != null)
					removeFragment(type);
				type = new GoodsTypeActivity();
				addFragment(type);
				showFragment(type);
				break;
			case 4:
				if (use != null)
					removeFragment(use);
				use = new Activity_user();
				addFragment(use);
				showFragment(use);
				break;
				default:
					break;
			}
			r1 = (RadioButton) findViewById(R.id.act_main_rb_homepage);
			r2 = (RadioButton) findViewById(R.id.act_main_rb_add);
			r3 = (RadioButton) findViewById(R.id.act_main_rb_kind);
			r4 = (RadioButton) findViewById(R.id.act_main_rb_about);
			r1.setOnClickListener(this);
			r2.setOnClickListener(this);
			r3.setOnClickListener(this);
			r4.setOnClickListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		// 首页
		case R.id.act_main_rb_homepage:
			if (home != null)
				removeFragment(home);
			home = new Activity_goods_search();
			addFragment(home);
			showFragment(home);
			break;
		// 发布
		case R.id.act_main_rb_add:
			if (add != null)
				removeFragment(add);
			add = new Activity_goods_release();
			addFragment(add);
			showFragment(add);
			break;
		// 分类
		case R.id.act_main_rb_kind:
			if (type != null)
				removeFragment(type);
			type = new GoodsTypeActivity();
			addFragment(type);
			showFragment(type);
			break;
		// 我的
		case R.id.act_main_rb_about:
			if (use != null)
				removeFragment(use);
			use = new Activity_user();
			addFragment(use);
			showFragment(use);
			break;
		}
	}

	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {

		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		ft.add(R.id.viewpager, fragment);
		ft.commit();
	}

	/** 删除Fragment **/
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	/** 显示Fragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		// 判断页面是否已经创建，如果已经创建，那么就隐藏掉
		if (home != null) {
			ft.hide(home);
		}
		if (add != null) {
			ft.hide(add);
		}
		if (type != null) {
			ft.hide(type);
		}
		if (use != null) {
			ft.hide(use);
		}

		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}
}
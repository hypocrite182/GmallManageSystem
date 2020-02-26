package com.gmall.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.gmall.R;
import com.gmall.Controllers.GoodController;
import com.gmall.Models.Good;
import com.gmall.Utils.PhotoSelectDialog;
import com.gmall.Utils.PhotoUtil;
import com.gmall.Utils.ViewUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
/*
 * 修改物品信息页面
 */
public class Activity_modefy_myrelease extends Activity {
	private EditText desc;
	private EditText name;
	private EditText price;
	private TextView style;
	private EditText phone;
	private ImageView image_photov;
	private String type;
	private PhotoUtil p;
	private String path;
	private int goodId;
	private Good currentGood;
	private GoodController gc;
	private Spinner sp;
	private PhotoSelectDialog psd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_modefy_myrelease);
		gc=new GoodController(this.getApplicationContext());
		goodId=this.getIntent().getIntExtra("good_id", 0);
		currentGood=gc.getGoodById(goodId);
		List<String> list = new ArrayList<String>();
		list.add("生活百货");
		list.add("数码产品");
		list.add("美装");
		list.add("服装");
		list.add("玩具");
		list.add("房屋租赁");
		list.add("游戏交易");
		list.add("学习用品");
		
		int position=-1;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(currentGood.getTypeName())) {
				position=i;
			}
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
		sp =(Spinner)findViewById(R.id.spinner1);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> item, View view, int position, long id) {
				type = item.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		desc = (EditText) findViewById(R.id.modefy_desc);
		name = (EditText) findViewById(R.id.modefy_name);
		price = (EditText) findViewById(R.id.modefy_price);
		phone = (EditText) findViewById(R.id.modefy_phone);
		image_photov = (ImageView) findViewById(R.id.modefy_photo);
		
		sp.setSelection(position);
		desc.setText(currentGood.getInformation());
		name.setText(currentGood.getName());
		price.setText(String.valueOf(currentGood.getMoney()));
		phone.setText(currentGood.getContact());
		path=currentGood.getPhotoPath();
		image_photov.setImageURI(Uri.fromFile(new File(currentGood.getPhotoPath())));
		
		p = new PhotoUtil(Activity_modefy_myrelease.this);
		Button goods_on = (Button) findViewById(R.id.goods_on);
		ButtonListener buttonListener = new ButtonListener();
		goods_on.setOnClickListener(buttonListener);
		image_photov.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				psd=new PhotoSelectDialog(Activity_modefy_myrelease.this, new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int id=v.getId();
						switch(id) {
						case R.id.photo_take:
							p.startCameraAndCrop();
							break;
						case R.id.photo_album:
							p.selectPhotoAlbumAndCrop();
							break;
						}
					}
				});
				psd.showAtLocation(Activity_modefy_myrelease.this.findViewById(R.id.modify_base), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		int flag = p.OnActivityResultAccess(data, requestCode);
		if (flag == 3) {
			path = p.getPhotoPath();
			image_photov.setImageURI(Uri.fromFile(new File(path)));
			psd.dismiss();
		}
		else if(flag==2) {
			path = p.getPhotoPath();
			image_photov.setImageURI(Uri.fromFile(new File(path)));
			psd.dismiss();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String goods_desc = desc.getText().toString();
			String goods_name = name.getText().toString();
			String goods_price = price.getText().toString();
			String goods_phone = phone.getText().toString();
			int id = v.getId();
				GoodController control = new GoodController(Activity_modefy_myrelease.this);
				if (goods_desc.equals("") || goods_name.equals("") || goods_price.equals("") || type.equals("")
						|| goods_phone.equals("")) {
					ViewUtil.setBottomPrompt(Activity_modefy_myrelease.this.getApplicationContext(), "请补全信息！");
				} else {
					control.updateGood(goodId, goods_desc, path==null?"":path, goods_name,
					Double.parseDouble(price.getText().toString()), type, goods_phone);
					ViewUtil.setBottomPrompt(Activity_modefy_myrelease.this.getApplicationContext(), "修改成功！");
					Intent intent=new Intent(Activity_modefy_myrelease.this,Activity_myrelease.class);
					startActivity(intent);
					Activity_modefy_myrelease.this.finish();
				}
		}
	}
}

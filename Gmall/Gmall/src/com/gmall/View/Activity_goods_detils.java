package com.gmall.View;


import java.io.File;

import com.example.gmall.R;
import com.gmall.Controllers.GoodController;
import com.gmall.Models.Good;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * ÉÌÆ·ÏêÇéÒ³
 */
public class Activity_goods_detils extends Activity {
	private GoodController gcon;
	TextView vName;
	TextView vPrice;
	TextView vType;
	TextView vInformation;
	TextView vContact;
	ImageView vImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_goods_detils);
		int goods_id = this.getIntent().getIntExtra("id", -1);
		if (goods_id != -1) {
			gcon = new GoodController(this.getApplicationContext());
			Good good = gcon.getGoodById(goods_id);
			vImage=(ImageView)findViewById(R.id.detil_img);
			vName = (TextView) findViewById(R.id.detil_name);
			vPrice = (TextView) findViewById(R.id.detil_price);
			vType = (TextView) findViewById(R.id.detile_type);
			vInformation = (TextView) findViewById(R.id.detil_ioformation);
			vContact = (TextView) findViewById(R.id.detil_contact);
			vImage.setImageURI(Uri.fromFile(new File(good.getPhotoPath())));
			vName.setText(good.getName());
			vPrice.setText(String.valueOf(good.getMoney()));
			vType.setText(good.getTypeName());
			vInformation.setText(good.getInformation());
			vContact.setText(good.getContact());
		}
	}
}

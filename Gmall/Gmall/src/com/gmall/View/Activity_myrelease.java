package com.gmall.View;

import java.io.File;
import java.util.List;

import com.example.gmall.R;
import com.gmall.Controllers.GoodController;
import com.gmall.Controllers.UserController;
import com.gmall.Models.Good;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/*
 * 我的发布物品页
 */
public class Activity_myrelease extends Activity {
	
	private GoodController gcon;
	private List<Good> list;
	private ListView mListView;
	private UserController user;
	private Button my_update;
	private Button my_delete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_myrelease);
		gcon= new GoodController(this);
		user= new UserController(this); 
		init();
		mListView=(ListView)findViewById(R.id.my_release_list);
		mListView.setAdapter(new BaseAdapter() {
			private LayoutInflater mInflater; 
			@Override
			public int getCount() {
				return list.size();
			}

			@Override
			public Object getItem(int position) {
				return list.get(position);
			}

			@Override
			public long getItemId(int position) {
				return list.get(position).getId();
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				this.mInflater = LayoutInflater.from(getApplicationContext());
				ViewHolder holder = null;            
	    		if (convertView == null) {                                 
	    			holder=new ViewHolder();                                   
	    			convertView = mInflater.inflate(R.layout.activity_activity_myrelease_adapter, null);
	    			holder.my_image = (ImageView)convertView.findViewById(R.id.my_image);
	    			holder.my_name = (TextView)convertView.findViewById(R.id.my_name);
	    			convertView.setTag(holder);                             
	    			}else {                                 
	    				holder = (ViewHolder)convertView.getTag();            
	    			} 
	    		holder.my_image.setImageURI(Uri.fromFile(new File(list.get(position).getPhotoPath())));;
	    		holder.my_name.setText(list.get(position).getName());
	    		my_update= (Button)convertView.findViewById(R.id.my_update);
	    		my_update.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(Activity_myrelease.this, Activity_modefy_myrelease.class);
						intent.putExtra("good_id", (int)getItemId(position));
						startActivity(intent);
						Activity_myrelease.this.finish();
					}
				});
	    		my_delete=(Button)convertView.findViewById(R.id.my_delete);
	    		my_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						gcon.deleteGood((int)getItemId(position));
						Intent intent = new Intent();
						intent.setClass(Activity_myrelease.this, Activity_myrelease.class);
						startActivity(intent);
						Activity_myrelease.this.finish();
					}
				});
	    		return convertView;        
	    	}     
		});
	}
	
	public void init() {
		list=user.getReleaseGoods();
	}
	
	class ViewHolder{
		public ImageView my_image;
		public TextView my_name;
	}
}

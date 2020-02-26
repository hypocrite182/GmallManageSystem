package com.gmall.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.gmall.R;
import com.gmall.Controllers.GoodController;
import com.gmall.Utils.PhotoSelectDialog;
import com.gmall.Utils.PhotoUtil;
import com.gmall.Utils.ViewUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
/*
 * ��Ʒ����ҳ��
 */
public class Activity_goods_release extends Fragment {
	private EditText desc;
	private EditText name;
	private EditText price;
	private Spinner style;
	private EditText phone;
	private ImageView image_photov;
	private View view;
	private String goods_style;
	private String photoPath;
	PhotoUtil p;
	private PhotoSelectDialog psd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_activity_goods_release, null);
		desc = (EditText) view.findViewById(R.id.goods_desc);
		image_photov = (ImageView) view.findViewById(R.id.image_photov);
		name = (EditText) view.findViewById(R.id.goods_name);
		price = (EditText) view.findViewById(R.id.goods_prace);
		phone = (EditText) view.findViewById(R.id.goods_phone);
		Button goods_on = (Button) view.findViewById(R.id.goods_on);
		ButtonListener buttonListener = new ButtonListener();
		goods_on.setOnClickListener(buttonListener);
		image_photov.setOnClickListener(buttonListener);
		p = new PhotoUtil(this);

		// ������
		List<String> list = new ArrayList<String>();
		list.add("--��ѡ����Ʒ����--");
		list.add("����ٻ�");
		list.add("�����Ʒ");
		list.add("��װ");
		list.add("��װ");
		list.add("���");
		list.add("��������");
		list.add("��Ϸ����");
		list.add("ѧϰ��Ʒ");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,
				list);
		adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
		Spinner sp = (Spinner) view.findViewById(R.id.spinner1);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> item, View view, int position, long id) {
				goods_style = item.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		image_photov.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				psd = new PhotoSelectDialog(getActivity(), new OnClickListener() {
					@Override
					public void onClick(View v) {
						int id = v.getId();
						switch (id) {
						case R.id.photo_take:
							p.startCameraAndCrop();
							break;
						case R.id.photo_album:
							p.selectPhotoAlbumAndCrop();
							break;
						}
					}
				});
				psd.showAtLocation(getActivity().findViewById(R.id.base),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});

		return view;
	}

	public class ButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			GoodController control = new GoodController(view.getContext());
			int id = v.getId();
			switch (id) {
			case R.id.goods_on:
				String goods_desc = desc.getText().toString();
				String goods_name = name.getText().toString();
				String goods_price = price.getText().toString();
				String goods_phone = phone.getText().toString();
				if (goods_desc.equals("") || goods_name.equals("") || goods_style.equals("--��ѡ����Ʒ����--") || goods_phone.equals("")) {
					ViewUtil.setBottomPrompt(Activity_goods_release.this.getActivity(), "�벹ȫ��Ϣ��");
				} else {
					control.addNewGoods(goods_desc, photoPath==null?"":photoPath, goods_name, Double.parseDouble(goods_price), goods_style, goods_phone);
					desc.setText("");
					name.setText("");
					price.setText("");
					phone.setText("");
					ViewUtil.setBottomPrompt(Activity_goods_release.this.getActivity(), "�����ɹ�");
					Intent intent = new Intent(getActivity(), MainActivity.class);
					intent.putExtra("id", 1);
					startActivity(intent);
					getActivity().finish();
				}
				break;
			}

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		int flag = p.OnActivityResultAccess(data, requestCode);
		if (flag == 3) {
			photoPath = p.getPhotoPath();
			image_photov.setImageURI(Uri.fromFile(new File(photoPath)));
			psd.dismiss();
		}
		else if(flag==2) {
			photoPath = p.getPhotoPath();
			image_photov.setImageURI(Uri.fromFile(new File(photoPath)));
			psd.dismiss();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}

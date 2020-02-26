package com.gmall.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

public class PhotoUtil {
	private String photoPath;
	private File cameraSavePath;// 拍照照片路径
	private Uri uri;// 照片uri
	private Object a;
	
	public String getPhotoPath() {
		return this.photoPath;
	}
	
	public PhotoUtil(Activity a) {
		this.a=a;
	}
	
	public PhotoUtil(Fragment f) {
		this.a=f;
	}
	
	/**
	 * OnActivityResult方法中调用
	 * @param data
	 * @param requestCode
	 * @return 返回值为0，代表是拍照不裁剪；1，代表选照片不裁剪；2，代表选照片并裁剪；3，代表拍照裁剪。均可通过getPhotoPath方法获取保存的照片路径
	 * 
	 */
	public int OnActivityResultAccess(Intent data,int requestCode) {
		if(requestCode==0) {
			return 0;
		}
		else if(requestCode == 1) {
			if(a instanceof Activity) {
				this.photoPath=GetPhotoFromPhotoAlbum.getRealPathFromUri((Activity)a, data.getData());
			}
			else if(a instanceof Fragment) {
				this.photoPath=GetPhotoFromPhotoAlbum.getRealPathFromUri(((Fragment)a).getActivity(), data.getData());
			}
			return 1;
		}
		else if (requestCode == 2) {
			photoClip(data.getData());
			return 23333;
		} 
		else if (requestCode == 3) {
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				// 在这里获得了剪裁后的Bitmap对象，可以用于上传
				Bitmap image = bundle.getParcelable("data");
				// 也可以进行一些保存、压缩等操作后上传
				this.photoPath = saveImage("头像", image);
			}
			return 2;
		}
		else if(requestCode == 4) {
			photoClip(this.getPhotoPath());
			return 23333;
		}
		else if(requestCode==5) {
			return 3;
		}
		return 2333;
	}
	
	public void startCamera() {
		 Intent intent=new Intent();
		 intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		 intent.addCategory(Intent.CATEGORY_DEFAULT);
		 File f=new File(Environment.getExternalStorageDirectory().getPath(),"拍照" + new Random().nextInt(10000000)+".jpg");
		 intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		 this.photoPath=f.getAbsolutePath();
		 if(a instanceof Activity) {
			 ((Activity)a).startActivityForResult(intent, 0);
		 }
		 else if(a instanceof Fragment) {
			 ((Fragment)a).startActivityForResult(intent, 0);
		 }
	}
	
	public void startCameraAndCrop() {
		 Intent intent=new Intent();
		 intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		 intent.addCategory(Intent.CATEGORY_DEFAULT);
		 File f=new File(Environment.getExternalStorageDirectory().getPath(),"拍照" + new Random().nextInt(10000000)+".jpg");
		 intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		 this.photoPath=f.getAbsolutePath();
		 if(a instanceof Activity) {
			 ((Activity)a).startActivityForResult(intent, 4);

		 }
		 else if(a instanceof Fragment) {
			 ((Fragment)a).startActivityForResult(intent, 4);
		 }
	}
	
	public void selectPhoto() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		 if(a instanceof Activity) {
			 ((Activity)a).startActivityForResult(intent, 1);

		 }
		 else if(a instanceof Fragment) {
			 ((Fragment)a).startActivityForResult(intent, 1);
		 }
	}

	public void selectPhotoAlbumAndCrop() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		 if(a instanceof Activity) {
			 ((Activity)a).startActivityForResult(intent, 2);

		 }
		 else if(a instanceof Fragment) {
			 ((Fragment)a).startActivityForResult(intent, 2);
		 }
	}

	private void photoClip(Uri uri) {
		// 调用系统中自带的图片剪裁
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
				intent.setDataAndType(uri, "image/*");
				// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
				intent.putExtra("crop", "true");
				// aspectX aspectY 是宽高的比例
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				// outputX outputY 是裁剪图片宽高
				intent.putExtra("outputX", 300);
				intent.putExtra("outputY", 300);
				intent.putExtra("return-data", true);
				intent.putExtra("circleCrop", false);
		 if(a instanceof Activity) {
			 ((Activity)a).startActivityForResult(intent, 3);

		 }
		 else if(a instanceof Fragment) {
			 ((Fragment)a).startActivityForResult(intent, 3);
		 }
	}
	
	private void photoClip(String path) {
		// 调用系统中自带的图片剪裁
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
				intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
				// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
				intent.putExtra("crop", "true");
				// aspectX aspectY 是宽高的比例
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				// outputX outputY 是裁剪图片宽高
				intent.putExtra("outputX", 300);
				intent.putExtra("outputY", 300);
				intent.putExtra("return-data", false);
				intent.putExtra("circleCrop", false);
	        	intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
				 if(a instanceof Activity) {
					 ((Activity)a).startActivityForResult(intent, 5);

				 }
				 else if(a instanceof Fragment) {
					 ((Fragment)a).startActivityForResult(intent, 5);
				 }
	}

	private String saveImage(String name, Bitmap bmp) {
		File appDir = new File(Environment.getExternalStorageDirectory().getPath());
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = name + new Random().nextInt(1000000)+".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			return file.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
}

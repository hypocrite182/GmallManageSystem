package com.gmall.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.os.Environment;

public class LoginUtil {
	private static String statePath=Environment.getExternalStorageDirectory().getPath();
	
	public static boolean isLogined(){
		File f=new File(statePath,"userState.prop");
		if(f.exists()) {
			Properties p=new Properties();
			try {
				p.load(new FileInputStream(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			UserIDUtil.id=Integer.parseInt(p.get("userId").toString());
			return true;
		}
		else return false;
	}
	
	public void noteLoginState(int id) {
		Properties p=new Properties();
		p.put("userId",String.valueOf(id));
		try {
			p.store(new FileOutputStream(new File(statePath,"userState.prop")), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exitLoginState(){
		File f=new File(statePath,"userState.prop");
		f.delete();
	}
}

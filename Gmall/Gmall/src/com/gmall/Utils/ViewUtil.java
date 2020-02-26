package com.gmall.Utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ViewUtil {
	public static void setBottomPrompt(Context context,String message) {
		Toast t=Toast.makeText(context, message, Toast.LENGTH_SHORT);
		t.setGravity(Gravity.BOTTOM, 0, 0);
		t.show();
	}
}

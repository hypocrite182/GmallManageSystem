package com.gmall.Utils;

import com.example.gmall.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PhotoSelectDialog extends PopupWindow {
	private TextView btn_take_photo, btn_pick_photo, btn_cancel;  
    private View mMenuView;  
  
    public PhotoSelectDialog(Activity context,View.OnClickListener itemsOnClick) {  
        super(context);  
        LayoutInflater inflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        mMenuView = inflater.inflate(R.layout.activity_activity_change_popu, null);  
        btn_take_photo = (TextView) mMenuView.findViewById(R.id.photo_take);  
        btn_pick_photo = (TextView) mMenuView.findViewById(R.id.photo_album);  
        btn_cancel = (TextView) mMenuView.findViewById(R.id.photo_cancel);  
        //ȡ����ť  
        btn_cancel.setOnClickListener(new View.OnClickListener() {  
  
            public void onClick(View v) {  
                //���ٵ�����  
                dismiss();  
            }  
        });  
        //���ð�ť����  
        btn_pick_photo.setOnClickListener(itemsOnClick);  
        btn_take_photo.setOnClickListener(itemsOnClick);  
        //����SelectPicPopupWindow��View  
        this.setContentView(mMenuView);  
        //����SelectPicPopupWindow��������Ŀ�  
        this.setWidth(LayoutParams.FILL_PARENT);  
        //����SelectPicPopupWindow��������ĸ�  
        this.setHeight(LayoutParams.WRAP_CONTENT);  
        //����SelectPicPopupWindow��������ɵ��  
        this.setFocusable(true);  
        //����SelectPicPopupWindow�������嶯��Ч��  
        this.setAnimationStyle(R.style.AppTheme);
        //ʵ����һ��ColorDrawable��ɫΪ��͸��  
        ColorDrawable dw = new ColorDrawable(0xb0000000);  
        //����SelectPicPopupWindow��������ı���  
        this.setBackgroundDrawable(dw);  
        //mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ������������ٵ�����  
        mMenuView.setOnTouchListener(new OnTouchListener() {  
              
            public boolean onTouch(View v, MotionEvent event) {  
                  
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();  
                int y=(int) event.getY();  
                if(event.getAction()==MotionEvent.ACTION_UP){  
                    if(y<height){  
                        dismiss();  
                    }  
                }                 
                return true;  
            }  
        });  
  
    }  
  
}

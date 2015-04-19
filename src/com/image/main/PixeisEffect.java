package com.image.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class PixeisEffect extends Activity {
	private ImageView mimg1, mimg2, mimg3, mimg4;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pixeis_effect);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sweet);
		
		mimg1 = (ImageView) findViewById(R.id.pix_img1);
		mimg2 = (ImageView) findViewById(R.id.pix_img2);
		mimg3 = (ImageView) findViewById(R.id.pix_img3);
		mimg4 = (ImageView) findViewById(R.id.pix_img4);
		
		mimg1.setImageBitmap(bitmap);
		mimg2.setImageBitmap(ImageHelper.handleImageNegative(bitmap));
		mimg3.setImageBitmap(ImageHelper.handleImageNostalgic(bitmap));
		mimg4.setImageBitmap(ImageHelper.handleImageEmboss(bitmap));
	}
}

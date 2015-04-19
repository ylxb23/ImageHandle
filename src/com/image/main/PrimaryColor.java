package com.image.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PrimaryColor extends Activity implements OnSeekBarChangeListener {
	private ImageView miv_image;
	private SeekBar msb_hue, msb_saturation, msb_lum;
	
	private static int MAX_VALUE = 255;
	private static int MID_VALUE = 127;
	
	private float mHue, mSaturation, mLum;
	
	private Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.primary_color);
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bubble);
		
		miv_image = (ImageView) findViewById(R.id.imgview);
		msb_hue = (SeekBar) findViewById(R.id.seekbar_hue);
		msb_saturation = (SeekBar) findViewById(R.id.seekbar_saturation);
		msb_lum = (SeekBar) findViewById(R.id.seekbar_lum);
		
		msb_hue.setOnSeekBarChangeListener(this);
		msb_saturation.setOnSeekBarChangeListener(this);
		msb_lum.setOnSeekBarChangeListener(this);
		
		msb_hue.setMax(MAX_VALUE);
		msb_saturation.setMax(MAX_VALUE);
		msb_lum.setMax(MAX_VALUE);
		msb_hue.setProgress(MID_VALUE);
		msb_saturation.setProgress(MID_VALUE);
		msb_lum.setProgress(MID_VALUE);
		
		
		miv_image.setImageBitmap(bitmap);
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean formUser) {
		switch (seekBar.getId()) {
		case R.id.seekbar_hue:
			mHue = (progress - MID_VALUE) * 1.0F / MID_VALUE * 180;
			break;
		case R.id.seekbar_saturation:
			mSaturation = progress * 1.0F / MID_VALUE;
			break;
		case R.id.seekbar_lum:
			mLum = progress * 1.0F / MID_VALUE;
			break;
		default:
			break;
		}
		
		miv_image.setImageBitmap(ImageHelper.handleImageEffect(bitmap, mHue, mSaturation, mLum));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
		
	}
}

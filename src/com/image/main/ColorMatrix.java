package com.image.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
/**
 * 此处所使用的4×5矩阵的作用就是将这个矩阵的值为不同的组合就可以在图片处理中产生不一样的效果
 * 如一下矩阵:
 * 0.393	0.769	0.189	0	0
 * 0.349	0.686	0.168	0	0
 * 0.272	0.534	0.131	0	0
 * 0		0		0		1	0
 * 所产生的效果为将图片处理成“老照片/怀旧”的效果
 * 即求像素点的老照片效果的算法为：
 * newR = (int)(0.393*pixR + 0.769*pixG + 0.189*pixB);
 * newG = (int)(0.349*pixR + 0.686*pixG + 0.168*pixB);
 * newB = (int)(0.272*pixR + 0.534*pixG + 0.131*pixB);
 * 
 * 有三个像素点ABC，求B点的“浮雕”效果的算法为：
 * B.r = C.r - B.r + 127;
 * B.g = C.g - B.g + 127;
 * B.b = C.b - B.b + 127;
 * 即产生浮雕效果的矩阵为：
 * 
 * @author YLXB23
 *
 */
public class ColorMatrix extends Activity {
	private ImageView mimg_color;
	private GridLayout mgl_group;
	private Bitmap bitmap;
	
	private int met_width, met_height;
	
	private EditText[] mets = new EditText[20];
	private float[] mcolorMatrix = new float[20];	// 存储颜色矩阵
	
	private static int screenWidth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_matrix);
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.butity);
		
		mimg_color = (ImageView) findViewById(R.id.color_image);
		mgl_group = (GridLayout) findViewById(R.id.color_group);
		
		mgl_group.post(new Runnable() { // 获取控件的宽高
			@Override
			public void run() { // 绘制后执行
//				DisplayMetrics metrics = new DisplayMetrics();
//				screenWidth = metrics.widthPixels;	// 获取屏幕像素宽度
				
				met_width = mgl_group.getWidth() / 5;	// 获取组件宽度
				met_height = mgl_group.getHeight() / 4;	// 获取组件高度
				addEts();
				initMatrix();
			}
		});
		
		mimg_color.setImageBitmap(bitmap);
	}
	
	private void addEts() {
		for(int i=0; i<20; i++){
			mets[i] = new EditText(ColorMatrix.this);
			mgl_group.addView(mets[i], met_width, met_height); // 将矩阵添加到GridLayout
		}
	}
	
	private void initMatrix() {
		for(int i=0; i<20; i++){
			if(i % 6 == 0) {
				mets[i].setText(String.valueOf(1));
			} else {
				mets[i].setText(String.valueOf(0));
			}
		}
	}
	
	private void getMatrix() {
		for(int i=0; i<20; i++) {
			mcolorMatrix[i] = Float.valueOf(mets[i].getText().toString());
		}
	}
	
	private void setImageMatrix() {
		Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		android.graphics.ColorMatrix colorMatrix = new android.graphics.ColorMatrix();
		colorMatrix.set(mcolorMatrix);
		
		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
		
		canvas.drawBitmap(bitmap, 0, 0, paint);	// 创建新的位图
		
		mimg_color.setImageBitmap(bmp);
	}
	
	public void bt_change(View v) {
		getMatrix();
		setImageMatrix();
	}
	
	public void bt_reset(View v) {
		initMatrix();
		getMatrix();
		setImageMatrix();
	}
}

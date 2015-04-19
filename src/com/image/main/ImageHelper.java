package com.image.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class ImageHelper {
	
	/**
	 * 调整图片色相、饱和度、亮度
	 * @param bm			待处理图片
	 * @param hue			色相/色调
	 * @param saturation	饱和度
	 * @param lum			亮度
	 * @return
	 */
	public static Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum) {
		Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);	// 画布 使操作在画布上操作而不是在原图上操作
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		ColorMatrix hueMatrix = new ColorMatrix();
		hueMatrix.setRotate(0, hue);	// 0 色调
		hueMatrix.setRotate(1, hue);	// 1 饱和度
		hueMatrix.setRotate(2, hue);	// 2 亮度
		
		ColorMatrix saturationMatrix = new ColorMatrix();
		saturationMatrix.setSaturation(saturation);
		
		ColorMatrix lumMatrix = new ColorMatrix();
		lumMatrix.setScale(lum, lum, lum, 1);
		
		ColorMatrix imageMatrix = new ColorMatrix();
		imageMatrix.postConcat(hueMatrix);
		imageMatrix.postConcat(saturationMatrix);
		imageMatrix.postConcat(lumMatrix);
		
		paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
		canvas.drawBitmap(bm, 0, 0, paint);
		
		return bmp;
	}
	
	
	/**
	 * 底片效果
	 * 所谓的底片效果就是将每个像素点的RGB值取相差颜色，即：
	 * newR = 255 - r;
	 * newG = 255 - g;
	 * newB = 255 - b;
	 * 在取反值得时候应注意不能出现负值
	 * @param bm
	 * @return
	 */
	public static Bitmap handleImageNegative(Bitmap bm) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color;
		int r, g, b, a;		// 临时存放旧图片的RGBA值
		
		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		
		int[] oldPix = new int[width*height];	// 存储旧图片的大小_以像素点计
		int[] newPix = new int[width*height];	// 存储图新片的大小_以像素点计
		
		bm.getPixels(oldPix, 0, width, 0, 0, width, height);
		
		for(int i=0; i<width*height; i++) {
			color = oldPix[i];
			r = Color.red(color);
			g = Color.green(color);
			b = Color.blue(color);
			a = Color.alpha(color);
			/*-----------------------------------------*/
			r = 255 - r;
			g = 255 - g;
			b = 255 - b;

//			if(r > 255) {
//				r = 255;
//			} else if(r < 0) {
//				r = 0;
//			}
//			if(g > 255) {
//				g = 255;
//			} else if(g < 0) {
//				g = 0;
//			}
//			if(b > 255) {
//				b = 255;
//			} else if(b < 0) {
//				b = 0;
//			}
			/*-----------------------------------------*/
			newPix[i] = Color.argb(a, r, g, b);
		}
		bmp.setPixels(newPix, 0, width, 0, 0, width, height);
		return bmp;
	}

	/**
	 * 怀旧效果
	 * 所产生的效果为将图片处理成“老照片/怀旧”的效果
	 * 即求像素点的老照片效果的算法为：
	 * newR = (int)(0.393*pixR + 0.769*pixG + 0.189*pixB);
	 * newG = (int)(0.349*pixR + 0.686*pixG + 0.168*pixB);
	 * newB = (int)(0.272*pixR + 0.534*pixG + 0.131*pixB);
	 * @param bm
	 * @return
	 */
	public static Bitmap handleImageNostalgic(Bitmap bm) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		int color;
		int r, g, b, a;		// 临时存放旧图片的RGBA值
		int r1, g1, b1, a1;		// 临时存放新图片的RGBA值
		
		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		
		int[] oldPix = new int[width*height];	// 存储旧图片的大小_以像素点计
		int[] newPix = new int[width*height];	// 存储图新片的大小_以像素点计
		
		bm.getPixels(oldPix, 0, width, 0, 0, width, height);
		
		for(int i=0; i<width*height; i++) {
			color = oldPix[i];
			r = Color.red(color);
			g = Color.green(color);
			b = Color.blue(color);
			a = Color.alpha(color);
			/*-----------------------------------------*/
			r1 = (int) (0.393*r + 0.769*g + 0.189*b);
			g1 = (int) (0.349*r + 0.686*g + 0.168*b);
			b1 = (int) (0.272*r + 0.534*g + 0.131*b);
			
			if(r1 > 255) {
				r1 = 255;
			}
			if(g1 > 255) {
				g1 = 255;
			}
			if(b1 > 255) {
				b1 = 255;
			}
			/*-----------------------------------------*/
			newPix[i] = Color.argb(a, r1, g1, b1);
		}
		bmp.setPixels(newPix, 0, width, 0, 0, width, height);
		return bmp;
	}

	/**
	 * 浮雕效果
	 * 有三个像素点ABC，求B点的“浮雕”效果的算法为：
	 * B.r = C.r - B.r + 127;
	 * B.g = C.g - B.g + 127;
	 * B.b = C.b - B.b + 127;
	 * @param bm
	 * @return
	 */
	public static Bitmap handleImageEmboss(Bitmap bm) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		int colorBefore;
		int color;
		int r, g, b, a;		// 临时存放旧图片的RGBA值
		int r2, g2, b2;		// 临时存放新图片的RGBA值
		
		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		
		int[] oldPix = new int[width*height];	// 存储旧图片的大小_以像素点计
		int[] newPix = new int[width*height];	// 存储图新片的大小_以像素点计
		
		bm.getPixels(oldPix, 0, width, 0, 0, width, height);
		
		for(int i=1; i<width*height; i++) {
			colorBefore = oldPix[i-1];
			r = Color.red(colorBefore);
			g = Color.green(colorBefore);
			b = Color.blue(colorBefore);
			a = Color.alpha(colorBefore);
			
			color = oldPix[i];
			r2 = Color.red(color);
			g2 = Color.green(color);
			b2 = Color.blue(color);
			
			/*-----------------------------------------*/
			r = r - r2 + 127;
			g = g - g2 + 127;
			b = b - b2 + 127;
			
			if(r > 255) {
				r = 255;
			}
			if(g > 255) {
				g = 255;
			}
			if(b > 255) {
				b = 255;
			}
			/*-----------------------------------------*/
			newPix[i] = Color.argb(a, r, g, b);
		}
		bmp.setPixels(newPix, 0, width, 0, 0, width, height);
		return bmp;
	}
	
}

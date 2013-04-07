package com.example.masterpieceyou;

import android.graphics.Bitmap;

public class BufferedImage {
	private final Bitmap bitmap;
	
	public BufferedImage(Bitmap bitmap) {
		this.bitmap = bitmap.copy(bitmap.getConfig(), true);
		this.bitmap.setHasAlpha(true);
	}
	
	public int getRGB(int x, int y) {
		return bitmap.getPixel(x, y);
	}
	
	public int getWidth(){
		return bitmap.getWidth();
	}
	
	public int getHeight(){
		return bitmap.getHeight();
	}
	
	public int getWidth(Object object){
		return bitmap.getWidth();
	}
	
	public int getHeight(Object object){
		return bitmap.getHeight();
	}

	public void setRGB(int i, int j, int newRGB) {
		// TODO Auto-generated method stub
		bitmap.setPixel(i, j, newRGB);
	}
	
	public Bitmap getBitmap(){
		return bitmap;
	}
}

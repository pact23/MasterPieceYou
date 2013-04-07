package com.example.masterpieceyou;

import android.graphics.Bitmap;

/*******Classe crée afin de faciliter la transition entre le code sous Eclipse/Java PC*********/
/*******et l'application Android (où la classe BufferedImage est remplacée par Bitmap**********/

public class BufferedImage {
	private final Bitmap bitmap;
	
	public BufferedImage(Bitmap bitmap) {
		this.bitmap = bitmap.copy(bitmap.getConfig(), true);
		
		//Ceci afin de gérer le transparence dans l'image
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
		bitmap.setPixel(i, j, newRGB);
	}
	
	public Bitmap getBitmap(){
		return bitmap;
	}
}

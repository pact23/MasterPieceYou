package Kernel;

import com.example.masterpieceyou.BufferedImage;

import Jama.Matrix;


public class Couple implements CoupleInterface{ 
	private double x;
	private double y;
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public Couple(double x, double y){
		this.x=x;
		this.y=y;
	}
	
	public Couple pixelCorrespondant(Matrix matrix){
		Couple pixel = new Couple(0,0);
		double x = this.getX();
		double y = this.getY();
		double[][] t = matrix.getArray();
		pixel.setX(t[0][0] + t[1][0]*x + t[2][0]*y);
		pixel.setY(t[3][0] - t[2][0]*x + t[1][0]*y);
		return pixel;		
		
	}
	
	
	public Couple pixelCorrespondant1(Matrix matrix){
		Couple pixel1 = new Couple(0,0);
		double x = this.getX();
		double y = this.getY();
		double[][] t = matrix.getArray();
		pixel1.setX(t[0][0] + t[1][0]*x + t[2][0]*y);
		pixel1.setY(t[3][0] + t[4][0]*x + t[5][0]*y);
		return pixel1;	}
		

	
	public boolean InOvale(Couple valCenter, double eyeRelief ){
		OvalFaceParameters ovalFace = new OvalFaceParameters(valCenter,eyeRelief);
		Couple ovalFaceCenter = ovalFace.getFaceCenter();
		double a= ovalFace.getGrandAxe();
		double b= ovalFace.getPetitAxe();
		double t = ((this.x-ovalFaceCenter.getX())*(this.x-ovalFaceCenter.getX()))/(b*b);
		double r = ((this.y-ovalFaceCenter.getY())*(this.y-ovalFaceCenter.getY()))/(a*a);
		double u = t+r;
		if(u<=1.0){return true;}
	    else{return false;}
	}
	
	public Couple ToCoordinnates(double x, double y){
		double x1 = x;
		double y1 = y;
		Couple couple = new Couple(x1,y1);
	return couple;
	}
	
	public boolean isSkin(BufferedImage image, int x, int y) {
		int rgb = image.getRGB(x,y);
		
		int red = (rgb >>> 16) & 0xFF;
		int green = (rgb >>> 8) & 0xFF;
		int blue = (rgb >>> 0) & 0xFF;
		
		int max = Math.max(Math.max(red, blue),green);
		int min = Math.min(Math.min(green,blue), red);
		
		return ((red>95)&&(green>40)&&(Math.abs(red-green)>15)&&(red>green)&&(red>blue)&&(max-min>15));
	}
	
}

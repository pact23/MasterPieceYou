package Kernel;



public class OvalFaceParameters {
	Couple center;
	double eyeRelief;
	double a,b;
	private static double sigmaA = 1.5,sigmaB = 1.0;
	private double centerX;
	private double centerY;
    
    public OvalFaceParameters(Couple center, double eyeRelief){
    	this.center=center;
    	this.eyeRelief=eyeRelief;
    	
    	a= (sigmaA)*eyeRelief;
    	b= (sigmaB)*eyeRelief;
    	
    	centerX = center.getX();
    	centerY = center.getY()+(eyeRelief)/2.7;
    }
	public Couple getCenter() {
		return center;
	}
	public void setCenter(Couple center) {
		this.center = center;
	}
	public double getEyeRelief() {
		return eyeRelief;
	}
	public void setEyeRelief(double eyeRelief) {
		this.eyeRelief = eyeRelief;
	}

	
	public Couple getFaceCenter(){
		Couple faceCenter= new Couple(centerX, centerY);
		return faceCenter;
	}
	
	public double getGrandAxe(){
		return a;
	}
	
	public double getPetitAxe(){
		return b;
	}
	
	public boolean InOvale(double x, double y){
		double t = ((x-centerX)*(x-centerX))/(b*b);
		double r = ((y-centerY)*(y-centerY))/(a*a);
		double u = t+r;
		if(u<=1.0){return true;}
	    else{return false;}
	}
	
	public int getK(double x, double y) {
		return (int) (- this.eyeRelief + Math.sqrt(Math.pow((x-centerX)/sigmaB, 2)+Math.pow((y-centerY)/sigmaA, 2)));
	}

}

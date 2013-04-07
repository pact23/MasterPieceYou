package Kernel;

import Jama.Matrix;


public interface CoupleInterface {
	public Couple pixelCorrespondant(Matrix matrix);
	
	public Couple pixelCorrespondant1(Matrix matrix);

	public boolean InOvale(Couple valCenter, double eyeRelief );
	
	public Couple ToCoordinnates(double x, double y);

}

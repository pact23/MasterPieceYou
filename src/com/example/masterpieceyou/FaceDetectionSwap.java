package com.example.masterpieceyou;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.util.Log;

public class FaceDetectionSwap {

	private Bitmap mFaceBitmap; 
	private int mFaceWidth = 0;
	private int mFaceHeight = 0;
	private int MAX_FACES = 10;
	private int numberFaces = 0;
	
	private double[] eyeCenterX;
	private double[] eyeCenterY;
	private double[] eyeDistance;
	private double[] confidence;
	
	public FaceDetectionSwap() {
	}

    public void detectFace(Bitmap photo) {
    	
		 mFaceBitmap = photo.copy(Bitmap.Config.RGB_565, true);
		 mFaceWidth = mFaceBitmap.getWidth();
		 mFaceHeight = mFaceBitmap.getHeight();

		// perform face detection and set the feature points setFace()
		
		 FaceDetector fd;
		 FaceDetector.Face [] faces = new FaceDetector.Face[MAX_FACES];
		 PointF midpoint = new PointF();
		 
		 try {
			 fd = new FaceDetector(mFaceWidth, mFaceHeight, MAX_FACES);
			 numberFaces = fd.findFaces(mFaceBitmap, faces);
		 } catch (Exception e) {
		 }
		 
		 if(numberFaces > 1)  {
			 try {
				 eyeCenterX = new double[numberFaces];
				 eyeCenterY = new double[numberFaces];
				 eyeDistance = new double[numberFaces];
				 confidence = new double[numberFaces];
				 
				 for(int i=0;i<numberFaces;i++) {
					 faces[i].getMidPoint(midpoint);
					 eyeCenterX[i] = midpoint.x;
					 eyeCenterY[i] = midpoint.y;
					 eyeDistance[i] = faces[0].eyesDistance();
					 confidence[i] = faces[0].confidence();
				 }
			 } catch (Exception e) {
				 Log.e("FaceDetection", "setFace(): face : " + e.toString());
			 }
		 }
	}

	public int getNumberFaces() {
		return numberFaces;
	}

	public double[] getEyeCenterX() {
		return eyeCenterX;
	}

	public double[] getEyeCenterY() {
		return eyeCenterY;
	}

	public double[] getEyeDistance() {
		return eyeDistance;
	}

	public double[] getConfidence() {
		return confidence;
	}
}
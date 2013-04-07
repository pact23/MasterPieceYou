package com.example.masterpieceyou;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.util.Log;

public class FaceDetection {

	private Bitmap mFaceBitmap; 
	private int mFaceWidth = 0;
	private int mFaceHeight = 0;
	private int MAX_FACES = 1;
	private int numberFaces = 0;
	
	private float eyeCenterX=-1;
	private float eyeCenterY=-1;
	private float eyeDistance=-1;
	private float confidence = -1;
	
	public FaceDetection() {
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
		 
		 if(numberFaces > 0)  {
			 try {
				 faces[0].getMidPoint(midpoint);
				 eyeCenterX = midpoint.x;
				 eyeCenterY = midpoint.y;
				 eyeDistance = faces[0].eyesDistance();
				 confidence = faces[0].confidence();
				 Log.e("POSE","X:"+faces[0].pose(FaceDetector.Face.EULER_X));
				 Log.e("POSE","Y:"+faces[0].pose(FaceDetector.Face.EULER_Y));
				 Log.e("POSE","Z:"+faces[0].pose(FaceDetector.Face.EULER_Z));
			 } catch (Exception e) {
				 Log.e("FaceDetection", "setFace(): face : " + e.toString());
			 }
		 }
	}

	public Double getEyeCenterX() {
		return (double)eyeCenterX;
	}

	public Double getEyeCenterY() {
		return (double)eyeCenterY;
	}

	public Double getEyeDistance() {
		return (double)eyeDistance;
	}

	public float getConfidence() {
		return confidence;
	}

	public int getNumberFaces() {
		return numberFaces;
	}
}
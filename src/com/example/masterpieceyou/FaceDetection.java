package com.example.masterpieceyou;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.util.Log;

/**********Cette classe nous fournie les informations nécessaires***********/
/**********Lors de la détection de un visage sur une photo******************/

public class FaceDetection {

	private Bitmap mFaceBitmap; 
	private int mFaceWidth = 0;
	private int mFaceHeight = 0;
	
	//On spécifie qu'on veut au plus un visage !!
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

		//On effectue la detection de visage sur la photo		
		 FaceDetector fd;
		 FaceDetector.Face [] faces = new FaceDetector.Face[MAX_FACES];
		 PointF midpoint = new PointF();
		 
		 try {
			 fd = new FaceDetector(mFaceWidth, mFaceHeight, MAX_FACES);
			 numberFaces = fd.findFaces(mFaceBitmap, faces);
		 } catch (Exception e) {
		 }
		 
		 //Si on a détecté un visage...
		 if(numberFaces > 0)  {
			 try {
				 faces[0].getMidPoint(midpoint);
				 //Les coordonées du centre des yeux
				 eyeCenterX = midpoint.x;
				 eyeCenterY = midpoint.y;
				 
				 //Et la distance séparant les deux yeux
				 eyeDistance = faces[0].eyesDistance();
				 
				 //Voilà un indice de confiance...
				 confidence = faces[0].confidence();
			 } catch (Exception e) {
				 Log.e("FaceDetection", e.toString());
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
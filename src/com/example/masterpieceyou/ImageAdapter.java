package com.example.masterpieceyou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/****Cette classe nous sert à afficher touts les résultats enregistrés******/
public class ImageAdapter extends BaseAdapter {
    private static final int REQUIRED_SIZE = 200;
	private Context mContext;
    private Activity activity;
    private Bitmap[] allPhotosBitmap;

    public ImageAdapter(Context c,Activity activity,File[] allPhotos) {
        mContext = c;
        this.activity = activity;

        allPhotosBitmap = new Bitmap[allPhotos.length];
        for(int i=0;i<allPhotos.length;i++) {
        	allPhotosBitmap[i] = decodeFile(allPhotos[i]);
        }
    }

    public int getCount() {
        return allPhotosBitmap.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	Display display = activity.getWindowManager().getDefaultDisplay();
        	Point size = new Point();
        	display.getSize(size);
        	int w = (size.x-10)/2;
        	
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(w, w));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(allPhotosBitmap[position]);
        return imageView;
    }
    
    /*****Charge un File avec un taille réduite (par soucis de mémoire)************/
    private Bitmap decodeFile(File f){
	    try {
	        //Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(new FileInputStream(f),null,o);

	        //Find the correct scale value. It should be the power of 2.
	        int scale=1;
	        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
	            scale*=2;

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize=scale;
	        return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	    } catch (FileNotFoundException e) {}
	    return null;
	}
}
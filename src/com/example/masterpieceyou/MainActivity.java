package com.example.masterpieceyou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import Kernel.Recalage;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	//The extension of the photo (JPEG here)
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	//The size of the photo
	private static final int REQUIRED_SIZE=300;
	private static final int IMAGE_SELECTED1 = 11;
	private static final int IMAGE_SELECTED2 = 12;
	private final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	
	SectionsPagerAdapter mSectionsPagerAdapter;

	private Bitmap finalPhoto = null;
	private ViewPager mViewPager;
	
	private File photo1File;
	private File photo2File;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		setContentView(R.layout.activity_main);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setProgressBarIndeterminateVisibility(false);

		//Create the two photos when the activity comes up if there are not set yet
		try {
			if(!isImage(photo1File))
				photo1File = this.createImageFile(1,true);
			if(!isImage(photo2File))
				photo2File = this.createImageFile(2,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}*/
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//If photo (1 or 2) is taken
		if(requestCode==1&&resultCode!=0){
			try {
				   Bitmap photo1 = decodeFile(photo1File);
			       FileOutputStream out = new FileOutputStream(photo1File);
			       photo1.compress(Bitmap.CompressFormat.JPEG, 100, out);
			       photo1.recycle();
			} catch (Exception e) {
			       e.printStackTrace();
			}
			((ImageView)findViewById(R.id.imageView1)).setImageURI(Uri.fromFile(photo1File));
			mSectionsPagerAdapter.notifyDataSetChanged();
		}
		else if(requestCode==2&&resultCode!=0) {
			try {
				   Bitmap photo2 = decodeFile(photo2File);
			       FileOutputStream out = new FileOutputStream(photo2File);
			       photo2.compress(Bitmap.CompressFormat.JPEG, 100, out);
			       photo2.recycle();
			} catch (Exception e) {
			       e.printStackTrace();
			}
			((ImageView)findViewById(R.id.imageView2)).setImageURI(Uri.fromFile(photo2File));
			mSectionsPagerAdapter.notifyDataSetChanged();
		}
		else if(requestCode == 11&&resultCode!=0){
			Uri selectedimg = data.getData();			
			try {
				   Bitmap photo1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg);
			       FileOutputStream out = new FileOutputStream(photo1File);
			       photo1.compress(Bitmap.CompressFormat.JPEG, 100, out);
			       photo1.recycle();
			} catch (Exception e) {
			       e.printStackTrace();
			}
			((ImageView)findViewById(R.id.imageView1)).setImageURI(Uri.fromFile(photo1File));
			mSectionsPagerAdapter.notifyDataSetChanged();
		}
		else if(requestCode == 12&&resultCode!=0){
			Uri selectedimg = data.getData();			
			try {
				   Bitmap photo2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg);
			       FileOutputStream out = new FileOutputStream(photo2File);
			       photo2.compress(Bitmap.CompressFormat.JPEG, 100, out);
			       photo2.recycle();
			} catch (Exception e) {
			       e.printStackTrace();
			}
			((ImageView)findViewById(R.id.imageView2)).setImageURI(Uri.fromFile(photo2File));
			mSectionsPagerAdapter.notifyDataSetChanged();
		}
	}
	
	/*************************************************/
	/*******TAKING THE PICTURES***********************/
	/*************************************************/
	public void takePic1(View v){
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo1File));
        startActivityForResult(takePictureIntent, 1);
	}
	public void takePic2(View v){
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo2File));
        startActivityForResult(takePictureIntent, 2);
	}
	
	public void chooseFile1(View v) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");

		// Do this if you need to be able to open the returned URI as a stream
		// (for example here to read the image data).
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		Intent folderIntent = Intent.createChooser(intent, "Select a picture");
		startActivityForResult(folderIntent, IMAGE_SELECTED1);
	}
	
	public void chooseFile2(View v) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");

		// Do this if you need to be able to open the returned URI as a stream
		// (for example here to read the image data).
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		Intent folderIntent = Intent.createChooser(intent, "Select a picture");
		startActivityForResult(folderIntent, IMAGE_SELECTED2);
	}
	
	/*************************************************/
	/*******CANCEL THE PICTURES***********************/
	/*************************************************/
	public void cancelPic1(View v){
		photo1File.delete();
    	try {
			photo1File = createImageFile(1,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	((ImageView)findViewById(R.id.imageView1)).setImageURI(null);
	}
	public void cancelPic2(View v){
		photo2File.delete();
    	try {
			photo2File = createImageFile(2,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	((ImageView)findViewById(R.id.imageView2)).setImageURI(null);
	}
	
	public void cancelPic3(View v){
		((ImageView)findViewById(R.id.imageView3)).setImageBitmap(null);
	}
	
	/*************************************************/
	/*******DO THE JOB********************************/
	/*************************************************/
	public void doTheJob(View v) {
		((ImageButton)findViewById(R.id.button3)).setEnabled(false);
		setProgressBarIndeterminateVisibility(true);
		new DoTheJobASync().execute();
	}

	public void saveFinalPhoto(View v) {
		if(finalPhoto!=null) {
        	File f = null;
			try {
				f = createImageFile(3,false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
			       FileOutputStream out = new FileOutputStream(f);
			       finalPhoto.compress(Bitmap.CompressFormat.JPEG, 90, out);
			} catch (Exception e) {
			       e.printStackTrace();
			}
			Context context = getApplicationContext();
    		CharSequence text = null;
			try {
				text = "File saved : "+f.getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		int duration = Toast.LENGTH_SHORT;

    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();
    	}
    	else {
    		Context context = getApplicationContext();
    		CharSequence text = "Take the pictures first !";
    		int duration = Toast.LENGTH_SHORT;

    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();
    	}
	}
	
	/*****Return if File f is an Image or not*****/
	public boolean isImage(File f) {
		return (f!=null)&&(f.length()>10);
	}
	
	/***********************************************************************/
	/*******Decodes image and scales it to reduce memory consumption********/
	/***********************************************************************/
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
	
	public void updateImage1(){
		if(findViewById(R.id.imageView1)!=null) {	
			if(isImage(photo1File))
				((ImageView)findViewById(R.id.imageView1)).setImageURI(Uri.fromFile(photo1File));
		}
	}
	public void updateImage2(){
		if(findViewById(R.id.imageView2)!=null) {	
			if(isImage(photo2File))
				((ImageView)findViewById(R.id.imageView2)).setImageURI(Uri.fromFile(photo2File));
		}
	}
	public void updateImage3(){
		if(findViewById(R.id.imageView3)!=null) {
			if(finalPhoto!=null)
				((ImageView)findViewById(R.id.imageView3)).setImageBitmap(finalPhoto);
		}
	}
	
	/************************************************************************************/
	/**Create a new File to Pictures/MasterPiece/(cache if isCache=true) and returns it**/
	/************************************************************************************/
	private File getAlbumDir() {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
              storageDir = new File(Environment.getExternalStoragePublicDirectory (
                    Environment.DIRECTORY_PICTURES), "MasterPiece");
              if (storageDir != null) {
                    if (! storageDir.mkdirs()) {
                          if (! storageDir.exists()){
                                Log.d("Create Dir", "failed to create directory CameraSample");
                                return null;
                          }
                    }
              }    
        }
        else {
              Log.v(getString(R.string.app_name),"External storage is not mounted READ/WRITE.");
	    }
        
	    return storageDir;
	}
	private File getAlbumDirCache() {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
              storageDir = new File(Environment.getExternalStoragePublicDirectory (
                    Environment.DIRECTORY_PICTURES), "MasterPiece/cache");
              if (storageDir != null) {
                    if (! storageDir.mkdirs()) {
                          if (! storageDir.exists()){
                                Log.d("Create Dir", "failed to create directory CameraSample");
                                return null;
                          }
                    }
              }    
        }
        else {
              Log.v(getString(R.string.app_name),"External storage is not mounted READ/WRITE.");
	    }
        
	    return storageDir;
	}
	
	private File createImageFile(int i, boolean isCache) throws IOException {
		File image;
		if(isCache) {
		    // Create an file : photoi.jpg
		    image = new File(getAlbumDirCache().getCanonicalPath()+"/photo"+i+JPEG_FILE_SUFFIX);
		}
		else {
			image = File.createTempFile("MasterPiece", JPEG_FILE_SUFFIX, getAlbumDir());
		}
		return image;
	}
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		 @Override
		public int getItemPosition(Object object) {
		    return POSITION_NONE;
		}
		
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(Locale.FRANCE);
			case 1:
				return getString(R.string.title_section2).toUpperCase(Locale.FRANCE);
			case 2:
				return getString(R.string.title_section3).toUpperCase(Locale.FRANCE);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public void onActivityCreated (Bundle savedInstanceState) {
			((MainActivity) getActivity()).updateImage1();
			((MainActivity) getActivity()).updateImage2();
			((MainActivity) getActivity()).updateImage3();
			super.onActivityCreated(savedInstanceState);
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			View returnedView = null;
			
			if(getArguments().getInt(ARG_SECTION_NUMBER)==1) {
				returnedView = inflater.inflate(R.layout.frag1, container, false);
			}
			else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
				returnedView = inflater.inflate(R.layout.frag2, container, false);
			}
			else if(getArguments().getInt(ARG_SECTION_NUMBER)==3){
				returnedView = inflater.inflate(R.layout.result, container, false);
			}
			
			return returnedView;
		}
	}
	
	 public class DoTheJobASync extends AsyncTask<Void, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(Void... params) {
			Bitmap photo1 = decodeFile(photo1File);
	    	Bitmap photo2 = decodeFile(photo2File);
	    	if(photo1==null||photo2==null) {
	    		publishProgress(1);
	    		return null;
	    	}
	    	else {
	        	FaceDetection faceDet1 = new FaceDetection();
	        	FaceDetection faceDet2 = new FaceDetection();
	        	
	        	faceDet1.detectFace(photo1);
	        	faceDet2.detectFace(photo2);
	        	
	        	if(faceDet1.getNumberFaces()== 0) {
	        		publishProgress(-1);
	        	}
	        	else if(faceDet2.getNumberFaces()== 0) {
	        		publishProgress(-2);
	        	}
	            else {
	            	Recalage rec = new Recalage(new BufferedImage(photo1),new BufferedImage(photo2),
	            			faceDet1.getEyeCenterX(),faceDet1.getEyeCenterY(),faceDet1.getEyeDistance(),
	            			faceDet2.getEyeCenterX(),faceDet2.getEyeCenterY(),faceDet2.getEyeDistance(),
	            			(double)photo1.getWidth(),(double)photo2.getWidth(),
	            			(double)photo1.getHeight(),(double)photo2.getHeight());
	            	
	            	finalPhoto = rec.recaler().getBitmap();
	            	return finalPhoto;
	            }
	    	}
	    	photo1.recycle();
	    	photo2.recycle();
	    	
	    	return null;
		}
		
		 protected void onProgressUpdate(Integer... progress) {
			if(progress[0] == 1) {
				Context context = getApplicationContext();
	    		CharSequence text = "Take the pictures first !";
	    		int duration = Toast.LENGTH_SHORT;

	    		Toast toast = Toast.makeText(context, text, duration);
	    		toast.show();
			}
			else if(progress[0] == -1) {
				Context context = getApplicationContext();
        		CharSequence text = "No faces detected in the first photo...";
        		int duration = Toast.LENGTH_SHORT;

        		Toast toast = Toast.makeText(context, text, duration);
        		toast.show();
			}
			else if(progress[0] == -2) {
				Context context = getApplicationContext();
        		CharSequence text = "No faces detected in the second photo...";
        		int duration = Toast.LENGTH_SHORT;

        		Toast toast = Toast.makeText(context, text, duration);
        		toast.show();
			}
		}
		
		protected void onPostExecute(Bitmap result) {			
			setProgressBarIndeterminateVisibility(false);
			((ImageButton)findViewById(R.id.button3)).setEnabled(true);
			
			((ImageView)findViewById(R.id.imageView3)).setImageBitmap(finalPhoto);
	     }
	 }
}

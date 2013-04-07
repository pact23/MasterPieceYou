package com.example.masterpieceyou;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Locale;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ViewPhotos extends Activity {
	
	private final File[] allPhotos = getAlbumDir().listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.toLowerCase(Locale.FRANCE).endsWith(".jpg");
        }
    });
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_photos);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
        
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this,this,allPhotos));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Intent intent = new Intent(); 
	        	intent.setAction(Intent.ACTION_VIEW);
	        	intent.setDataAndType(Uri.fromFile(allPhotos[position]),
				"image/*"); 
	        	startActivity(intent);
	        }
	    });
	    gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	        @Override
	        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
	        	FireMissilesDialogFragment dialog = new FireMissilesDialogFragment(allPhotos[position]);
	        	dialog.show(getFragmentManager(), "Papa");
	        	return true;
	        }
	    });
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_photos, menu);
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
    
    public class FireMissilesDialogFragment extends DialogFragment {
    	private final CharSequence[] actionArray = {"Delete","View"};
    	private final File photo;
    	
    	public FireMissilesDialogFragment(File photo) {
    		this.photo = photo;
    	}
    	
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
        	 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        	    builder.setTitle("Choose an action")
        	           .setItems(actionArray, new DialogInterface.OnClickListener() {
        	               public void onClick(DialogInterface dialog, int which) {
        	            	   if(which==0) {
        	            		   photo.delete();
        	            		   finish();
        	            		   startActivity(getIntent());
        	            		   Toast.makeText(getApplicationContext(), "Deleted : " + photo.getName(), Toast.LENGTH_SHORT).show();
        	            	   }
        	            	   else if(which==1) {
        	            		   	Intent intent = new Intent(); 
	        	       	        	intent.setAction(Intent.ACTION_VIEW);
	        	       	        	intent.setDataAndType(Uri.fromFile(photo),
	        	       				"image/*"); 
	        	       	        	startActivity(intent);
        	            	   }
       	            	   }
        	    });
        	    return builder.create();
        }
    }
}

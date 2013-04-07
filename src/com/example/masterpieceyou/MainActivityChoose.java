package com.example.masterpieceyou;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivityChoose extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_choose);
		
		new AlertDialog.Builder(this)
	    .setTitle("How to")
	    .setMessage("Stand in front of the camera with your head straight and face clear of hair or glasses. \n\nTake the picture, and swap faces!")
	    .show();

		
		final ImageView iv = (ImageView)findViewById(R.id.imageFond);
		ViewTreeObserver vto = iv.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
		    public boolean onPreDraw() {
		        double finalHeight = iv.getMeasuredHeight();
		        double finalWidth = iv.getMeasuredWidth();
		        
		        ImageButton button1 = (ImageButton)findViewById(R.id.imageButton1);
				double h =  280*finalHeight/868;
				double w =  288*finalWidth/640;
				LayoutParams params = new LayoutParams((int)w,(int)h);
				button1.setLayoutParams(params);
				
				ImageButton button2 = (ImageButton)findViewById(R.id.imageButton2);
				h =  276*finalHeight/868;
				w =  295*finalWidth/640;
				params = new LayoutParams((int)w,(int)h);
				params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
				button2.setLayoutParams(params);
				
				ImageButton button3 = (ImageButton)findViewById(R.id.imageButton3);
				h =  212*finalHeight/868;
				w =  640*finalWidth/640;
				params = new LayoutParams((int)w,(int)h);
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
				button3.setLayoutParams(params);
		        return true;
		    }
		});
		
		ImageButton button1 = (ImageButton)findViewById(R.id.imageButton1);
		button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            	myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	getApplicationContext().startActivity(myIntent);
            }
        });
		
		ImageButton button2 = (ImageButton)findViewById(R.id.imageButton2);
		button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(getApplicationContext(), Swap.class);
            	myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	getApplicationContext().startActivity(myIntent);
            }
        });
		
		ImageButton button3 = (ImageButton)findViewById(R.id.imageButton3);
		button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent myIntent = new Intent(getApplicationContext(), ViewPhotos.class);
            	myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	getApplicationContext().startActivity(myIntent);
            }
        });
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_activity_choose, menu);
		return true;
	}*/

}

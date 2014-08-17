package com.example.textcontrols;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements View.OnLongClickListener{
	
	MediaPlayer player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		player = MediaPlayer.create(this, R.raw.final1);
		player.start();
		
		XMLParser xmlParser = new XMLParser();
		InputStream XmlFileInputStream = getResources().openRawResource(R.raw.text1); // getting XML file stream.
		// String[] Input = { "One"," thing"," was"," certain,"," that"," the"," WHITE"," kitten"," had"," had"," nothing"," to"," do"," with"," it:—it"," was"," the"," black"," kitten's"," fault"," entirely."," For"," the"," white"," kitten"," had"," been"," having"," its"," face"," washed"," by"," the"," old"," cat"," for"," the"," last"," quarter"," of"," an"," hour"," (and"," bearing"," it"," pretty"," well,"," considering);"," so"," you"," see"," that"," it"," COULDN'T"," have"," had"," any"," hand"," in"," the"," mischief.","One"," thing"," was"," certain,"," that"," the"," WHITE"," kitten"," had"," had"," nothing"," to"," do"," with"," it:—it"," was"," the"," black"," kitten's"," fault"," entirely."," For"," the"," white"," kitten"," had"," been"," having"," its"," face"," washed"," by"," the"," old"," cat"," for"," the"," last"," quarter"," of"," an"," hour"," (and"," bearing"," it"," pretty"," well,"," considering);"," so"," you"," see"," that"," it"," COULDN'T"," have"," had"," any"," hand"," in"," the"," mischief.","One"," thing"," was"," certain,"," that"," the"," WHITE"," kitten"," had"," had"," nothing"," to"," do"," with"," it:—it"," was"," the"," black"," kitten's"," fault"," entirely."," For"," the"," white"," kitten"," had"," been"," having"," its"," face"," washed"," by"," the"," old"," cat"," for"," the"," last"," quarter"," of"," an"," hour"," (and"," bearing"," it"," pretty"," well,"," considering);"," so"," you"," see"," that"," it"," COULDN'T"," have"," had"," any"," hand"," in"," the"," mischief."}; 
		String[] Input = xmlParser.getWords(XmlFileInputStream);
		
		LinearLayout ly = (LinearLayout) findViewById(R.id.LinearLayout1);
		
		final TextView[] tview = new TextView[1000];
		LinearLayout llAlso = new LinearLayout(this);
		llAlso.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		llAlso.setOrientation(LinearLayout.HORIZONTAL);
	        
		ly.setOrientation(LinearLayout.VERTICAL);
		
		Display display = getWindowManager().getDefaultDisplay();
	    int maxWidth = display.getWidth() - 10;
		int widthsofar=0;
		int k =0;
		
		for(int i=0;i<180;i++)
		{
			tview[i] = new TextView(this);
			tview[i].setText(Input[i]);
			
			tview[i].setOnLongClickListener((OnLongClickListener) this);
			
			tview[i].measure(0, 0);
			
			widthsofar += tview[i].getMeasuredWidth();
			if(widthsofar >= maxWidth)
			{
				widthsofar = tview[i].getMeasuredWidth();
				ly.addView(llAlso);
				k++;
				llAlso = new LinearLayout(this);
				llAlso.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		                LayoutParams.WRAP_CONTENT));
				llAlso.setOrientation(LinearLayout.HORIZONTAL);
				llAlso.addView(tview[i]);
			}
			else
			{
				llAlso.addView(tview[i]);
			}
		
		}
		ly.addView(llAlso);
				
		tview[0].setTextColor(Color.RED);
	
		final Handler handler = new Handler();
		handler.post( new Runnable(){ 
		    private int k = 1;

		    public void run() {
		    	tview[k-1].setTextColor(Color.BLACK);
				tview[k].setTextColor(Color.CYAN);
		        
				k++;
		        if( k < 180 )
		        {
		            // Here `this` refers to the anonymous `Runnable`
		            handler.postDelayed(this, 500);
		        }
		    }
		});  
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mymenu, menu);		
		return true;		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case R.id.item1:
			player.start();
			break;
		case R.id.item2:
			player.pause();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onLongClick(View v)
	{
		TextView tv = (TextView) v;
	    Toast.makeText(this,"Meaning of "+tv.getText().toString(),Toast.LENGTH_LONG).show();
	    return true;
	}


}

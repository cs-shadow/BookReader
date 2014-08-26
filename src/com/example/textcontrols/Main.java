package com.example.textcontrols;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;


public class Main extends Activity implements View.OnLongClickListener{
	
	MediaPlayer player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		XMLParser xmlParser = new XMLParser();
		InputStream XmlFileInputStream = getResources().openRawResource(R.raw.text2); // getting XML file stream.
		// String[] Input = { "One"," thing"," was"," certain,"," that"," the"," WHITE"," kitten"," had"," had"," nothing"," to"," do"," with"," it:—it"," was"," the"," black"," kitten's"," fault"," entirely."," For"," the"," white"," kitten"," had"," been"," having"," its"," face"," washed"," by"," the"," old"," cat"," for"," the"," last"," quarter"," of"," an"," hour"," (and"," bearing"," it"," pretty"," well,"," considering);"," so"," you"," see"," that"," it"," COULDN'T"," have"," had"," any"," hand"," in"," the"," mischief.","One"," thing"," was"," certain,"," that"," the"," WHITE"," kitten"," had"," had"," nothing"," to"," do"," with"," it:—it"," was"," the"," black"," kitten's"," fault"," entirely."," For"," the"," white"," kitten"," had"," been"," having"," its"," face"," washed"," by"," the"," old"," cat"," for"," the"," last"," quarter"," of"," an"," hour"," (and"," bearing"," it"," pretty"," well,"," considering);"," so"," you"," see"," that"," it"," COULDN'T"," have"," had"," any"," hand"," in"," the"," mischief.","One"," thing"," was"," certain,"," that"," the"," WHITE"," kitten"," had"," had"," nothing"," to"," do"," with"," it:—it"," was"," the"," black"," kitten's"," fault"," entirely."," For"," the"," white"," kitten"," had"," been"," having"," its"," face"," washed"," by"," the"," old"," cat"," for"," the"," last"," quarter"," of"," an"," hour"," (and"," bearing"," it"," pretty"," well,"," considering);"," so"," you"," see"," that"," it"," COULDN'T"," have"," had"," any"," hand"," in"," the"," mischief."}; 
		final XMLWords[] Input = xmlParser.getWords(XmlFileInputStream);
		final int textLength = Input.length;
		
		LinearLayout ly = (LinearLayout) findViewById(R.id.LinearLayout1);
		
		final TextView[] tview = new TextView[textLength];
		LinearLayout llAlso = new LinearLayout(this);
		llAlso.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		llAlso.setOrientation(LinearLayout.HORIZONTAL);
	        
		ly.setOrientation(LinearLayout.VERTICAL);
		
		Display display = getWindowManager().getDefaultDisplay();
	    int maxWidth = display.getWidth() - 10;
		int widthsofar=0;
		int k =0;
		
		for(int i=0;i<textLength;i++)
		{
			tview[i] = new TextView(this);
			tview[i].setText(Input[i].token + " ");
			
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
				
		player = MediaPlayer.create(this, R.raw.final2);
		player.start();
	
		final Handler handler = new Handler();
		handler.post( new Runnable(){ 
		    private int k = -1;

		    public void run() {
		    	if (k == -1) {
		    		k++;
		    		handler.postDelayed(this, (9000));
		    	}
		    	
		    	else {
		    		if (k > 0) {
		    			tview[k-1].setTextColor(Color.BLACK);
		    		}
		    		tview[k].setTextColor(Color.CYAN);

		    		int textDuration = (Input[k+1].startTime - Input[k].startTime) * 10 ;

		    		k++;
		    		if( k < textLength )
		    		{
		    			// Here `this` refers to the anonymous `Runnable`
		    			handler.postDelayed(this, textDuration);
		    		}
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
		
	    Toast.makeText(this, getMeaning(tv.getText().toString(), getBaseContext()),Toast.LENGTH_LONG).show();
	    
	    return true;
	}
	
	protected String getMeaning(String word, Context context)
	{
		InputStream jsonInputStream = null;
		String filename = "gcide_" + word.charAt(0) + ".json";
		// Capitalize word according to dictionary format.
		word = Character.toUpperCase(word.charAt(0)) + word.substring(1);
		// Remove last character, i.e. space from word.
		word = word.substring(0, word.length()-1);
		try {
			jsonInputStream = context.getAssets().open(filename);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String jsonString = convertStreamToString(jsonInputStream);
		
		try {
			JSONObject root = new JSONObject(jsonString);
			String meaning = root.getString(word);
			return meaning;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Meaning not found";
	}

	protected String convertStreamToString(java.io.InputStream is) {
	    @SuppressWarnings("resource")
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
}

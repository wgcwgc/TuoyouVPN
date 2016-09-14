package com.runcom.tuoyouvpn.business;

import com.runcom.tuoyouvpn.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Business extends Activity
{

	private Intent intent;
//	private int result;

	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business);
		intent = getIntent();
		String contents = intent.getStringExtra("account");
		
		textView = (TextView) findViewById(R.id.textView1);
		textView.setText(contents);
		
	}
}

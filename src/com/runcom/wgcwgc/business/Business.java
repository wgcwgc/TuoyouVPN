package com.runcom.wgcwgc.business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.runcom.wgcwgc.R;

public class Business extends Activity
{

	private Intent intent;
	// private int result;
	private String contents;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business);

		intent = getIntent();

		contents = "login:\t" + intent.getStringExtra("login") + "\n";
		contents += "pass:\t" + intent.getStringExtra("pass") + "\n";

		contents += "reslut:\t" + intent.getLongExtra("result" , -1) + "\n";
		// System.out.println(intent.getLongExtra("result" ,-1));
		// contents += "reslut:\t" + intent.getIntExtra("result" , -1) + "\n";
		contents += "mesg:\t" + intent.getStringExtra("mesg") + "\n";
		contents += "uid:\t" + intent.getStringExtra("uid") + "\n";
		contents += "expire:\t" + intent.getStringExtra("expire") + "\n";
		contents += "freetime:\t" + intent.getStringExtra("freetime") + "\n";
		contents += "flow:\t" + intent.getStringExtra("flow") + "\n";
		contents += "score:\t" + intent.getStringExtra("score") + "\n";
		contents += "coupon:\t" + intent.getStringExtra("coupon") + "\n";
		contents += "type:\t" + intent.getStringExtra("type") + "\n";
		contents += "email:\t" + intent.getStringExtra("email") + "\n";
		contents += "session:\t" + intent.getStringExtra("session") + "\n";

		textView = (TextView) findViewById(R.id.textView1);
		textView.setText(contents);

	}
}

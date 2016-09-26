package com.runcom.wgcwgc.business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.runcom.wgcwgc.R;

public class Business extends Activity
{

	private Intent intent;
	private String contents;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business);

		Toast.makeText(this ,"µÇÂ¼³É¹¦£¡£¡£¡" ,Toast.LENGTH_LONG).show();

		intent = getIntent();

		contents = "login:\t" + intent.getStringExtra("login") + "\n";
		contents += "pass:\t" + intent.getStringExtra("pass") + "\n";

		contents += "reslut:\t" + intent.getLongExtra("result" , -1) + "\n";
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu )
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.business ,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item )
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id)
		{
			case R.id.business_personInfromation:
				getPersonInfromation();
				break;

			case R.id.business_bind:
				bind();
				break;

			case R.id.business_cheakNewVersion:
				cheakNewVersion();
				break;

			case R.id.business_aboutUs:
				aboutUs();
				break;

			case R.id.business_settings:
				setting();

			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void getPersonInfromation()
	{
		// TODO Auto-generated method stub

	}

	private void bind()
	{
		Toast.makeText(this ,"bind()" ,Toast.LENGTH_LONG).show();
		Intent intent = new Intent();
		intent.setClass(Business.this ,Bind.class);
		startActivity(intent);
	}

	private void cheakNewVersion()
	{
		// TODO Auto-generated method stub
		
	}

	private void aboutUs()
	{
		// TODO Auto-generated method stub

	}

	private void setting()
	{
		// TODO Auto-generated method stub

	}
}

package com.runcom.tuoyouvpn.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.runcom.tuoyouvpn.R;
import com.runcom.tuoyouvpn.main.MainActivity;

public class Register extends Activity
{

	private EditText editText_login , editText_password ,
	        editText_password_resure , editText_number , editText_email ,
	        editText_chkcode;
	@SuppressWarnings("unused")
	private String login , password , password_resure , number , email ,
	        chkcode;

	@Override
	protected void onCreate(Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

	}

	public void okButtonClick(View view )
	{
		editText_login = (EditText) findViewById(R.id.register_login);
		editText_password = (EditText) findViewById(R.id.register_password);
		editText_password_resure = (EditText) findViewById(R.id.register_password_resure);
		editText_number = (EditText) findViewById(R.id.register_number);
		editText_email = (EditText) findViewById(R.id.register_email);
		editText_chkcode = (EditText) findViewById(R.id.register_chkcode);

		login = editText_login.getText().toString();
		password = editText_password.getText().toString();
		password_resure = editText_password_resure.getText().toString();
		number = editText_number.getText().toString();
		email = editText_email.getText().toString();
		chkcode = editText_chkcode.getText().toString();

		String contents = "Çë×ÐÏ¸ºË¶ÔÐÅÏ¢\nÕËºÅ£º\n\t\t\t\t" + login + "\nÃÜÂë£º\n\t\t\t\t" + password + "\nÊÖ»úºÅÂë£º\n\t\t\t\t" + number + "\nÓÊÏä£º\n\t\t\t\t" + email + "\nÑéÖ¤Âë£º\n\t\t\t\t" + chkcode + "\n\t\t\t\t";
		// System.out.println(contents);
		Toast.makeText(this ,contents ,Toast.LENGTH_LONG).show();

		if(true)
		{
			// System.out.println("¹§Ï²Äú£º×¢²á³É¹¦£¡£¡£¡");
			Toast.makeText(this ,"¹§Ï²Äú£º " + "\n\t\t\t\t×¢²á³É¹¦£¡£¡£¡" ,Toast.LENGTH_LONG).show();
			finish();
		}

	}

	public void cancelButtonClick(View view )
	{
		Intent intent = new Intent();
		intent.setClass(Register.this ,MainActivity.class);
		startActivity(intent);
		Register.this.closeOptionsMenu();
		finish();
	}

}

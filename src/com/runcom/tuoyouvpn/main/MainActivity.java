package com.runcom.tuoyouvpn.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.runcom.tuoyouvpn.R;
import com.runcom.tuoyouvpn.business.Business;
import com.runcom.tuoyouvpn.md5.MD5;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity
{

	private EditText editText_account , editText_password;

	private String account = null;
	private String password = null;

	@Override
	protected void onCreate(Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText_account = (EditText) findViewById(R.id.main_editText_account);
		editText_password = (EditText) findViewById(R.id.main_editText_password);

	}

	/**
	 * 
	 * @param view
	 *            button && activit_main's call and user achieves login
	 */
	public void login(View view )
	{
		editText_account.setText("runcomtest");
		editText_password.setText("123456");
		account = editText_account.getText().toString();
		password = editText_password.getText().toString();

		String contents = "account: " + account + "\npassword: " + password;
		Log.d("LOG" ,contents);
		Toast.makeText(this ,contents ,Toast.LENGTH_LONG).show();

		if(localJudges(account ,password))
		{
			serverJudges(account ,password);

		}
		else
		{
			Toast.makeText(this ,"username or password is wrong." ,Toast.LENGTH_LONG).show();
		}
	}

	void serverJudges(String account , String password )
	{
		GetThread getThread = new GetThread(account , password);
		getThread.start();

	}

	public String toURLEncoded(String paramString )
	{
		if(paramString == null || paramString.equals(""))
		{
			// LogD("toURLEncoded error:"+paramString);
			Log.d("LOG" ,"toURLEncoded error:" + paramString);
			return "";
		}

		try
		{
			String str = new String(paramString.getBytes() , "UTF-8");
			str = URLEncoder.encode(str ,"UTF-8");
			Log.d("LOG" ,str);
			return str;
		}
		catch(Exception localException)
		{
			// LogE("toURLEncoded error:"+paramString, localException);
			Log.d("LOG" ,"toURLEncoded error:" + paramString);
		}

		return "";
	}

	/**
	 * ���̣߳�ͨ��GET����������������û������������Ϣ
	 * 
	 * @author Administrator
	 * 
	 */

	class GetThread extends Thread
	{

		String account;
		String password;

		public GetThread(String account , String password)
		{
			this.account = account;
			this.password = password;
		}

		@Override
		public void run()
		{
			// ��HttpClient�������󣬷�Ϊ�岽
			// ��һ��������HttpClient����
			@SuppressWarnings("resource")
			HttpClient httpClient = new DefaultHttpClient();
			// https://a.redvpn.cn:8443/interface/
			String signValu = "tuoyouvpn" + account + password;
			signValu = new MD5().md5(signValu);
			String url = "https://a.redvpn.cn:8443/interface/dologin.php?login=" + account + "&pass=" + password + "&sign" + signValu;
			// �ڶ�����������������Ķ���,�����Ƿ��ʵķ�������ַ
			System.out.println(url);
			
			HttpGet httpGet = new HttpGet(url);
			try
			{
				// ��������ִ�����󣬻�ȡ��������������Ӧ����
				HttpResponse response = httpClient.execute(httpGet);
				// ���Ĳ��������Ӧ��״̬�Ƿ����������״̬���ֵ��200��ʾ����
				if(response.getStatusLine().getStatusCode() == 200)
				{
					// ���岽������Ӧ������ȡ�����ݣ��ŵ�entity����
					HttpEntity entity = response.getEntity();
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
					String result = reader.readLine();
					Log.d("LOG" ,"GET:" + result);
					System.out.println(result);
					Toast.makeText(MainActivity.this ,result ,Toast.LENGTH_LONG).show();
					Intent intent = new Intent();
					intent.setClass(MainActivity.this ,Business.class);
					intent.putExtra("account" ,account);
					startActivity(intent);
					// finish();

				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}

	}

	/**
	 * 
	 * @param account
	 *            String && The account of user's input
	 * @param password
	 *            String && The password of user's input
	 * @return boolean && local judge account's and password's legitimacy
	 */
	@SuppressLint("DefaultLocale")
	boolean localJudges(String account , String password )
	{
		if(password.length() > 10 || password.length() < 4)
		{
			// System.out.println(password.length());
			return false;
		}

		char [] accountArray = account.trim().toLowerCase().toCharArray();
		char temp;
		int length = accountArray.length;

		for(int i = 0 ; i < length ; i ++ )
		{
			temp = accountArray[i];
			if(temp >= 'a' && temp <= 'z' || temp >= '0' && temp <= '9')
			{
			}
			else
			{
				return false;
			}

		}

		return true;
	}

	/**
	 * 
	 * @param view
	 */
	public void register(View view )
	{
		Toast.makeText(this ,"register" ,Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu )
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main ,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item )
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

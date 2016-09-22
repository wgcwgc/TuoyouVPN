package com.runcom.wgcwgc.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.runcom.wgcwgc.R;
import com.runcom.wgcwgc.business.Business;
import com.runcom.wgcwgc.configure.Configure;
import com.runcom.wgcwgc.configure.GetConfigure;
import com.runcom.wgcwgc.md5.MD5;
import com.runcom.wgcwgc.register.Register_01;
import com.runcom.wgcwgc.web.SSLSocketFactoryEx;

public class MainActivity extends Activity
{
	public Configure getConfigure_set;

	EditText editText_account , editText_password;

	String account = null;
	String password = null;

	String app;
	String ver;
	String build;

	int term = 0;
	String os = Build.VERSION.RELEASE;
	String dev;
	// private int isbreak;
	String lang = Locale.getDefault().getLanguage();
	int market = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText_account = (EditText) findViewById(R.id.main_editText_account);
		editText_password = (EditText) findViewById(R.id.main_editText_password);

		// String packageName = this.getPackageName();

		// Log.d("LOG" ,"包名：" + packageName);
		getConfigure_set = new Configure();
		try
		{
			PackageManager packageManager = this.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName() ,0);
			int labelRes = packageInfo.applicationInfo.labelRes;

			// Log.d("LOG" ,"start************************");
			String versionName = packageInfo.versionName;
			// int versionCode = packageInfo.versionCode;
			// Log.d("LOG" ,"版本名：" + versionName);
			// Log.d("LOG" ,"版本号：" + versionCode);
			// getConfigure_set.setApp(getResources().getString(R.string.app_name));
			getConfigure_set.setApp(this.getResources().getString(labelRes));
			getConfigure_set.setVer(versionName);
			getConfigure_set.setBuild("57");
		}
		catch(NameNotFoundException e)
		{
			Log.d("LOG" ,"set version code bug");
		}
		new GetConfigure(this).start();

		// getConfigure = new Configure();
		// int term = getConfigure_set.getTerm();
		// String os = getConfigure_set.getOs();
		// String dev = getConfigure_set.getDev();
		app = getConfigure_set.getApp();
		ver = getConfigure_set.getVer();
		build = getConfigure_set.getBuild();

		// int isbreak = getConfigure_set.getIsbreak();
		// String lang = getConfigure_set.getLang();
		// int market = getConfigure_set.getMarket();

		// Log.d("LOG" ,"term:" + term + "\nos:" + os + "\ndev:" + dev +
		// "\napp:" + app + "\nver:" + ver + "\nbuild:" + build + "\nisbreak:" +
		// isbreak + "\nlang:" + lang + "\nmarket:" + market);
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
			Toast.makeText(this ,"Username or password is wrong.\nPlease try again!" ,Toast.LENGTH_LONG).show();
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
	 * 子线程：通过GET方法向服务器发送用户名、密码的信息
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

		@SuppressLint("DefaultLocale")
		@Override
		public void run()
		{
			// 用HttpClient发送请求，分为五步
			// HttpClient httpClient = new DefaultHttpClient();
			HttpClient httpClient = SSLSocketFactoryEx.getNewHttpClient();// getNewHttpClient

			// https://a.redvpn.cn:8443/interface/
			// *************************************************************************************************************************************

			// term = 0;
			// os = Build.VERSION.RELEASE;
			dev = android.provider.Settings.Secure.getString(MainActivity.this.getContentResolver() ,android.provider.Settings.Secure.ANDROID_ID);

			String signValu = "tuoyouvpn" + app + build + dev + lang + account + market + os + password + term + ver;
			signValu = new MD5().md5(signValu).toUpperCase();
			// Log.d("LOG" ,signValu);
			String url = "https://a.redvpn.cn:8443/interface/dologin.php?login=" + account + "&pass=" + password + "&app=" + app + "&build=" + build + "&dev=" + dev + "&lang=" + lang + "&market=" + market + "&os=" + os + "&term=" + term + "&ver=" + ver + "&sign=" + signValu;
			// 第二步：创建代表请求的对象,参数是访问的服务器地址
			// url = toURLEncoded(url);
			// System.out.println(url);
			Log.d("LOG" ,url);
			// System.out.println(new
			// MD5().md5("runcom8888123@abc.comxyz9.3.26666").toUpperCase());
			HttpGet httpGet = new HttpGet(url);
			try
			{
				// 第三步：执行请求，获取服务器发还的相应对象
				HttpResponse response = httpClient.execute(httpGet);
				// System.out.println("test00");
				// 第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
				if(response.getStatusLine().getStatusCode() == 200)
				{
					// 第五步：从相应对象当中取出数据，放到entity当中
					// System.out.println("test01");
					HttpEntity entity = response.getEntity();
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
					String json_result = reader.readLine();
					JSONObject jsonObject = new JSONObject(json_result);

					// String result = jsonObject.getString("result");
					Long result = jsonObject.getLong("result");
					String mesg = jsonObject.getString("mesg");
					String uid = jsonObject.getString("uid");
					String expire = jsonObject.getString("expire");
					String freetime = jsonObject.getString("freetime");
					String flow = jsonObject.getString("flow");
					String score = jsonObject.getString("score");
					String coupon = jsonObject.getString("coupon");
					String type = jsonObject.getString("type");
					String email = jsonObject.getString("email");
					String session = jsonObject.getString("session");

					Log.d("LOG" ,json_result);
					// Log.d("LOG" ,result.toString());

					// System.out.println(result);
					// Toast.makeText(MainActivity.this ,result
					// ,Toast.LENGTH_LONG).show();
					Intent intent = new Intent();
					intent.setClass(MainActivity.this ,Business.class);
					intent.putExtra("login" ,account);
					intent.putExtra("pass" ,password);

					intent.putExtra("result" ,result);
					intent.putExtra("mesg" ,mesg);
					intent.putExtra("uid" ,uid);
					intent.putExtra("expire" ,expire);
					intent.putExtra("freetime" ,freetime);
					intent.putExtra("flow" ,flow);
					intent.putExtra("score" ,score);
					intent.putExtra("coupon" ,coupon);
					intent.putExtra("type" ,type);
					intent.putExtra("email" ,email);
					intent.putExtra("session" ,session);

					startActivity(intent);
					finish();
					// System.out.println("test02");

				}
			}
			catch(Exception e)
			{
				System.out.println("http_bug");
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
	 *            button && activit_main's call and user achieves register
	 */
	public void register(View view )
	{
		// Toast.makeText(this ,"register" ,Toast.LENGTH_LONG).show();
		Intent intent = new Intent();
		intent.setClass(MainActivity.this ,Register_01.class);
		startActivity(intent);
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

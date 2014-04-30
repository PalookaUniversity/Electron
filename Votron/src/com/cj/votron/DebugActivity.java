package com.cj.votron;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;

public class DebugActivity extends Activity {


	EditText editText;
	Config config;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);

		initialize();
	}


	void initialize(){

		editText = (EditText)findViewById(R.id.display);
		config = Config.getInstance();

	}


	public void dbg1Pressed(View view){
		System.out.println("Pressed DBG1");
		String result = config.debug1("foo", DebugActivity.this);
		editText.setText(result);
	}

	public void dbg2Pressed(View view){
		System.out.println("Pressed DBG2");
		String result = config.debug2("bar", DebugActivity.this);
		editText.setText(result);
	}

	public void dbg3Pressed(View view){
		System.out.println("Pressed DBG3");
		String result = config.debug3("baz", DebugActivity.this);
		editText.setText(result);
	}
}
package com.example.voter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class CheckOut extends Activity{

    LinearLayout linearlayout, rootlayout;
    Visualization checkCircle;
    ScrollView scrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent = getIntent();
		
		scrollView = new ScrollView(this);
        linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        
        checkCircle = new Visualization(this,(Integer) intent.getSerializableExtra("ID"));
        linearlayout.addView(checkCircle);
        setContentView(linearlayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_out, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
	        Intent intent = new Intent(this,MainCode.class);
	        startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}
}

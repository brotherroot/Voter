package com.example.voter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Join extends Activity {
	EditText inputvoteidEditText;
	Button confirmButton;
	
	@Override
	public void onCreate(Bundle saveInstanceState) {
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);
		inputvoteidEditText = (EditText)findViewById(R.id.get_voteid);
		confirmButton = (Button)findViewById(R.id.button_ok);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int voteid = Integer.parseInt(inputvoteidEditText.getText().toString());
				//Vote status = WebAccess.getResult(voteid);
			}
		});
	}
}

package com.example.changshuai.microuniversity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SearchActivity extends Activity {

	
	EditText search_name ;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.search);
		 
		search_name = (EditText) this.findViewById(R.id.so_name);
  
		Button btn = (Button) findViewById(R.id.button1) ;
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

	      	  Intent all = new Intent(SearchActivity.this, SearchListActivity.class);
	          all.putExtra("search_name", search_name.getText().toString().trim() );
	          startActivity(all);
			}
		}) ;
		
	}
	
	
}

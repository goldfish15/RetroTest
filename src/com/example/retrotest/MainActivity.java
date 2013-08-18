package com.example.retrotest;

import java.util.ArrayList;
import java.util.List;

import com.origamilabs.library.views.StaggeredGridView;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String API_URL = "http://api.ezypeezy.com/1";
	private API api;
	private RestAdapter restAdapter;
	private static int pageNum = 1;
	ArrayList<String> arrayList = new ArrayList<String>();
	ArrayList<Business> businessArrayList = new ArrayList<Business>();
	//StaggeredAdapter arrayAdapter;
	Response<Business> response;
	//StaggeredGridView businessListView;
	StaggeredAdapter arrayAdapter;
	
	interface API {
		@Headers({
			"Authorization: Token token=350e87e94060be22ebbcc5ddb5c368f1",
			"LOCATION: -36.910644,174.742481"
		})
		@GET("/businesses")
	    Response<Business> listBusinesses(@Query("page")int pageNum, @Query("per")int perPage);
	}
	
	//@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
					 
		StaggeredGridView businessListView = (StaggeredGridView) findViewById(R.id.staggeredGridView1);
        int margin = 40;//getResources().getDimensionPixelSize(20);
		businessListView.setItemMargin(20);
		businessListView.setPadding(margin,  0, margin, 0);
		arrayAdapter = new StaggeredAdapter(this, businessArrayList);
		businessListView.setAdapter(arrayAdapter);
		arrayAdapter.notifyDataSetChanged();
		
	}
	
	 private class SetUpRetrofitTask extends AsyncTask<Void, Void, Void> {
		List<Business> businesses;
        @Override
        protected Void doInBackground(Void... params) {
	        // Debug the task thread name
	        Log.d("ITCRssReader", Thread.currentThread().getName());
	         
	        try {
	        	api = restAdapter.create(API.class);  
	        	response = api.listBusinesses(pageNum, 10);	  
	        	pageNum++;
	        	businesses = response.response;
	        	
			    for (Business business : businesses) {
			    	businessArrayList.add(business);
			    }
	   		 } catch (Exception e) {System.out.println("Error: " + e.toString());}
	         //
	        return null;
        }
        @Override
        protected void onPostExecute(Void result) {
        	arrayAdapter.notifyDataSetChanged();
        }
 	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 public void buttonPress(View v)
	    { 
	        if(v.getId() == R.id.button1)
	        {
	            MessageBox("Grabbing more businesses");
	            getMoreBusinesses();
	        }       
	    }
	 
	 public void MessageBox(String message) {
	       Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	    } 
	 
	 
	 
	 protected void getMoreBusinesses() {
		 restAdapter = new RestAdapter.Builder()
	        .setServer(API_URL)
	        .build();								
			SetUpRetrofitTask task = new SetUpRetrofitTask();	
			task.execute();
	 }
}

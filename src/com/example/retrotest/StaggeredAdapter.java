package com.example.retrotest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.origamilabs.library.views.StaggeredGridView.OnItemClickListener;
import com.origamilabs.library.views.StaggeredGridView.OnItemLongClickListener;



public class StaggeredAdapter extends BaseAdapter {
	Context context;
	int layoutResourceId;
	ArrayList<Business> data = new ArrayList<Business>();
	ArrayList<Bitmap> imageArray = new ArrayList<Bitmap>();
	public ImageLoader imageLoader;
	
    OnItemClickListener mOnItemClickListener;
    OnItemLongClickListener mOnItemLongClickListener;
	
	public StaggeredAdapter(Context context, ArrayList<Business> data) {
		this.context = context;
		this.data = data;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View gridView;
		//RecordHolder holder = null;
		
		if (convertView == null) {
			
			gridView = new View(context);
			
			gridView = inflater.inflate(R.layout.business_view, null);
			
		} else {
			gridView = (View) convertView;
		}
		
		TextView textView = (TextView) gridView.
				findViewById(R.id.textView1);
		textView.setText(data.get(position).name + "\n" + data.get(position).distance + "m");
		
		ImageView image=(ImageView)gridView.findViewById(R.id.imageView1);
        imageLoader.DisplayImage(data.get(position).logo_url, image);
		
		//LoadBusinessLogos task = new LoadBusinessLogos();
		//task.execute(gridView, position);
		
		
		
		return gridView;
	}
	
	private class LoadBusinessLogos extends AsyncTask<Object, Void, Void> {
		@Override
		protected Void doInBackground(Object... params) {
			// Crashing when loading business Logos
			try {
				View gridView = (View) params[0];
				int position = (Integer) params[1];
				ImageView imageView = (ImageView) gridView.
						findViewById(R.id.imageView1);
				Bitmap bitmap = BitmapFactory.decodeStream((InputStream)
						new URL(data.get(position).logo_url).getContent());
				imageView.setImageBitmap(bitmap); 
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}

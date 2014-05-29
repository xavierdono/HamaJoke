package com.xav.hamajoke.listener;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.xav.hamajoke.R;

public class OnItemClickListenerListView implements OnItemClickListener {

	private MediaPlayer p;
	
	public OnItemClickListenerListView(MediaPlayer p) {
		this.p = p;
		
		if(this.p == null) {
			this.p = new MediaPlayer();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		TextView textViewItem = ((TextView) view
				.findViewById(R.id.textViewItem));

		String listItemId = textViewItem.getTag().toString();
		
		
		try {
			this.p.setDataSource(listItemId);
			this.p.prepare();
			this.p.start();

		} catch (Exception e) {
			
			Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			
			this.p.release();
			this.p = null;
		}
	}
}

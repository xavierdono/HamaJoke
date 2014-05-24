package com.xav.hamajoke.listener;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.xav.hamajoke.R;

public class OnItemClickListenerListView implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		TextView textViewItem = ((TextView) view
				.findViewById(R.id.textViewItem));

		String listItemId = textViewItem.getTag().toString();

		MediaPlayer p = new MediaPlayer();
		try {
			p.setDataSource(listItemId);
			p.prepare();
			p.start();

			p.release();
			p = null;
		} catch (Exception e) {
			p.release();
			p = null;
		}
	}
}

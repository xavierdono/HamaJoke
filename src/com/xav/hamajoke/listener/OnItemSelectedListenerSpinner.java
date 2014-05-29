package com.xav.hamajoke.listener;

import java.io.File;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xav.hamajoke.R;
import com.xav.hamajoke.activity.MainActivity;
import com.xav.hamajoke.adapter.ArrayAdapterItem;
import com.xav.hamajoke.domain.ObjectItem;

public class OnItemSelectedListenerSpinner implements OnItemSelectedListener {

	private MainActivity mainActivity;
	private MediaPlayer p;

	public OnItemSelectedListenerSpinner(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		try {
			TextView textViewItem = ((TextView) view
					.findViewById(R.id.textViewItem));

			String listItemId = textViewItem.getTag().toString();

			ListView contextChooser = (ListView) this.mainActivity
					.findViewById(R.id.listViewNom);

			File f = new File(listItemId);
			ObjectItem[] items = new ObjectItem[f.list().length];

			for (int i = 0; i < items.length; i++) {
				items[i] = new ObjectItem(listItemId.concat("/"
						+ f.list()[i].toString()), f.list()[i].toString());
			}

			ArrayAdapterItem listViewAdapter = new ArrayAdapterItem(
					view.getContext(), R.layout.list_view_row_item, items);
			listViewAdapter
					.setDropDownViewResource(android.R.layout.simple_selectable_list_item);

			contextChooser
					.setOnItemClickListener(new OnItemClickListenerListView(p));
			contextChooser.setAdapter(listViewAdapter);
		} catch (Exception e) {
			Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		p.release();
		p = null;
	}
}

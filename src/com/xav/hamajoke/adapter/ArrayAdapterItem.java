package com.xav.hamajoke.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.xav.hamajoke.R;
import com.xav.hamajoke.domain.ObjectItem;

public class ArrayAdapterItem extends ArrayAdapter<ObjectItem> {
	
	Context mContext;
	int layoutResourceId;
	ObjectItem data[] = null;

	public ArrayAdapterItem(Context mContext, int layoutResourceId,
			ObjectItem[] data) {

		super(mContext, layoutResourceId, data);

		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		ObjectItem objectItem = data[position];

		TextView textViewItem = (TextView) convertView
				.findViewById(R.id.textViewItem);
		textViewItem.setText(objectItem.getName());
		textViewItem.setTag(objectItem.getFullname());

		return convertView;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		ObjectItem objectItem = data[position];

		TextView textViewItem = (TextView) convertView
				.findViewById(R.id.textViewItem);
		textViewItem.setText(objectItem.getName());
		textViewItem.setTag(objectItem.getFullname());

		return convertView;
	}
}

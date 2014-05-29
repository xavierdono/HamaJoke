package com.xav.hamajoke.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.xav.hamajoke.R;
import com.xav.hamajoke.adapter.ArrayAdapterItem;
import com.xav.hamajoke.domain.ObjectItem;

public class OptionsActivity extends Activity {

	private String PATH_HAMA = "";
	private static final int FILE_SELECT_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);

		this.PATH_HAMA = MainActivity.PATH_HAMA;

		loadSpinner();
	}

	public void save(View view) {

		try {
			EditText textViewItem = (EditText) this.findViewById(R.id.txtRep);

			if (textViewItem.getText().toString().trim().length() != 0) {

				File f = new File(this.PATH_HAMA + "/"
						+ textViewItem.getText().toString().trim());

				if (!f.exists()) {
					f.mkdir();
					Toast.makeText(view.getContext(), "Répertoire créé !",
							Toast.LENGTH_LONG).show();

					setResult(RESULT_OK);
				} else {
					Toast.makeText(view.getContext(),
							"Le répertoire existe déjà !", Toast.LENGTH_LONG)
							.show();
				}
			}
			else
			{
				Toast.makeText(view.getContext(),
						"Veuillez saisir un nom de répertoire !", Toast.LENGTH_LONG)
						.show();
			}
		} catch (Exception e) {
			Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
	}

	public void importFile(View view) {

		Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
		fileIntent.setType("file/*");
		fileIntent.addCategory(Intent.CATEGORY_OPENABLE);

		try {
			startActivityForResult(
					Intent.createChooser(fileIntent, "Sélectionnez un fichier"),
					FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "SVP, installer un gestionnaire de fichier.",
					Toast.LENGTH_LONG).show();
		}
	}

	private void loadSpinner() {

		try {
			File f = new File(PATH_HAMA);

			if (f.list().length != 0) {
				ObjectItem[] items = new ObjectItem[f.list().length];

				for (int i = 0; i < items.length; i++) {
					items[i] = new ObjectItem(PATH_HAMA.concat("/"
							+ f.list()[i].toString()), f.list()[i].toString());
				}

				ArrayAdapterItem spinnerAdapter = new ArrayAdapterItem(this,
						R.layout.list_view_row_item, items);
				spinnerAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				Spinner contextChooser = (Spinner) findViewById(R.id.spinnerName);
				contextChooser.setOnItemSelectedListener(new OnItemSelectedListener() 
		        {
					@Override
		            public void onItemSelected(AdapterView<?> parent, View v,int position, long id)
		            {
		            	PATH_HAMA = ((ObjectItem) ((Spinner) v.findViewById(R.id.spinnerName)).getItemAtPosition(position)).getFullname();
		            }
		            
		            @Override
		            public void onNothingSelected(AdapterView<?> parent) {		                
		            }
		        });
				contextChooser.setAdapter(spinnerAdapter);
			}
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case FILE_SELECT_CODE:
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();

				try {
					copy(uri.getPath(), PATH_HAMA + "/" + new File(uri.getPath()).getName());
				} catch (IOException e) {
					Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG)
							.show();
				}
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void copy(String src, String dst) throws IOException {
		FileInputStream inStream = new FileInputStream(src);
		FileOutputStream outStream = new FileOutputStream(dst);
		FileChannel inChannel = inStream.getChannel();
		FileChannel outChannel = outStream.getChannel();
		inChannel.transferTo(0, inChannel.size(), outChannel);
		inStream.close();
		outStream.close();
	}
}
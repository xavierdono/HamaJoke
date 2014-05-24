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
import android.widget.TextView;
import android.widget.Toast;

import com.xav.hamajoke.R;

public class OptionsActivity extends Activity {

	private String path = "";
	private static final int FILE_SELECT_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);

		Intent intent = getIntent();
		this.path = intent.getStringExtra(MainActivity.PATH_HAMA);
	}

	public void save(View view) {

		try {
			TextView textViewItem = (TextView) view.findViewById(R.id.txtRep);

			File f = new File(this.path + "/" + textViewItem.getText());

			if (!f.exists()) {
				f.mkdir();
				Toast.makeText(view.getContext(), "Répertoire créé !",
						Toast.LENGTH_LONG).show();
				this.path = f.getAbsolutePath();
			} else {
				Toast.makeText(view.getContext(),
						"Le répertoire existe déjà !", Toast.LENGTH_LONG)
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
			Toast.makeText(this, "Please install a File Manager.",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case FILE_SELECT_CODE:
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();

				try {
					copy(uri.getPath(), path);
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
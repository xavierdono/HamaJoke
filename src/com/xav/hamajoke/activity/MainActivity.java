package com.xav.hamajoke.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import com.xav.hamajoke.R;
import com.xav.hamajoke.adapter.ArrayAdapterItem;
import com.xav.hamajoke.domain.ObjectItem;
import com.xav.hamajoke.listener.OnItemSelectedListenerSpinner;

public class MainActivity extends Activity {

	public static String PATH_HAMA = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		PATH_HAMA = getApplicationInfo().dataDir + "/HAMA";

		createHamaDirectory();

		loadSpinner();
	}

	private void createHamaDirectory() {
		
		File f = new File(PATH_HAMA);

		if (!f.exists()) {
			f.mkdir();
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
				contextChooser
						.setOnItemSelectedListener(new OnItemSelectedListenerSpinner(this));
				contextChooser.setAdapter(spinnerAdapter);
			}
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, OptionsActivity.class);
			intent.putExtra("PATH", PATH_HAMA);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

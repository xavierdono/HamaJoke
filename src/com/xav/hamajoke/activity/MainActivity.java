package com.xav.hamajoke.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.xav.hamajoke.R;
import com.xav.hamajoke.adapter.ArrayAdapterItem;
import com.xav.hamajoke.domain.ObjectItem;
import com.xav.hamajoke.listener.OnItemSelectedListenerSpinner;

public class MainActivity extends Activity {

	private String path = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fragment);

		path = getApplicationInfo().dataDir + "/HAMA";

		createHamaDirectory();

		loadSpinner();
	}

	private void createHamaDirectory() {
		
		File f = new File(path);

		if (!f.exists()) {
			f.mkdir();
		}
	}

	private void loadSpinner() {
		
		File f = new File(path);

		if (f.list().length != 0) {
			ObjectItem[] items = new ObjectItem[f.list().length];

			for (int i = 0; i < items.length; i++) {
				items[i] = new ObjectItem(path.concat("/"
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
			intent.putExtra("PATH", path);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

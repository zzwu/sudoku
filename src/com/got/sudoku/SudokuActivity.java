package com.got.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class SudokuActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "Sudoku";
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.new_button).setOnClickListener(this);
        findViewById(R.id.continue_button).setOnClickListener(this);
        findViewById(R.id.about_button).setOnClickListener(this);
        findViewById(R.id.exit_button).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.about_button:
			Intent i = new Intent(this, About.class);
			startActivity(i);
			break;
		case R.id.new_button:
			openNewGameDialog();
			break;
		case R.id.exit_button:
			finish();
			break;
		case R.id.continue_button:
			startGame(Game.DIFFICULTY_FREE);
			break;
			default:
				//do nothing
		}
	}

	private void openNewGameDialog() {
		new AlertDialog.Builder(this)
		.setTitle(R.string.difficulty_title)
		.setItems(R.array.difficultys, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startGame(which);
			}
			
		})
		.show();
		
	}
	
	private void startGame(int which) {
		Log.d(TAG, "start game at difficulty : " + which);
		Intent i = new Intent(this, Game.class);
		i.putExtra(Game.KEY_DIFFICULTY, which);
		startActivity(i);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater menuflater = getMenuInflater();
		menuflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.setting:
			Intent i = new Intent(this, Prefs.class);
			startActivity(i);
			return true;
		}
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		//Music.paly(this, R.raw.sy);	
	}

	@Override
	protected void onPause() {
		super.onPause();
		Music.stop(this);
	}

	
	
	
}
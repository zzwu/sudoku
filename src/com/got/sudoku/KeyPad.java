package com.got.sudoku;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class KeyPad extends Dialog {

	protected static final String TAG = "Sudoku";
	private final View keys[] = new View[9];
	private View keypad;
	private final List<Integer> unUseds;
	private final PuzzleView puzzleView;

	public KeyPad(Context context, List<Integer> unUseds, PuzzleView puzzleView) {
		super(context);
		this.unUseds = unUseds;
		this.puzzleView = puzzleView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.keypad_title);
		setContentView(R.layout.keypad);
		findViews();
		for (int i = 0; i < 9; i++) {
			keys[i].setVisibility(View.INVISIBLE);
		}
		for (int element : unUseds) {
			if (element != 0)
				keys[element - 1].setVisibility(View.VISIBLE);
		}
		setListeners();

	}

	private void setListeners() {
		for (int i = 0; i < keys.length; i++) {
			final int t = i + 1;
			keys[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					returnResult(t);
				}
			});
		}
		keypad.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				returnResult(0);
			}
		});

	}

	private void returnResult(int t) {
		puzzleView.setSelectedTitle(t);
		dismiss();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int tile = 0;
		switch (keyCode) {
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_SPACE:
			tile = 0;
			break;
		case KeyEvent.KEYCODE_1:
			tile = 1;
			break;
		case KeyEvent.KEYCODE_2:
			tile = 2;
			break;
		case KeyEvent.KEYCODE_3:
			tile = 3;
			break;
		case KeyEvent.KEYCODE_4:
			tile = 4;
			break;
		case KeyEvent.KEYCODE_5:
			tile = 5;
			break;
		case KeyEvent.KEYCODE_6:
			tile = 6;
			break;
		case KeyEvent.KEYCODE_7:
			tile = 7;
			break;
		case KeyEvent.KEYCODE_8:
			tile = 8;
			break;
		case KeyEvent.KEYCODE_9:
			tile = 9;
			break;
		default:
			return super.onKeyDown(keyCode, event);

		}
		if (isValid(tile)) {
			returnResult(tile);
		}

		return true;
	}

	private void findViews() {
		keypad = findViewById(R.id.keypad);
		keys[0] = findViewById(R.id.button1);
		keys[1] = findViewById(R.id.button2);
		keys[2] = findViewById(R.id.button3);
		keys[3] = findViewById(R.id.button4);
		keys[4] = findViewById(R.id.button5);
		keys[5] = findViewById(R.id.button6);
		keys[6] = findViewById(R.id.button7);
		keys[7] = findViewById(R.id.button8);
		keys[8] = findViewById(R.id.button9);
	}

	private boolean isValid(int tile) {
		for (int t : unUseds) {
			if (tile == t)
				return false;
		}
		return true;
	}

}

package com.got.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class Game extends Activity {

	private static final String TAG = "Sudoku";
	public static final String KEY_DIFFICULTY = "org.example.sudoku.difficulty";
	public static final int DIFFICULTY_FREE = -1;
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;
	private final String easyPuzzle = "360000000004230800000004200" + "070460003820000014500013020" + "001900000007048300000000045" ;
	private final String mediumPuzzle = "650000070000506000014000005" + "007009000002314700000700800" + "500000630000201000030000097" ;
	private final String hardPuzzle = "009000000080605020501078000" + "000000700706040102004000000" + "000720903090301080000000600" ;
	
	private static final String PREF_PUZZLE = "puzzle" ;
	protected static final int DIFFICULTY_CONTINUE = -1;
	
	public int[] puzzle = new int[9 * 9];
	
	private PuzzleView view;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Log.d(TAG, "onCreate()");
		
		//get saved difficulty
		int difficulty = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
		puzzle = getPuzzleByDifficulty(difficulty);
		
		view = new PuzzleView(this);
		setContentView(view);
		view.requestFocus();
		
	}

	private int[] getPuzzleByDifficulty(int difficulty) {
		switch (difficulty) {
		case DIFFICULTY_EASY:
			return stringToInts(easyPuzzle);
		case DIFFICULTY_MEDIUM:
			return stringToInts(mediumPuzzle);
		case DIFFICULTY_HARD:
			return stringToInts(hardPuzzle);
		case DIFFICULTY_FREE:
			return stringToInts(getPreferences(MODE_PRIVATE).getString(PREF_PUZZLE, easyPuzzle));
			default:
				return stringToInts(easyPuzzle);
		}
	}
	
	private int[] stringToInts(String s) {
		int[] result = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			result[i] = Integer.parseInt("" + s.charAt(i));
		}
		return result;
	}
	
	public int getTitle(int x, int y) {
		return puzzle[y * 9 + x];
	}
	
	public void setTitle(int num, int x, int y) {
		puzzle[y * 9 + x] = num;
	}

	public List<Integer> getUnUsedTiles(int x, int y) {
		Set<Integer> lefts = new HashSet<Integer>();
		lefts.add(new Integer(1));
		lefts.add(new Integer(2));
		lefts.add(new Integer(3));
		lefts.add(new Integer(4));
		lefts.add(new Integer(5));
		lefts.add(new Integer(6));
		lefts.add(new Integer(7));
		lefts.add(new Integer(8));
		lefts.add(new Integer(9));
		//横向去除
		for (int i = 0; i < 9; i++) {
			if (i == x) continue;
			lefts.remove(puzzle[i + y * 9]);
		}
		//竖向去除
		for (int i =0; i < 9; i++) {
			if (i == y) continue;
			lefts.remove(puzzle[x + i * 9]);
		}
		//宫内去除
		int bigX = (int)(x/3);
		int bigY = (int)(y/3);
		for (int i = 3 * bigX; i < 3 * (bigX + 1); i++ ) {
			for (int j = 3 * bigY; j < 3 * (bigY + 1); j++) {
				if (i == x && j == y) continue;
				lefts.remove(puzzle[i + j * 9]);
			}
		}
		List<Integer> list = new ArrayList<Integer>(lefts);
		Collections.sort(list);
		return list;
	}
	
	public void showKeypadOrError(int x, int y) {
		List<Integer> list = getUnUsedTiles(x, y);
		if (0 == list.size()) {
			Toast toast = Toast.makeText(this, R.string.no_moves_label, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		} else {
 			Dialog v = new KeyPad(this, list, view); 
			v.show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		//Music.paly(this, R.raw.one);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Music.stop(this);
		//save game status
		getPreferences(MODE_PRIVATE).edit().putString(PREF_PUZZLE, getPuzzleString(puzzle)).commit();
	}

	private String getPuzzleString(int[] puzzle) {
		if (puzzle == null) return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < puzzle.length; i++) {
			sb.append(puzzle[i]);
		}
		return sb.toString();
	}

}

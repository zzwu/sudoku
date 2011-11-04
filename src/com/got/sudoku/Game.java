package com.got.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Game extends Activity {

	private static final String TAG = "Sudoku";
	public static final String KEY_DIFFICULTY = "org.example.sudoku.difficulty";
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;
	private final String easyPuzzle = "360000000004230800000004200" + "070460003820000014500013020" + "001900000007048300000000045" ;
	private final String mediumPuzzle = "650000070000506000014000005" + "007009000002314700000700800" + "500000630000201000030000097" ;
	private final String hardPuzzle = "009000000080605020501078000" + "000000700706040102004000000" + "000720903090301080000000600" ;
	
	public int[] puzzle = new int[9 * 9];

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Log.d(TAG, "onCreate()");
		
		//get saved difficulty
		int difficulty = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
		puzzle = getPuzzleByDifficulty(difficulty);
		
		PuzzleView  view = new PuzzleView(this);
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
	
	public int getNumAt(int x, int y) {
		return puzzle[y * 9 + x];
	}
	
	public void setNumAt(int num, int x, int y) {
		puzzle[y * 9 + x] = num;
	}

}

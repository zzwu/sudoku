package com.got.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class PuzzleView extends View {

	private Game game;
	private float width;
	private float height;
	private int selX;
	private int selY;
	private Rect selRect = new Rect();
	private static final String TAG = "PuzzleView";

	public PuzzleView(Context context) {
		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = getWidth() / 9f;
		height = getHeight() / 9f;
		// change selected rect
		selRect = new Rect((int) (selX * width), (int) (selY * height),
				(int) ((selX + 1) * width), (int) ((selY + 1) * height));
	}

	// 支持上下左右
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			selectedTile(selX, selY - 1);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			selectedTile(selX, selY + 1);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			selectedTile(selX - 1, selY);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			selectedTile(selX + 1, selY);
			break;
		case KeyEvent.KEYCODE_0:
			setSelectedTitle(0);
			break;
		case KeyEvent.KEYCODE_1:
			setSelectedTitle(1);
			break;
		case KeyEvent.KEYCODE_2:
			setSelectedTitle(2);
			break;
		case KeyEvent.KEYCODE_3:
			setSelectedTitle(3);
			break;
		case KeyEvent.KEYCODE_4:
			setSelectedTitle(4);
			break;
		case KeyEvent.KEYCODE_5:
			setSelectedTitle(5);
			break;
		case KeyEvent.KEYCODE_6:
			setSelectedTitle(6);
			break;
		case KeyEvent.KEYCODE_7:
			setSelectedTitle(7);
			break;
		case KeyEvent.KEYCODE_8:
			setSelectedTitle(8);
			break;
		case KeyEvent.KEYCODE_9:
			setSelectedTitle(9);
			break;
		default:
			super.onKeyDown(keyCode, event);
		}
		return true;
	}

	public void setSelectedTitle(int tile) {
		if (game.getUnUsedTiles(selX, selY).contains(new Integer(tile))) {
			game.setTitle(tile, selX, selY);
			invalidate();
		} else {
			Log.d(TAG, "setSelectedTile: invalid: " + tile); 
			startAnimation(AnimationUtils.loadAnimation(game, R.anim.shake));
		}
		
	}

	private void selectedTile(int x, int y) {
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(y, 0), 8);
		getRect(selX, selY, selRect);
		invalidate(selRect);
	}

	private void getRect(int x, int y, Rect rect) {
		rect.set((int) (x * width), (int) (y * height),
				(int) (x * width + width), (int) (y * height + height));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint backgroundPaint = new Paint();
		backgroundPaint.setColor(getResources().getColor(
				R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

		Paint dark = new Paint();
		dark.setColor(getResources().getColor(R.color.puzzle_dark));

		Paint hilite = new Paint();
		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));

		Paint light = new Paint();
		light.setColor(getResources().getColor(R.color.puzzle_light));

		// Draw the minor grid lines 画小格
		for (int i = 0; i < 9; i++) {
			// 画线
			// draw X line
			canvas.drawLine(0, i * height, getWidth(), i * height, light);
			// draw Y line
			canvas.drawLine(i * width, 0, i * width, getHeight(), light);
			// 描边
			// 描X
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			// 描Y
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
					hilite);
		}

		// Draw the major grid lines 画宫
		for (int i = 0; i < 9; i++) {
			if (i % 3 == 0)
				continue;
			// 画线
			// draw X line
			canvas.drawLine(0, i * height, getWidth(), i * height, dark);
			// draw Y line
			canvas.drawLine(i * width, 0, i * width, getHeight(), dark);

			// 描边
			// 描X
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			// 描Y
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
					hilite);
		}

		// draw numbers
		Paint foreground = new Paint();
		foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height * 0.75f);
		foreground.setTextScaleX(width / height);
		foreground.setTextAlign(Paint.Align.CENTER);

		FontMetrics fm = foreground.getFontMetrics();
		// Centering in X: use alignment (and X at midpoint)
		float x = width / 2;
		// Centering in Y: measure ascent/descent first
		float y = height / 2 - (fm.ascent + fm.descent) / 2;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				canvas.drawText("" + game.getTitle(i, j), i * width + x, j
						* height + y, foreground);
			}
		}

		// draw selected
		Paint selected = new Paint();
		selected.setColor(getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selRect, selected);

		Paint hint = new Paint();
		int c[] = { getResources().getColor(R.color.puzzle_hint_0),
				getResources().getColor(R.color.puzzle_hint_1),
				getResources().getColor(R.color.puzzle_hint_2), };
		Rect r = new Rect();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (game.getTitle(i, j) != 0) continue; 
				int movesleft = game.getUnUsedTiles(i, j).size();
				if (movesleft < c.length) {
					getRect(i, j, r);
					hint.setColor(c[Math.min(movesleft - 1, 2)]);
					canvas.drawRect(r, hint);
				}
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		selectedTile((int) (event.getX() / width), (int) (event.getY() / height));
		Log.d(TAG, "onTouchEvent: x " + selX + ", y " + selY);
		//showKeypadOrError
		game.showKeypadOrError(selX, selY);
		return true;
	}

}

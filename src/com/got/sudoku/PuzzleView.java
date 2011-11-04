package com.got.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View;

public class PuzzleView extends View {
	
	private Game game;
	private float width;
	private float height;
	private int selX; 
	private int selY; 
	private Rect selRect = new Rect();

	public PuzzleView(Context context) {
		super(context);
		this.game = (Game)context;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	 
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = getWidth()/9f;
		height = getHeight()/9f;
		//change selected rect
		selRect = new Rect((int)(selX * width), (int)(selY * height), (int)((selX + 1) * width), (int)((selY + 1) * height));
	}



	@Override
	protected void onDraw(Canvas canvas) {
		Paint backgroundPaint = new Paint();
		backgroundPaint.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
		
		Paint dark = new Paint(); 
		dark.setColor(getResources().getColor(R.color.puzzle_dark));
		
		Paint hilite = new Paint(); 
		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
		
		Paint light = new Paint(); 
		light.setColor(getResources().getColor(R.color.puzzle_light));
		
		// Draw the minor grid lines 画小格
		for (int i = 0; i < 9; i++) {
			//画线
			//draw X line
			canvas.drawLine(0, i * height, getWidth(), i * height, light);
			//draw Y line 
			canvas.drawLine(i * width, 0, i * width, getHeight(), light);
			
			//描边
			//描X
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
			//描Y
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
		}
		
		// Draw the major grid lines 画宫
		for (int i = 0; i < 9; i++) {
			if (i % 3 == 0) continue; 
			//画线
			//draw X line
			canvas.drawLine(0, i * height, getWidth(), i * height, dark);
			//draw Y line 
			canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
			
			//描边
			//描X
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
			//描Y
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
		}
		
		//draw numbers 
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
				canvas.drawText("" + game.getNumAt(i, j), i * width + x, j * height + y, foreground);
			}
		}
		
	}
	
	

}

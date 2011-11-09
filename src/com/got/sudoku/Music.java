package com.got.sudoku;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
	
	private static MediaPlayer mp;
	
	public static void paly(Context context, int id) {
		stop(context);
		if (Prefs.getMusic(context)) {
			mp = MediaPlayer.create(context, id);
			mp.setLooping(true);
			mp.start();			
		}
	}

	public static void stop(Context c) {
		if (null != mp) {
			mp.stop();
			mp.release();
			mp = null;
		}
	}
	
}

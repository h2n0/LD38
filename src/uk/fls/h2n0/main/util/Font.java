package uk.fls.h2n0.main.util;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.LD38;

public class Font {

	private final static String LETTERS = "0123456789%#+/?^";
	
	public static void renderString(Renderer r, String msg, int x, int y){
		for(int i = 0; i < msg.length(); i++){
			int pos = LETTERS.indexOf(msg.charAt(i));
			if(pos == -1)continue;
			r.renderSection(LD38.sp.getData(pos), x + i * 6, y, 8);
		}
	}
	
	public static void renderWithTint(Renderer r, String msg, int x, int y, int col){
		for(int i = 0; i < msg.length(); i++){
			int pos = LETTERS.indexOf(msg.charAt(i));
			if(pos == -1)continue;
			int[] d = LD38.sp.getData(pos);
			for(int j = 0; j < 8 * 8; j++){
				int tx = j % 8;
				int ty = j / 8;
				int v = d[tx + ty * 8];
				if(v == -1)continue;
				r.setPixel(x + tx + i * 6, y + ty, v & col);
			}
		}
	}
}

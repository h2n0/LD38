package uk.fls.h2n0.main.screens;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import fls.engine.main.art.Art;
import fls.engine.main.io.FileIO;
import fls.engine.main.screen.Screen;
import fls.engine.main.util.Renderer;
import fls.engine.main.util.rendertools.SpriteParser;
import uk.fls.h2n0.main.LD38;

public class TitleScreen extends Screen {
	
	private Renderer r;
	private SpriteParser title;
	private int tick;
	private int fade;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.title = new SpriteParser(FileIO.instance.readInternalFile("/title.art"));
		this.tick = 0;
		this.fade = 60 * 6;
		this.input.setScreenshotKey(KeyEvent.VK_P);
		
		if(LD38.df.getData("played")!=null){
			this.fade = 0;
		}else{
			LD38.df.setValue("played", "true");
		}
	}

	@Override
	public void render(Graphics arg0) {
		r.fill(0);
		if(this.fade < 60 * 3){
			int[] t = new int[]{0,1,2,3,-1, 3, 4, 5, 7, 6, 3, 8};
			int xo = (LD38.w - t.length * 8) / 2;
			int yo = (LD38.h - 16) / 2;
			for(int i = 0; i < t.length; i++){
				if(t[i] == -1)continue;
				r.renderSection(this.title.getData(t[i]), xo + i * 8, yo + 8, 8);
			}
		}
		
		if(this.fade == 0){
			int xo = (LD38.w - 16)/2;
			int yo = (LD38.h + 16)/2;
			//r.renderSection(this.title.getData(9), 9, 8, 8);
			drawColor(this.title.getData(9), xo + 1, yo + 8, this.tick>60?0xCC0000:0xFFFFFF);
			r.renderSection(this.title.getData(10), xo + 1, yo + 16, 8);
			drawXFlipped(this.title.getData(9), xo + 8, yo + 8);
			drawXFlipped(this.title.getData(10), xo + 8, yo + 16);
		}
	}

	@Override
	public void update() {
		if(this.fade > 0){
			this.fade--;
		}else{
			this.tick = (this.tick + 1) % 120;
			if(this.input.leftMouseButton.justClicked()){
				setScreen(new GameScreen());
			}
		}
		
		if(this.input.isKeyPressed(this.input.getScreenshotKey())){
			Art.saveScreenShot(game, true);
		}
	}

	
	private void drawXFlipped(int[] d, int x, int y){
		for(int i = 0; i < 8 * 8; i++){
			if(d[i] == -1)continue;
			int tx = i % 8;
			int ty = i / 8;
			r.setPixel(x + (8-tx), y + ty, d[i]);
		}
	}
	
	private void drawColor(int[] d, int x, int y, int col){
		for(int i = 0; i < 8 * 8; i++){
			if(d[i] == -1)continue;
			int tx = i % 8;
			int ty = i / 8;
			r.setPixel(x + tx, y + ty, d[i] & col);
		}
	}
}

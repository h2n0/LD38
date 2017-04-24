package uk.fls.h2n0.main;

import fls.engine.main.Init;
import fls.engine.main.input.Input;
import fls.engine.main.io.DataFile;
import fls.engine.main.io.FileIO;
import fls.engine.main.util.rendertools.SpriteParser;
import uk.fls.h2n0.main.screens.TitleScreen;

@SuppressWarnings("serial")
public class LD38 extends Init{

	public static int w = 320;
	public static int h = 240;
	public static int s = 2;
	
	public static SpriteParser sp = new SpriteParser(FileIO.instance.readInternalFile("/img.art"));
	public static DataFile df = new DataFile("data");
	
	public LD38(){
		super("LD38", w * s, h * s);
		useCustomBufferedImage(w, h, false);
		setInput(new Input(this, Input.KEYS, Input.MOUSE));
		//setScreen(new GameScreen());
		setScreen(new TitleScreen());
		skipInit();
		start();
	}
	
	public static void main(String[] args){
		new LD38();
	}
}

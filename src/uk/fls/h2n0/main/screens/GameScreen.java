package uk.fls.h2n0.main.screens;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import fls.engine.main.art.Art;
import fls.engine.main.screen.Screen;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.LD38;
import uk.fls.h2n0.main.entitys.Planet;
import uk.fls.h2n0.main.entitys.Resource;
import uk.fls.h2n0.main.entitys.buildings.Building;
import uk.fls.h2n0.main.entitys.buildings.CloudGen;
import uk.fls.h2n0.main.entitys.buildings.Miner;
import uk.fls.h2n0.main.entitys.buildings.Rocket;
import uk.fls.h2n0.main.util.Font;

public class GameScreen extends Screen {

	private Renderer r;
	private Planet p;
	private Building activeBuilding;
	
	private int mx, my;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		
		this.p = new Planet();
	}
	
	@Override
	public void render(Graphics g) {
		this.r.fill(0);
		this.p.render(this.r);
		//Font.renderString(r, ""+this.p.getHealth(), 8, 8);
		
		drawHappiness();
		drawHealth();
		drawWater();
		drawComposite();
		drawItemBar();
		
		if(this.activeBuilding != null){
			this.activeBuilding.renderPlacement(r, mx, my);
		}
	}

	@Override
	public void update() {

		this.mx = this.input.mouse.getX() / LD38.s;
		this.my = this.input.mouse.getY() / LD38.s;
		
		this.p.update();
		
		float rot = this.input.isKeyHeld(this.input.shift)?2f:1f;
		if(this.input.isKeyHeld(this.input.a)){//Rotate planet left
			this.p.rot(-rot);
		}else if(this.input.isKeyHeld(this.input.d)){
			this.p.rot(rot);
		}
		
		if(this.input.isKeyPressed(this.input.space)){
			//this.p.setRot(0);
		}
		
		if(this.input.isKeyPressed(this.input.getScreenshotKey())){
			Art.saveScreenShot(this.game, true);
			this.input.releaseAllKeys();
		}
		
		if(this.activeBuilding != null){
			if(this.input.leftMouseButton.justClicked()){
				int cx = LD38.w / 2;
				int cy = LD38.h / 2;
				float dx = mx - cx;
				float dy = my - cy;
				
				if((dx * dx) + (dy * dy) < 52 * 52){
					int vi = this.activeBuilding instanceof CloudGen?100:this.activeBuilding instanceof Miner?200:0;
					if(this.activeBuilding instanceof CloudGen){	
						vi = 100 + (this.p.getNumberOfBuilding(CloudGen.class) * 50);
						if(!this.p.hb.takeResource(Resource.Composite, vi))return;
					}else if(this.activeBuilding instanceof Miner){
						vi = 200 + (this.p.getNumberOfBuilding(Miner.class) * 150);
						if(!this.p.hb.takeResource(Resource.Composite, vi))return;
					}else if(this.activeBuilding instanceof Rocket){
						if(!this.p.hb.takeResource(Resource.Composite, 500))return;
					}
					this.activeBuilding.place((int)dx - 8, (int)dy);
					this.activeBuilding = null;
				}else{
					this.activeBuilding = null;
				}
			}
		}
		
		if(this.p.hb.getHappiness() == 0 && this.p.getHealth() == 0){//Every one is sick and unhappy this is the lose condition
			setScreen(new TitleScreen());
		}
	}
		
	private void drawHappiness(){
		Font.renderWithTint(r, "# "+this.p.hb.getHappiness()+"%", 8, 16, 0xFFCC00);
	}
	
	private void drawHealth(){
		Font.renderWithTint(r, "+ "+this.p.getHealth()+"%", 8, 32, 0xCC0000);
	}
	
	private void drawWater(){
		int amt = this.p.hb.getWater();
		Font.renderWithTint(r, "/ "+amt, 8, 48, 0x00CCFF);
	}
	
	private void drawComposite(){
		int amt = this.p.hb.getComposite();
		Font.renderWithTint(r, "? "+amt, 8, 64, 0xCCCCCC);
	}
	
	private void drawItemBar(){// Draws the cloud generators, mines and pods
		int[] values = new int[]{100,200,500};
		for(int i = 1; i <= 3; i++){
			int xo = 105 + (24 * i);
			int yo = LD38.h - 24;
			drawItem(xo, i);
			
			if(mx >= xo && mx <= xo + 16){
				if(my >= yo && my <= yo + 16){
					int vi = values[i-1];
					if(i == 1){
						vi = values[i-1] + (this.p.getNumberOfBuilding(CloudGen.class) * 50);
					}else if(i == 2){
						vi = values[i-1] + (this.p.getNumberOfBuilding(Miner.class) * 150);
					}
					Font.renderString(r, "^", xo + 4, LD38.h - (24+8));
					Font.renderString(r, ""+vi+"?", xo-5, LD38.h - (24+12));
					
					if(this.input.leftMouseButton.justClicked()){
						if(this.activeBuilding == null){
							this.activeBuilding = (i==1?new CloudGen(this.p):i==2?new Miner(this.p):new Rocket(this.p));
							this.input.relaseMouseButtons();
						}
					}
				}
			}
		}
	}
	
	private void drawItem(int xo, int x){
		if(x >= 1)x++;
		
		int yo = LD38.h - 24;
		

		if(x == 4){
			r.renderSection(LD38.sp.getData(3,6), xo + 4, yo, 8);
			r.renderSection(LD38.sp.getData(3,7), xo + 4, yo + 8, 8);
		}else{
			for(int i = 0; i < 2 * 2; i++){
				int tx = i % 2;
				int ty = i / 2;
				r.renderSection(LD38.sp.getData((x*2) + tx, 2 + ty), xo + (tx * 8), yo + ty * 8, 8);
			}
		}
	}
}

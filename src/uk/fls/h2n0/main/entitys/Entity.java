package uk.fls.h2n0.main.entitys;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;

public class Entity {

	protected Point pos;
	protected Planet home;
	public boolean remove;
	
	public Entity(Planet home){
		this.home = home;
		this.remove = false;
	}
	
	
	public void render(Renderer r, int off){
		
	}
	
	public void update(){
		
	}
}

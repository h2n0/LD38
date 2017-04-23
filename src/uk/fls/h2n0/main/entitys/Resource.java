package uk.fls.h2n0.main.entitys;

public enum Resource {
	
	Water(0x5B6EE1),
	Composite(0xCCCCCC),
	Illness(0x00CC00);
	
	private int color;
	
	Resource(int col){
		this.color = col;
	}
	
	public int getColor(){
		return this.color;
	}
}

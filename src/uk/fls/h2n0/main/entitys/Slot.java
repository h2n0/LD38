package uk.fls.h2n0.main.entitys;

public class Slot {

	public Resource type;
	public boolean full;
	public final int maxAmount;
	public int currentAmount;
	
	public Slot(int maxAmount){
		this.maxAmount = maxAmount;
		this.currentAmount = 0;
	}
	
	public void setCurrentAmmount(int amt){
		this.currentAmount = amt;
		limitAmount();
	}
	
	public void fill(Resource type){
		this.type = type;
		this.currentAmount = this.maxAmount;
		this.full = true;
	}
	
	public Resource empty(){
		Resource r = this.type;
		this.full = false;
		this.type = null;
		this.currentAmount = 0;
		return r;
	}
	
	public boolean addResource(Resource a, int amt){
		boolean suc = false;
		if(this.type == null){// Means we have an empty slot
			this.type = a;
			this.currentAmount += amt;
			suc = true;
		}else{
			if(this.type == a){
				this.currentAmount += amt;
				suc = true;
			}
		}
		if(suc)limitAmount();
		return suc;
	}
	
	public boolean removeResource(int amt){
		if(this.type == null)return false;
		if(this.currentAmount - amt < 0)return false;
		this.currentAmount -= amt;
		limitAmount();
		return true;
	}
	
	private void limitAmount(){
		if(this.currentAmount > this.maxAmount)this.currentAmount = this.maxAmount;
		if(this.currentAmount < 0)this.currentAmount = 0;
		
		if(this.currentAmount == this.maxAmount)this.full = true;
		else this.full = false;
	}
}

package sapling.model;

public class Leaf implements Bud{

	public final float classifiedAs;
	public Leaf(float classifiedAs) {
		this.classifiedAs = classifiedAs;
	}
	
	public String toString(){
		return "LEAF: "+this.classifiedAs;
	}

}

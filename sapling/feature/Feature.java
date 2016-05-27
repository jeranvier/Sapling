package sapling.feature;

public abstract class Feature {
	
	public static enum Type{CATEGORICAL, NUMERICAL};
	
	public final String name;
	public final Type type;
	private boolean isClass;
	
	public Feature(String name, Type type){
		this.name = name;
		this.type = type;
		this.isClass = false;
	}
	
	public void setAsClass(){
		this.isClass = true;
	}
	
	public boolean isClass(){
		return this.isClass;
	}

	public abstract float generateFloatFrom(String value);
	
	public abstract float generateFloatFrom(int value);
}

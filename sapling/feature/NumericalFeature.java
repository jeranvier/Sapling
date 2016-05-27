package sapling.feature;


public class NumericalFeature extends Feature{
	
	public NumericalFeature(String name){
		super(name, Feature.Type.NUMERICAL);
	}

	@Override
	public float generateFloatFrom(String value) {
		throw new ClassCastException("Expected categorical data, got integer");
	}

	@Override
	public float generateFloatFrom(int value) {
		return (float) value;
	}
	
	public String toString(){
		return name;
	}

}

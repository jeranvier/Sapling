package sapling.feature;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class CategoricalFeature extends Feature{
	
	private final Map<String, Float> categories;
	private Float latestN = 0f;

	public CategoricalFeature(String name, List<String> cats){
		super(name, Feature.Type.CATEGORICAL);
		this.categories = new LinkedHashMap<>();
		for(String cat: cats){
			this.categories.put(cat, latestN++);
		}
	}
	
	public CategoricalFeature(String ... args){
		super(args[0], Feature.Type.CATEGORICAL);
		this.categories = new LinkedHashMap<>();
		for(int i=1; i<args.length; i++){
			this.categories.put(args[i], latestN++);
		}
	}
	
	public boolean contains(String cat){
		return this.categories.containsKey(categories);
	}
	
	public float get(String cat){
		return this.categories.get(categories);
	}
	
	public int size(){
		return this.categories.size();
	}
	
	public String get(float index){
		String cat = null;
		for(Entry<String, Float> entry : categories.entrySet()){
			if(entry.getValue() == index){
				cat = entry.getKey();
				break;
			}
		}
		return cat;
	}
	
	public Set<String> getKeySet(){
		return categories.keySet();
	}
	
	public Collection<Float> getValues(){
		return categories.values();
	}

	@Override
	public float generateFloatFrom(String value) {
		return this.categories.get(value);
	}

	@Override
	public float generateFloatFrom(int value) {
		throw new ClassCastException("Expected categorical data, got integer");
	}
	
	public String toString(){
		return name;
	}
	
	public String toVerboseString(){
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append("[");
		for(Entry<String, Float> entry : categories.entrySet()){
			sb.append(entry.getKey());	
			sb.append("(");	
			sb.append(entry.getValue());	
			sb.append(")");	
		}
		sb.append("]");
		return sb.toString();
	}

}
	

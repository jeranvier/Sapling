package sapling;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import sapling.feature.CategoricalFeature;
import sapling.feature.Feature;

//We need a fast access to the the feature
//With consistant iterator access
//With access to the feature index
public class SampleMold{
	private Map<String, Feature> features;
	private CategoricalFeature classFeature;
	private boolean locked;
	private Map<String, Integer> indices;
	
	public SampleMold(Feature ... features){
		super();
		this.features = new HashMap<>();
		this.locked = false;
		for(Feature feature: features){
			this.add(feature);
		}
	}
	
	public void lock(){
		this.locked= true;
		indices = new HashMap<>();
		int i = 0;
		for(Feature feature : features.values()){
			indices.put(feature.name, i++);
		}
	}

	public void add(Feature feature) {
		if(this.locked){
			System.out.println("ERROR");
		}
		this.features.put(feature.name, feature);
		if(feature.isClass()){
			if(this.classFeature == null){
				this.classFeature = (CategoricalFeature) feature;				
			}else{
				System.out.println("Another feature is defined as class for this mold: "+this.classFeature.toString());
			}
		}
	}
	
	public void remove(Feature feature){
		if(this.locked){
			System.out.println("ERROR");
		}
		this.features.remove(feature.name);
	}
	
	public int size(){
		return this.features.size();
	}
	
	public Map<String, Feature> getFeatures(){
		return Collections.unmodifiableMap(this.features);
	}
	
	public CategoricalFeature getClassFeature(){
		return this.classFeature;
	}

	public Feature get(String featureName) {
		return this.features.get(featureName);
	}

	public boolean containsKey(String featureName) {
		return this.features.containsKey(featureName);
	}

	public int indexOf(Feature feature) {
		return this.indices.get(feature.name);
	}	
}

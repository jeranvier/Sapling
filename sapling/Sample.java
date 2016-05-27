package sapling;

import java.util.Arrays;

import sapling.feature.Feature;

public class Sample {
	
	public final float[] features;
	
	private Sample(SampleMold mold, float[] features){
		this.features = features;
	}
	
	public String toString(){
		return Arrays.toString(this.features);
	}
	
	public static final class Builder{	
		private SampleMold mold;
		private float[] features;


		public Builder(SampleMold mold){
			this.mold = mold;
			this.features = new float[this.mold.size()];
		}
		
		public Builder set(String featureName, String featureValue){
			if(mold.containsKey(featureName)){
				Feature feature = mold.get(featureName);
				if(feature.type == Feature.Type.CATEGORICAL){
					this.features[mold.indexOf(feature)] = feature.generateFloatFrom(featureValue);
				} 
			}
			return this;
		}
		
		public Builder set(String featureName, int featureValue){
			if(mold.containsKey(featureName)){
				Feature feature = mold.get(featureName);
				if(feature.type == Feature.Type.NUMERICAL){
					this.features[mold.indexOf(feature)] = feature.generateFloatFrom(featureValue);
				} 
			}
			return this;
		}
		
		public Builder set(String featureName, float featureValue){
			if(mold.containsKey(featureName)){
				Feature feature = mold.get(featureName);
				if(feature.type == Feature.Type.NUMERICAL){
					this.features[mold.indexOf(feature)] = featureValue;
				} 
			}
			return this;
		}
		
		public Sample build(){
			return new Sample(mold, features);
		}
		
		public Builder clear(){
			this.features = new float[this.mold.size()];
			return this;
		}
	}
}

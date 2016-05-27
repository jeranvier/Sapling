package sapling.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import sapling.Sample;
import sapling.Samples;
import sapling.feature.CategoricalFeature;
import sapling.feature.Feature;
import sapling.feature.NumericalFeature;

public class NodeBuilder{
	private static final double log2 = Math.log(2);
		
	public static Bud generateFrom(Samples samples){
		if(samples.getDistinctClasses().size()<=1){
			return new Leaf(samples.getDistinctClasses().iterator().next());
		}
		
		//Select the best feature to split on ( the one with the minimum entropy)
		Feature bestFeature=samples.mold.getFeatures().values().iterator().next();
		EntropyResult minEntropyResult = new EntropyResult();
		EntropyResult currentEntropyResult; //stores the computed entropy and, if numerical feature, the split point which gives the lowest entropy
		for(Feature f : samples.mold.getFeatures().values()){
			if(f.isClass()){
				continue;
			}
			
			if(f.type == Feature.Type.CATEGORICAL){
				currentEntropyResult = computeEntropy(samples, (CategoricalFeature)f);
			}else if(f.type == Feature.Type.NUMERICAL){
				currentEntropyResult = splitAndComputeEntropy(samples, (NumericalFeature)f);
			}else{
				currentEntropyResult = new EntropyResult();
			}
			
			if(Double.isNaN(minEntropyResult.getEntropy()) || currentEntropyResult.getEntropy() < minEntropyResult.getEntropy()){
				minEntropyResult = currentEntropyResult;
				bestFeature = f;
			}
		}
		
		//once the split feature is known, we effectively split the dataset based on this feature and
		//recursively build the children nodes based on the "sub-dataset"
		int bestFeatureIndex = samples.mold.indexOf(bestFeature);
		if(bestFeature.type == Feature.Type.CATEGORICAL){
			//split the dataset into each sub dataset according to the split
			CategoricalFeature feat = (CategoricalFeature)bestFeature;
			Map<Float,Samples> listOfSamples = new HashMap<>();
			for(float cat : feat.getValues()){
				listOfSamples.put(cat, new Samples(samples.mold));
			}
			
			for(Sample s : samples){
				listOfSamples.get(s.features[bestFeatureIndex]).add(s);
			}
			
			//generate children, if there exist sample for this attribute value
			Map<Float,Bud> children = new HashMap<>();
			for(float cat : feat.getValues()){
				if(!listOfSamples.get(cat).isEmpty()){
					children.put(cat, generateFrom(listOfSamples.get(cat)));
				}
			}
			return new Node(bestFeature, children);
		}
		else if(bestFeature.type == Feature.Type.NUMERICAL){
			// we split based on what we found (need a reference to the best split point)
			return null;
		}
		else{
			return null;
		}
	}

	/*
	 * Compute the weighted entropy resulting from the split of the dataset into different category
	 */
	private static EntropyResult computeEntropy(Samples samples, CategoricalFeature f) {
		int index = samples.mold.indexOf(f);
		int total = samples.size();
		int[][] frequencies = new int[f.size()][samples.mold.getClassFeature().size()];
		int[] totalForCategory = new int[f.size()];
		for(Sample s : samples){
			frequencies[(int) s.features[index]][(int) s.features[samples.mold.indexOf(samples.mold.getClassFeature())]]++;
			totalForCategory[(int) s.features[index]]++;
		}
		
		double weightedEntropy = 0.0;
		for(int i=0; i< frequencies.length; i++){
			if(totalForCategory[i]==0){
				continue;
			}
			double entropy = 0.0;
			for(int currentClass : frequencies[i]){
				if(currentClass!=0){
					double p = ((double)currentClass)/totalForCategory[i];
					entropy-=p*Math.log(p)/log2;
				}
			}
			weightedEntropy += totalForCategory[i]*entropy/total;
		}		
		EntropyResult result = new EntropyResult();
		result.setEntropy(weightedEntropy);
		return result;
	}
	
	/*
	 * Used for numerical attributes. We need to know where to split and then compute the entropy
	 * This can be done in a single pass over the data
	 */
	private static EntropyResult splitAndComputeEntropy(Samples samples, NumericalFeature f) {
		final int index = samples.mold.indexOf(f);
		int total = samples.size();
		
		//sort the samples based on the attribute at hand (to be able to find the best splitPoint in one path)
		Collections.sort(samples, new Comparator<Sample>() {

			@Override
			public int compare(Sample s1, Sample s2) {
				return Float.compare(s1.features[index], s2.features[index]);
			}
		});
		
		//progress through the samples and compute entropy, keep the minimum and the splitPoint associated
		int[] leftFrequencies = new int[samples.mold.getClassFeature().size()];
		int[] rightFrequencies = new int[samples.mold.getClassFeature().size()];
		return null;
	}
	
	public static final class EntropyResult {
		
		private double entropy;
		private float splitPoint;
		
		public EntropyResult(){
			entropy = Double.NaN;
			splitPoint = Float.NaN;
		}
		
		public double getEntropy() {
			return entropy;
		}
		public void setEntropy(double entropy) {
			this.entropy = entropy;
		}
		public float getSplitPoint() {
			return splitPoint;
		}
		public void setSplitPoint(float splitPoint) {
			this.splitPoint = splitPoint;
		}
	}
}

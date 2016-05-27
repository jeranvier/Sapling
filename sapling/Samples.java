package sapling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Samples extends ArrayList<Sample>{

	private static final long serialVersionUID = -4271223301637822054L;
	public final SampleMold mold;
	private Map<Float, Integer> distribution;
	private Set<Float> distinctClasses;
	private int classIndex;
	
	public Samples(SampleMold mold){
		super();
		this.mold = mold;
		classIndex = mold.indexOf(mold.getClassFeature());
		this.distribution=new HashMap<>();
		this.distinctClasses = new HashSet<>();
	}
	
	public Samples(SampleMold mold, List<Sample> samples){
		super();
		this.mold = mold;
		this.addAll(samples);
	}

	@Override
	public boolean add(Sample e) {
		distinctClasses.add(e.features[classIndex]);
		return super.add(e);
	}

	@Override
	public void add(int index, Sample element) {
		distinctClasses.add(element.features[classIndex]);
		super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends Sample> c) {
		for(Sample e : c){
			distinctClasses.add(e.features[classIndex]);
		}
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Sample> c) {
		for(Sample e : c){
			distinctClasses.add(e.features[classIndex]);
		}
		return super.addAll(index, c);
	}
	
	public Set<Float> getDistinctClasses(){
		return this.distinctClasses;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Sample e : this){
			sb.append("\n");
			sb.append(e.toString());
		}
		return sb.toString();
	}

}

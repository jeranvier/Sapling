package sapling.model;

import java.util.Map;

import sapling.feature.Feature;

public class Node implements Bud{

	private Feature feature;
	private Map<Float, Bud> children;

	public Node(Feature feature, Map<Float, Bud> children) {
		this.feature = feature;
		this.children = children;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("NODE: ");
		sb.append(this.feature.name);
		sb.append(" [");
		for(Bud child:children.values()){
			sb.append(child.toString());
		}
		sb.append(" ]");
		
		return sb.toString();
	}

}

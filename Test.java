import sapling.Sample;
import sapling.SampleMold;
import sapling.Samples;
import sapling.feature.CategoricalFeature;
import sapling.feature.Feature;
import sapling.feature.NumericalFeature;
import sapling.model.Bud;
import sapling.model.Node;
import sapling.model.NodeBuilder;


public class Test {
	
	public static void main(String[] args){
		
		Feature drinkFeature = new CategoricalFeature("drink", "C", "E", "S", "T");
		Feature ageFeature = new CategoricalFeature("age", "over", "under");
		Feature genderFeature = new CategoricalFeature("gender", "M", "F");
		Feature canDrinkFeature = new CategoricalFeature("canDrink", "T", "F");
		canDrinkFeature.setAsClass();
		SampleMold mold = new SampleMold(drinkFeature, ageFeature, genderFeature, canDrinkFeature);
		mold.lock();
		Samples dataset = new Samples(mold);
		Sample.Builder sampleBuilder = new Sample.Builder(mold);
		dataset.add(sampleBuilder.clear().set("drink", "C").set("age", "under").set("gender", "M").set("canDrink", "F").build());
		dataset.add(sampleBuilder.clear().set("drink", "E").set("age", "over").set("gender", "M").set("canDrink", "F").build());
		dataset.add(sampleBuilder.clear().set("drink", "C").set("age", "under").set("gender", "F").set("canDrink", "F").build());
		dataset.add(sampleBuilder.clear().set("drink", "C").set("age", "over").set("gender", "F").set("canDrink", "F").build());
		dataset.add(sampleBuilder.clear().set("drink", "S").set("age", "over").set("gender", "F").set("canDrink", "T").build());
		dataset.add(sampleBuilder.clear().set("drink", "E").set("age", "under").set("gender", "M").set("canDrink", "T").build());
		dataset.add(sampleBuilder.clear().set("drink", "E").set("age", "under").set("gender", "F").set("canDrink", "T").build());
		
		Bud model = NodeBuilder.generateFrom(dataset);
		
		System.out.println(model);
	}

}
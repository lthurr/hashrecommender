package recommender.hashrecommender;

import java.io.File;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class Experiment {

	public static void main(String[] args) throws Exception {

		File data_csv = new File(System.getProperty("user.dir") + "/src/main/java/recommender/hashrecommender/data.csv");
		DataModel model = new FileDataModel(data_csv);

		RecommenderEvaluator evaluator_mae = new AverageAbsoluteDifferenceRecommenderEvaluator();
		RecommenderEvaluator evaluator_rme = new RMSRecommenderEvaluator();
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel model) throws TasteException {
				UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
				UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.5, similarity, model);
				return new GenericUserBasedRecommender(model, neighborhood, similarity);
			}
		};
		double score = 0.0;
		
		System.out.println("User-based:");
		// Use 90% of the data to train; test using the other 10%.
		score = evaluator_mae.evaluate(recommenderBuilder, null, model, 0.9, 1.0);
		System.out.println("\tMean average error: " + score);
		
		// Use 90% of the data to train; test using the other 10%.
		score = evaluator_rme.evaluate(recommenderBuilder, null, model, 0.9, 1.0);
		System.out.println("\tRoot mean error: " + score);

		recommenderBuilder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel model) throws TasteException {
				ItemSimilarity similarity = new EuclideanDistanceSimilarity(model);
				return new GenericItemBasedRecommender(model, similarity);
			}
		};
		System.out.println("Item-based:");
		
		// Use 90% of the data to train; test using the other 10%.
		score = evaluator_mae.evaluate(recommenderBuilder, null, model, 0.9, 1.0);
		System.out.println("\tMean average error: " + score);

		// Use 90% of the data to train; test using the other 10%.
		score = evaluator_rme.evaluate(recommenderBuilder, null, model, 0.9, 1.0);
		System.out.println("\tRoot mean error: " + score);
	}
}

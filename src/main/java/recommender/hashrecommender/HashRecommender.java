package recommender.hashrecommender;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.commons.math3.primes.Primes;


public class HashRecommender {

    public static void main(String args[]) throws Exception {

        System.out.println("UserBased Recommendation based on Twitter Hashtags");
        System.out.println("\t-----------------------");
        System.out.println("\tSearching recommendations for: " + args[0]);
        
        String full_directory = System.getProperty("user.dir") + "/src/main/java/recommender/hashrecommender/";
        
        Map<String, String> users_ids = ReadToHashMap(full_directory + args[1]); // Source of users_ids.txt
        Map<String, String> hashtags_ids = ReadToHashMap(full_directory + args[2]); //Source of hashtags_ids.txt
        Map<String, String> hashtags_per_user = ReadToHashMap(full_directory + args[3]); //Source of hashtags_per_user.txt
        
        File data_csv = new File(System.getProperty("user.dir") + "/src/main/java/recommender/hashrecommender/data.csv");
        DataModel model = new FileDataModel(data_csv);

        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.5, similarity, model);

        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        String user_id = users_ids.get(args[0]);
        
        List<RecommendedItem> recommendations = recommender.recommend(Long.parseLong(user_id, 10), 10);
        System.out.println("\tThe user " + args[0] + " has the following tweets");
        System.out.println("\t" + hashtags_per_user.get(args[0]));
        System.out.println("\tRecommendations:");
        for (RecommendedItem recommendation : recommendations) {
        	String hashtag_id = String.valueOf(recommendation.getItemID());
        	float value = recommendation.getValue();
            System.out.println("\t> " +  hashtags_ids.get(hashtag_id) + " - value: " + value);
        }
        System.out.println("\t-----------------------");
    }

	private static Map<String, String> ReadToHashMap(String string) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        BufferedReader in = new BufferedReader(new FileReader(string));
        String line = "";
        while ((line = in.readLine()) != null) {
            String parts[] = line.split("\t");
            map.put(parts[0], parts[1]);
        }
        in.close();
        return map;
	}
    
}

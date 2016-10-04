package analyze;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

import datamodel.SentimentType;
import datamodel.WordModel;
import datamodel.WordPercentage;
import training.TrainModel;
import utilities.BuildMovies;
import utilities.LoggerProducer;

public class Analysis {

	private static final int LOW_PERCENTAGE = 30;
	private static final int HIGH_PERCENTAGE = 70;
	
	@Inject
	private static final Logger logger = Logger.getLogger(LoggerProducer.class);
	
	public static SentimentType analyzeWordsSentence(String sentence) {
		int posWords = 0, negWords = 0;

		if (sentence != null) {
			List<WordPercentage> wordsAndPercentages = new ArrayList<WordPercentage>();
			sentence = Normalization.normalizeMessage(sentence);

			wordsAndPercentages = Analysis.getPercentagesPerWord(sentence);
			for (WordPercentage wordAndPercentage : wordsAndPercentages) {
				if ((int) wordAndPercentage.getPercentage() < LOW_PERCENTAGE) {
					negWords++;
				} else if ((int) wordAndPercentage.getPercentage() > HIGH_PERCENTAGE) {
					posWords++;
				}
			}
		}
		return calculateSentimentSentence(posWords, negWords);
	}

	public static SentimentType calculateSentimentSentence(int posWords, int negWords) {
		List<Movie> movies = new ArrayList<Movie>();
		SentimentType sentiment = null;
		
		ClassLoader classLoader = TrainModel.class.getClassLoader();
		
		try {
			String[] reactions = { 
					classLoader.getResource("/reactions/blink.mp4").getPath(),
					classLoader.getResource("/reactions/sad.mp4").getPath(),
					classLoader.getResource("/reactions/thumbs-up.mp4").getPath(),
					classLoader.getResource("/reactions/small-smile.mp4").getPath(),
					classLoader.getResource("/reactions/smile.mp4").getPath()
					};
			
			
			if (posWords + negWords == 0) {
				movies.add(MovieCreator.build(reactions[2]));
				sentiment = SentimentType.NEUTRAL;
			}
			else{
				double doublePercentage = (posWords * 100.0f) / (posWords + negWords);
				
				if (doublePercentage >= 70.00) {
					movies.add(MovieCreator.build(reactions[4]));
					sentiment = SentimentType.POSITIVE;
				}
	
				if (doublePercentage < 70.00 && doublePercentage > 35.00) {
					movies.add(MovieCreator.build(reactions[2]));
					sentiment = SentimentType.NEUTRAL;
				}
				if (doublePercentage <= 35.00) {
					movies.add(MovieCreator.build(reactions[1]));
					sentiment = SentimentType.NEGATIVE;
				}
			}
			for(int i=0;i<40;i++){
				movies.add(MovieCreator.build(reactions[0]));
			}
			
			BuildMovies.generateMovie("test.mp4", movies);

		} catch (Exception ex) {
			logger.error(ex);
		}
		return sentiment;
	}

	public static List<WordPercentage> getPercentagesPerWord(String sentence) {

		DecimalFormat df = new DecimalFormat("#.##");
		List<WordPercentage> wordsAndPercentages = new ArrayList<WordPercentage>();

		for (String word : sentence.split(" ")) {
			WordModel wm = Dictionary.findWordModel(word);
			if (wm != null) {
				
				double posCases = wm.getPosCases();
				double percentage = posCases / (posCases + wm.getNegCases()) * 100;
				percentage = Double.valueOf(df.format(percentage));

				WordPercentage wp = new WordPercentage(word, percentage);
				wordsAndPercentages.add(wp);

			}
		}
		return wordsAndPercentages;

	}
}

package analyze;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

import datamodel.SentimentType;
import datamodel.WordModel;
import datamodel.WordPercentage;
import utilities.BuildMovies;

public class Analysis {

	private static final int LOW_PERCENTAGE = 30;
	private static final int HIGH_PERCENTAGE = 70;
	private static String basePath = new File("").getAbsolutePath();
	private static String[] reactions = { basePath.concat("\\reactions\\blink.mp4"), basePath.concat("\\reactions\\very-sad.mp4"),
			basePath.concat("\\reactions\\dont-know.mp4"), basePath.concat("\\reactions\\small-smile.mp4"),
			basePath.concat("\\reactions\\laughter.mp4"), basePath.concat("\\reactions\\thumbs-up.mp4") };
	
	public static SentimentType analyzeWordsSentence(String sentence) {
		int posWords = 0, negWords = 0, totalWords = 0;
		
		if (sentence != null) {
			List<WordPercentage> wordsAndPercentages = new ArrayList<WordPercentage>();
			sentence = Normalization.normalizeMessage(sentence);

			wordsAndPercentages = Analysis.getPercentagesPerWord(sentence);
			for (WordPercentage wordAndPercentage : wordsAndPercentages) {
				if ((int) wordAndPercentage.getPercentage() < LOW_PERCENTAGE) {
					negWords++;
					totalWords++;
				} else if ((int) wordAndPercentage.getPercentage() > HIGH_PERCENTAGE) {
					posWords++;
					totalWords++;
				}
			}
		}
		return calculateSentimentSentence(posWords, negWords, totalWords);
	}

	public static SentimentType calculateSentimentSentence(int posWords, int negWords, int totalWords) {
		//TODO
		double doublePercentage = 0.00;
		List<Movie> movies = new ArrayList<>();
		
		doublePercentage = (posWords * 100.0f) / totalWords;
		
		SentimentType sentiment = null;

		if (posWords == 0) {
			doublePercentage += 0.01;
		}
		try {

			if (totalWords == 0) {
				movies.add(MovieCreator.build(reactions[2]));
				sentiment = SentimentType.NEUTRAL;
			}

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

			movies.add(MovieCreator.build(reactions[0]));
			BuildMovies.generateMovie("test.mp4", movies);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sentiment;
	}

	public static List<WordPercentage> getPercentagesPerWord(String sentence) {

		DecimalFormat df = new DecimalFormat("#.##");
		List<WordPercentage> wordsAndPercentages = new ArrayList<WordPercentage>();

		for (String word : sentence.split(" ")) {
			WordModel wm = Dictionary.findWordModel(word);
			if (wm != null) {
				double percentage = wm.getPosCases() / (wm.getPosCases() + wm.getNegCases());
				percentage = Double.valueOf(df.format(percentage));

				WordPercentage wp = new WordPercentage(word, percentage);
				wordsAndPercentages.add(wp);

			}
		}
		return wordsAndPercentages;

	}
}

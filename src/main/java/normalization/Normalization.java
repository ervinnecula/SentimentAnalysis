package normalization;

public class Normalization {

	static public String stripNewLineTab(String message) {
		return message.replaceAll("(\\r|\\n|\\t)", "").replaceAll("\\s+", " ");
	}

	static public String stripURLs(String message) {
		return message.replaceAll("https?://\\S+\\s?", "").replaceAll("\\s+", " ");
	}

	static public String stripHashtags(String message) {
		return message.replaceAll("#([A-Za-z0-9_]+)", "").replaceAll("\\s+", " ");
	}

	static public String stripMentions(String message) {
		return message.replaceAll("@([A-Za-z0-9_]+)", "").replaceAll("\\s+", " ");
	}

	static public String stripQuotAmpLt(String message) {
		return message.replaceAll("(&quot|&amp|&lt)", "").replaceAll("\\s+", " ");
	}

	static public String correctApostrophes(String message) {
		return message.replaceAll("[’‘]", "'").replaceAll("\\s+", " ");
	}

	static public String stripNumbers(String message) {
		return message.replaceAll("[0-9]*", "").replaceAll("\\s+", " ");
	}

	static public String correctDotCommaDoublePoints(String message) {
		String strippedText;

		strippedText = message.replaceAll("[.]([a-z])", " $1 ");
		strippedText = strippedText.replaceAll("[,]([a-z])", " $1 ");
		strippedText = strippedText.replaceAll("[:] ([a-z])", ": $1");
		return strippedText;
	}

}
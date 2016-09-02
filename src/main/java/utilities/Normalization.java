package utilities;

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
		message = message.replaceAll("([a-z]*) [.]([a-z]*)", "$1. $2");
		message = message.replaceAll("([a-z]*) [,]([a-z]*)", "$1, $2");
		message = message.replaceAll("([a-z]*) [:]([a-z]*)", "$1: $2");
		return message;
	}
	
	static public String removeQuotesApostrophes(String message){
		return message.replaceAll("[\"']", " ");
	}
	
	static public String replaceUnderscore(String message){
		return message.replaceAll("[_]", " ");
	}
	
	static public String stripMultipleSpaces(String message) {
        return message.trim().replaceAll("( +| +$)", " ").trim();
    }
	
	static public String stripUnnecessaryWords(String message) {
        StringBuilder strippedText = new StringBuilder();
        String[] unnecesaryWords = {"a", "an", "at", "and", "am", "any", "as", "are", "all",
            "be", "his", "he", "him", "her", "hers", "how", "have", "has", "had", "from",
            "i", "it", "its", "is", "in", "ll", "im",
            "me", "my", "mine", "on", "off", "ok",
            "of", "over", "she", "the", "that", "this",
            "you", "your", "yours", "to", "why", "where", "was", "when", "got", "do", "did", "done"
        };
        String[] sentenceWords = message.split(" ");

        for (int i = 0; i < sentenceWords.length; i++) {
            String sentenceWord = sentenceWords[i];
            for (int j = 0; j < unnecesaryWords.length; j++) {
                String unnecesaryWord = unnecesaryWords[j];
                if (sentenceWord.equals(unnecesaryWord)) {
                    sentenceWords[i] = "";
                }
            }
        }
        for (int index = 0; index < sentenceWords.length - 1; index++) {
            strippedText = strippedText.append(sentenceWords[index]).append(" ");
        }
        
        String stringStrippedText = strippedText.toString();
        stripMultipleSpaces(stringStrippedText);
        
        return stringStrippedText;
    }
	
	static public String stripLonelyLetters(String message) {
        String strippedText;

        String expression = "( [a-z] | [a-z]$)";
        strippedText = message.replaceAll(expression, "");

        return strippedText;
    }
	
	static public String normalizeMessage(String message){
		message = replaceUnderscore(message);
		message = correctApostrophes(message);
		message = correctDotCommaDoublePoints(message);
		message = removeQuotesApostrophes(message);
		message = stripHashtags(message);
		message = stripMentions(message);
		message = stripNewLineTab(message);
		message = stripNumbers(message);
		message = stripQuotAmpLt(message);
		message = stripUnnecessaryWords(message);
		message = stripLonelyLetters(message);
		message = stripMultipleSpaces(message);
		
		return message;
	}

}
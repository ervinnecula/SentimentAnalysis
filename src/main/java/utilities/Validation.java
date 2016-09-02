package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	public static boolean validateFirstLetter(String word){
        Pattern p = Pattern.compile("[a-z]");
        Matcher m = p.matcher(word.substring(0, 1));
        return m.matches();
	}
}

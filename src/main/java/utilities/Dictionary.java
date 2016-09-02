package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import datamodel.SentimentType;
import datamodel.WordModel;

public class Dictionary {

	private static Map<String, List<WordModel>> dictionary;

	/**
	 * This method gives you the dictionary (which is unique because it is
	 * static.
	 * 
	 * @return a Map with a String key (the letter of the alphabet) and the
	 *         words that begin with that letter.
	 */
	public static Map<String, List<WordModel>> getDictionary() {
		return dictionary;
	}

	/**
	 * Create a dictionary representation using a List of WordModel objects for
	 * each starting letter (from a-z). The dictionary contains now pairs letter
	 * - WordModel objects: a List<WordModel> b List<WordModel> ... z List
	 * <WordModel>
	 */
	public static void initializeDictionary() {
		ArrayList<WordModel> wordsStartLetter;
		dictionary = new HashMap<String, List<WordModel>>();

		for (char letter = 'a'; letter <= 'z'; letter++) {
			wordsStartLetter = new ArrayList<WordModel>();

			dictionary.put(Character.toString(letter), wordsStartLetter);
		}
	}

	/**
	 * Add a particular word to the dictionary. If the word already exists
	 * inside the dictionary, it will be increased its positivity or negativity
	 * according to the parameter given. The first letter of the word is
	 * verified, and then the word is added to the corresponding list, according
	 * to its first letter.
	 * 
	 * @param word
	 *            an absolute URL giving the base location of the image
	 * @param positivity
	 *            the value of the word, can be SentimentType.POSITIVE or
	 *            SentimentType.NEGATIVE
	 */
	public static void addWord(String word, SentimentType positivity) {

		if (Validation.validateFirstLetter(word) == true) {

			WordModel wordModel = new WordModel();
			boolean alreadyExists = false;
			List<WordModel> wordsOfLetter = new ArrayList<WordModel>();

			wordsOfLetter = dictionary.get(word.substring(0, 1));

			for (int i = 0; i < wordsOfLetter.size(); i++) {
				if (alreadyExists == false) {
					wordModel = wordsOfLetter.get(i);

					if (wordModel.getWord().equals(word)) {
						if (positivity.equals(SentimentType.POSITIVE)) {
							wordModel.setPosCases(wordModel.getPosCases() + 1);
						} else {
							wordModel.setNegCases(wordModel.getNegCases() + 1);
						}

						alreadyExists = true;
					}
				}
			}
			if (alreadyExists == false) {
				wordModel = new WordModel(word, 0, 0);

				if (positivity.equals(SentimentType.POSITIVE)) {
					wordModel.setPosCases(wordModel.getPosCases() + 1);
				} else {
					wordModel.setNegCases(wordModel.getNegCases() + 1);

				}
				wordsOfLetter.add(wordModel);
			}
		}
	}

	/**
	 * This method gives you the words that begin with a certain letter.
	 *
	 * @param letter
	 *            First letter of the list that you would like to receive. For
	 *            "g" you would get: great, good, etc.
	 * @return A list of WordModel elements that contain the word, positive and
	 *         negative apparitions.
	 */
	public static List<WordModel> getWordsOfLetter(String letter) {
		return dictionary.get(letter);
	}

	/**
	 * This method writes the dictionary Map to a file. The structure is as it follows:
	 * 
	 * <dictionary>
	 * 		<a/>
	 * 		<b/>
	 * 		<c>
	 * 					
	 * 
	 * </dictionary>
	 *
	 * @param letter
	 *            First letter of the list that you would like to receive. For
	 *            "g" you would get: great, good, etc.
	 * @return A list of WordModel elements that contain the word, positive and
	 *         negative apparitions.
	 */
	public static void serializeDictionary(String docName) {

		List<WordModel> wordsToBeSerialized = new ArrayList<WordModel>();

		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.newDocument();

			// <dictionary>
			Element rootElement = doc.createElement("dictionary");
			doc.appendChild(rootElement);

			for (char letter = 'a'; letter <= 'z'; letter++) {

				String strLetter = Character.toString(letter);

				// all words that begin with current letter
				wordsToBeSerialized = dictionary.get(strLetter);

				// <a>, <b>, <c> ...
				Element letterElement = doc.createElement(strLetter);
				rootElement.appendChild(letterElement);

				for (WordModel wordEntry : wordsToBeSerialized) {

					Element entryElement = doc.createElement("entry");
					letterElement.appendChild(entryElement);

					Element wordElement = doc.createElement("word");
					wordElement.appendChild(doc.createTextNode(wordEntry.getWord()));
					entryElement.appendChild(wordElement);

					Element posElement = doc.createElement("pos");
					posElement.appendChild(doc.createTextNode(Integer.toString(wordEntry.getPosCases())));
					entryElement.appendChild(posElement);

					Element negElement = doc.createElement("neg");
					negElement.appendChild(doc.createTextNode(Integer.toString(wordEntry.getNegCases())));
					entryElement.appendChild(negElement);
				}

			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(docName));
			transformer.transform(source, result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

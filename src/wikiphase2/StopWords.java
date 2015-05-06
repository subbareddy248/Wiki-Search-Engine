/**
 * 
 */
package wikiphase2;

import java.util.HashSet;

/**
 * @author subba
 *
 */
public class StopWords {
	
	public static final char [] alphabets = { 'a','b','c','d','e','f','g','h',
		'i','j','k','l','m','n','o','p','g','r','s','t','u','v','w','x','y','z'
	};

	public static final String stopwordslist[] = { "a", "able", "about",
			"across", "after", "all", "almost", "also", "am", "among", "an",
			"and", "any", "are", "as", "at", "b", "be", "because", "been",
			"but", "by", "c", "can", "cannot", "could", "d", "dear", "did",
			"do", "does", "e", "either", "else", "ever", "every", "f", "for",
			"from", "g", "get", "got", "h", "had", "has", "have", "he", "her",
			"hers", "him", "his", "how", "however", "i", "if", "in", "into",
			"is", "it", "its", "j", "just", "k", "l", "least", "let", "like",
			"likely", "m", "may", "me", "might", "most", "must", "my",
			"neither", "n", "no", "nor", "not", "o", "of", "off", "often",
			"on", "only", "or", "other", "our", "own", "p", "q", "r", "rather",
			"s", "said", "say", "says", "she", "should", "since", "so", "some",
			"t", "than", "that", "the", "their", "them", "then", "there",
			"these", "they", "this", "tis", "to", "too", "twas", "u", "us",
			"v", "w", "wants", "was", "we", "were", "what", "when", "where",
			"which", "while", "who", "whom", "why", "will", "with", "would",
			"x", "y", "yet", "you", "your", "z" };

	public static final HashSet<String> stopwordsarrList = new HashSet<String>();
	static {
		for (String stopword : stopwordslist) {
			stopwordsarrList.add(stopword);
		}
		HashSet<String> alphabetslist = new HashSet<String>();
		for(int i =0; i<26;i++) {
		StringBuffer sb = new StringBuffer();
		char currentchar = alphabets[i];
		for(int j=0;j<20;j++) {
		sb.append(currentchar);
		if(sb.length()>4) {
		alphabetslist.add(sb.toString());
		}
		}
		sb.setLength(0);
		}
		//System.out.println("Alpha "+alphabetslist);
		stopwordsarrList.addAll(alphabetslist);
		//System.out.println(""+stopwordsarrList);
	}
}

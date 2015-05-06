/**
 * 
 */
package wikiphase2;

/**
 * @author subba
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import wikiphase2.StopWords;
import wikiphase2.WikiTextParser;
import wikiphase2.WikipediaBean;

/**
 * Searching tool to search in the index
 * @author gaurav
 *
 */
public class SearchingTool 
{
	
	public static String indexfolderpath = "/home/subba/Desktop/index";
	public static String filesec = "";
	public static String fileindex = "";
	public static String input = "";
	public static String titleFile = indexfolderpath + "/titles.txt";
	
	public static HashMap<String, Integer> mapField = new HashMap<String, Integer>();
	
	static 
	{
		mapField.put("t", 15);//"00001111"
		mapField.put("i", 23);//"00010111");
		mapField.put("c", 29);//"11111101");
		mapField.put("l", 27);//"//11111011");
		mapField.put("b", 30);//"11101110");
	}
	public static String searchword(String word) throws Exception {
		long startcount = 1;
		long endcount = 1;
			String line = "";
			String line2 = "";
			StringBuffer sb = new StringBuffer();
			char ch[] = word.toCharArray();
			int len = ch.length;
			for (int i = 0; i < len; i++) {
				ch[i] = Character.toLowerCase(ch[i]);
				int chin = (int) ch[i];
				if (chin >= 97 && chin <= 122) {
					sb.append((ch[i]));
				}
			}
			word = sb.toString();
			if(StopWords.stopwordsarrList.contains(word)) {
				return "";
			}
			word = WikiTextParser.doStemming(word);
			char startchar = word.charAt(0);
			
			String outfile = 	indexfolderpath+"/temp"+startchar+".txt";
			 File out = new File(outfile);
			RandomAccessFile raf = new RandomAccessFile(new File(outfile), "r");
			String fileline = WikiTextParser.binarySearch(raf, word, 0, out.length());
			
		return fileline;	
	}

	public static String sort(String line) 
	{
		String [] sarr = line.split(",");
		TreeSet<String> idlist = new TreeSet<String>();
		for(String s: sarr) 
		{
			idlist.add(s);
		}
		StringBuffer sb = new StringBuffer();
		for(String id: idlist) 
		{
			sb.append(id);
			sb.append(WikipediaBean.comma);
		}
		return sb.toString().substring(0, sb.length()-1);
	}
	
	
	public static void main(String[] args)  
	{
		try {
			SearchingTool.indexfolderpath = args[0];
			//indexfolderpath = "index";
			filesec = indexfolderpath+File.separatorChar+"filesec.txt";
			fileindex = indexfolderpath+File.separatorChar+"index.txt";
			input=indexfolderpath+File.separatorChar+"input.txt";
			//	String word = args[1];
			//	String word = "bye";
			
			BufferedReader br = new BufferedReader(new FileReader(new File(args[1])));
			int len=Integer.parseInt(br.readLine());
			
			//BufferedReader br = new BufferedReader(new InputStreamReader(new File(input)));
			String line = "";
			for(int xx=0;xx<len;xx++)
			{
				line = br.readLine();
				long time1 = System.currentTimeMillis();
				String inputString = line;
				String words[] = inputString.split(" ");
				ArrayList<LinkedHashSet<String>> searchedlist = new ArrayList<LinkedHashSet<String>>();
				for(String word: words) 
				{
					Integer fieldvalue = 0;
					String s = "";
					LinkedHashSet<String> listofpage = new LinkedHashSet();
					if(word.indexOf(':')!=-1) 
					{
						String newword = word.substring(2);
						String field = word.substring(0, 1);
						fieldvalue = mapField.get(field);
						listofpage = new LinkedHashSet<String>();
						try 
						{
							s = searchword(newword);
							
							if(s==null || s == "") 
							{
								 continue;
							}
							String firstsplit[] = s.split(",");
							int i=0;
							while(i<firstsplit.length) 
							{
								//System.out.println("First "+firstsplit[i]);
								String secondsplit[] = firstsplit[i].split("_");
								//System.out.println("Second split "+secondsplit[0]);
								String [] thirdsplit = secondsplit[1].split("-");
								int j=0;
								while(j < thirdsplit.length) 
								{
									String fourthsplit [] = thirdsplit[j].split(":");
									Integer value = Integer.parseInt(fourthsplit[1]);
									int val = value | fieldvalue;
									//System.out.println("Val "+val+"FIled value "+fieldvalue);
									if(val == 31) 
									{
										listofpage.add(fourthsplit[0]);
										//listofpage.append(',');
									}
									j++;
								}
								i++;
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		 
						//s = listofpage.toString();
						//System.out.println(" Print -->"+s);
					} 
					else 
					{
						try 
						{
							s = searchword(word);
							
							if(s==null || s == "") 
							{
								 continue;
							}
							// System.out.println("Line Found "+s);
							String firstsplit[] = s.split(",");
							int i=0;
							while(i<firstsplit.length) 
							{
									String secondsplit[] = firstsplit[i].split("_");
									String [] thirdsplit = secondsplit[1].split("-");
									int j=0;
									while(j < thirdsplit.length) 
									{
										String fourthsplit [] = thirdsplit[j].split(":");
										Integer value = Integer.parseInt(fourthsplit[1]);
										listofpage.add(fourthsplit[0]);
										//listofpage.append(',');
										j++;
									}
									i++;
							}
						} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						} 
						//s = listofpage.toString();
						//System.out.println("Print "+s);
					}
					searchedlist.add(listofpage);
				}
				//System.out.println("Searched list "+searchedlist);
				//System.out.println("Seached List "+searchedlist.size());
				Iterator<LinkedHashSet<String>> list = searchedlist.iterator();
				LinkedHashSet<String> intersecthashset = searchedlist.get(0);
				LinkedHashSet<String> unionhashset = searchedlist.get(0);
				int i=1;
				while(i<searchedlist.size()) 
				{
					LinkedHashSet<String> newhashset = searchedlist.get(i);
					intersecthashset.retainAll(newhashset);
					unionhashset.addAll(newhashset);
					i++;
				}
				ArrayList<String> finalpages = new ArrayList<String>();
				int count = 10;
				Iterator<String> it1 = intersecthashset.iterator();
				i=1;
				while(i<=count) 
				{
					if(it1.hasNext()) 
					{
						finalpages.add(it1.next());
					}
					i++;
				//it1.next();
				}
				Iterator<String> it2 = intersecthashset.iterator();
				int j=i;
				while(j<=count) 
				{
					String page= "";
					if(it2.hasNext()) 
					{
						page = it2.next();
					} 
					else 
					{
						break;
					}
					if(!finalpages.contains(page)) 
					{
						finalpages.add(page);
					}
					j++;
				}
				System.out.println("Final Pages "+finalpages);
				File titlef = new File(titleFile);
				RandomAccessFile raf = new RandomAccessFile(titlef, "r");
				
				//Retrieving top 10 results of a document
				int length = 9;
				i=length;
				while(i>=0) 
				{
					System.out.print(finalpages.get(i));
					String fileline = WikiTextParser.binarySearchinTitle(raf, Integer.parseInt(finalpages.get(i)), 0, titlef.length());
					System.out.println(" "+fileline);
					i--;
				}
				StringBuffer sb = new StringBuffer();
				//System.out.println("Final Pages "+finalpages);
				for(String pageno: finalpages) 
				{
					sb.append(pageno);
					sb.append(WikipediaBean.comma);
				}
				//System.out.println(sb.toString());
				long time2 = System.currentTimeMillis();
				System.out.println("TIme consumed "+(time2-time1)/1000.0);
			}
			//searchword("Age!");
			//searchword("!bye");
			//searchword("$$$bye!$$$");
			//searchword("$$$Bye!$$$");
			//searchword("$$$b$ye!$$$");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
}


/**
 * 
 */
package wikiphase2;

import java.io.File;
import java.util.HashMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



//import wikipedia.FileIO;
import wikiphase2.WikiTextParser;
import wikiphase2.Page;
import wikiphase2.WikipediaBean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author subba
 *
 */
public class WikiSAXParser extends DefaultHandler {
	
	boolean istitle = false;
	String currenttag = "";
	private boolean starttag = false;
	private StringBuffer sb = new StringBuffer();
	private HashMap<String, StringBuffer> tagConent = new HashMap<String, StringBuffer>();
	private boolean isFirst = true;
	private String pageid;
	private Page currentPage = null;
	String sampXmlFileName;
	public static String outtitlefile="title";
	public WikiSAXParser(String smapleXmlFileName) 
	{
	        this.sampXmlFileName=smapleXmlFileName;
	        parseDocument(smapleXmlFileName);
	}
	/**
	 * parse the document using SAX Parser
	 * @param inputfilename
	 */
	public void parseDocument(String inputfilename) 
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		File inputfile = new File(inputfilename);
		//SAXParser saxParser = null;
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(inputfile,this);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	/**
	 * starting tag 
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		try {
			currenttag = qName;
			starttag = true;
			if (currenttag.equals("page")) {
				currentPage = new Page();
				isFirst = true;
				pageid = new String();
			}
			sb = new StringBuffer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * characters between tags
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		try {
			for (int i = start; i < start + length; i++) {
				sb.append(Character.toLowerCase(ch[i]));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
	//	System.out.println("Ending -->" + qName);
		try {
			if (currenttag.equals("id") && isFirst) 
			{
				pageid = sb.toString();
				currentPage.setId(pageid);
				isFirst = false;
			} 
			else if (qName.equals("title")) 
			{
				currentPage.setTitle(sb);
			} 
			else if (qName.equals("text")) 
			{
				currentPage.setBodytext(sb);
			} 
			else if (qName.equals("page")) 
			{
				//System.out.println("Page end");
				// sb.setLength(0);
				WikiTextParser.treatPage(currentPage);
			} 
			else if(qName.equalsIgnoreCase("mediawiki") || qName.equalsIgnoreCase("file")) 
			{
				System.out.println("End of media file Tag");
				WikiTextParser.fileend();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	/**
	 * main program start from here 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//String inputfilepath = args[0];
		String inputfilepath = "/home/subba/Desktop/sample2.8.xml";//args[0];
		long time1 = System.currentTimeMillis();
		//System.out.println("Input file path "+inputfilepath);
		//IndexingTools.indexoutputfolder = args[1];
		WikiTextParser.indexoutputfolder = "/home/subba/Desktop/index";
		new WikiSAXParser(inputfilepath);
		long time2 = System.currentTimeMillis();
		System.out.println("Difference"+(time2-time1)/1000+"seconds");
	}
};

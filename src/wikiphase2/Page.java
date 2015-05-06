/**
 * 
 */
package wikiphase2;

/**
 * @author subba
 *
 */
public class Page {

	private StringBuffer infobox = new StringBuffer();
	private StringBuffer bodytext = new StringBuffer();
	private StringBuffer title = new StringBuffer();
	private StringBuffer category = new StringBuffer();
	private StringBuffer references = new StringBuffer();
	private StringBuffer externallinks = new StringBuffer();
	private int tcount = 0;
	private int bcount = 0;
	private int ccount = 0;
	private int icount = 0;
	private int ecount = 0;
	
	public int getTcount() {
		return tcount;
	}
	public void setTcount(int tcount) {
		this.tcount = tcount;
	}
	public int getBcount() {
		return bcount;
	}
	public void setBcount(int bcount) {
		this.bcount = bcount;
	}
	public int getCcount() {
		return ccount;
	}
	public void setCcount(int ccount) {
		this.ccount = ccount;
	}
	public int getIcount() {
		return icount;
	}
	public void setIcount(int rcount) {
		this.icount = rcount;
	}
	public int getEcount() {
		return ecount;
	}
	public void setEcount(int ecount) {
		this.ecount = ecount;
	}
	private String id = "";
	private char [] text = new char[100];
	public char[] getText() {
		return text;
	}
	public void setText(char[] text) {
		this.text = text;
	}
	public StringBuffer getInfobox() {
		return infobox;
	}
	public void setInfobox(StringBuffer infobox) {
		this.infobox = infobox;
	}
	public StringBuffer getBodytext() {
		return bodytext;
	}
	public void setBodytext(StringBuffer bodytext) {
		this.bodytext = bodytext;
	}
	public StringBuffer getTitle() {
		return title;
	}
	public void setTitle(StringBuffer title) {
		this.title = title;
	}
	public StringBuffer getCategory() {
		return category;
	}
	public void setCategory(StringBuffer category) {
		this.category = category;
	}
	public StringBuffer getReferences() {
		return references;
	}
	public void setReferences(StringBuffer references) {
		this.references = references;
	}
	public StringBuffer getExternallinks() {
		return externallinks;
	}
	public void setExternallinks(StringBuffer externallinks) {
		this.externallinks = externallinks;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}


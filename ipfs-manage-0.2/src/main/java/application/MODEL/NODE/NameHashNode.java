package application.MODEL.NODE;

public class NameHashNode {

	private String filename;
	private String hash;
	
	
	public NameHashNode() {}
	
	public NameHashNode(String _filename,String _hash) 
	{
		filename = _filename;
		hash = _hash;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
}

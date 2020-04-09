package application.MODEL.TABLE;

import java.util.List;

public class FileBackupTable {

	private List<String> files;
	private int num;
	
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public boolean isExistFile(String filehash) 
	{
		for(int i=0;i<files.size();i++) 
		{
			if(files.get(i).equalsIgnoreCase(filehash)) 
			{
				return true;
			}
		}
		
		return false;
	}
	
	
}

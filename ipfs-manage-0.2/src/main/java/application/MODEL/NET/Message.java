package application.MODEL.NET;

public class Message {

	
	private String filehash;
	private String backupIp;
	private String senderIp;
	private boolean isBackup;
	
	
	
	public String getFilehash() {
		return filehash;
	}
	public void setFilehash(String filehash) {
		this.filehash = filehash;
	}
	public String getBackupIp() {
		return backupIp;
	}
	public void setBackupIp(String backupIp) {
		this.backupIp = backupIp;
	}
	public boolean isBackup() {
		return isBackup;
	}
	public void setBackup(boolean isBackup) {
		this.isBackup = isBackup;
	}
	public String getSenderIp() {
		return senderIp;
	}
	public void setSenderIp(String senderIp) {
		this.senderIp = senderIp;
	}
	
	
}

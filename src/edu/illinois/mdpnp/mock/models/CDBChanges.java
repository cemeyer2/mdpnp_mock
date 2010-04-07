package edu.illinois.mdpnp.mock.models;

public class CDBChanges {
	
	public static class Changes {
		private String rev;
		public String getRev() {return rev;}
		public void setRev(String rev) {this.rev = rev;}
	}
	
	private int seq;
	private String id;
	private Changes[] changes;
	private boolean deleted;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Changes[] getChanges() {
		return changes;
	}
	
	public void setChanges(Changes[] changes) {
		this.changes = changes;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}

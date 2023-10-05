package org.python.net;

public class ValidateOtpResult {
	protected byte[] userid;
	protected byte[] encUserId;
	protected byte[] tagUserId;
	protected byte[] encPassword;
	protected byte[] tagPassword;
	protected byte[] nonce;
	
	/**
	 * 
	 * @param userid
	 * @param encUserId
	 * @param tagUserId
	 * @param encPassword
	 * @param tagPassword
	 * @param nonce
	 */
	public ValidateOtpResult(byte[] userid, byte[] encUserId, byte[] tagUserId, byte[] encPassword, byte[] tagPassword,
			byte[] nonce) {
		super();
		this.userid = userid;
		this.encUserId = encUserId;
		this.tagUserId = tagUserId;
		this.encPassword = encPassword;
		this.tagPassword = tagPassword;
		this.nonce = nonce;
	}

	public byte[] getUserid() {
		return userid;
	}

	public void setUserid(byte[] userid) {
		this.userid = userid;
	}

	public byte[] getEncUserId() {
		return encUserId;
	}

	public void setEncUserId(byte[] encUserId) {
		this.encUserId = encUserId;
	}

	public byte[] getTagUserId() {
		return tagUserId;
	}

	public void setTagUserId(byte[] tagUserId) {
		this.tagUserId = tagUserId;
	}

	public byte[] getEncPassword() {
		return encPassword;
	}

	public void setEncPassword(byte[] encPassword) {
		this.encPassword = encPassword;
	}

	public byte[] getTagPassword() {
		return tagPassword;
	}

	public void setTagPassword(byte[] tagPassword) {
		this.tagPassword = tagPassword;
	}

	public byte[] getNonce() {
		return nonce;
	}

	public void setNonce(byte[] nonce) {
		this.nonce = nonce;
	}
	
}

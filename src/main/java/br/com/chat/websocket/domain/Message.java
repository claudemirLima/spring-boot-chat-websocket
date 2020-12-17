package br.com.chat.websocket.domain;

public class Message {
	private String username;
	private String type;
	private String msg;
	private Integer onlineCount;
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setOnlineCount(Integer onlineCount) {
		this.onlineCount = onlineCount;
	}
	public Integer getOnlineCount() {
		return onlineCount != null ? onlineCount : Integer.valueOf(0);
	}
	
}

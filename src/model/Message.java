package model;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String source;
	private String destination;
	private String type;
	private Object content;
	
	public Message (String source, String destination, String type, Object content){
		this.source = source;
		this.destination = destination;
		this.type = type;
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public String getType() {
		return type;
	}

	public Object getContent() {
		return content;
	}
	
	
	
}

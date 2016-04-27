package common;

import java.io.Serializable;

public class LineDrawString implements Serializable{
	private String s;
	public LineDrawString(String s){
		this.s = s;
	}
	public String getValue(){
		return s;
	}
}

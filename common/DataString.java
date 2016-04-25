package common;

import java.io.Serializable;

public class DataString implements Serializable {
	  private String s;
	  public DataString(String s){
		  this.s = s;
	  }
	  public String getValue(){
		  return s;
	  }
}

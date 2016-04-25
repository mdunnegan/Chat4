package common;

import java.io.Serializable;

public class UpdateGUIString implements Serializable {
	  private String s;
	  public UpdateGUIString(String s){
		  this.s = s;
	  }
	
	  public String getValue(){
		  return s;
	  }
}

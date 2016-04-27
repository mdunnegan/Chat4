package client;

import java.io.IOException;

public class linedraw extends ClientCommand{

	public linedraw(String str, Chat4ClientCommandProcessor client)
	  {
	    super(str, client);
	  }

	@Override
	public void doCommand() {
		try
	    {
	      String handlerMessage = "LineDrawHandler " + getStr();
	      getClient().OC().sendToServer(handlerMessage);
	    }
	    catch(IOException ex)
	    {
	      getClient().clientUI().display("Exception " + ex + "\nUnable to draw ");
	    }
		
	}
	
}

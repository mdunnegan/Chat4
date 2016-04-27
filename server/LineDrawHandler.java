package server;

import common.DataString;
import common.LineDrawString;
import ocsf.server.ConnectionToClient;

public class LineDrawHandler extends ServerNonLoginHandler{
	
	public LineDrawHandler(String str, Chat4Server server, ConnectionToClient client) {
		super(str, server, client);
		//System.out.println("linedraw hander str" + str);
	}

	@Override
	public void handleMess() {
		
		System.out.println("In LineDrawHandler");
		
		getServer().sendToAllClients(new LineDrawString("#linedraw " + getMessage()), (String) getClient().getInfo("channel"), (String)getClient().getInfo("id"));
		
	}

}

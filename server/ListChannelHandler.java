package server;

import server.*;
import ocsf.server.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import common.DataString;
import javafx.util.Pair;

public class ListChannelHandler extends ServerNonLoginHandler{
/**
 * This Class will list all available channels for a user
 *
 */

  public ListChannelHandler(String str, Chat4Server server, ConnectionToClient client)
    {
      super(str, server, client);

    }

  @Override
  public void handleMess()
  {
    try
    {
      //ArrayList<String> channelNameSet = (ArrayList<String>) getServer().getChannelManager().getChannelNames();
      
      //getClient().sendToClient(channelNameSet);
    	      
      Set<String> channelNameSet = getServer().getChannelManager().getChannelNames();
      String toSend = "channels ";
      
      for (String channelName : channelNameSet)
      {
        //getClient().sendToClient(channelName);// send each to client
    	toSend += channelName + " ";
      }
      getClient().sendToClient(new DataString(toSend));
      
    }
    catch(IOException e)
    {
      getServer().getConsole().display(e + "\nError sending channel names in list command.");
    }
  }
}

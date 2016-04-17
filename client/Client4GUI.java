package client;

/* TextDemo.java requires no other files. */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import ocsf.client.ObservableClient;
import common.ChatIF;

public class Client4GUI extends JPanel implements ActionListener, ChatIF, Observer {

	protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    
    /**
     * The default port to connect on.
     */
    final public static int DEFAULT_PORT = 6666;
    
    /**
     * The instance of the client created by this ClientGUI.
     */
    Chat4ClientCommandProcessor client;

    public Client4GUI(String host, int port, String id, String password) {
        super(new GridBagLayout());

        textField = new JTextField(20);
        
        // 'this' (Client4GUI) must implement ActionListener
        // text field listens for Client4GUI (this one) - thus, once enter is pressed in the text field (GUI?),
        //												  actionPerformed is called
        // or
        // Client4GUI listens for text field
        
        
        // Simple way to understand: 
        // Because of this line,
        // when textField is activated, actionPerformed will be called
        textField.addActionListener(this);

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);

        try
        {
        	client = new Chat4ClientCommandProcessor(id, password, host, port, this);
        } catch(IOException ex){
        	display("IOException " + ex + "when connecting, shutting down.");	
        }
        display("connected to " + host + "-" + port);
        
        // Very important. This makes it so that the following happens:
        // When the observable client instance calls notifyObservers(), 
        // this object's update() method will be called
        client.OC().addObserver(this);
    }

    // gets called every time enter is pressed in the text box
    public void actionPerformed(ActionEvent evt) {
        String message = textField.getText(); 
        
        // Self revelation here. In OOP, every object can basically get to any other object
        // Division of responsibility is a difficult design process
        // Having this method tell the client to handleMessageFromClientUI is one of those choices
        // This is an example of message passing. Telling an object to do something
        
        client.handleMessageFromClientUI(message); // literally message passing
        textField.setText("");
    }
    
    // This gets called when the ObservableClient instance calls notifyObservers()
    // This is because this object is observing the ObservableClient instance
    // (See end of this class's constructor)
    public void update(Observable OC, Object msg)
    {
      if(msg instanceof String)
      	display((String)msg);
      else if(msg instanceof Exception)
      	display("Connection exception " + (Exception)msg);
    }
    
    public void display(String message)
    {
        textArea.append("> " + message + newline);    	
        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }    
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI(String host, int port) {
    	
//    	String id = JOptionPane.showInputDialog("Username: ");
//    	String password = JOptionPane.showInputDialog("Password: ");

    	
    	JTextField usernameField = new JTextField(5);
        JTextField passwordField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Username: "));
        myPanel.add(usernameField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Password: "));
        myPanel.add(passwordField);

        JButton submitButton = new JButton("Submit");
        myPanel.add(submitButton);
        
        JFrame f = new JFrame("Login");
        f.add(myPanel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        f.pack();
        f.setVisible(true);
        
        submitButton.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText(); 
                
                JFrame guiFrame = new JFrame();
                guiFrame.add(new Client4GUI(host, port, username, password));
            	f.dispose(); 
            	guiFrame.pack();
            	guiFrame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        String hostStr = "";
        String portStr = "";  //The port number
        
        try
        {
          hostStr = args[0];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          hostStr = "localhost";
        }
        	
        try
        {
          portStr = args[1];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          portStr = "" + DEFAULT_PORT;
        }
        	
        final String host = hostStr;
        final int port = Integer.parseInt(portStr);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() { 
            	createAndShowGUI(host, port);}
            }
        );
    }
}


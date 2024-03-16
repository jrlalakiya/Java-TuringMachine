import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Client {
	
	
	    private static Socket sock;
	    private static boolean endIt = false;
	    private static String clientId;
	    private static PrintStream dat;
	    private static DataInputStream dis;
	    private static JTextArea console;
	    private static JTextArea tmInput;
	    public static boolean connectToServer(String serverAddress, int port, JTextArea consoleOutput, JTextArea tmTextField) {
	        try {
	            sock = new Socket(serverAddress, port);
	            dis = new DataInputStream(sock.getInputStream());
	            dat = new PrintStream(sock.getOutputStream());
	            console = consoleOutput;
	            tmInput = tmTextField;
	            // Receive client ID from the server after connecting
	            clientId = dis.readLine();
	            console.append("Connecting.......\n");
	            console.append("Connection with "+serverAddress+" on port "+port+" established with Cliend ID: "+clientId+"..\n");
	            

	            // Sending message to the server to end the connection
	            
	            
	            Thread serverListenerThread = new Thread(() -> {
	                while (!sock.isClosed()) {
	                    try {
	                        String serverMessage = dis.readLine();
	                        if (serverMessage != null) {
	                            processServerMessage(serverMessage);
	                        }
	                    } catch (IOException e) {
	                        // Handle exceptions
	                        e.printStackTrace();
	                    }
	                }
	               
	            });
	            serverListenerThread.start();
	            return true;
	       
	        } catch (IOException e) {
	            System.out.println(e);
	            return false;
	        }
	    }
	    
	 // Method to handle processing server messages based on protocol
	    private static void processServerMessage(String message) {
	        // Implement logic to process incoming server messages based on defined protocol
	        // Example: Split the message by protocol separator and interpret it accordingly
	        String[] parts = message.split(Config.PROTOCOL_SEPARATOR);
	        if (parts.length >= 2) {
	            String clientId = parts[0];
	            String protocolId = parts[1];

	            // Handle different protocol IDs received from the server
	            switch (protocolId) {
	                case Config.PROTOCOL_END:
	                	try {
	                		sock.close();
	                		
	                	} catch (IOException e) {
	                		// TODO Auto-generated catch block
	                		e.printStackTrace();
	                	}
	                    break;
	                case Config.PROTOCOL_RECVMODEL:
	                    console.append("TM Model Received:"+parts[2]+"\n");
	                    tmInput.setText(parts[2]);
	                    break;
	                // Add more cases to handle other protocols if needed
	                default:
	                    // Handle unknown protocol or other cases
	                    break;
	            }
	        }
	    }
	    
	    public static void sendMessage(String action) {
	    	String finalMessage=null;
	    	switch(action) {
	    	case "DC":
	    		finalMessage= clientId + Config.PROTOCOL_SEPARATOR + Config.PROTOCOL_END;
	    		console.append("Connection terminated!!!\n");
	    		break;
	    	case "SND":
	    		if(tmInput.getText()==null) {
	    			JOptionPane.showMessageDialog(null, "Invalid TM Model.");
	    			break;
	    		}
	    		finalMessage= clientId + Config.PROTOCOL_SEPARATOR + Config.PROTOCOL_SENDMODEL+Config.PROTOCOL_SEPARATOR+tmInput.getText();
	    		console.append("Sending Data....\n");
	    		break;
	    	case "RCV":
	    		finalMessage= clientId + Config.PROTOCOL_SEPARATOR + Config.PROTOCOL_RECVMODEL;
	    		console.append("Fetching TM Model.....");
	    		break;
	    	}
	    	dat.println(finalMessage);
	        dat.flush();
	    	
	    }
	    
	    public static boolean validateTMModel(String tmModel) {
	        // Step 1: Split the full string into individual tuples
	        String[] tuples = tmModel.split(" ");
	        
	        // Check the size for a valid string
	        int expectedLength = 5 * tuples.length; // Each tuple has 5 symbols
	        if (tmModel.length() != expectedLength + tuples.length - 1) {
	            System.out.println("Invalid Length");
	        	return false; // Invalid length
	        }

	        // Step 2, 3, and 4: Validate individual tuples
	        for (String tuple : tuples) {
	            String[] symbols = tuple.split("");
	            if (symbols.length != 5) {
	            	System.out.println("Each tuple should have exactly 5 symbols");
	                return false; // Each tuple should have exactly 5 symbols
	            }

	            // Validate the parameters of each tuple
	            try {
	                int an = Integer.parseInt(symbols[0]);
	                int bn = Integer.parseInt(symbols[1]);
	                int cn = Integer.parseInt(symbols[2]);
	                char dn = symbols[3].charAt(0);
	                int en = Integer.parseInt(symbols[4]);

	                // Check if parameters an and cn are states (0 to 9)
	                if (an < 0 || an > 9 || cn < 0 || cn > 9) {
	                	 System.out.println("Check if parameters an and cn are states (0 to 9)");
	                	return false;
	                }

	                System.out.println(bn);
	                // Check if {bn, dn} is in the alphabet
	                if (!(bn == 0 || bn == 1)) {
	                	System.out.println("Check if {bn, dn} is in the alphabet");
	                    return false;
	                }
	                
	                if (!(dn == '0' || dn == '1')) {
	                	System.out.println("Check if {bn, dn} is in the alphabet");
	                    return false;
	                }

	                // Check if en is a valid direction or a final/error state
	                if (!(en >= 0 && en <= 3)) {
	                	 System.out.println("Check if en is a valid direction or a final/error state");
	                	return false;
	                }
	            } catch (NumberFormatException e) {
	            	 System.out.println("Parsing error, invalid symbols");
	                return false; // Parsing error, invalid symbols
	            }
	        }

	        // All validations passed
	        return true;
	    }
	


}

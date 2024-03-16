import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

public class Server implements Runnable {
    Socket sock;
    static int nclient = 0, nclients = 0;
    static ServerSocket servsock;
    int port;
    boolean isRunning = true;
    JTextArea console;

    public Server() {
    }

    public Server(int port, JTextArea consoleOutput) {
    	console=consoleOutput;
        this.port = port;
    }

    public static void main(String arg[]) throws IOException {
        Thread servDaemon = new Thread(new Server());
        servDaemon.start();
    }

    public void stopServer() {
        this.isRunning = false;
        try {
            if (servsock != null && !servsock.isClosed()) {
                servsock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            servsock = new ServerSocket(port);
            console.append("Server running on " + InetAddress.getLocalHost() + " at port " + port + "!\n");

            while (isRunning) {
                if (servsock.isClosed()) {
                    break;
                }
                sock = servsock.accept();
                if (!isRunning) {
                    break;
                }

                nclient += 1;
                nclients += 1;
                console.append("Connecting " + sock.getInetAddress() + " at port " + sock.getPort() + ".\n");
                Worked w = new Worked(sock, nclient,console);
                w.start();
            }
            if (servsock != null && !servsock.isClosed()) {
                servsock.close();
            }
        } catch (IOException e) {
            if (!isRunning && servsock.isClosed()) {
            	console.append("\nServer socket closed.");
            } else {
            	console.append("\nFailed to start the server on port " + port);
                e.printStackTrace();
            }
        }
    }

    public boolean isInterrupted() {
        return false;
    }
}

class Worked extends Thread {
    Socket sock;
    int clientid, poscerq;
    String strcliid;
    JTextArea console;
    String tmModel=Config.DEFAULT_TMMODEL;
    
    
    
    public Worked(Socket s, int nclient, JTextArea console) {
        sock = s;
        clientid = nclient;
        this.console = console;
    }

    public void run() {
        String data = null;
        PrintStream out = null;
        DataInputStream in = null;
        try {
            out = new PrintStream(sock.getOutputStream());
            in = new DataInputStream(sock.getInputStream());

            // Send client ID as the first response
            out.println(clientid);
            out.flush();

            while (true) {
                data = in.readLine();

                if (data != null && !data.isEmpty()) {
                    String[] parts = data.split(Config.PROTOCOL_SEPARATOR);
                    strcliid = parts[0]; // Extract client ID

                    if (parts.length > 1) {
                        String protocolId = parts[1];

                        // Handle different protocols
                        switch (protocolId) {
                            case Config.PROTOCOL_END:
                            	console.append("Client[" + strcliid + "] sent an end message.\n");
                            	console.append("Ending connection with Client: "+strcliid+" ....\n");
                            	 try {
                                     sock.close();
                                     return; 
                                 } catch (IOException e) {
                                     e.printStackTrace();
                                 }
                                break;
                            case Config.PROTOCOL_SENDMODEL:
                                
                            	tmModel = parts[2]; 
                                console.append("Received TM Model from Client :" + strcliid + "\n Updated TM Model:" + tmModel+"\n");
                                break;
                            case Config.PROTOCOL_RECVMODEL:
                                // Handle sending game configuration to the client
                                out.println(strcliid+Config.PROTOCOL_SEPARATOR+Config.PROTOCOL_RECVMODEL+Config.PROTOCOL_SEPARATOR+tmModel);
                                console.append("Senidng TM Model to Client :" + strcliid + "\n TM Model Sent:" + tmModel+"\n");
                                break;
                            default:
                                // Handle other protocols or unknown requests
                                break;
                        }
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

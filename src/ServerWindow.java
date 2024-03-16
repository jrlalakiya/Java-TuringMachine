import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ServerWindow extends JFrame {

		private JLabel imageLabel;
		private JPanel contentPane;
		private JLabel portLabel;
		private JTextArea portInput;
	    private JButton startButton;
	   
	    private JButton endButton;

	    private Server serverInstance;
	    
	    
	    
	    // Components for footer
	    private JTextArea modelInput;
	    private JTextArea consoleOutput;

	  
	    public void initializeUI() {
	        // Set title of the frame
	        setTitle("Turing Machine");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setPreferredSize(new Dimension(620, 350));

	        // Initialize components for header
	        imageLabel = new JLabel(new ImageIcon("images/tm-server.png"));
	       	        
	        // Initialize components for footer
	        modelInput = new JTextArea();
	        modelInput.setColumns(15);
	        modelInput.addKeyListener(new KeyListener() {  
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					char c = e.getKeyChar();
	                if (!Character.isDigit(c) && c != '\b') {  
	                    e.consume();
	                }
	                if(modelInput.getText().length()>4) {
	                	e.consume();
	                }
				}

				@Override
				public void keyPressed(KeyEvent e) {
				}
				@Override
				public void keyReleased(KeyEvent e) {			
				}
	        });

	    
	        
	        portLabel = new JLabel("Port:");
	        portInput = new JTextArea();
	        portInput.setColumns(7);
	        portInput.addKeyListener(new KeyListener() {  
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					char c = e.getKeyChar();
	                if (!Character.isDigit(c) && c != '\b') {  
	                    e.consume();
	                }
	                if(portInput.getText().length()>4) {
	                	e.consume();
	                }
				}

				@Override
				public void keyPressed(KeyEvent e) {
				}
				@Override
				public void keyReleased(KeyEvent e) {			
				}
	        });
	        portInput.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a black line border
	        consoleOutput = new JTextArea();
		    consoleOutput.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a black line border
		    startButton = new JButton("Start");
		    startButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Check if the server instance already exists
	            	
	                if (serverInstance != null && !serverInstance.isInterrupted()) {
	                    JOptionPane.showMessageDialog(null, "Server is already running.");
	                } else {
	                    // Get the port number from portInput JTextArea
	                    int portNumber = 0;
	                    try {
	                        portNumber = Integer.parseInt(portInput.getText().trim());
	                        serverInstance = new Server(portNumber,consoleOutput); // Create a new server instance
	                        Thread serverThread = new Thread(serverInstance);
	                        startButton.setEnabled(false);
	                        endButton.setEnabled(true);
	                        portInput.setEnabled(false);
	                        serverThread.start(); // Start the server in a new thread
	                        consoleOutput.append("Server started on port " + portNumber + "\n");
	                    } catch (NumberFormatException ex) {
	                        JOptionPane.showMessageDialog(null, "Invalid port number.");
	                    } 
	                }
	            }
	        });
		    endButton = new JButton("End");
		    endButton.setEnabled(false);
		    endButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (serverInstance != null && !serverInstance.isInterrupted()) {
	                    // Stop the server and reset serverInstance
	                    serverInstance.stopServer();
	                    serverInstance = null;
	                    consoleOutput.append("Server stopped.......................");
	                    startButton.setEnabled(true);
	                    endButton.setEnabled(false);
	                    portInput.setEnabled(true);
	                } else {
	                    JOptionPane.showMessageDialog(null, "Server is not running.");
	                }
	            }
	        });
		   
		    JPanel mainPanel = new JPanel(new BorderLayout());
	        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
	        imagePanel.add(imageLabel);
	        JPanel componentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
	        componentPanel.add(portLabel);
	        componentPanel.add(portInput);
	        componentPanel.add(startButton);
	      
	        componentPanel.add(endButton);
	        mainPanel.add(imagePanel,BorderLayout.NORTH);
	        mainPanel.add(componentPanel,BorderLayout.SOUTH);
	        getContentPane().add(mainPanel, BorderLayout.NORTH);

	        JScrollPane consoleScrollPane = new JScrollPane(consoleOutput);
	        consoleScrollPane.setPreferredSize(new Dimension(580, 130)); // Adjust the size as needed

	        getContentPane().add(consoleScrollPane, BorderLayout.SOUTH);
	        pack();
	   
	        setVisible(true);
	    }
	    
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow frame = new ServerWindow();
					frame.initializeUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	}

}

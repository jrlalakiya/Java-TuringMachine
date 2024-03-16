import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ClientWindow extends JFrame implements ActionListener {
	private JLabel imageLabel;
	private JPanel contentPane;
	private JLabel userLabel;
	private JTextArea userInput;
	private JLabel serverLabel;
	private JTextArea serverInput;
	private JLabel portLabel;
	private JTextArea portInput;
    private JButton connectButton;
    private JButton endButton;
    private JLabel tmLabel;
    private JTextArea tmInput;
    private JButton validateButton;
    private JButton sendButton;
    private JButton receiveButton;
    private JButton runButton;
    
    // Components for footer
    private JTextArea consoleOutput;
   
    

    
    public void initializeUI() {
        setTitle("Turing Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(620, 400));
        imageLabel = new JLabel(new ImageIcon("images/tm-client.png"));
        
        tmInput = new JTextArea();
        tmInput.setColumns(15);
       
        tmInput.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a black line border
        tmInput.setText("");
        userLabel = new JLabel("User:");
        userInput = new JTextArea();
        userInput.setColumns(7);
        userInput.setText("");
        userInput.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a black line border
        serverLabel = new JLabel("Server:");
        serverInput = new JTextArea();
        serverInput.setColumns(7);
        serverInput.setText("");
        serverInput.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a black line border
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
                if(tmInput.getText().length()>6) {
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
        connectButton = new JButton("Connect");
        connectButton.setActionCommand("CN");
        connectButton.addActionListener(this);
        endButton = new JButton("End");
        endButton.setActionCommand("DC");
        endButton.addActionListener(this);
        endButton.setEnabled(false);
        tmLabel = new JLabel("TM:");
        validateButton = new JButton("Validate");
        validateButton.setActionCommand("VLD");
        validateButton.addActionListener(this);
        sendButton = new JButton("Send");
        sendButton.setActionCommand("SND");
        sendButton.addActionListener(this);
        receiveButton = new JButton("Receive");
        receiveButton.setActionCommand("RCV");
        receiveButton.addActionListener(this);
        runButton = new JButton("Run");
        runButton.setActionCommand("RN");
        runButton.addActionListener(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        imagePanel.add(imageLabel);
        JPanel componentPanel = new JPanel(new BorderLayout());
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row1.add(userLabel);
        row1.add(userInput);
        row1.add(serverLabel);
        row1.add(serverInput);
        row1.add(portLabel);
        row1.add(portInput);
        row1.add(connectButton);
        row1.add(endButton);
        row2.add(tmLabel);
        row2.add(tmInput);
        row2.add(validateButton);
        row2.add(sendButton);
        row2.add(receiveButton);
        row2.add(runButton);
        componentPanel.add(row1, BorderLayout.NORTH);
        componentPanel.add(row2, BorderLayout.SOUTH);
        mainPanel.add(imagePanel,BorderLayout.NORTH);
        mainPanel.add(componentPanel,BorderLayout.SOUTH);
        getContentPane().add(mainPanel, BorderLayout.NORTH);
        
        consoleOutput = new JTextArea();
	    consoleOutput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane consoleScrollPane = new JScrollPane(consoleOutput);
        consoleScrollPane.setPreferredSize(new Dimension(580, 130)); // Adjust the size as needed

        getContentPane().add(consoleScrollPane, BorderLayout.SOUTH);

        // Pack and display the frame
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
					ClientWindow frame = new ClientWindow();
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
	public ClientWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String action = e.getActionCommand();
		switch(action) {
			case "CN":
				System.out.println(serverInput.getText());
				if((userInput.getText().equals(""))||(serverInput.equals(""))||(portInput.getText().equals(""))) {
					JOptionPane.showMessageDialog(this, "PLease Enter all the required fields to connect to the server !!");
					break;
				}
				String serverAddress = serverInput.getText();
				int port = Integer.parseInt(portInput.getText());
				if(Client.connectToServer(serverAddress, port, consoleOutput, tmInput)) {
					connectButton.setEnabled(false);
					endButton.setEnabled(true);
				}else {
					JOptionPane.showMessageDialog(this, "Unable to connect to the server !!: Server might not be ACTIVE!!");
				}
				
				break;
			case "DC":
				endButton.setEnabled(false);
				connectButton.setEnabled(true);
				Client.sendMessage(action);
				break;
			case "SND":
				Client.sendMessage(action);
				break;
			case "RCV":
				Client.sendMessage(action);
				break;
			case "RN":
				TuringMachineWindow tmWindow = new TuringMachineWindow();
				tmWindow.initializeUI(tmInput.getText());
				break;
			case "VLD":
				if(Client.validateTMModel(tmInput.getText())) {
					JOptionPane.showMessageDialog(this, "TM Model is VALID!! :).");
				} else {
					JOptionPane.showMessageDialog(this, "X TM Model is INVALID X :(");
				}
				break;
		}
	}

}

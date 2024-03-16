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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class TuringMachineWindow extends JFrame implements ActionListener {

	private JLabel imageLabel;
	private JPanel contentPane;
	private JLabel tmLabel;
    private JTextArea tmInput;
    private JLabel tapeLabel;
    private JTextArea tapeInput;
    private JButton runButton;
    private JButton clearButton;
    
    // Components for footer
    private JTextArea consoleOutput;
   
    
    
    
    public void initializeUI(String tmModel) {
        setTitle("Turing Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(620, 400));
        imageLabel = new JLabel(new ImageIcon("images/tm.png"));
        
        tmInput = new JTextArea();
        tmInput.setColumns(30);
        tmInput.addKeyListener(new KeyListener() {  
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') {  
                    e.consume();
                }
                if(tmInput.getText().length()>30) {
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
        tmInput.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a black line border
        tmInput.setText(tmModel);
        tapeInput = new JTextArea();
        tapeInput.setColumns(30);
        tapeInput.addKeyListener(new KeyListener() {  
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') {  
                    e.consume();
                }
                if(tmInput.getText().length()>30) {
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
        tapeInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        tmLabel = new JLabel("TM: ");
        tapeLabel = new JLabel("Tape: ");
        runButton = new JButton("Run");
        runButton.setActionCommand("RN");
        runButton.addActionListener(this);
        clearButton = new JButton("Clear");
        clearButton.setActionCommand("CLR");
        clearButton.addActionListener(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        imagePanel.add(imageLabel);
        JPanel componentPanel = new JPanel(new BorderLayout());
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row1.add(tmLabel);
        row1.add(tmInput);
        row2.add(tapeLabel);
        row2.add(tapeInput);
        row2.add(runButton);
        row2.add(clearButton);
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
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
					TuringMachineWindow frame = new TuringMachineWindow();
					frame.initializeUI("00000 01000 10010 11000");
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
	public TuringMachineWindow() {
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
		case "RN":
			TuringMachine objTM = new TuringMachine(tmInput.getText(),tapeInput.getText(),consoleOutput);
			objTM.run();
			break;
		case "CLR":
			consoleOutput.setText("");
		}
	}
}

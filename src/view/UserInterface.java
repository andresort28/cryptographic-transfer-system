package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserInterface extends JFrame {
	
	private JMenuBar mbMenuBar;
	private JMenu mMenu;
	private JMenuItem miConnect;
	private JMenuItem miDesconnect;
	
	private JTextField txtIp;
	private JTextField txtSourcePort;
	private JTextField txtFile;
	private JTextField txtDestinationPort;
	private JTextField txtMD5;
	
	private JButton butAttach;
	private JButton butSend;
	
	private JFileChooser chooser;	
		
	public UserInterface()
	{
		setTitle("Encrypted file transfer");		
		setSize(520, 230);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setResizable(false);		
		setLayout(new BorderLayout());
		
		chooser = new JFileChooser(".");
		chooser.setDialogTitle("Select file to transfer");
		
		mbMenuBar = new JMenuBar();
		mMenu = new JMenu("Init");
		miConnect = new JMenuItem("Connect");
		miDesconnect = new JMenuItem("Disconnnect");
		mbMenuBar.add(mMenu);
		mMenu.add(miConnect);
		mMenu.add(miDesconnect);
		setJMenuBar(mbMenuBar);
		
		butAttach = new JButton("Attach");
		butSend = new JButton("Send");
		
		
		JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
		
		txtIp = new JTextField();
		txtSourcePort = new JTextField();
		txtSourcePort.setEditable(false);
		txtDestinationPort = new JTextField();	
		txtDestinationPort.setEditable(false);
		txtFile = new JTextField();
		txtFile.setEditable(false);
		txtMD5 = new JTextField();
		txtMD5.setEditable(false);
		
		panel.add(new JLabel("  Ip Address:"));
		panel.add(txtIp);
		panel.add(new JLabel("  Port Source:"));
		panel.add(txtSourcePort);
		panel.add(new JLabel("  Port Destination:"));
		panel.add(txtDestinationPort);
		panel.add(new JLabel("  File to send:"));
		panel.add(txtFile);
		panel.add(new JLabel("  MD5 Checksum:"));
		panel.add(txtMD5);
		panel.add(butAttach);
		panel.add(butSend);
		
		add(panel, BorderLayout.CENTER);
	}
	
	public JMenuItem getMiConnect (){
		return miConnect;
	}
	
	public JMenuItem getMiDisconnect (){
		return miDesconnect;
	}
	
	public JButton getButAttach (){
		return butAttach;
	}
	
	public JButton getButSend (){
		return butSend;
	}
	
	public JFileChooser getChooser (){
		return chooser;
	}
	
	public String getIP (){
		return txtIp.getText();
	}
	
	
	public void setSourcePort (String txt){
		txtSourcePort.setText(txt);
	}
	
	public void setDestinationPort (String txt){
		txtDestinationPort.setText(txt);
	}
	
	public void setFileName (String txt){
		txtFile.setText(txt);
	}
	
	public void setMD5 (String txt){
		txtMD5.setText(txt);
	}
		
	public String saveFile() 
	{
		chooser.setDialogTitle("Save as");
		String path = "";
		int status = chooser.showSaveDialog(this);
		if (status == JFileChooser.APPROVE_OPTION)
			path = chooser.getSelectedFile().toString();
		return path;
	}	
}

package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import protocol.CommunicationProtocol;
import view.UserInterface;

public class Executable 
{
	private UserInterface view;
	private File file;
	private ThreadServer server;
		
	
	public Executable ()
	{
		System.out.println("*** Security Project | Universidad Icesi ***");
		System.out.println("Andres Ortiz - Cód: 10207000 | Jonathan España - Cód: 12103002");
		view = new UserInterface();
		initializeInterface();
		view.setVisible(true);
	}
	
	private void connect() 
	{
		server = new ThreadServer(view);
		server.start();
		view.setSourcePort(CommunicationProtocol.PORT + "");
		view.getMiConnect().setEnabled(false);
		view.getMiDisconnect().setEnabled(true);
	}
	
	private void disconnect() 
	{
		server.finishThread();
		view.getMiConnect().setEnabled(true);
		view.getMiDisconnect().setEnabled(false);
	}
	
	public void initializeInterface ()
	{
		view.getMiConnect().addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {					
					@Override
					public void run() {
						connect();
					}
				}).start();
			}
		});
		
		view.getMiDisconnect().addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {					
					@Override
					public void run() {
						disconnect();
					}
				}).start();
			}
		});
		
		view.getButAttach().addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				int status = view.getChooser().showOpenDialog(view);
				if (status == JFileChooser.APPROVE_OPTION) {
					file = view.getChooser().getSelectedFile();
					view.setFileName(file.getName());
					JOptionPane.showMessageDialog(view, "File ready to be sent");						
				}
			}
		});
		
		view.getButSend().addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Validar que ya se haya adjuntado un archivo y que exista un IP.
				try {
					startTransfer();
					JOptionPane.showMessageDialog(view, "Transfer request sent");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
	}
	
	public void startTransfer ()
	{
		try {
			ThreadSender sender = new ThreadSender(view.getIP(), file, view);
			sender.start();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
	public static void main(String[] args) throws Exception {
		new Executable();
	}

}

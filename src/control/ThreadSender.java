package control;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import model.Message;
import protocol.CommunicationProtocol;
import view.UserInterface;

public class ThreadSender extends Thread 
{
	private CommunicatorSender communicator;
	private String destination;
	private File file;
	private UserInterface view;
	
	public ThreadSender(String destination, File file, UserInterface view) throws Exception
	{
		this.destination = destination;
		this.file = file;
		this.view = view;
		communicator = new CommunicatorSender(destination);
	}
	
	public void sendAndReceiveMessage(Message message) throws Exception
	{
		communicator.sendMessage(message);
		Message msg = communicator.receiveMessage();
		
		switch (msg.getType()) 
		{
			case CommunicationProtocol.REQUEST_FOR_SENDING_DENIED:
				JOptionPane.showMessageDialog(view, "Transfer denied !");
				communicator.sendMessage(new Message(null, null, CommunicationProtocol.CONNECTION_CLOSE, null));
				break;
			case CommunicationProtocol.REQUEST_FOR_SENDING_ACCEPTED:
				view.setDestinationPort((String)msg.getContent());
				JOptionPane.showMessageDialog(view, "Transfer accepted !");
				
				String md5code = Cyptografy.getMD5(file);
				view.setMD5(md5code);
				byte[] bFile = Cyptografy.fileToBytes(file);	
				byte[] bCryp = Cyptografy.encrypt(bFile, "2b7e151628aed2a6abf7158809cf4f3c"); 
				
				communicator.sendMessage(new Message(msg.getDestination(), msg.getSource(), CommunicationProtocol.FILE_TRANSFER, bCryp));
				sendAndReceiveMessage(new Message(null, null, null, md5code));
				break;
			case CommunicationProtocol.FILE_TRANSFER_OK:
				JOptionPane.showMessageDialog(view, "Transfer COMPLETED !");
				communicator.sendMessage(new Message(null, null, CommunicationProtocol.CONNECTION_CLOSE, null));
				break;
		}
	}
		
	public void run ()
	{
		try 
		{
			String source = InetAddress.getLocalHost().toString();
			String msg = "Do you want to accept the file '" + file.getName()+ "' from " + source + " ? (" + file.length() +" bytes)" ;
			Message message = new Message(source, destination, CommunicationProtocol.REQUEST_FOR_SENDING, msg);
			sendAndReceiveMessage(message);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

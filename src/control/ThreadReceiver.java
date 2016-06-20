package control;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import model.Message;
import protocol.CommunicationProtocol;
import view.UserInterface;

public class ThreadReceiver extends Thread 
{
	private CommunicatorReceiver communicator;
	private UserInterface view;
	private boolean finish;
	
	public ThreadReceiver(Socket channel, UserInterface view) throws Exception
	{
		this.view = view;
		communicator = new CommunicatorReceiver(channel);
	}
	
	public void run ()
	{
		try 
		{
			while(!finish)	{			
				receiveAndSendMessage(communicator.receiveMessage());
			}			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("FINISH !");
	}
	
	
	public void receiveAndSendMessage(Message msg) throws Exception
	{
		switch (msg.getType()) 
		{
			case CommunicationProtocol.REQUEST_FOR_SENDING:
				int rsp = JOptionPane.showConfirmDialog(view, (String)msg.getContent());
				if(rsp == 0)
					communicator.sendMessage(new Message(msg.getDestination(), msg.getSource(), CommunicationProtocol.REQUEST_FOR_SENDING_ACCEPTED, communicator.getPort()));
				else
					communicator.sendMessage(new Message(msg.getDestination(), msg.getSource(), CommunicationProtocol.REQUEST_FOR_SENDING_DENIED, communicator.getPort()));
				break;	
			case CommunicationProtocol.FILE_TRANSFER:
				String path = view.saveFile();
				if(!path.equals(""))
				{
					BufferedOutputStream buffer = new BufferedOutputStream(new FileOutputStream(new File(path)));
					byte[] bCryp = (byte[])msg.getContent();
					byte[] bFile = Cyptografy.dencrypt(bCryp, "2b7e151628aed2a6abf7158809cf4f3c");															
					buffer.write(bFile);
					buffer.close();
					
					Message msg2 = communicator.receiveMessage();
					String md5code_sent = (String)msg2.getContent();
					File file = new File(path);
					String md5code_calc = Cyptografy.getMD5(file);
					if(md5code_sent.equals(md5code_calc))
						JOptionPane.showMessageDialog(view, "MD5 Checksum PERFECT !");
					else
						JOptionPane.showMessageDialog(view, "MD5 Checksum WRONG !");					
					communicator.sendMessage(new Message(msg.getDestination(), msg.getSource(), CommunicationProtocol.FILE_TRANSFER_OK, null));
				}
				break;	
			case CommunicationProtocol.CONNECTION_CLOSE:
				finish = true;
				break;
		}
	}
	
	
}

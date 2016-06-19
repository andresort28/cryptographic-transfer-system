package control;

import java.net.ServerSocket;
import java.net.Socket;

import protocol.CommunicationProtocol;
import view.UserInterface;

public class ThreadServer  extends Thread 
{
	private ServerSocket serverSocket;	
	private UserInterface view;
	
	public ThreadServer(UserInterface view) 
	{
		this.view = view;
		try 
		{			
			serverSocket = new ServerSocket(CommunicationProtocol.PORT);
			System.out.println("Server UP !");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
		
	public void run ()
	{
		try {
			while(true)
			{
				System.out.println("Waiting connections by port: " + CommunicationProtocol.PORT);
				Socket channel = serverSocket.accept();
				attendRequest(channel);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void attendRequest (Socket channel) throws Exception
	{
		System.out.println("Client received by port: " + channel.getPort());
		view.setSourcePort(channel.getPort()+"");
		ThreadReceiver thread = new ThreadReceiver(channel, view);
		thread.start();
	}
	
	public void finishThread (){
		try {
			this.suspend();
			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}

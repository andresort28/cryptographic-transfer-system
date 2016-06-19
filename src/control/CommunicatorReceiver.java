package control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Message;

public class CommunicatorReceiver 
{
	private Socket channel;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	public CommunicatorReceiver(Socket channel) throws Exception
	{
		try 
		{
			this.channel = channel;
			this.out = new ObjectOutputStream(this.channel.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(this.channel.getInputStream());			
		} 
		catch (Exception e) {
			throw new Exception ("Error to create the channel with the client");
		}
		
	}
	
	public Message receiveMessage() throws Exception
	{
		//decodificar antes de
		return (Message)this.in.readObject();
	}

	public void sendMessage(Message message) throws Exception
	{
		//Codify message before sending
		this.out.writeObject(message);
		this.out.flush();
	}
	
	public Message decodifyMessage(String msg)
	{
		return null;
	}
	
	public String getPort (){
		return this.channel.getPort() +"";
	}
	
	
	
}

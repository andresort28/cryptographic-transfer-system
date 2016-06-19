package control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Message;
import protocol.CommunicationProtocol;

public class CommunicatorSender 
{
	private Socket channel;	
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public CommunicatorSender(String destination) throws Exception
	{
		try
		{
			this.channel = new Socket(destination, CommunicationProtocol.PORT);
			this.out = new ObjectOutputStream(this.channel.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(this.channel.getInputStream());
		}
		catch(Exception e)
		{
			throw new Exception("Error to create the communication channel");
		}
	}

	public Message receiveMessage() throws Exception
	{
		return (Message)this.in.readObject();
		//return decodifyMessage(in.readLine());
	}

	public void sendMessage(Message message) throws Exception
	{
		//Codify message before sending
		this.out.writeObject(message);
		this.out.flush();
	}

	public Message codifyMessage(String msg)
	{
		return null;
	}
	
	public String getPort(){
		return this.channel.getPort()+"";
	}


}

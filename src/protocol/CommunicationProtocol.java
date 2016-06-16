package protocol;

public class CommunicationProtocol 
{	
	public static final int PORT = 9999;
	
	public static final String REQUEST_FOR_SENDING = "REQUEST_FOR_SENDING";
	
	public static final String REQUEST_FOR_SENDING_ACCEPTED = "REQUEST_FOR_SENDING_ACCEPTED";
	
	public static final String REQUEST_FOR_SENDING_DENIED = "REQUEST_FOR_SENDING_DENIED";
	
	public static final String FILE_TRANSFER = "FILE_TRANSFER";
	
	public static final String FILE_TRANSFER_OK = "FILE_TRANSFER_OK";
	
	public static final String FILE_TRANSFER_ERROR = "FILE_TRANSFER_ERROR";
		
	public static final String CONNECTION_CLOSE = "CONNECTION_CLOSE";
	
	//Anadir verificaicon del MD5 cuando llegue el archivo
}

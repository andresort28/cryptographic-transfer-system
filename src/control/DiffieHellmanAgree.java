package control;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import com.sun.crypto.provider.SunJCE;

public class DiffieHellmanAgree {
	
	private KeyAgreement KeyAgree;
	
	public DiffieHellmanAgree(){
		
	}

	private byte[] getPubKeyEnc1 (String mode) throws Exception 
	{
        DHParameterSpec dhSkipParamSpec;

        if (mode.equals("GENERATE_DH_PARAMS")) 
        {
            // Some central authority creates new DH parameters
            System.out.println ("Creating Diffie-Hellman parameters (takes VERY long) ...");
            AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
            paramGen.init(512);
            AlgorithmParameters params = paramGen.generateParameters();
            dhSkipParamSpec = (DHParameterSpec)params.getParameterSpec
                (DHParameterSpec.class);
        } 
        else 
        {
            // use some pre-generated, default DH parameters
            dhSkipParamSpec = new DHParameterSpec(skip1024Modulus,  skip1024Base);
        }

        //Creates the own DH key pair, using the DH parameters from
        KeyPairGenerator aliceKpairGen = KeyPairGenerator.getInstance("DH");
        aliceKpairGen.initialize(dhSkipParamSpec);
        KeyPair aliceKpair = aliceKpairGen.generateKeyPair();

        //Creates and initializes the DH KeyAgreement object
        KeyAgree = KeyAgreement.getInstance("DH");
        KeyAgree.init(aliceKpair.getPrivate());

        //Encodes the public key, and sends it to another
        byte[] pubKeyEnc = aliceKpair.getPublic().getEncoded();
        return pubKeyEnc;
	}
	
	
	public byte[] respondToKey (byte[] pubKeyEnc1) throws Exception
	{
		KeyFactory KeyFac = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyEnc1);
        PublicKey PubKey = KeyFac.generatePublic(x509KeySpec);

        /*
         * Gets the DH parameters associated with Alice's public key.
         * He must use the same parameters when he generates his own key
         * pair.
         */
        DHParameterSpec dhParamSpec = ((DHPublicKey)PubKey).getParams();

        //Creates his own DH key pair
        System.out.println("BOB: Generate DH keypair ...");
        KeyPairGenerator KpairGen = KeyPairGenerator.getInstance("DH");
        KpairGen.initialize(dhParamSpec);
        KeyPair Kpair = KpairGen.generateKeyPair();

        //Creates and initializes his DH KeyAgreement object
        System.out.println("BOB: Initialization ...");
        KeyAgreement KeyAgree = KeyAgreement.getInstance("DH");
        KeyAgree.init(Kpair.getPrivate());

        //Encodes his public key, and sends it over to Alice.
        byte[] PubKeyEnc2 = Kpair.getPublic().getEncoded();
        return PubKeyEnc2;
	}
	
	
	public int calculateSharedKey (byte[] PubKeyEnc2) throws Exception
	{
		KeyFactory aliceKeyFac = KeyFactory.getInstance("DH");
        X509EncodedKeySpec obj = new X509EncodedKeySpec(PubKeyEnc2);
        PublicKey PubKey2 = aliceKeyFac.generatePublic(obj);
        KeyAgree.doPhase(PubKey2, true);
        byte[] SharedSecret = KeyAgree.generateSecret();
        int Len = SharedSecret.length;
        return Len;
	}
	
	
	public void calculateSharedKey2 (PublicKey PubKey, int Len) throws Exception
	{
		KeyAgree.doPhase(PubKey, true);
		byte[] bobSharedSecret = new byte[Len];
        int bobLen;
        //try {
            // show example of what happens if you
            // provide an output buffer that is too short
            //bobLen = bobKeyAgree.generateSecret(bobSharedSecret, 1);
        //} //catch (ShortBufferException e) {
            //System.out.println(e.getMessage());
        //}
	}
	
	
	
	
	
	
	
	
	
	
	
	// The 1024 bit Diffie-Hellman modulus values used by SKIP
    private static final byte skip1024ModulusBytes[] = {
        (byte)0xF4, (byte)0x88, (byte)0xFD, (byte)0x58,
        (byte)0x4E, (byte)0x49, (byte)0xDB, (byte)0xCD,
        (byte)0x20, (byte)0xB4, (byte)0x9D, (byte)0xE4,
        (byte)0x91, (byte)0x07, (byte)0x36, (byte)0x6B,
        (byte)0x33, (byte)0x6C, (byte)0x38, (byte)0x0D,
        (byte)0x45, (byte)0x1D, (byte)0x0F, (byte)0x7C,
        (byte)0x88, (byte)0xB3, (byte)0x1C, (byte)0x7C,
        (byte)0x5B, (byte)0x2D, (byte)0x8E, (byte)0xF6,
        (byte)0xF3, (byte)0xC9, (byte)0x23, (byte)0xC0,
        (byte)0x43, (byte)0xF0, (byte)0xA5, (byte)0x5B,
        (byte)0x18, (byte)0x8D, (byte)0x8E, (byte)0xBB,
        (byte)0x55, (byte)0x8C, (byte)0xB8, (byte)0x5D,
        (byte)0x38, (byte)0xD3, (byte)0x34, (byte)0xFD,
        (byte)0x7C, (byte)0x17, (byte)0x57, (byte)0x43,
        (byte)0xA3, (byte)0x1D, (byte)0x18, (byte)0x6C,
        (byte)0xDE, (byte)0x33, (byte)0x21, (byte)0x2C,
        (byte)0xB5, (byte)0x2A, (byte)0xFF, (byte)0x3C,
        (byte)0xE1, (byte)0xB1, (byte)0x29, (byte)0x40,
        (byte)0x18, (byte)0x11, (byte)0x8D, (byte)0x7C,
        (byte)0x84, (byte)0xA7, (byte)0x0A, (byte)0x72,
        (byte)0xD6, (byte)0x86, (byte)0xC4, (byte)0x03,
        (byte)0x19, (byte)0xC8, (byte)0x07, (byte)0x29,
        (byte)0x7A, (byte)0xCA, (byte)0x95, (byte)0x0C,
        (byte)0xD9, (byte)0x96, (byte)0x9F, (byte)0xAB,
        (byte)0xD0, (byte)0x0A, (byte)0x50, (byte)0x9B,
        (byte)0x02, (byte)0x46, (byte)0xD3, (byte)0x08,
        (byte)0x3D, (byte)0x66, (byte)0xA4, (byte)0x5D,
        (byte)0x41, (byte)0x9F, (byte)0x9C, (byte)0x7C,
        (byte)0xBD, (byte)0x89, (byte)0x4B, (byte)0x22,
        (byte)0x19, (byte)0x26, (byte)0xBA, (byte)0xAB,
        (byte)0xA2, (byte)0x5E, (byte)0xC3, (byte)0x55,
        (byte)0xE9, (byte)0x2F, (byte)0x78, (byte)0xC7
    };

    // The SKIP 1024 bit modulus
    private static final BigInteger skip1024Modulus
    = new BigInteger(1, skip1024ModulusBytes);

    // The base used with the SKIP 1024 bit modulus
    private static final BigInteger skip1024Base = BigInteger.valueOf(2);
	
	
	
	
}

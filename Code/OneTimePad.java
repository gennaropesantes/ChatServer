package Chat_Server_4;

public class OneTimePad {

	private static final char[] abcVal = {

		'A','B','C','D','E','F','G','H', 'I','J','K','L','M','N','O','P',

		'Q','R','S','T','U','V','W','X','Y','Z' };

	private String plainMessage ="";

	private String encryptedMessage = "";

	private String currentKey="";

	public OneTimePad() {

		// TODO Auto-generated constructor stub
		plainMessage="NO MESSAGE";
		currentKey= this.generateKey(plainMessage);

	}

	public OneTimePad(String msg) {

		// TODO Auto-generated constructor stub
		plainMessage=msg;
		currentKey= generateKey(msg);
		encryptedMessage = encrypt(msg);
	}
	
	public String generateKey(String msg){

		String key ="";

		for(int i=0; i<msg.length(); i++){
			key= key+ abcVal[(int)(Math.random()*10)];
		}
		return key;
	}

	public String getEncryptedMessage(){
		//return this.encryptedMessage;
		return encryptedMessage;
	}

	public void setKey(String k){
		currentKey = k;
	}

	public String getKey(){
		return this.currentKey;
	}


	public String encrypt(String plainMsg){

		String encMsg="";
		
		for(int i=0; i<plainMsg.length(); i++){
			System.out.println("inside encrypt for loop at "+i+" value is "+plainMsg.charAt(i));
			//assign numerical value to each character in the plain message
			int numForPlainChar = this.getNumberForChar(plainMsg.charAt(i));
			int numForKeyChar = this.getNumberForChar(currentKey.charAt(i));   
			System.out.println("numForKeyChar at "+i+" is "+ numForKeyChar  + " numForPlainChar at "+i+" is "+ numForPlainChar);
			int valOfEncrChar = numForKeyChar + numForPlainChar;  
			System.out.println("valOfEncrChar at "+i+" is "+ valOfEncrChar);
			int numForEncChar = Math.abs(valOfEncrChar % abcVal.length); //absolute value to prevent out of bounds 
			System.out.println("numForEncChar at "+i+" is "+ numForEncChar); //for debugging
			//find the letter for that number from the abcVal array put it in the encMsg string
			encMsg = encMsg+ abcVal[numForEncChar];
		}
		System.out.println("encrypted message is: " + encMsg);
		return encMsg;
	}

	private int getNumberForChar(char c){

		int n = -1;
		
		for(int i=0; i<abcVal.length; i++){
			if(c == (abcVal[i])){
				return i;
			}
		}
		return n;
	}

	public String decrypt(String encMsg){

		String decMsg="";

		for(int i=0; i<encMsg.length(); i++){
		
			//assign numerical value to each character in the encrypted message
			int numForEncChar = this.getNumberForChar(encMsg.charAt(i));
			System.out.println("the num for decrypted char at "+i+" is: " +numForEncChar);
			
			//assign numerical value to each character in the key 
			int numForKeyChar = this.getNumberForChar(currentKey.charAt(i));
			System.out.println("the num for key  at "+i+" is: " +numForKeyChar);
			
			//subtract key from encrypted message, or whatever your decryption algorithm is
			int valOfCharAtIndex = numForEncChar - numForKeyChar;  
			System.out.println("the val at "+i+" is: " + valOfCharAtIndex);
			
			//modulus array abcVal length to decrypt it
			int numForDecChar = valOfCharAtIndex % abcVal.length;
			System.out.println("the num for the dec char at "+i+" is: " +numForDecChar);
			
			//find the letter for that number from the abcVal array and append it to the decMsg string
			decMsg = decMsg + abcVal[numForDecChar];
		}
		System.out.println("the decrypted message is: " +decMsg);
		return decMsg;
	}
}

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Scanner;

public class RSA {
	public static void main(String[] args) {
		RSA newRSA = new RSA();
		newRSA.run(args);
	}
	
	public void run(String[] args) {
		 Scanner sc = null;
	      try
	      {
	    	  while(true) {
		    	  sc = new Scanner(System.in);
		    	  String action;
		    	  
		    	  
		    	  System.out.println("What would you like to do? (Type one of the following: generate / encrypt / decrypt)");
		    	  action = sc.next();
	    
		    	  while (!(action.equals("generate") || action.equals("encrypt") || action.equals("decrypt"))) {
		    		  System.out.println("Unrecognised command. Try again.");
		    		  System.out.println("What would you like to do? (Type one of the following: generate key / encrypt / decrypt)");
			    	  action = sc.next();
		    	  }
		    	  
		    	  if (action.equals("generate")) {
		    		  int p, q, n, t, e, d;
		    		  p = randomPrime(1, 255);
		    		  q = randomPrime(1, 255);
		    		  while (p == q) {
		    			  p = randomPrime(1, 255);
			    		  q = randomPrime(1, 255);
		    		  }
		    		  System.out.println("Choosing 2 unequal numbers that are prime");
			          System.out.println("p:" + p + " q:" + q + "\n");
			          
			          n = p * q;  //n is the modulus for the public key and the private keys
			          
			          t = (p - 1) * (q - 1); //Calculate Euler's totient function
			          
			          System.out.println("Choosing a number e such that 1 < e < " + t + " and e and " + t + " are coprime");    
			          e = 0;
			          while (!(1 < e && e < t) || !coprime(e,t)) {
			        	  e++;
			          }
			          System.out.println("e = " + e + "\n");
			          
			          System.out.println("Choosing a number d such that d * " + e + " mod " + t + " = 1"); 
			          d = 0;
			          while (d * e % t != 1) {
			        	  d++;
			          }
			          System.out.println("d = " + d + "\n");
			          
			          
			          
			          System.out.println("The PUBLIC KEY is (n = " + n + ", e = "+ e +")");
			          
			          System.out.println("The PRIVATE KEY is (d = " + d + ")");
			          System.out.println();
		    	  } else if (action.equals("encrypt")) {
		    		  int n, e;		    		  
		    		  System.out.println("Enter the public key.");
		    		  System.out.print("n: ");
		    		  n = sc.nextInt();
		    		  
		    		  System.out.print("e: ");
		    		  e = sc.nextInt();
	    		     		   		  
		    		  System.out.print("Message to encrypt: ");
		    		  
						while(sc.hasNext()) {
							String plainText = sc.next();
							char[] plainCharArray = plainText.toCharArray();
							
							  
							  for(int i = 0; i < plainCharArray.length; i++) {			    				
								  //System.out.print(plainCharArray[i] + " ("+(int)plainCharArray[i] +" ^ e = ");  
								  
								  long result = (long) plainCharArray[i];
								  result = (long) Math.pow(result, e);
								  result = result % n;
								  
								  System.out.print((result) + " ");
							  }	  		
						}	  	 
		    			  System.out.println();

		    	  } else if (action.equals("decrypt")) {
		    		  BigInteger n;
		    		  int d;
		    		  System.out.println("Enter the public key component.");
		    		  System.out.print("n: ");
		    		  n = new BigInteger(sc.next());
		    		  
		    		  System.out.println("Enter the private key.");
		    		  System.out.print("d: ");
		    		  d = sc.nextInt();
		    		  
		    		  System.out.println("Enter message to decrypt: ");	   
		    		  
		    		  while(sc.hasNext()) {
		    			  String cipherText = sc.next();
		    			  BigInteger result = new BigInteger(cipherText);
		    			  result = result.pow(d);
		    			  result = result.mod(n);
	    			  
		    			  System.out.print((char)result.intValue() + " ");    				  
		    		  } 		  
		    	  }        
	    	  }              
	          
	      }
	      finally
	      {
	          if (sc != null) sc.close();
	      }
	}
	
	public int randomPrime(int min, int max) {
		double prime = Math.floor(Math.random() * ((max - 1) - min + 1)) + min;
		
		if(isPrime((int) prime)) {
			return (int) prime;
		} else {
			return randomPrime(min, max);
		}
		
	}
	
	
	
	
	public boolean isPrime(int n) {
		if(n <= 1) 
			return false;
		else if(n <= 3)
			return true;
		else if(n % 2 == 0 || n % 3 == 0)
			return false;
		
		int i = 5;
		while (i*i <= n) {
			if(n % i == 0 || n % (i + 2) == 0)
				return false;
			i += 6;
		}
		
		return true;
	}
	
	public int gcd(int a, int b) {
	    int t;
	    while(b != 0){
	        t = a;
	        a = b;
	        b = t%b;
	    }
	    return a;
	}
	
	private boolean coprime(int a, int b) {
	    return gcd(a,b) == 1;
	}
	
}

package newcryptoalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manage the encryption and decryption from our algorithm
 * @author Andarias Silvanus & Cilvia Sianora Putri
 */
public class NewCryptoAlgorithm {
    // Atribut
    public StringBuilder plainText;
    public StringBuilder cipherText;
    public StringBuilder binary;       // Menampung plainText dalam bentuk bit
//    public ArrayList<ArrayList<StringBuilder>> container16;  // Untuk menampung 16 blok plaintext awal
    String[][] container16;
    public int first_idx, last_idx;
	
	private StringBuilder key;
    public StringBuilder[][] substitutionMatrix;
	private List<Integer> sBox;
	private int SIZE;
	
    
    public NewCryptoAlgorithm() {
        plainText = new StringBuilder();
        cipherText = new StringBuilder();
		key = new StringBuilder();
//        container16 = new ArrayList<ArrayList<StringBuilder>>();
        container16 = new String[4][4];
        binary = new StringBuilder();
        first_idx = 0;
        last_idx = plainText.length()-1;
		SIZE = 16;
		sBox = new ArrayList<>();
		substitutionMatrix = new StringBuilder[SIZE][SIZE];
		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				substitutionMatrix[i][j] = new StringBuilder();
			}
		}
	}
	
	/** Getters and Setters **/
    
	public void setKey(String k){
		key.append(k);
	}
	
	public StringBuilder getKey(){
		return key;
	}
	
	public StringBuilder[][] getSubstitutionMatrix(){
		return substitutionMatrix;
	}
	
	/** Other Functions **/
	
	/**
	 * change the text into binary string
	 * @param text initial text
	 * @return binary string of text
	 */
	public StringBuilder convertToBinaryString (StringBuilder text) {
            StringBuilder result = new StringBuilder();
            byte[] b = text.toString().getBytes();
            for (byte b1 : b) {
                int val = b1;
                for (int i = 0; i < 8; i++) {
                   result.append((val & 128) == 0 ? 0 : 1);
                   val <<= 1;
                }
            }
            return result;
        }
    
    public String get8BitForward () {
    // Ambil 8 bit secara maju dari idx yang dikembalikan melalui stringBuilder
        String result = new String();
        result="";
        char temp;
        for (int i=0; i<8; i++) {
            temp = binary.toString().charAt(first_idx);
            result += temp;
            first_idx++;
        }
        return result;
    }
    
    public String get8BitBackward () {
    // Ambil 8 bit secara mundur ke belakang dari idx yang dikembalikan melalui stringBuilder
        String result = new String();
        result = "";
        char temp;
        for (int i=0; i<8; i++) {
            temp = binary.toString().charAt(last_idx);
            result += temp;
            last_idx--;
        }
        String result2 = new String();
        result2 = reverseString(result);
        return result2;
    }
    
    public String reverseString(String target) {
        String result = new String();
        result = "";
        char temp;
        int idx = target.length()-1;
        for (int i=0; i<target.length(); i++) {
            temp = target.charAt(idx);
            result = result + temp;
            idx--;
        }
        return result;
    }
    
    public void set16block() {
        int i = 0;
        int j = 0;
        int idx = 0;
        while (i<4) {
            if (j>3)
                j=0;
            while (j<4) {
                if (j%2==0) { // Jika bagian kolom genap (indeks j == 0 atau 2), isi dengan 8BitForward
                    container16[i][j] = get8BitForward();
                }
                else {
                    container16[i][j] = get8BitBackward();
                }
                j++;
            }
            i++;
        }
    }
    
    public String fillPadding() {
        String result = "00000000";
//        String result = "11111111";
        return result;
    }
    
    public void set16blockPadding() {
        int i = 0;
        int j = 0;
        int idx = 0;
        while (i<4) {
            if (j>3)
                j=0;
            while (j<4) {
                if (last_idx-first_idx>6) {
                    if (j%2==0) { // Jika bagian kolom genap (indeks j == 0 atau 2), isi dengan 8BitForward
                        container16[i][j] = get8BitForward();
                    }
                    else {
                        container16[i][j] = get8BitBackward();
                    }
                }
                else {
                    container16[i][j] = fillPadding();
                }
                j++;
            }
            i++;
        }
    }
    
    public void barisToKolom() {
    // Mengubah container16 baris-barisnya menjadi kolom
        
        String[][] temp = new String[4][4];
        
        int i =0 ,j=0;
        while (i<4) {
            if (j>3)
                j=0;
            while (j<4) {
                temp[j][i] = container16[i][j];
                j++;
            }
            i++;
        }
        i = 0; j = 0;
        while (i<4) {
            if (j>3)
                j=0;
            while (j<4) {
                container16[i][j] = temp[i][j];
                j++;
            }
            i++;
        }
    }
    
    public void printContainer16() {
        int i =0 ,j=0;
        while (i<4) {
            if (j>3) {
                j=0;
                System.out.println("");
            }
            while (j<4) {
                System.out.print(container16[i][j]+"\t");
                j++;
            }
            i++;
        }
    }
    
    public void printContainer16Manusiawi() {
        int i =0 ,j=0;
        char display;
        while (i<4) {
            if (j>3) {
                j=0;
                System.out.println("");
            }
            while (j<4) {
                display = (char) Integer.parseInt(container16[i][j]);
                System.out.print(display+"\t");
                j++;
            }
            i++;
        }
        System.out.println("");
    }
	
	/** From Key Input to Substitution Matrix **/
	
	/**
	 * make S-BOX based on seed
	 * @param seed parameter to random the integer
	 */
	public void makeSBox (int seed){
		Random ran = new Random(seed);
		int temp;
		while(sBox.size() < SIZE){
			temp = ran.nextInt(SIZE);
			if(!sBox.contains(temp)){
				sBox.add(temp);
			}
		}
	}

	/**
	 * do the permutation with S-BOX
	 * @param box the initial box
	 * @return the result of permutation
	 */
	public StringBuilder[] permutation(StringBuilder[] box){
		StringBuilder[] result = new StringBuilder[SIZE];
		for(int i=0;i<SIZE;i++){
			result[i] = new StringBuilder();
			result[i] = box[sBox.get(i)];
		}
		return result;
	}
	
	/**
	 * do the XOR operation between first and second string
	 * @param string1 the first string
	 * @param string2 the second string
	 * @return result of XOR operation
	 */
	public StringBuilder XOR(StringBuilder string1, StringBuilder string2){
		StringBuilder result = new StringBuilder();
		
		// if length of the string2 different
		if(string1.length() > string2.length()){
			do{
				string2.insert(0, '0');
			} while(string2.length()<string1.length());
		} else if (string1.length() < string2.length()){
			do{
				string2.insert(0, '0');
			} while(string2.length()<string1.length());
			
		}
		
		// do the XOR
		for(int i=0;i<string1.length();i++){
			result.append((char) string1.charAt(i) ^ string2.charAt(i));
		}
		
		return result;
	}
	
	/**
	 * return the hexadecimal of binary string
	 * @param text binary string input
	 * @return hexadecimal string of text
	 */
	public StringBuilder getHex(StringBuilder text){
		StringBuilder temp = new StringBuilder();		
		temp.append(Integer.toString(Integer.parseInt(text.toString(),2),16));
		return temp;
	}
	
	public void printArray(StringBuilder[] arr){
		for(int i=0;i<SIZE;i++){
			System.out.println(arr[i]);
			System.out.println(getHex(arr[i]));
		}
	}
	
	/**
	 * make the substitution matrix based on our algorithm
	 */
	public void makeSubstitutionMatrix(){
		// inisialization
		StringBuilder[] k = new StringBuilder[SIZE];
		StringBuilder[] ka = new StringBuilder[SIZE];
		StringBuilder[] kBefore = new StringBuilder[SIZE];
		StringBuilder binaryKey = new StringBuilder();
		for(int i=0;i<SIZE;i++){
			k[i] = new StringBuilder();
			ka[i] = new StringBuilder();
			kBefore[i] = new StringBuilder();
		}
		
		// change key to binary string
		binaryKey = convertToBinaryString(key);
		
		// divide the key into 16 parts
		for(int i=0;i<SIZE;i++){
			k[i].append(binaryKey.substring(0,8));
			binaryKey.delete(0,8);
		}
		
//		printArray(k);

//		int i = 0;
		for(int i=0;i<SIZE;i++){
			// do the inisialization and assignment
			if(i>0){ // after the first iteration
//				System.out.println("-- Iteration "+ i +"--");
//				
//				System.out.println("- ka before goes to for -");
//				printArray(ka);
//				
//				System.out.println("- ka before goes to for, but without printArray -");
//				for(int j=0;j<SIZE;j++){
//					System.out.println(ka[j]);
//					System.out.println(getHex(ka[j]));
//				}
				
//				System.out.println("- ka inside for, at the very first of iteration before k.setLength(0) -");
//				System.out.println("- ka inside the first for -");
				for(int j=0;j<SIZE;j++){
//					System.out.println(ka[j]);
//					System.out.println(getHex(ka[j]));
				//	k[j].setLength(0);
					k[j].replace(0,8,substitutionMatrix[i-1][j].toString());
					kBefore[j].replace(0,8,ka[j].toString());
					ka[j].setLength(0);
				//	kBefore[j].setLength(0);
				//	ka[j] = new StringBuilder();
				}
//				for(int j=0;j<SIZE;j++){
//					kBefore[j].replace(0,8,ka[j].toString());
//				}
//				System.out.println("- k get from substitutionMatrix -");
//				printArray(k);
//				System.out.println("- kBefore get from ka -");
//				printArray(kBefore);
//				for(int j=0;j<SIZE;j++){
//					ka[j].setLength(0);
//				}
				
			} else if (i == 0){ // for the first iteration
//				System.out.println("--First Iteration--");
				for(int j=0;j<SIZE;j++){
					kBefore[j].append(k[j]);
				}
			}
			
			// do the permutation
			for(int j=0;j<SIZE;j++){
				ka[j].setLength(0);
				ka[j].append(permutation(k)[j]);
			}
			
//			printArray(k);
//			System.out.println("- After permutation: ka -");
//			printArray(ka);
//			
//			System.out.println("- Result XO ka&k -");
			
			// XOR and put to matrix
			for(int j=0;j<SIZE;j++){
//				System.out.println(XOR(kBefore[j],ka[j]));
//				System.out.println(getHex(XOR(kBefore[j],ka[j])));
				substitutionMatrix[i][j].append(XOR(kBefore[j],ka[j]));
//				k[j].replace(0,8,substitutionMatrix[i][j].toString());
			}	
			
//			System.out.println("- Result XO ka&k and put to matrix -");
//			printArray(substitutionMatrix[i]);
		}
		
	}
        
        public void printBinary() {
            // Print binary
            int idx = 0;
            char temp;
            String bin8;
            bin8 = "";
            for (int i=0; i<binary.length(); i++) {
                for (int j=0; j<8; j++) {
                    if (idx<binary.length()) {
                        temp = binary.charAt(idx);
                        idx++;
                        bin8 = bin8 + temp;
                    }
                }
                System.out.print(bin8);
                System.out.print(" ");
                bin8 = "";
            }
            System.out.println("");
        }
        
        public void encrypt() {
            binary = convertToBinaryString(plainText);
            last_idx = binary.length()-1;
//            System.out.println("panjang binary: "+binary.length());
//            System.out.println("first idx: "+first_idx);
//            System.out.println("last idx: "+last_idx);
            
            boolean finished = false;
            while (!finished) {
            // Encrypt here
                if (last_idx-first_idx>=127) {
                // Pengelompokkan 16 blok normal
                    set16block();
                    barisToKolom();
                    printContainer16Manusiawi();
                }
                else {
                // Pengelompokkan 16 blok dengan padding jika jumlah blok tersisa tidak mencapai 16 blok
                    System.out.println("Masuk Padding");
                    set16blockPadding();
                    barisToKolom();
                    printContainer16Manusiawi();
                    finished = true;
                }
            }
        }
        
        public void decrypt() {
            
        }
}

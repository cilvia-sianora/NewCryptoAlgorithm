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
    
    public String[][] copyContainer16() {
        String[][] temp = new String[4][4];
        int i =0 ,j=0;
        while (i<4) {
            if (j>3)
                j=0;
            while (j<4) {
                temp[i][j] = container16[i][j];
                j++;
            }
            i++;
        }
        return temp;
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
        
        i =0; j=0;
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
                display = (char) Integer.parseInt(container16[i][j],2);
                System.out.print(display+"\t");
                j++;
            }
            i++;
        }
        System.out.println("");
    }
	
	/** From Key Input to Substitution Matrix **/
	
	/**
	 * Get an integer from key to be a seed
	 * @return seed
	 */
	public int getSeed(){
		int sum = 0;
		for(int i=0;i<key.length();i++)
			sum += (int) key.charAt(i);
		return sum;
	}
	
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
		if(text.substring(0,4).equals("0000")){
			temp.insert(0, '0');
		}
		return temp;
	}
	
	/**
	 * print array of StringBuilder to terminal
	 * @param arr array of StringBuilder
	 */
	public void printArray(StringBuilder[] arr){
		for(int i=0;i<SIZE;i++){
			System.out.println(arr[i]);
			System.out.println(getHex(arr[i]));
		}
	}
	
	/**
	 * shift binary string to the left 
	 * @param binaryStr binary string in StringBuilder
	 * @param digit	amount of shifting
	 * @return binary string result in StringBuilder
	 */
	public StringBuilder shiftBitToLeft(StringBuilder binaryStr, int digit){
		StringBuilder result = new StringBuilder();
		result.append(binaryStr.substring(digit,binaryStr.length()));
		result.append(binaryStr.substring(0,digit));
		return result;
	}

	/**
	 * Get the binary string from array with size=16
	 * @param arr array of StringBuilder
	 * @return binary string
	 */
	public StringBuilder getBinaryStringFromArray(StringBuilder[] arr){
		StringBuilder result = new StringBuilder();
		for(int i=0;i<SIZE;i++){
			result.append(arr[i]);
		}
		return result;
	}
	
	
	public StringBuilder negation(StringBuilder binaryStr, int digit, int iteration){
		StringBuilder result = new StringBuilder();
		if(digit>8){
			digit -= 8;
		}
		if(iteration % 2 == 0){ // even iteration
			for(int i=0;i<digit;i++){
				if(binaryStr.charAt(i) == '0'){
					result.append('1');
				} else {
					result.append('0');
				}
			}
			result.append(binaryStr.substring(digit));
		} else { //odd iteration
			for(int i=digit-1;i>=0;i--){
				if(binaryStr.charAt(i) == '0'){
					result.append('1');
				} else {
					result.append('0');
				}
			}
			result.append(binaryStr.substring(digit));
		}
		return result;
	}
	
	/**
	 * Do the addition operation of binary string
	 * @param binaryStr binary string
	 * @param adder integer to be added to binaryStr
	 * @return binary string result
	 */
	public StringBuilder add(StringBuilder binaryStr, int adder){
		StringBuilder result = new StringBuilder();
		int sum = Integer.parseInt(binaryStr.toString(), 2) + adder;
		if(sum > 255)
			sum -= 256;
		result.append(Integer.toBinaryString(sum));
		while (result.length() < 8){
			result.insert(0,'0');
		}
		return result;
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
		
		for(int i=0;i<SIZE;i++){
			// do the inisialization and assignment
			if(i>0){ // after the first iteration
				for(int j=0;j<SIZE;j++){
					kBefore[j].replace(0,8,ka[j].toString());
					ka[j].setLength(0);
				}
				StringBuilder temp = new StringBuilder();
				temp.append(getBinaryStringFromArray(substitutionMatrix[i-1]));
				temp.replace(0,temp.length(),shiftBitToLeft(temp,i).toString());
				for(int j=0;j<SIZE;j++){
					k[j].setLength(0);
					k[j].append(temp.substring(0,8));
					temp.delete(0,8);
				}
				
			} else if (i == 0){ // for the first iteration
				for(int j=0;j<SIZE;j++){
					kBefore[j].append(k[j]);
				}
			}
			
			// do the permutation
			for(int j=0;j<SIZE;j++){
				ka[j].setLength(0);
				ka[j].append(permutation(k)[j]);
			}
						
			// XOR and put to matrix
			for(int j=0;j<SIZE;j++){
				substitutionMatrix[i][j].append(XOR(kBefore[j],ka[j]));
				}	
			
		}
	}
	
	/**
	 * make the substitutionMatrix distinct
	 */
	public void fixSubstitutionMatrix(){
		List<String> distinctBinary = new ArrayList<>();
		StringBuilder temp = new StringBuilder();
		
		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				temp.append(substitutionMatrix[i][j]);
				if(distinctBinary.contains(temp.toString())){
					do{
						temp.replace(0,8,add(temp,1).toString());
					}while(distinctBinary.contains(temp.toString()));
					substitutionMatrix[i][j].replace(0,8,temp.toString());
				}
				distinctBinary.add(temp.toString());
				temp.setLength(0);
			}
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

	public void slidingHorizontal() {
	// Geser baris container16 secara horizontal
		String[][] temp = new String[4][4];
		temp = copyContainer16();
		String temp_cont = new String();

		// Geser baris 1
		container16[0][0] = temp[0][3];
		for (int i=0; i<3; i++)
			container16[0][i+1] = temp[0][i];

		// Geser baris 2
		for (int i=0; i<2; i++)
			container16[1][i] = temp[1][i+2];
		for (int i=0; i<2; i++)
			container16[1][i+2] = temp[1][i];

		// Geser baris 3
		container16[2][3] = temp[2][0];
		for (int i=1; i<=3; i++)
			container16[2][i-1] = temp[2][i];
	}

	public void slidingVertical() {
	// Geser baris container16 secara vertical
		String[][] temp = new String[4][4];
		temp = copyContainer16();
		String temp_cont = new String();

		// Geser baris 1
		container16[0][0] = temp[3][0];
		for (int i=0; i<3; i++)
			container16[i+1][0] = temp[i][0];

		// Geser baris 2
		for (int i=0; i<2; i++)
			container16[i][1] = temp[i+2][1];
		for (int i=0; i<2; i++)
			container16[i+2][1] = temp[i][1];

		// Geser baris 3
		container16[3][2] = temp[0][2];
		for (int i=1; i<=3; i++)
			container16[i-1][2] = temp[i][2];
	}
	
	public void substitution(){
		int row, col;
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				row = Integer.parseInt(container16[i][j].substring(0,4),2);
				col = Integer.parseInt(container16[i][j].substring(4),2);
				container16[i][j] = substitutionMatrix[row][col].toString();
			}
		}
	}

	public void printContainer16InHex(){
		StringBuilder temp = new StringBuilder();
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				temp.append(container16[i][j]);
				System.out.print(getHex(temp) + "\t");
				temp.setLength(0);
			}
			System.out.println("");
		}
	}
	
	public void printSubstitutionMatrixInHex(){
		for(int i=0;i<16;i++){
			for(int j=0;j<16;j++){
				System.out.print(getHex(substitutionMatrix[i][j]) + " ");
			}
			System.out.println();
		}
	}
	
	public void XORContainer16ToEndEncrypt(){
		StringBuilder temp = new StringBuilder();
		int k = 0;
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				temp.append(container16[i][j]);
				container16[i][j] = XOR(temp, substitutionMatrix[0][k]).toString();
				k++;
				temp.setLength(0);
			}
		}
	}
	
	public void encrypt() {
		binary = convertToBinaryString(plainText);
		last_idx = binary.length()-1;

		// make substitution matrix
		makeSBox(getSeed());
		makeSubstitutionMatrix();
		fixSubstitutionMatrix();

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
			
			// do the encrypt for 16 cycle
			for(int i=0;i<16;i++){
				//do the substitution with substitutionMatrix
				substitution();

				// shift the blocks of container16
				slidingHorizontal();
				slidingVertical();
			}
			
			// do XOR for container16 to end the encryption
			XORContainer16ToEndEncrypt();
		}
	}

	public void decrypt() {

	}
}

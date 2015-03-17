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
    public ArrayList<ArrayList<StringBuilder>> container16;  // Untuk menampung 16 blok plaintext awal
    public int first_idx, last_idx;
	
	private StringBuilder key;
    public StringBuilder[][] substitutionMatrix;
	private List<Integer> sBox;
	private int SIZE;
	
    
    public NewCryptoAlgorithm() {
        plainText = new StringBuilder();
        cipherText = new StringBuilder();
		key = new StringBuilder();
        container16 = new ArrayList<ArrayList<StringBuilder>>();
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
    
    public StringBuilder get8BitForward () {
    // Ambil 8 bit secara maju dari idx yang dikembalikan melalui stringBuilder
        StringBuilder result = new StringBuilder();
        for (int i=0; i<8; i++) {
            result.append(binary.toString().charAt(first_idx));
            first_idx++;
        }
        return result;
    }
    
    public StringBuilder get8BitBackward () {
    // Ambil 8 bit secara mundur ke belakang dari idx yang dikembalikan melalui stringBuilder
        StringBuilder result = new StringBuilder();
        for (int i=0; i<8; i++) {
            result.append(binary.toString().charAt(last_idx));
            last_idx--;
        }
        return result;
    }
    
    public void get16block() {
        int i = 0;
        int j = 0;
        int idx = 0;
        while (i<4) {
            i = 0;
            while (j<4) {
                if (j>3)
                    j=0;
                if (j%2==0) // Jika bagian kolom genap (indeks j == 0 atau 2), isi dengan 8BitForward
                    container16.get(i).get(j).append(get8BitForward());
                else
                    container16.get(i).get(j).append(get8BitBackward());
                j++;
            }
            i++;
        }
    }
    
    public void barisToKolom() {
    // Mengubah container16 baris-barisnya menjadi kolom
        ArrayList<ArrayList<StringBuilder>> temp = new ArrayList<ArrayList<StringBuilder>>();
        int i =0 ,j=0;
        while (i<4) {
            i = 0;
            while (j<4) {
                if (j>3)
                    j=0;
                temp.get(j).get(i).append(container16.get(i).get(j).toString());
                container16.get(i).get(j).setLength(0);
                j++;
            }
            i++;
        }
        i = 0; j = 0;
        while (i<4) {
            i = 0;
            while (j<4) {
                if (j>3)
                    j=0;
                container16.get(i).get(j).append(temp.get(i).get(j).toString());
                j++;
            }
            i++;
        }
    }
    
    public void printContainer16() {
        int i =0 ,j=0;
        while (i<4) {
            i = 0;
            while (j<4) {
                if (j>3) {
                    j=0;
                    System.out.println("");
                }
                System.out.print(container16.get(i).get(j).toString()+"\t");
                j++;
            }
            i++;
        }
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
		if(text.substring(0,4).equals("0000")){
			temp.insert(0, '0');
		}
		return temp;
	}
	
	
	public void printArray(StringBuilder[] arr){
		for(int i=0;i<SIZE;i++){
			System.out.println(arr[i]);
			System.out.println(getHex(arr[i]));
		}
	}
	
	public StringBuilder shiftBit(StringBuilder binaryStr, int digit){
		StringBuilder result = new StringBuilder();
//		if(digit % 2 == 0){
			result.append(binaryStr.substring(digit,binaryStr.length()));
			result.append(binaryStr.substring(0,digit));
//		} else {
//			result.append(binaryStr.substring(binaryStr.length()-digit));
//			result.append(binaryStr.substring(0,binaryStr.length()-digit));
//			
//		}
		return result;
	}
//	
//	public StringBuilder[] putBinaryStringToArray(StringBuilder binaryStr){
//		StringBuilder[] result = new StringBuilder[SIZE];
//		for(int i=0;i<SIZE;i++){
//			result[i] = new StringBuilder();
//			result[i].append(binaryStr.substring(0,8));
//			binaryStr.delete(0,8);
//		}
//		return result;
//	}
	
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
				temp.replace(0,temp.length(),shiftBit(temp,i).toString());
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
	
	public void fixSubstitutionMatrix(){
		List<String> distinctBinary = new ArrayList<>();
		StringBuilder temp = new StringBuilder();
		
		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				temp.append(substitutionMatrix[i][j]);
//				System.out.println(temp);
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
}

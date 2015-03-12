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
	
	public StringBuilder key;
    public StringBuilder[][] substitutionMatrix;
	public List<Integer> sBox;
	public int SIZE;
	
    
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
    
	// return binary string of text
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
	 * make the substitution matrix based on our algorithm
	 */
	public void makeSubstitutionMatrix(){
		// inisialization
		StringBuilder[] k = new StringBuilder[SIZE];
		StringBuilder[] ka = new StringBuilder[SIZE];
		StringBuilder binaryKey = new StringBuilder();
		for(int i=0;i<SIZE;i++){
			k[i] = new StringBuilder();
			ka[i] = new StringBuilder();
		}
		
		// change key to binary string
		binaryKey = convertToBinaryString(key);
		
		// divide the key into 16 parts
		for(int i=0;i<SIZE;i++){
			k[i].append(binaryKey.substring(0,8));
			binaryKey.delete(0,8);
		}
		int i = 0;
//		for(int i=0;i<SIZE;i++){
			// do the permutation
			ka = permutation(k);
			
			for(int j=0;j<SIZE;j++){
				substitutionMatrix[i][j].append(XOR(k[j],ka[j]));
			}
			
			StringBuilder[] k1a = new StringBuilder[SIZE];
			for(int l=0;l<SIZE;l++)
				k1a[l] = new StringBuilder();
			k1a = permutation(substitutionMatrix[i]);
			i++;
			for(int j=0;j<SIZE;j++){
				substitutionMatrix[i][j].append(XOR(ka[j],k1a[j]));
			}
			
			StringBuilder[] k2a = new StringBuilder[SIZE];
			for(int l=0;l<SIZE;l++)
				k2a[l] = new StringBuilder();
			k2a = permutation(substitutionMatrix[i]);
			i++;
			for(int j=0;j<SIZE;j++){
				substitutionMatrix[i][j].append(XOR(k1a[j],k2a[j]));
			}
//		}
		
	}
}

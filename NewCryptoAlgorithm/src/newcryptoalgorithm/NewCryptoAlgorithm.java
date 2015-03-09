/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newcryptoalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Andarias Silvanus & Cilvia Sianora Putri
 */
public class NewCryptoAlgorithm {
    // Atribut
    public static StringBuilder plainText;
    public static StringBuilder cipherText;
    public static StringBuilder binary;       // Menampung plainText dalam bentuk bit
    public static ArrayList<ArrayList<StringBuilder>> container16;  // Untuk menampung 16 blok plaintext awal
    public static int first_idx, last_idx;
	public static StringBuilder key;
    public static StringBuilder[][] substitutionMatrix;
	public static List<Integer> sBox;
	public static int SIZE;
	
	
    NewCryptoAlgorithm() {
        plainText = new StringBuilder();
        cipherText = new StringBuilder();
        container16 = new ArrayList<ArrayList<StringBuilder>>();
        binary = new StringBuilder();
        first_idx = 0;
        last_idx = plainText.length()-1;
		SIZE = 16;
		substitutionMatrix = new StringBuilder[SIZE][SIZE];
		sBox = new ArrayList<>();
		
	}
    
    public static void main(String[] args) {
//        ConvertToBinaryString();
//        get16block();
			plainText = new StringBuilder("haha");
			System.out.println(plainText);
			ConvertToBinaryString();
			System.out.println(plainText);
			
    }
	
    
    public static void ConvertToBinaryString () {
        byte[] b = plainText.toString().getBytes();
        for (byte b1 : b) {
            int val = b1;
            for (int i = 0; i < 8; i++) {
               binary.append((val & 128) == 0 ? 0 : 1);
               val <<= 1;
            }
        }
    }
    
    public static StringBuilder get8BitForward () {
    // Ambil 8 bit secara maju dari idx yang dikembalikan melalui stringBuilder
        StringBuilder result = new StringBuilder();
        for (int i=0; i<8; i++) {
            result.append(binary.toString().charAt(first_idx));
            first_idx++;
        }
        return result;
    }
    
    public static StringBuilder get8BitBackward () {
    // Ambil 8 bit secara mundur ke belakang dari idx yang dikembalikan melalui stringBuilder
        StringBuilder result = new StringBuilder();
        for (int i=0; i<8; i++) {
            result.append(binary.toString().charAt(last_idx));
            last_idx--;
        }
        return result;
    }
    
    public static void get16block() {
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
	
	/** From Key Input to Substitution Matrix **/
	
	// make S-BOX based on seed
	public static void makeSBox (int seed){
		Random ran = new Random(seed);
		int temp;
		while(sBox.size() < SIZE){
			temp = ran.nextInt(SIZE);
			if(!sBox.contains(temp)){
				sBox.add(temp);
			}
		}
	}
	
	// return the result of box permutation with S-BOX
	public static StringBuilder[] permutation(StringBuilder[] box){
		StringBuilder[] result = new StringBuilder[SIZE];
		for(int i=0;i<SIZE;i++)
			result[sBox.get(i)] = box[i];
		return result;
	}
	
	public static StringBuilder XOR(StringBuilder init){
		
		return null;
	}
	
	// make the substitution matrix based on our algorithm
	public static void makeSubstitutionMatrix(){
		StringBuilder[] K = new StringBuilder[SIZE];
		StringBuilder[] Ka = new StringBuilder[SIZE];
		
		// TODO : bagi key jadi 16 bagian trus masukin K
		
		Ka = permutation(K);
	}
}

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
        container16 = new ArrayList<ArrayList<StringBuilder>>();
        binary = new StringBuilder();
        first_idx = 0;
        last_idx = plainText.length()-1;
		SIZE = 16;
		substitutionMatrix = new StringBuilder[SIZE][SIZE];
		sBox = new ArrayList<>();
		
	}
    
    public void ConvertToBinaryString () {
        byte[] b = plainText.toString().getBytes();
        for (byte b1 : b) {
            int val = b1;
            for (int i = 0; i < 8; i++) {
               binary.append((val & 128) == 0 ? 0 : 1);
               val <<= 1;
            }
        }
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
	
	// make S-BOX based on seed
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
	
	// return the result of box permutation with S-BOX
	public StringBuilder[] permutation(StringBuilder[] box){
		StringBuilder[] result = new StringBuilder[SIZE];
		for(int i=0;i<SIZE;i++)
			result[sBox.get(i)] = box[i];
		return result;
	}
	
	public StringBuilder XOR(StringBuilder init){
		
		return null;
	}
	
	// make the substitution matrix based on our algorithm
	public void makeSubstitutionMatrix(){
		StringBuilder[] K = new StringBuilder[SIZE];
		StringBuilder[] Ka = new StringBuilder[SIZE];
		
		// TODO : bagi key jadi 16 bagian trus masukin K
		
		Ka = permutation(K);
	}
}

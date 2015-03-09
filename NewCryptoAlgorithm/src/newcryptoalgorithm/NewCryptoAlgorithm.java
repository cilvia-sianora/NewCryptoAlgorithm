/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newcryptoalgorithm;

import java.util.ArrayList;

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
    
    NewCryptoAlgorithm() {
        plainText = new StringBuilder();
        cipherText = new StringBuilder();
        container16 = new ArrayList<ArrayList<StringBuilder>>();
        binary = new StringBuilder();
        first_idx = 0;
        last_idx = plainText.length()-1;
    }
    
    public static void main(String[] args) {
        ConvertToBinaryString();
        get16block();
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
}

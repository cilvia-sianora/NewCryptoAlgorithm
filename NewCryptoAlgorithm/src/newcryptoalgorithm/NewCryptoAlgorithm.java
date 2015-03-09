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
    public static int first_cnt, last_cnt;
    
    NewCryptoAlgorithm() {
        plainText = new StringBuilder();
        cipherText = new StringBuilder();
        container16 = new ArrayList<ArrayList<StringBuilder>>();
        binary = new StringBuilder();
        first_cnt = 0;
        last_cnt = plainText.length()-1;
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
    
    public static void get8Bit ()
    
    public static void get16block() {
        int i = 0;
        int j = 0;
        int idx = 0;
        while (i<4) {
            i = 0;
            while (j<4) {
                if (j>3)
                    j=0;
                container16[i][j]
                j++;
            }
            i++;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newcryptoalgorithm;

/**
 *
 * @author Andarias Silvanus
 */
public class NewCryptoMain {
    
	public static void main(String args[]){
		NewCryptoAlgorithm NC = new NewCryptoAlgorithm();
		String text = "Plaintext akan dibagi per 8 bit. Bila ternyata hasil mod plaintext tidak 0, maka sisa plaintext akan digabung dengan padding. Selanjutnya program akan mengambil 4 blok untuk disusun menjadi blok 4x4. Baris pertama diambil dari 8 bit pertama dari plaintext, baris kedua diambil dari 8 bit terakhir, baris ketiga diambil dari 8 bit dari kedua pertama, dan baris keempat diambil dari 8 bit kedua terakhir dari plaintext. Counter pertama diset pada posisi kedua pertama (second), dan counter kedua akan diset pada posisi kedua terakhir. Counter akan terus berjalan seiring dengan pengambilan blok 4x4 plaintext lainnya.";
		NC.plainText.append(text);
		NC.ConvertToBinaryString();
		System.out.println(NC.binary);
//		while (NC.first_idx + 8 < NC.last_idx) {
//		// MASIH GAGAL
//			NC.get16block();
//			NC.barisToKolom();
//		}
		
		
	}
}

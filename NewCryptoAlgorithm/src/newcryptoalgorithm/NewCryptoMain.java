package newcryptoalgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Main
 * @author Andarias Silvanus
 */
public class NewCryptoMain {
    
	public static void main(String args[]){
		NewCryptoAlgorithm NC = new NewCryptoAlgorithm();
		//String text = "Plaintext akan dibagi per 8 bit. Bila ternyata hasil mod plaintext tidak 0, maka sisa plaintext akan digabung dengan padding. Selanjutnya program akan mengambil 4 blok untuk disusun menjadi blok 4x4. Baris pertama diambil dari 8 bit pertama dari plaintext, baris kedua diambil dari 8 bit terakhir, baris ketiga diambil dari 8 bit dari kedua pertama, dan baris keempat diambil dari 8 bit kedua terakhir dari plaintext. Counter pertama diset pada posisi kedua pertama (second), dan counter kedua akan diset pada posisi kedua terakhir. Counter akan terus berjalan seiring dengan pengambilan blok 4x4 plaintext lainnya.";
		String text = "ABCDEFGHIJKLMNOPQRSTU";
        
		NC.setKey("ABCDEFGHIJKLMNOP");
		NC.plainText.append(text);
		
		// ECB
//		NC.encrypt();
//		NC.decrypt();

		// CBC
		NC.encryptCBC();
		NC.decryptCBC();
		
		// CFB
//		NC.encryptCFB();		
//		NC.decryptCFB();
		
		
	}
}

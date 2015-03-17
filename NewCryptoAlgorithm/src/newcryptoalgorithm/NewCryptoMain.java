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
		String text = "ABCEDFGHIJKLMNOPQRSTU";
                
		NC.plainText.append(text);
//                System.out.println("plaintext: "+NC.plainText.toString());
                NC.encrypt();
//		NC.binary = NC.convertToBinaryString(NC.plainText);
//                NC.last_idx = NC.binary.length()-1;
//		System.out.println(NC.binary);
                
//		NC.plainText.append(text);
//		NC.binary = NC.convertToBinaryString(NC.plainText);
//		System.out.println(NC.binary);
//		while (NC.first_idx + 8 < NC.last_idx) {
//		// MASIH GAGAL
//			NC.get16block();
//			NC.barisToKolom();
//		}
		
//		StringBuilder test = new StringBuilder();
//		StringBuilder test2 = new StringBuilder();
//		StringBuilder test3 = new StringBuilder();
//		StringBuilder result = new StringBuilder();
//		test.append("AB");
//		test2.append("01");
//		result = NC.XOR(test, test2);
//		System.out.println(result);
		
//		System.out.println(NC.getHex(NC.convertToBinaryString(test)));
		
		
//		test.append("10101010");
////		test2 = test;
//		test.replace(0,8,"01010101");
//		System.out.println(test);
//		System.out.println(test2);
//		test.setLength(0);
//		test2.setLength(0);
////		System.out.println(test);
//		test3.append("test3");
//		test.append(test3);
//		test2 = test;
//		System.out.println(test);
//		System.out.println(test2);
		
		
//		NC.setKey(text);
//		NC.makeSBox(30);
//		NC.makeSubstitutionMatrix();
//		
//		for(int i=0;i<16;i++){
//			for(int j=0;j<16;j++){
//				System.out.print(NC.getHex(NC.substitutionMatrix[i][j]) + " ");
//			}
//			System.out.println();
//		}
		
		
//		StringBuilder[] init = new StringBuilder[16];
//		StringBuilder[] result = new StringBuilder[16];
//		for(int i=0;i<16;i++){
//			init[i] = new StringBuilder();
//			result[i] = new StringBuilder();
//			init[i].append(NC.key.charAt(i));
//		//	System.out.println(init[i]);
//		}
//		
//		result = NC.permutation(init);
//		for(int i=0;i<16;i++)
//			System.out.println(result[i]);
	}
}

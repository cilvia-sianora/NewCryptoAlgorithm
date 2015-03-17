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
                
		List<String> distinctHex = new ArrayList<>();
		List<String> doubleHex = new ArrayList<>();
		
		NC.setKey(text);
		NC.makeSBox(30);
		NC.makeSubstitutionMatrix();
		NC.fixSubstitutionMatrix();
		
		int counterDistinctOri = 0;
		int counterDistinct1 = 0;
		StringBuilder temp = new StringBuilder();
		for(int i=0;i<16;i++){
			for(int j=0;j<16;j++){
				
				if(!distinctHex.contains(NC.getHex(NC.substitutionMatrix[i][j]).toString())){
						System.out.print(NC.getHex(NC.substitutionMatrix[i][j]) + " ");
					
					distinctHex.add(NC.getHex(NC.substitutionMatrix[i][j]).toString());
				} else {
						doubleHex.add(NC.getHex(NC.substitutionMatrix[i][j]).toString());
						System.out.print(NC.getHex(NC.substitutionMatrix[i][j]) + " ");
				}
			}
			System.out.println();
		}
		System.out.println("--EVALUATION--");
		System.out.println("-disctintHex-");
		System.out.println("size="+distinctHex.size());
		System.out.println("-doubleHex-");
		System.out.println("size="+doubleHex.size());
		for(String s: doubleHex){
			System.out.println(s);
		}
		
		
	}
}

package paket;

import java.io.FileNotFoundException;
import java.util.Scanner;

import org.neuroph.nnet.learning.BackPropagation;

public class Program {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		YSA ysa=null;
		BP backp = null;
		int araKatmanNoron;
		double momentum, ogrenmeKatSayisi,hata;
		int epoch, sec=0;
		
		do {
			System.out.println("--Momentumlu BP--");
			System.out.println("1.Eðitim ve Test");
			System.out.println("--Momentumsuz BP--");
			System.out.println("2.Eðitim ve Test");
			System.out.println("3.Cikis");
			sec=in.nextInt();
			switch(sec) {
			case 1:
				System.out.print("Ara Katman Noron Sayisi: ");
				araKatmanNoron = in.nextInt();
				System.out.print("Momentum: ");
				momentum = in.nextDouble();
				System.out.print("Ogrenme Kat Sayisi: ");
				ogrenmeKatSayisi=in.nextDouble();
				System.out.print("Min Hata ");
				hata = in.nextDouble();
				System.out.print("Epoch: ");
				epoch=in.nextInt();
				
				ysa = new YSA(araKatmanNoron,momentum,ogrenmeKatSayisi,hata,epoch);
				ysa.Egit();
				System.out.print("Egitimde elde edilen hata:  " +ysa.egitimHata());
				System.out.print("Testte elde edilen hata:  "+ysa.testHata());
				break;
				
			case 2:
				
					System.out.print("Ara Katman Noron Sayisi: ");
					araKatmanNoron = in.nextInt();
					System.out.print("Ogrenme Kat Sayisi: ");
					ogrenmeKatSayisi=in.nextDouble();
					System.out.print("Min Hata ");
					hata = in.nextDouble();
					System.out.print("Epoch: ");
					epoch=in.nextInt();
					
					backp = new BP(araKatmanNoron, ogrenmeKatSayisi,hata,epoch);
					backp.Egit();
					System.out.print("Egitimde elde edilen hata:  " +backp.egitimHata());
					System.out.print("Testte elde edilen hata:  "+backp.testHata());
					
				break;
			
			}
			
		}while(sec != 3);
	}

}

package paket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

public class BP {
	
	private static final File egitimDosya= new File(BP.class.getResource("Egitim.txt").getPath());
	private static final File testDosya = new File(BP.class.getResource("Test.txt").getPath());
	
	private double[] maksimumlar;
	private double[] minimumlar;
	
	private DataSet egitimVeriSeti;
	private DataSet testVeriSeti;
	private int araKatmanNoronSayisi;
	private BackPropagation bp;

	
	public BP(int araKatmanSayisi, double ogrenmeKatSayisi, double hata, int epoch) throws FileNotFoundException
	{
		maksimumlar=new double[4];
		minimumlar=new double[4];
		
		for(int i=0; i<4; i++) {
			maksimumlar[i] = Double.MIN_VALUE;
			minimumlar[i] = Double.MAX_VALUE;
		}
		//verilerdeki minmax
		VeriSetiMaks(egitimDosya);
		VeriSetiMaks(testDosya);
		egitimVeriSeti = VeriSeti(egitimDosya);
		testVeriSeti = VeriSeti(testDosya);
		
		//egitim algoritmasi
		
		bp.setLearningRate(ogrenmeKatSayisi);
		bp.setMaxError(hata);
		bp.setMaxIterations(epoch);
		this.araKatmanNoronSayisi=araKatmanNoronSayisi;	
	}


	public void Egit() {
		MultiLayerPerceptron sinirselAg= new MultiLayerPerceptron(TransferFunctionType.SIGMOID,3,araKatmanNoronSayisi,1);
		sinirselAg.setLearningRule(bp);
		sinirselAg.learn(egitimVeriSeti);
		sinirselAg.save("BP.nnet");
		System.out.println("Egitim Tamamlandi");
	}
	
	public double egitimHata() {
		return bp.getTotalNetworkError();
	}
	//kareselhatalaryontemi
	private double mse(double[]beklenen, double[]cikti) {
		return Math.pow((beklenen[0] - cikti[0]), 2);
	}
	public double testHata() {
		NeuralNetwork sinirselAg= NeuralNetwork.createFromFile("BP.nnet");
		double toplamHata=0;
		List<DataSetRow> satirlar = testVeriSeti.getRows();
		for(DataSetRow dr : satirlar) {
			sinirselAg.setInput(dr.getInput());
			sinirselAg.calculate();
			toplamHata+=mse(dr.getDesiredOutput(),sinirselAg.getOutput());
		}
		return toplamHata/testVeriSeti.size();
	}
	public void VeriSetiMaks(File file) throws FileNotFoundException {
		Scanner fle = new Scanner(file);
		while(fle.hasNextDouble()) {
			for(int i=0; i<4; i++) {
				double d=fle.nextDouble();
				if(d > maksimumlar[i]) maksimumlar[i]=d;
				if(d < minimumlar[i]) minimumlar[i]=d;
			}	
		}
		fle.close();
	}
	private double minMax(double max, double min, double x) {
		return (x-min)/(max-min);
	}
	//veriseti döndürecek
	private DataSet VeriSeti(File file) throws FileNotFoundException {
		Scanner fle = new Scanner(file);
		DataSet dataset = new DataSet(3,1);
		while(fle.hasNextDouble()) {
			double[]inputs = new double[3];
			for(int i=0;i<3;i++) {
				double d = fle.nextDouble();
				inputs[i]=minMax(maksimumlar[i],minimumlar[i],d);
			}
			double output=minMax(maksimumlar[3],minimumlar[3],fle.nextDouble());
			dataset.add(new DataSetRow(inputs, new double[] {output}));
		}
		fle.close();
		return dataset;
		
	}
	
	
	
}

package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Scanner;

public class OneShotAgent extends Agent {

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new MyOneShotBehaviour());
  }

  public class GradientDescent {
	private static final double TOLERANCE = 1E-11;
	//Betha_0 and Betha_1
	private double betha_0;
    private double betha_1;
    
    public double getBetha_0() {
        return betha_0;
    }
    
    public double getBetha_1() {
        return betha_1;
    }
    
    public GradientDescent(double betha_0, double betha_1) {
         this.betha_0 = betha_0;
         this.betha_1 = betha_1;
    }

    public double getHypothesisResult(double x){
        return betha_0 + betha_1 * x;
    }
    
    private double getResult(double[][] trainingData, boolean enableFactor){
        double result = 0;
        for (int i = 0; i < trainingData.length; i++) {
            result = (getHypothesisResult(trainingData[i][0]) - trainingData[i][1]);
            if (enableFactor) result = result*trainingData[i][0]; 
        }
        return result;
    }
    
    public void train(double learningRate, double[][] trainingData){
        int iteration = 0;
        do{
            iteration++;
            double temp0 = betha_0 - learningRate*(((double) 1/trainingData.length)*getResult(trainingData, false));
            double temp1 = betha_1 - learningRate*(((double) 1/trainingData.length)*getResult(trainingData, true));
            betha_0 = temp0; betha_1 = temp1;
        }while(iteration < 100000);
    }

}
 

  private class MyOneShotBehaviour extends OneShotBehaviour {
    private static final double[][] dataSet = {{23, 651},{26, 762},{30, 856},{34, 1063},{43, 1190},{48, 1298},{52, 1421},{57, 1440}, {58, 1518}};
    public void action() {
      Scanner sc = new Scanner(System.in);
      System.out.println("Ingrese el advertising (Millones): ");
      int x = sc.nextInt();
      // TODO Auto-generated method stub
      GradientDescent gradient = new GradientDescent(168,23);
      gradient.train(0.000001, dataSet);
      System.out.println("BETHA_0: " + gradient.getBetha_0() + " - BETHA_1: " + gradient.getBetha_1());
	    System.out.println("PREDICTION: " + gradient.getHypothesisResult(x));

    } 
    
    public int onEnd() {
      myAgent.doDelete();   
      return super.onEnd();
    } 
  }    // END of inner class ...Behaviour
}


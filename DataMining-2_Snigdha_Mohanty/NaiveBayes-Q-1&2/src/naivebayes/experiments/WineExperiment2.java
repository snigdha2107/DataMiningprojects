package naivebayes.experiments;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import naivebayes.CategoricalAttribute;
import naivebayes.ContinuousAttribute;
import naivebayes.Example;
import naivebayes.NaiveBayesClassifier;

public class WineExperiment2 {

  private static final String DEFAULT_DATA_FILE = "wine.data";

  private static final int INPUTS = 13;

  public static void main(String[] args) {
    String dataFile = DEFAULT_DATA_FILE;
    Map<String, List<Example>> classData = readClassDataFromFile(dataFile);


    List <Example> totalExamples = new ArrayList <Example> ();
    List <Example> totalValidationSet = new ArrayList <Example> ();

    /* read data from file and create a training data set (with name totalExample),
     which contains random 2/3rd of data from each class and a validation/evalutaion 
     data set which contains 1/3rd of random data from each class.*/
    for (String classStr : classData.keySet()) {
    	
    	List<Example> eachClassData = classData.get(classStr);
    	
    	Collections.shuffle(eachClassData, new Random(42));
    	normalize(eachClassData);
    	
        List <Example> examples = new ArrayList <Example> ();
        List <Example> validationSet = new ArrayList <Example> ();

        for(int i=0;i<eachClassData.size();i++){
        	if(i % 3 == 0){
        		validationSet.add(eachClassData.get(i));
        	}else{
        		examples.add(eachClassData.get(i));
        	}
        }

		totalExamples.addAll(examples);
		totalValidationSet.addAll(validationSet);
		
       // System.out.println(examples.size());
       // System.out.println(validationSet.size());
        
    }
    
    /* Naive Bayesian error rate*/
    System.out.println(testClassifier(totalExamples, totalValidationSet));

    /*Collections.shuffle(all, new Random(42));

    normalize(all);

    List <Example> examples = new ArrayList <Example> ();
    List <Example> validationSet = new ArrayList <Example> ();
    for(int i = 0; i < trainingExamples; i++) {
      examples.add(all.get(i));
    }
    for(int i = DEFAULT_TRAINING_EXAMPLES; i < all.size(); i++) {
      validationSet.add(all.get(i));
    }
    System.out.println(testClassifier(examples, validationSet));*/
  }

  private static double testClassifier(List <Example> examples,
                                       List <Example> validationSet) {

    NaiveBayesClassifier classifier = new NaiveBayesClassifier(examples);

    int correct = 0;
    for(Example example : validationSet) {
      String output = classifier.classify(
          example.getCategoricalInputs(),
          example.getContinuousInputs()).getValue();
      if(output.equals(example.getOutput().getValue())) {
        correct++;
      }
    }

    return (double)correct / validationSet.size();
  }

  
  
  
  private static Map<String, List<Example>> readClassDataFromFile(String filename) {
	Map<String, List<Example>> classData = new HashMap<String,List<Example>>();
    try {
      Scanner sc = new Scanner(new FileInputStream(filename));
      while(sc.hasNextLine()) {
    	  
    	String nextLine = sc.nextLine();
    	
    	String[] vals = nextLine.split(",");

    	
		if( classData.get(vals[0])!=null ){
			classData.get(vals[0]).add(parseExample(nextLine));
		}else{
			List<Example> exampleList = new ArrayList<Example>();
			exampleList.add(parseExample(nextLine));
			classData.put(vals[0], exampleList);
		}
    	
    	/*if("1".equals(vals[0])){
    		
    	}else if("2".equals(vals[0])){
    		if( classData.get("2")!=null ){
    			classData.get("2").add(parseExample(sc.nextLine()));
    		}else{
    			List<Example> exampleList = new ArrayList<Example>();
    			exampleList.add(parseExample(sc.nextLine()));
    			classData.put("2", exampleList);
    		}
    	}else if("3".equals(vals[0])){
    		if( classData.get("3")!=null ){
    			classData.get("3").add(parseExample(sc.nextLine()));
    		}else{
    			List<Example> exampleList = new ArrayList<Example>();
    			exampleList.add(parseExample(sc.nextLine()));
    			classData.put("3", exampleList);
    		}
    	}*/
    	  
      }
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    return classData;
  }  
  
  private static List <Example> readFile(String filename) {
    List <Example> examples = new ArrayList <Example> ();
    try {
      Scanner sc = new Scanner(new FileInputStream(filename));
      while(sc.hasNextLine()) {
        examples.add(parseExample(sc.nextLine()));
      }
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    return examples;
  }

  private static Example parseExample(String record) {
    String[] tokens = record.split(",");

    List <CategoricalAttribute> catInputs =
        new ArrayList <CategoricalAttribute> ();
    List <ContinuousAttribute> conInputs =
        new ArrayList <ContinuousAttribute> ();

    for(int i = 1; i < tokens.length; i++) {
      conInputs.add(new ContinuousAttribute(
          Double.parseDouble(tokens[i])));
    }

    return new Example(
        catInputs,
        conInputs,
        new CategoricalAttribute(tokens[0]));
  }

  private static void normalize(List <Example> examples) {
    for(int i = 0; i < INPUTS; i++) {
      double minValue =
          examples.get(0).getContinuousInputs().get(i).getValue();
      double maxValue =
          examples.get(0).getContinuousInputs().get(i).getValue();
      for(int j = 0; j < examples.size(); j++) {
        minValue = Math.min(
            minValue,
            examples.get(j).getContinuousInputs().get(i).getValue());
        maxValue = Math.max(
            maxValue,
            examples.get(j).getContinuousInputs().get(i).getValue());
      }
      for(int j = 0; j < examples.size(); j++) {
        double value =
            examples.get(j).getContinuousInputs().get(i).getValue();
        value = (value - minValue) / (maxValue - minValue);
        examples.get(j).getContinuousInputs().set(
            i, new ContinuousAttribute(value));
      }
    }
  }
}


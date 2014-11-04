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

public class TenFoldNaiveBayes {

  private static final String DEFAULT_DATA_FILE = "wine.data";

  private static final int INPUTS = 13;

  public static void main(String[] args) {
    String dataFile = DEFAULT_DATA_FILE;
    List<Example> classData = readDataFromFile(dataFile);
    
    System.out.println(classData.size());
    
	Collections.shuffle(classData, new Random(32));
	normalize(classData);

	int size = classData.size();
	int tenFoldSize = size/10;
	
	int startIndex = 0;
	int endIndex = tenFoldSize;

	int i= 0;
	
	ArrayList<Double> tenFoldAccuracyValues = new ArrayList<Double>();

	
	List<List<Example>> listOfSubLists = new ArrayList<List<Example>>();
	
	//Breaking data into 10 sets of total datasize(178)/10.
	
	while(i<10){
		List<Example> eachSubList = classData.subList(startIndex, endIndex-1);
		listOfSubLists.add(eachSubList);
		i++;
		startIndex = endIndex;
		endIndex = endIndex + tenFoldSize;
	}
	
	
	//Training on data of 9 datasets and testing on 1 dataset.
	//Repeating this 10 times changing the training data sets and testing data set.
	for(int k =0 ;k< listOfSubLists.size() ; k++){
		List <Example> examples = new ArrayList <Example> ();
		List <Example> validationSet = new ArrayList <Example> ();
	
		for(int j = 0;j<listOfSubLists.size();j++){
			if(j == k){
				validationSet.addAll(listOfSubLists.get(j));
			}else{
				examples.addAll(listOfSubLists.get(j));
			}
		}
		tenFoldAccuracyValues.add(testClassifier(examples, validationSet));
	}

	//System.out.println(testClassifier(examples, validationSet));
	
	
	//Finding the mean of all above 10 validation values.
	Collections.sort(tenFoldAccuracyValues);
	
	double median = 0;
	if (tenFoldAccuracyValues.size() % 2 == 0)
	    median = (tenFoldAccuracyValues.get(tenFoldAccuracyValues.size()/2) + tenFoldAccuracyValues.get(tenFoldAccuracyValues.size()/2 - 1))/2;
	else
	    median = tenFoldAccuracyValues.get(tenFoldAccuracyValues.size()/2);	

	System.out.println(median);
	
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

  
  
  
  private static List<Example> readDataFromFile(String filename) {
	List<Example> dataList = new ArrayList<Example>();
    try {
      Scanner sc = new Scanner(new FileInputStream(filename));
      while(sc.hasNextLine()) {
    	  
    	String nextLine = sc.nextLine();
    	
    	dataList.add(parseExample(nextLine));
    	
      }
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    return dataList;
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


import pca_transform.PCA;
import Jama.Matrix;
import java.util.ArrayList;

/** An example program using the library */
public class PCATransform {
    public static void main(String[] args){
        System.out.println("Running a demonstrational program on some sample data ...");
        Matrix trainingData = new Matrix(new double[][] {
            {1, 2, 3, 4, 5, 6},
            {6, 5, 4, 3, 2, 1},
            {2, 2, 2, 2, 2, 2}});
        PCA pca = new PCA(trainingData);
        Matrix testData = new Matrix(new double[][] {
                {1, 2, 3, 4, 5, 6},
                {1, 2, 1, 2, 1, 2}});
        Matrix transformedData =
            pca.transform(testData, PCA.TransformationType.ROTATION);
        System.out.println("Transformed data:");
        for(int r = 0; r < transformedData.getRowDimension(); r++){
            for(int c = 0; c < transformedData.getColumnDimension(); c++){
                System.out.print(transformedData.get(r, c));
                if (c == transformedData.getColumnDimension()-1) continue;
                System.out.print(", ");
            }
            System.out.println("");
        }
    }
    
    
    
    public void pcaTransform(ArrayList<ArrayList<Double>> data){
    	int size1 = data.size();
    	int size2 = data.get(0).size();
    	
    	double[][] dataArr = new double[size1][size2];
    	
    	for (int i=0;i<data.size();i++){
    		ArrayList<Double> tempData = data.get(i);
    		
    		for(int j=0;j<tempData.size();j++){
    			Double d = tempData.get(j);
    			dataArr[i][j] = d;
    		}
		}
    	
        	Matrix trainingData = new Matrix(dataArr);
            PCA pca = new PCA(trainingData);

            Matrix transformedData = pca.transform(trainingData, PCA.TransformationType.ROTATION);
            System.out.println("Transformed data:");
            for(int r = 0; r < transformedData.getRowDimension(); r++){
                for(int c = 0; c < transformedData.getColumnDimension(); c++){
                    System.out.print(transformedData.get(r, c));
                    if (c == transformedData.getColumnDimension()-1) continue;
                    System.out.print(", ");
                }
                System.out.println("");
            }
    }
    
    public void pcaTransform(double[][] dataArr){
        	Matrix trainingData = new Matrix(dataArr);
            PCA pca = new PCA(trainingData);

            Matrix transformedData = pca.transform(trainingData, PCA.TransformationType.ROTATION);
            System.out.println("Transformed data:");
            for(int r = 0; r < transformedData.getRowDimension(); r++){
                for(int c = 0; c < transformedData.getColumnDimension(); c++){
                    System.out.print(transformedData.get(r, c));
                    if (c == transformedData.getColumnDimension()-1) continue;
                    System.out.print(", ");
                }
                System.out.println("");
            }
    }    

    
    
}

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadXLSX {

	public static void main(String[] args)throws Exception {
       
		
		String fname = "C:\\java\\assignment\\assign 1 Data Mining2\\assign 1 Data Mining\\.assignment1_matricesxlsx";
        InputStream inp = new FileInputStream(fname);
        Workbook  wb = new XSSFWorkbook(inp); // Declare XSSF WorkBook
        
        Sheet sheet = wb.getSheetAt(0); // sheet can be used as common for XSSF and HSSF
        
        ArrayList<ArrayList<Double>> firstSheetData = getSheetData(wb.getSheetAt(0));
        ArrayList<ArrayList<Double>> firstImageDCTdata = dctTransformForCollectionOfVectors(firstSheetData);
        
        getPCAData(firstImageDCTdata);
        
        ArrayList<ArrayList<Double>> secondSheetData = getSheetData(wb.getSheetAt(1));
        ArrayList<ArrayList<Double>> secondImageDCTdata = dctTransformForCollectionOfVectors(secondSheetData);

        getPCAData(secondImageDCTdata);
        
        ArrayList<ArrayList<Double>> thirdSheetData = getSheetData(wb.getSheetAt(2));
        ArrayList<ArrayList<Double>> thirdImageDCTdata = dctTransformForCollectionOfVectors(thirdSheetData);
        
        getPCAData(thirdImageDCTdata);
        
        inp.close();	
        
	}
	
	
	public static ArrayList<Double> dctTransform(ArrayList<Double> data){
		int count = data.size();
		double sum = 0;

		ArrayList<Double> transformedData = new ArrayList<Double>();
		
		for( int i=0; i< count; i++){
			sum = 0;
			for( int j =0; j< count; j++){
				sum += data.get(j) * Math.cos(( (2*j +1) * i * Math.PI) / (2* count) );
			}
			
			double temp = getAlpha( i, count) * sum;
			transformedData.add(temp);
		}
		
		for (Double trasnformedVal : transformedData) {
			System.out.print(trasnformedVal+" ");
		}
		System.out.println();
		
		return transformedData;

	}	

	
	
	static double getAlpha( int i, int k){
		if( i ==0)
			return Math.sqrt( 1/ ((double)k) );
		else 
			return Math.sqrt( 2/ (double)k);
	}
	
	public static ArrayList<ArrayList<Double>> dctTransformForCollectionOfVectors(ArrayList<ArrayList<Double>> data){

		ArrayList<ArrayList<Double>> trasnformedData = new ArrayList<ArrayList<Double>>();
		
		for (ArrayList<Double> eachVectorData : data) {
			trasnformedData.add(dctTransform(eachVectorData));
		}
		return trasnformedData;
	}	
	
	public static void getPCAData(ArrayList<ArrayList<Double>> data){
		PCATransform pca = new PCATransform();
		pca.pcaTransform(data);
	}
	

	public static ArrayList<ArrayList<Double>> getSheetData(Sheet sheet){
		ArrayList<ArrayList<Double>> mattrixData = new ArrayList<ArrayList<Double>>();
        Iterator<Row> rows=sheet.rowIterator();
        while (rows.hasNext()) {
            Row row = (Row) rows.next();
            System.out.println("row#=" + row.getRowNum() + "");
            ArrayList<Double> rowData = new ArrayList<Double>();
            Iterator cells = row.cellIterator();
            while (cells.hasNext()) {
                Cell cell = (Cell) cells.next();

                switch (cell.getCellType()) {
                
                case Cell.CELL_TYPE_NUMERIC:
                	rowData.add(cell.getNumericCellValue());
                    break;
                default:
                
                }
                mattrixData.add(rowData);
            }
        }
		return mattrixData;
	}
	
}

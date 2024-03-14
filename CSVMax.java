import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * 
 * 
 * @author Raquel Mendes
 * @version june 2022
 */
public class CSVMax {

    public CSVRecord hottestHourInFile(CSVParser parser){
    
        CSVRecord largestSoFar = null;
        
        for(CSVRecord currentRow : parser){
        
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
    
    return largestSoFar;
    
    }
    
    public void testHottestInDay(){
    
        FileResource fr = new FileResource();
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("Hottest temperature was " + largest.get("TemperatureF") + " at " + largest.get("TimeEST"));
    
    }
    
    public CSVRecord hottestInManyDays(){
    
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        
        for(File f : dr.selectedFiles()){
        
           FileResource fr = new FileResource(f);
           CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
           largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
    
        return largestSoFar;
    }
    
    public CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar){
    
        if(largestSoFar == null){
            
               largestSoFar = currentRow;
            }
           else{
            
               double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
               double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
                
               if(currentTemp > largestTemp){
                
                    largestSoFar = currentRow;
                
               }
            
           }
        return largestSoFar;
    
    }
    
    public void testHottestInManyDay(){
    
        CSVRecord largest = hottestInManyDays();
        System.out.println("Hottest temperature was " + largest.get("TemperatureF") + " at " + largest.get("DateUTC"));
    }
}

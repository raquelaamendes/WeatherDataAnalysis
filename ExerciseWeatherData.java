import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * 
 * @author Raquel Mendes
 * @version june 2022
 */
public class ExerciseWeatherData {

    public CSVRecord getColdestOfTwo (CSVRecord currentRow, CSVRecord coldestSoFar){
    
        if(Double.parseDouble(currentRow.get("TemperatureF")) != -9999){
            
                if(coldestSoFar == null){
                
                    coldestSoFar = currentRow;
                }
                else{
                
                    double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                    double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                    if(currentTemp < coldestTemp){
                    
                        coldestSoFar = currentRow;
                    }
                
                }
            
            }
        return coldestSoFar;
    
    }
    
    public CSVRecord coldestHourInFile(CSVParser parser){
    
        CSVRecord coldestSoFar = null;
        
        
        for(CSVRecord currentRow : parser){
        
            
            coldestSoFar = getColdestOfTwo(currentRow, coldestSoFar);
            
            
            
        
        }
    
        return coldestSoFar;
    }
    
    public void testColdestHourInFile(){
    
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldest = coldestHourInFile(parser);
        System.out.println("Coldest hour on that day was at " + coldest.get("TimeEST") + " with a temperature of " + coldest.get("TemperatureF"));
    
    }
    
    public String fileWithColdestTemperature(){
    
        DirectoryResource dr = new DirectoryResource();
        String nomeFicheiro = null;
        CSVRecord coldestSoFar = null;
        coldestSoFar = null;
        CSVRecord tempColdestSoFar = coldestSoFar; 
        
        for(File f : dr.selectedFiles()){
        
           FileResource fr = new FileResource(f);
           CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
           coldestSoFar = getColdestOfTwo(currentRow, coldestSoFar);
           
           if(tempColdestSoFar != coldestSoFar){
            
               nomeFicheiro = f.getName();
               tempColdestSoFar = coldestSoFar;
               
            }
        }
        
        return nomeFicheiro;
    }
    
    public void testFileWithColdestTemperature(){
    
        String nomeFicheiro = fileWithColdestTemperature(); 
        System.out.println("Coldest day was in file " + nomeFicheiro);
        
        FileResource fr = new FileResource("data/2013/" + nomeFicheiro);
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldest = coldestHourInFile(parser);
        System.out.println("Coldest hour on that day was at " + coldest.get("TimeEST") + " with a temperature of " + coldest.get("TemperatureF"));
        System.out.println("All the temperatures on the coldest day were:");
        
        for (CSVRecord currentRow : fr.getCSVParser()){
        
            System.out.println(currentRow.get("DateUTC") + " " + currentRow.get("TemperatureF"));
            
        }
        
        
        
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
    
        CSVRecord lowestHumidity = null;
        
        for(CSVRecord currentRow : parser){
        
            
            if(currentRow.get("Humidity").compareTo("N/A") > 0 || currentRow.get("Humidity").compareTo("N/A") < 0 ){
            
                if(lowestHumidity == null){
                
                    lowestHumidity = currentRow;
                
                }else{
                
                    if(Double.parseDouble(currentRow.get("Humidity")) < Double.parseDouble(lowestHumidity.get("Humidity"))){
                    
                        lowestHumidity = currentRow;
                    }
                }
            
            }
            
            
            
        
        }
        
        return lowestHumidity;
    }
    
    public void testLowestHumidityInFile(){
    
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
        
    
    
    }
    
    public CSVRecord lowestHumidityInManyFiles(){
    
        CSVRecord lowestHumidity = null;
        DirectoryResource dr = new DirectoryResource();
        
        
        for(File f : dr.selectedFiles()){
        
           FileResource fr = new FileResource(f);
           CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
           
         
        
            
            if(currentRow.get("Humidity").compareTo("N/A") > 0 || currentRow.get("Humidity").compareTo("N/A") < 0 ){
            
                if(lowestHumidity == null){
                
                    lowestHumidity = currentRow;
                
                }else{
                
                    if(Double.parseDouble(currentRow.get("Humidity")) < Double.parseDouble(lowestHumidity.get("Humidity"))){
                    
                        lowestHumidity = currentRow;
                    }
                }
            
            }
            
            
            
        
       
        }
        
        return lowestHumidity;
    }
    
    public void testLowestHumidityInManyFiles(){
    
        CSVRecord lowest = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + lowest.get("Humidity") + " at " + lowest.get("DateUTC"));
    }
    
    public Double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
    
        double averageTemperatureHighHumidity = 0.0;
        
        int contador = 0; 
        
        for (CSVRecord currentRow : parser){
        
            if( Double.parseDouble(currentRow.get("Humidity")) >= Double.valueOf(value)){
            
                averageTemperatureHighHumidity = averageTemperatureHighHumidity + Double.parseDouble(currentRow.get("TemperatureF"));
                contador++;
            }
        }
        
        if(contador != 0){
        averageTemperatureHighHumidity = averageTemperatureHighHumidity / contador;
        }
        return averageTemperatureHighHumidity;
    
    }
    
    public void testAverageTemperatureWithHighHumidityInFile(){
    
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        int i = 80;
        double average = averageTemperatureWithHighHumidityInFile(parser, i);
        if(average == 0.0){
            
            System.out.println("No temperatures with that humidity");
        
        }else{
        
            System.out.println("Average temp when high Humidity is " + average);
        }
        
    }
}

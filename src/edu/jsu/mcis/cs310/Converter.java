package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.opencsv.*;
import java.io.*;
import java.util.*;

public class Converter {
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String result = "{}"; // default return value; replace later!
        
        try {
        
            // INSERT YOUR CODE HERE
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> csvData = reader.readAll();
            reader.close();
            
            JsonArray prodNums   = new JsonArray();
            JsonArray colHeadings = new JsonArray();
            JsonArray data       = new JsonArray();
            
            String[] headers = csvData.get(0);
            for(String header : headers){
                colHeadings.add(header);
            }
            
            for (int i = 1; i < csvData.size(); i++){
                String[] row = csvData.get(i);
                prodNums.add(row[0]);
                
                JsonArray dataRow = new JsonArray();
                dataRow.add(row[1]);
                dataRow.add(Integer.parseInt(row[2]));
                dataRow.add(Integer.parseInt(row[3]));
                dataRow.add(row[4]);
                dataRow.add(row[5]);
                dataRow.add(row[6]);
                
                data.add(dataRow);
            }
            
            JsonObject json = new JsonObject();
            json.put("ProdNums", prodNums);
            json.put("ColHeadings", colHeadings);
            json.put("Data", data);
            
            result = Jsoner.serialize(json);
                        
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        
        String result = ""; // default return value; replace later!
        
        try {
            
            
            JsonObject json = (JsonObject) Jsoner.deserialize(jsonString, new JsonObject());
            
            JsonArray prodNums    = (JsonArray) json.get("ProdNums");
            JsonArray colHeadings = (JsonArray) json.get("ColHeadings");
            JsonArray data        = (JsonArray) json.get("Data");
            
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer);
            
            String[] headers = new String[colHeadings.size()];
            for (int i = 0; i < colHeadings.size(); i++){
                headers[i] = colHeadings.get(i).toString();
            }
            csvWriter.writeNext(headers);
            
            
            for (int i = 0; i < data.size(); i++){
                JsonArray dataRow = (JsonArray) data.get(i);
                String[] csvRow = new String[headers.length];
                csvRow[0] = prodNums.get(i).toString();
                csvRow[1] = dataRow.get(0).toString();
                csvRow[2] = dataRow.get(1).toString();
                int episode = ((Number)dataRow.get(2)).intValue();
                csvRow[3] = String.format("%02d", episode);
                csvRow[4] = dataRow.get(3).toString();
                csvRow[5] = dataRow.get(4).toString();
                csvRow[6] = dataRow.get(5).toString();
                
                csvWriter.writeNext(csvRow);
            }
            
            csvWriter.close();
            result = writer.toString();
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
        
    }
    
}

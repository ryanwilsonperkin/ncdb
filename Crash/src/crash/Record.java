/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryan
 */
public class Record {
    Collision collision;
    Person person;
    Vehicle vehicle;
    
    public Record(String data) {
        this.collision = new Collision(data);
        this.person = new Person(data);
        this.vehicle = new Vehicle(data);
    }

    public static int dataParse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public static List<Record> loadFile(String file_name) 
            throws FileNotFoundException, IOException {
        List<Record> records = new ArrayList<Record>();
        BufferedReader in = new BufferedReader(new FileReader(file_name));
        String line;
        
        // Consume and dispose of first line (column headers).
        in.readLine();
        
        // Read all lines into new Records.
        while ((line = in.readLine()) != null) {
            records.add(new Record(line));
        }
        return records;
    }
}

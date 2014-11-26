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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ryan
 */
public class Record {
    
    Collision collision;
    Person person;
    Vehicle vehicle;
    
    public Record(String data) {
        
        String[] parts = data.split(",");
        int c_year = parseField(parts[0]);
        int c_month = parseField(parts[1]);
        int c_weekday = parseField(parts[2]);
        int c_hour = parseField(parts[3]);
        int c_severity = parseField(parts[4]);
        int c_vehicles = parseField(parts[5]);
        int c_config = parseField(parts[6]);
        int c_road_config = parseField(parts[7]);
        int c_weather = parseField(parts[8]);
        int c_road_surface = parseField(parts[9]);
        int c_road_alignment = parseField(parts[10]);
        int c_traffic_control = parseField(parts[11]);
        int v_id = parseField(parts[12]);
        int v_type = parseField(parts[13]);
        int v_year = parseField(parts[14]);
        int p_id = parseField(parts[15]);
        char p_sex = parts[16].charAt(0);
        int p_age = parseField(parts[17]);
        int p_position = parseField(parts[18]);
        int p_treatment = parseField(parts[19]);
        int p_safe = parseField(parts[20]);
        int p_user = parseField(parts[21]);
        this.collision = new Collision(c_year, c_month, c_weekday, c_hour,
                c_severity, c_vehicles, c_config, c_road_config, c_weather,
                c_road_surface, c_road_alignment, c_traffic_control);
        this.vehicle = new Vehicle(v_id, v_type, v_year);
        this.person = new Person(p_id, p_sex, p_age, p_position, p_treatment, 
                p_safe, p_user);
    }
        
    public static List<Record> filterDuplicateCollisions(List<Record> records) {
        List<Record> result = new ArrayList<>();
        Set<Collision> unique = new HashSet<>();
        for (Record r : records) {
            if (unique.add(r.collision)) {
                result.add(r);
            }
        }
        return result;
    }
    
    public static List<Record> filterDuplicateVehicles(List<Record> records) {
        List<Record> result = new ArrayList<>();
        Set<Pair<Collision,Vehicle>> unique = new HashSet<>();
        for (Record r : records) {
            if (unique.add(new Pair<>(r.collision,r.vehicle))) {
                result.add(r);
            }
        }
        return result;
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
    
    private static int parseField(String s) {
        List<String> options = Arrays.asList("N","Q","U","X");
        for (int len = options.size(), i = 0; i < len; i++) {
            if (s.contains(options.get(i))) return (-1)*(i+1);
        }
        return Integer.parseInt(s);
    }
    
    @Override
    public String toString() {
        return collision.toString() + "," + 
               vehicle.toString() + "," + 
               person.toString();
    }
    
    private static class Pair<Collision, Vehicle> {
        final Collision collision;
        final Vehicle vehicle;
        
        Pair(Collision collision, Vehicle vehicle) {
            this.collision = collision;
            this.vehicle = vehicle;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + this.collision.hashCode();
            hash = 97 * hash + this.vehicle.hashCode();
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            Pair<Collision, Vehicle> other = (Pair<Collision, Vehicle>) o;
            return this.collision.equals(other.collision) &&
                    this.vehicle.equals(other.vehicle);
        }
    }
}

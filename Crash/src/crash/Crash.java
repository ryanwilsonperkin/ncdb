/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crash;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author ryan
 */
public class Crash {
    
    public static final String output_filename = "crash.txt";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
            throws FileNotFoundException, IOException {
        if (args.length < 3) {
            System.out.println("usage: ./crash file threads queries...");
            System.exit(1);
        }
        
        String filename = args[0];
        int n_threads = Integer.parseInt(args[1]);
        PrintWriter output = new PrintWriter(output_filename, "UTF-8");
        
        List<Record> records = Record.loadFile(filename);
        List<Record> uniqueCollisions = Record.filterDuplicateCollisions(records);
        List<Record> uniqueVehicles = Record.filterDuplicateVehicles(records);
        System.out.println(records.size() + " records");
        System.out.println(uniqueCollisions.size() + " collisions");
        System.out.println(uniqueVehicles.size() + " vehicles");
        
        ForkJoinPool thread_pool = new ForkJoinPool(n_threads);
        
        String result = "";
        for (int i = 2; i < args.length; i++) {
            switch(args[i]) {
                case "1": 
                    Query1 q1 = new Query1(records, thread_pool, 100);
                    result = q1.result();
                    System.out.print(result);
                    output.print(result);
                    break;
                case "2":
                    Query2 q2 = new Query2(records, thread_pool, 100);
                    result = q2.result();
                    System.out.println(result);
                    output.println(result);
                    break;
                case "3":
                    Query3 q3 = new Query3(records, thread_pool, 100);
                    result = q3.result();
                    System.out.println(result);
                    output.println(result);
                    break;
                case "4":
                    Query4 q4 = new Query4(uniqueVehicles, thread_pool, 100);
                    result = q4.result();
                    System.out.println(result);
                    output.println(result);
                    break;
                case "5":
                    Query5 q5 = new Query5(uniqueCollisions, thread_pool, 100);
                    result = q5.result();
                    System.out.println(result);
                    output.println(result);
                    break;
                default:
                    System.err.println("error: Invalid entry");
                    System.exit(1);
                    break;
            }
        }
        output.close();
    }
}

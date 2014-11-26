/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crash;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author ryan
 */
public class Crash {
    
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
        List<Record> records = Record.loadFile(filename);
        List<Record> uniqueCollisions = Record.filterDuplicateCollisions(records);
        List<Record> uniqueVehicles = Record.filterDuplicateVehicles(records);
        System.out.println(records.size() + " records");
        System.out.println(uniqueCollisions.size() + " collisions");
        System.out.println(uniqueVehicles.size() + " vehicles");
        
        ForkJoinPool thread_pool = new ForkJoinPool(n_threads);
        
        for (int i = 2; i < args.length; i++) {
            switch(args[i]) {
                case "1":
                    System.out.println("$Q1");
                    break;
                case "2":
                    Query2 q2 = new Query2(records, thread_pool, 100);
                    System.out.println(q2.result());
                    break;
                case "3":
                    Query3 q3 = new Query3(records, thread_pool, 100);
                    System.out.println(q3.result());
                    break;
                case "4":
                    System.out.println("$Q4");
                    break;
                case "5":
                    Query5 q5 = new Query5(uniqueCollisions, thread_pool, 100);
                    System.out.println(q5.result());
                    break;
                default:
                    break;
            }
        }
    }
}

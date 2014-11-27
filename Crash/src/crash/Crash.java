/*
 * Crash.java
 *  Main function of the crash program.
 *  Runs queries on NCDB data.
 *
 *  usage: ./crash filename threads queries...
 *
 *  Ryan Wilson-Perkin (0719644)
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
        long program_runtime, program_start = System.currentTimeMillis();
        long query_runtime, query_start;
        
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
        
        program_runtime = System.currentTimeMillis() - program_start;
        System.out.println(String.format("$T0,%d,%.1f", n_threads, program_runtime / 1000.0));
        output.println(String.format("$T0,%d,%.1f", n_threads, program_runtime / 1000.0));
        
        System.out.println(records.size() + " records");
        System.out.println(uniqueCollisions.size() + " collisions");
        
        ForkJoinPool thread_pool = new ForkJoinPool(n_threads);
        
        String result;
        for (int i = 2; i < args.length; i++) {
            query_start = System.currentTimeMillis();
            switch(args[i]) {
                case "1": 
                    Query1 q1 = new Query1(records, thread_pool, 100);
                    result = q1.result();
                    System.out.print(result);
                    output.print(result);
                    query_runtime = System.currentTimeMillis() - query_start;
                    System.out.println(String.format("$T1,%d,%.1f", n_threads, query_runtime / 1000.0));
                    output.println(String.format("$T1,%d,%.1f", n_threads, query_runtime / 1000.0));
                    break;
                case "2":
                    Query2 q2 = new Query2(records, thread_pool, 100);
                    result = q2.result();
                    System.out.println(result);
                    output.println(result);
                    query_runtime = System.currentTimeMillis() - query_start;
                    System.out.println(String.format("$T2,%d,%.1f", n_threads, query_runtime / 1000.0));
                    output.println(String.format("$T2,%d,%.1f", n_threads, query_runtime / 1000.0));
                    break;
                case "3":
                    Query3 q3 = new Query3(records, thread_pool, 100);
                    result = q3.result();
                    System.out.println(result);
                    output.println(result);
                    query_runtime = System.currentTimeMillis() - query_start;
                    System.out.println(String.format("$T3,%d,%.1f", n_threads, query_runtime / 1000.0));
                    output.println(String.format("$T3,%d,%.1f", n_threads, query_runtime / 1000.0));
                    break;
                case "4":
                    Query4 q4 = new Query4(uniqueVehicles, thread_pool, 100);
                    result = q4.result();
                    System.out.println(result);
                    output.println(result);
                    query_runtime = System.currentTimeMillis() - query_start;
                    System.out.println(String.format("$T4,%d,%.1f", n_threads, query_runtime / 1000.0));
                    output.println(String.format("$T4,%d,%.1f", n_threads, query_runtime / 1000.0));
                    break;
                case "5":
                    Query5 q5 = new Query5(uniqueCollisions, thread_pool, 100);
                    result = q5.result();
                    System.out.println(result);
                    output.println(result);
                    query_runtime = System.currentTimeMillis() - query_start;
                    System.out.println(String.format("$T5,%d,%.1f", n_threads, query_runtime / 1000.0));
                    output.println(String.format("$T5,%d,%.1f", n_threads, query_runtime / 1000.0));
                    break;
                default:
                    System.err.println("error: Invalid entry");
                    System.exit(1);
                    break;
            }
        }
        program_runtime = System.currentTimeMillis() - program_start;
        System.out.println(String.format("$T9,%d,%.1f", n_threads, program_runtime / 1000.0));
        output.println(String.format("$T9,%d,%.1f", n_threads, program_runtime / 1000.0));
        output.close();
    }
}

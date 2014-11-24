/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crash;

import java.util.List;

/**
 *
 * @author ryan
 */
public class Crash {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("usage: ./crash file threads queries...");
            System.exit(1);
        }
        List<Record> records;
        String filename = args[0];
        int n_threads = Integer.parseInt(args[1]);
        
        try {
            records = Record.loadFile(filename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        for (int i = 2; i < args.length; i++) {
            switch(args[i]) {
                case "1":
                    System.out.println("$Q1");
                    break;
                case "2":
                    System.out.println("$Q2");
                    break;
                case "3":
                    System.out.println("$Q3");
                    break;
                case "4":
                    System.out.println("$Q4");
                    break;
                case "5":
                    System.out.println("$Q5");
                    break;
                default:
                    break;
            }
        }
    }
}

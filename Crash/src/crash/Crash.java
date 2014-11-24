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
        try {
            records = Record.loadFile(filename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

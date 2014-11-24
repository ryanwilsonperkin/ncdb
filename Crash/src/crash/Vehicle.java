/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crash;

import static crash.Record.dataParse;

/**
 *
 * @author ryan
 */
public class Vehicle {
    int id;
    int type;
    int year;
    
    public Vehicle(String data) {
        this.id = dataParse(data.substring(33,35));
        this.type = dataParse(data.substring(36,38));
        this.year = dataParse(data.substring(39,43));
    }
}

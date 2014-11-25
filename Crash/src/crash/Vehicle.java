/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crash;

/**
 *
 * @author ryan
 */
public class Vehicle {
    int id;
    int type;
    int year;
    
    public Vehicle(int id_, int type_, int year_) {
        this.id = id_;
        this.type = type_;
        this.year = year_;
    }
    
    @Override
    public String toString() {
        return id + "," +
                type + "," +
                year;
    }
}

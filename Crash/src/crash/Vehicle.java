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
    public boolean equals(Object o) {
        Vehicle other = (Vehicle) o;
        return this.id == other.id &&
                this.type == other.type &&
                this.year == other.year;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + this.type;
        hash = 89 * hash + this.year;
        return hash;
    }
    
    @Override
    public String toString() {
        return id + "," +
                type + "," +
                year;
    }
}

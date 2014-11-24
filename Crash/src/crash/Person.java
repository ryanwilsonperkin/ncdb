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
public class Person {
    int id;
    int sex;
    int age;
    int position;
    int treatment;
    int safe;
    int user;
    
    public Person(String data) {
        this.id = dataParse(data.substring(44,46));
        this.sex = dataParse(data.substring(47,48));
        this.age = dataParse(data.substring(49,51));
        this.position = dataParse(data.substring(52,54));
        this.treatment = dataParse(data.substring(55,56));
        this.safe = dataParse(data.substring(57,59));
        this.user = dataParse(data.substring(60,61));
    }
}

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
public class Collision {
    int year;
    int month;
    int weekday;
    int hour;
    int severity;
    int vehicles;
    int config;
    int road_config;
    int weather;
    int road_surface;
    int road_alignment;
    int traffic_control;
    
    public Collision(String data) {
        this.year = dataParse(data.substring(0,4));
        this.month = dataParse(data.substring(5,7));
        this.weekday = dataParse(data.substring(8,9));
        this.hour = dataParse(data.substring(10,12));
        this.severity = dataParse(data.substring(13,14));
        this.vehicles = dataParse(data.substring(15,17));
        this.config = dataParse(data.substring(18,20));
        this.road_config = dataParse(data.substring(21,23));
        this.weather = dataParse(data.substring(24,25));
        this.road_surface = dataParse(data.substring(26,27));
        this.road_alignment = dataParse(data.substring(28,29));
        this.traffic_control = dataParse(data.substring(30,32));
    }
}

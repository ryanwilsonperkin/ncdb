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
public class Record {
    // Collision level data.
    int collision_year;
    int collision_month;
    int collision_weekday;
    int collision_hour;
    int collision_severity;
    int collision_vehicles;
    int collision_config;
    int collision_road_config;
    int collision_weather;
    int collision_road_surface;
    int collision_road_alignment;
    int collision_traffic_control;
    
    // Vehicle level data.
    int vehicle_id;
    int vehicle_type;
    int vehicle_year;
    
    // Person level data.
    int person_id;
    int person_sex;
    int person_age;
    int person_position;
    int person_treatment;
    int person_safe;
    int person_user;
    
    public Record(String data) {
        this.collision_year = dataParse(data.substring(0,4));
        this.collision_month = dataParse(data.substring(5,7));
        this.collision_weekday = dataParse(data.substring(8,9));
        this.collision_hour = dataParse(data.substring(10,12));
        this.collision_severity = dataParse(data.substring(13,14));
        this.collision_vehicles = dataParse(data.substring(15,17));
        this.collision_config = dataParse(data.substring(18,20));
        this.collision_road_config = dataParse(data.substring(21,23));
        this.collision_weather = dataParse(data.substring(24,25));
        this.collision_road_surface = dataParse(data.substring(26,27));
        this.collision_road_alignment = dataParse(data.substring(28,29));
        this.collision_traffic_control = dataParse(data.substring(30,32));
        this.vehicle_id = dataParse(data.substring(33,35));
        this.vehicle_type = dataParse(data.substring(36,38));
        this.vehicle_year = dataParse(data.substring(39,43));
        this.person_id = dataParse(data.substring(44,46));
        this.person_sex = dataParse(data.substring(47,48));
        this.person_age = dataParse(data.substring(49,51));
        this.person_position = dataParse(data.substring(52,54));
        this.person_treatment = dataParse(data.substring(55,56));
        this.person_safe = dataParse(data.substring(57,59));
        this.person_user = dataParse(data.substring(60,61));
    }

    public static int dataParse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

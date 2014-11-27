/*
 * Collision.java
 *  Representation of a collision from the NCDB data file.
 *
 *  Ryan Wilson-Perkin (0719644)
 */
package crash;

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
    
    public Collision(int year_, int month_, int weekday_, int hour_, 
            int severity_, int vehicles_, int config_, int road_config_, 
            int weather_, int road_surface_, int road_alignment_, 
            int traffic_control_) {
        this.year = year_;
        this.month = month_;
        this.weekday = weekday_;
        this.hour = hour_;
        this.severity = severity_;
        this.vehicles = vehicles_;
        this.config = config_;
        this.road_config = road_config_;
        this.weather = weather_;
        this.road_surface = road_surface_;
        this.road_alignment = road_alignment_;
        this.traffic_control = traffic_control_;
    }
    
    @Override
    public boolean equals(Object o) {
        Collision other = (Collision) o;
        return (year == other.year &&
                month == other.month &&
                weekday == other.weekday &&
                hour == other.hour &&
                severity == other.severity &&
                vehicles == other.vehicles &&
                config == other.config &&
                road_config == other.road_config &&
                weather == other.weather &&
                road_surface == other.road_surface &&
                road_alignment == other.road_alignment &&
                traffic_control == other.traffic_control);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.year;
        hash = 97 * hash + this.month;
        hash = 97 * hash + this.weekday;
        hash = 97 * hash + this.hour;
        hash = 97 * hash + this.severity;
        hash = 97 * hash + this.vehicles;
        hash = 97 * hash + this.config;
        hash = 97 * hash + this.road_config;
        hash = 97 * hash + this.weather;
        hash = 97 * hash + this.road_surface;
        hash = 97 * hash + this.road_alignment;
        hash = 97 * hash + this.traffic_control;
        return hash;
    }

    @Override
    public String toString() {
        return year + "," + 
                month + "," + 
                weekday + "," + 
                hour + "," + 
                severity + "," + 
                vehicles + "," + 
                config + "," + 
                road_config + "," + 
                weather + "," + 
                road_surface + "," + 
                road_alignment + "," + 
                traffic_control;
    }
}

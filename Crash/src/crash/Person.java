/*
 * Person.java
 *  Representation of a person from the NCDB data file.
 *
 *  Ryan Wilson-Perkin (0719644)
 */
package crash;

/**
 *
 * @author ryan
 */
public class Person {
    int id;
    char sex;
    int age;
    int position;
    int treatment;
    int safe;
    int user;
    
    public Person(int id_, char sex_, int age_, int position_, int treatment_,
            int safe_, int user_) {
        this.id = id_;
        this.sex = sex_;
        this.age = age_;
        this.position = position_;
        this.treatment = treatment_;
        this.safe = safe_;
        this.user = user_;
    }
    
    @Override
    public String toString() {
        return id + "," +
                sex + "," +
                age + "," +
                position + "," +
                treatment + "," +
                safe + "," +
                user;
    }        
}

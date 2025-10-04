// 代码生成时间: 2025-10-05 01:35:21
package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

// Define a simple data model
@Entity
public class DataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private int age;
    
    // Constructor
    public DataModel() {
    }
    
    // Parameterized constructor
    public DataModel(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    // toString method for easy debugging
    @Override
    public String toString() {
        return "DataModel{"id":"" + id + "", "name":"" + name + "", "age":"" + age + ""}";
    }
}

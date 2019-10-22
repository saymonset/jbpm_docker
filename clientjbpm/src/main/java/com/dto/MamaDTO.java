package com.dto;



/**
 * Created by simon on 15/10/19.
 */
public class MamaDTO {
    private java.lang.Long id;
    private java.lang.String name;

    public MamaDTO() {
    }
   /* public MamaDTO(Mama mama) {
        this.id = mama.getId();
        this.name = mama.getName();
    }*/
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
}

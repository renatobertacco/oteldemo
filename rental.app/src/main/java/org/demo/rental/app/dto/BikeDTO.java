package org.demo.rental.app.dto;


public class BikeDTO {

    private long id;
    private String model;
    private String size;
    private int rate;
    private Long rentedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Long getRentedBy() {
        return rentedBy;
    }

    public void setRentedBy(Long rentedBy) {
        this.rentedBy = rentedBy;
    }

}

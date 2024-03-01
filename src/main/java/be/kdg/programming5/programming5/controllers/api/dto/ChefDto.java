package be.kdg.programming5.programming5.controllers.api.dto;

import be.kdg.programming5.programming5.domain.Restaurant;

import java.time.LocalDate;

public class ChefDto {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Restaurant restaurant;

    public ChefDto() {
    }

    public ChefDto(int id, String firstName, String lastName, LocalDate dateOfBirth, Restaurant restaurant) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.restaurant = restaurant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}

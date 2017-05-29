package com.sebagarcia.tennis.Clases;


/**
 * Created by cristian on 5/29/17.
 */

public class Player {

    private int id;
    private String name;
    private String email;
    private String phone;
    private int Score;

    //Constructor
    public Player(int id, String name, String email, String phone, int score) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        Score = score;
    }



    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}

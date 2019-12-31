package fr.istic.mob.starldv2.model;

public class Stop {

    private int id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private int wheelchairBoarding;

    public Stop (int id, String name, String description, double latitude, double longitude, int wheelchairBoarding) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.wheelchairBoarding = wheelchairBoarding;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getWheelchairBoarding() {
        return wheelchairBoarding;
    }
}

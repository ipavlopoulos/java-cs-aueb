package gr.aueb.cs.projects.walkroute.agentstack;

/**
 * Represents a real-world geographic location with
 * latitude, longitude, height, and address metadata.
 */
public class Location {
    public double latitude;       // Latitude (e.g. 37.9838)
    public double longitude;      // Longitude (e.g. 23.7329)
    public double height;         // Height above sea level (meters)
    public String street;         // Street name
    public String number;         // Street number
    public String postalCode;     // Postal code

    /**
     * Constructs a location with GPS coordinates and address.
     *
     * @param latitude GPS latitude
     * @param longitude GPS longitude
     * @param height elevation in meters
     * @param street street name
     * @param number street number
     * @param postalCode postal code
     */
    public Location(double latitude, double longitude, double height,
                    String street, String number, String postalCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.height = height;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
    }

    /**
     * Returns a human-readable representation of the location.
     */
    @Override
    public String toString() {
        return street + " " + number + ", ZIP " + postalCode +
                " [lat: " + latitude + ", lon: " + longitude + ", h: " + height + "m]";
    }

    /**
     * Checks equality based on coordinates (latitude, longitude, height).
     */
    public boolean equals(Location other) {
        return Double.compare(latitude, other.latitude) == 0 &&
                Double.compare(longitude, other.longitude) == 0 &&
                Double.compare(height, other.height) == 0;
    }

}

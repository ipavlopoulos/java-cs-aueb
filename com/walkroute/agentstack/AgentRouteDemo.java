package com.walkroute.agentstack;

public class AgentRouteDemo {
    public static void main(String[] args) {
        // Define a sample path manually
        Location[] path = {
                new Location(37.9870, 23.7314, 95, "Patision", "76", "10434"),
                new Location(37.9875, 23.7320, 95, "Stournari", "20", "10434"),
                new Location(37.9872, 23.7335, 94, "Marni", "12", "10433"),
                new Location(37.9866, 23.7322, 94, "3is Septemvriou", "6", "10432")
        };

        Agent agent = new Agent(path[0]);  // start at first location
        double totalDistance = 0;

        System.out.println("🚶 Agent starts at: " + agent.getCurrentLocation());

        for (int i = 1; i < path.length; i++) {
            Location from = agent.getCurrentLocation();
            Location to = path[i];

            double dist = haversine(from, to);
            agent.moveTo(to);
            totalDistance += dist;

            System.out.printf("→ Moved to: %s (%.2f m from previous)\n", to, dist);
        }

        System.out.printf("\n📏 Total distance walked: %.2f meters\n", totalDistance);

        // Undo last 2 moves
        System.out.println("\n⏪ Undoing last 2 steps...");
        agent.undo();
        System.out.println("↩️ Now at: " + agent.getCurrentLocation());

        agent.undo();
        System.out.println("↩️ Now at: " + agent.getCurrentLocation());
    }

    /**
     * Calculates the great-circle distance between two locations on Earth
     * using the Haversine formula.
     *
     * The formula accounts for the curvature of the Earth and returns
     * the distance in meters between two points specified by their
     * latitude and longitude.
     *
     * @param a the first location (with latitude and longitude in degrees)
     * @param b the second location
     * @return the distance between the two locations, in meters
     */
    public static double haversine(Location a, Location b) {
        final double R = 6371e3; // Earth's radius in meters

        // Convert latitude and longitude differences to radians
        double dLat = Math.toRadians(b.latitude - a.latitude);
        double dLon = Math.toRadians(b.longitude - a.longitude);

        // Convert individual latitudes to radians
        double lat1 = Math.toRadians(a.latitude);
        double lat2 = Math.toRadians(b.latitude);

        // Apply the Haversine formula
        double h = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));

        // Final distance in meters
        return R * c;
    }

}

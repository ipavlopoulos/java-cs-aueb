package com.walkroute.search;

import com.walkroute.agentstack.Location;

import java.util.*;

/**
 * BFS route finding between real Athens locations,
 * with obstacle avoidance, path recording, and distance calculation.
 */
public class BFS {
    public static void main(String[] args) {
        // Start and goal locations (realistic)
        Location start = new Location(37.9870, 23.7314, 95, "Patision", "76", "10434");
        Location goal = new Location(37.9875, 23.7335, 96, "Stournari", "20", "10434"); // nearby

        // Hardcoded obstacles
        List<Location> obstacles = new ArrayList<>();
        obstacles.add(new Location(37.9873, 23.7324, 95, "Tositsa", "5", "10682"));

        // BFS structures
        Queue<Location> queue = new LinkedList<>();
        List<Location> visited = new ArrayList<>();
        Map<Location, Location> cameFrom = new HashMap<>();

        queue.add(start);
        visited.add(start);

        boolean found = false;
        Location current = null;

        while (!queue.isEmpty()) {
            current = queue.poll();

            if (haversine(current, goal) < 30) {  // within 30 meters
                found = true;
                break;
            }

            // Create nearby points (small offsets)
            double[] latOffsets = {-0.0005, 0, 0.0005};
            double[] lonOffsets = {-0.0005, 0, 0.0005};
            double[] hOffsets = {0};

            for (double dLat : latOffsets) {
                for (double dLon : lonOffsets) {
                    for (double dH : hOffsets) {
                        if (dLat == 0 && dLon == 0) continue;

                        double newLat = current.latitude + dLat;
                        double newLon = current.longitude + dLon;
                        double newH = current.height + dH;

                        Location neighbor = new Location(
                                newLat, newLon, newH,
                                "Street", "-", "11111"
                        );

                        boolean visitedBefore = visited.stream().anyMatch(l -> l.equals(neighbor));
                        boolean isObstacle = obstacles.stream().anyMatch(o -> o.equals(neighbor));

                        if (!visitedBefore && !isObstacle) {
                            queue.add(neighbor);
                            visited.add(neighbor);
                            cameFrom.put(neighbor, current);
                        }
                    }
                }
            }
        }

        if (found) {
            // Reconstruct path
            List<Location> path = new ArrayList<>();
            path.add(goal); // actual goal, not current

            while (!current.equals(start)) {
                path.add(current);
                current = cameFrom.get(current);
            }
            path.add(start);
            Collections.reverse(path);

            System.out.println("\n🎯 Route found:");
            double total = 0;
            for (int i = 0; i < path.size(); i++) {
                System.out.println("→ " + path.get(i));
                if (i > 0) {
                    total += haversine(path.get(i - 1), path.get(i));
                }
            }
            System.out.printf("\n📏 Total distance: %.2f meters\n", total);
            // Export path to GeoJSON
            exportPathToGeoJSON(path, "walkroute_path.geojson");
            System.out.println("\n🗺️ Path exported to walkroute_path.geojson (open at geojson.io)");

        } else {
            System.out.println("⚠️ No route found.");
        }

        System.out.println("Search complete.");
    }

    /**
     * Calculates distance in meters between two geo-locations.
     */
    public static double haversine(Location a, Location b) {
        final double R = 6371e3; // Earth radius in meters
        double dLat = Math.toRadians(b.latitude - a.latitude);
        double dLon = Math.toRadians(b.longitude - a.longitude);
        double lat1 = Math.toRadians(a.latitude);
        double lat2 = Math.toRadians(b.latitude);

        double h = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));
        return R * c;
    }

    /**
     * Exports a path (list of Locations) to a GeoJSON LineString file.
     */
    public static void exportPathToGeoJSON(List<Location> path, String filename) {
        StringBuilder geojson = new StringBuilder();
        geojson.append("{\n");
        geojson.append("  \"type\": \"FeatureCollection\",\n");
        geojson.append("  \"features\": [\n");
        geojson.append("    {\n");
        geojson.append("      \"type\": \"Feature\",\n");
        geojson.append("      \"geometry\": {\n");
        geojson.append("        \"type\": \"LineString\",\n");
        geojson.append("        \"coordinates\": [\n");

        for (int i = 0; i < path.size(); i++) {
            Location loc = path.get(i);
            geojson.append("          [").append(loc.longitude).append(", ").append(loc.latitude).append("]");
            if (i < path.size() - 1) geojson.append(",");
            geojson.append("\n");
        }

        geojson.append("        ]\n");
        geojson.append("      },\n");
        geojson.append("      \"properties\": {\n");
        geojson.append("        \"name\": \"walkroute path\"\n");
        geojson.append("      }\n");
        geojson.append("    }\n");
        geojson.append("  ]\n");
        geojson.append("}\n");

        try (java.io.FileWriter writer = new java.io.FileWriter(filename)) {
            writer.write(geojson.toString());
        } catch (Exception e) {
            System.err.println("Error writing GeoJSON: " + e.getMessage());
        }
    }

}


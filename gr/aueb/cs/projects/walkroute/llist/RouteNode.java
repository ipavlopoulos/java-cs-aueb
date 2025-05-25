package gr.aueb.cs.projects.walkroute.llist;

public class RouteNode {
    String location;
    RouteNode next;

    public RouteNode(String location) {
        this.location = location;
        this.next = null;
    }

    public String toString() {
        return "My location is " + location;
    }
}

package com.walkroute.llist;

public class RouteNode {
    private String location;
    private RouteNode next;

    public RouteNode(String location) {
        this.location = location;
        this.next = null;
    }


    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }


    public void setNext(RouteNode next){
        this.next = next;
    }

    public RouteNode getNext(){
        return next;
    }

    public String toString() {
        return "My location is " + location;
    }
}

package com.company;

public class Main {

    public static void main(String[] args) {
        Graph a = new Graph();
        a.addNode();
        a.addNode();
        a.addNode();
        a.addNode();
        a.addNode();
        a.addConnection(0, 1,10);
        a.addConnection(0, 3,30);
        a.addConnection(0, 4,100);
        a.addConnection(1, 2,50);
        a.addConnection(2, 4,10);
        a.addConnection(3, 2,20);
        a.addConnection(3, 4,60);


        a.findMin(0, 4, true);
        a.allPath(0, 4);
        a.centre();
    }
}

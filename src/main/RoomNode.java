package main;

import java.io.*;
import java.util.*;

public class RoomNode {
    // This class must account for these properties:
        // Type of floor.
        // Is this an obstacle?
        // Surrounding room nodes.
        // Coordinates relative to starting point.
        // And eventually, which sides have walls.
    private FloorType floor;
    private boolean obstacle;
    private RoomNode node_N; // North
    private RoomNode node_S; // South
    private RoomNode node_E; // East
    private RoomNode node_W; // West
    private Vector2 position;

    public RoomNode(FloorType to_floor, boolean to_obstacle, Vector2 to_position) {
        floor = to_floor;
        obstacle = to_obstacle;
        node_N = null;
        node_S = null;
        node_E = null;
        node_W = null;
        position = to_position;
    }

    public FloorType get_floor() {
        return floor;
    }

    public void set_floor(FloorType floor) {
        this.floor = floor;
    }

    public boolean is_obstacle() {
        return obstacle;
    }

    public void set_obstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    public RoomNode get_node_N() {
        return node_N;
    }

    public void set_node_N(RoomNode node_N) {
        this.node_N = node_N;
    }

    public RoomNode get_node_S() {
        return node_S;
    }

    public void set_node_S(RoomNode node_S) {
        this.node_S = node_S;
    }

    public RoomNode get_node_E() {
        return node_E;
    }

    public void set_node_E(RoomNode node_E) {
        this.node_E = node_E;
    }

    public RoomNode get_node_W() {
        return node_W;
    }

    public void set_node_W(RoomNode node_W) {
        this.node_W = node_W;
    }

    public Vector2 get_position() {
        return position;
    }

    public void set_position(Vector2 position) {
        this.position = position;
    }

}

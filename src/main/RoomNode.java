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
    private ArrayList<RoomNode> adj_list;

    public RoomNode(FloorType to_floor, boolean to_obstacle, Vector2 to_position) {
        this.floor = to_floor;
        this.obstacle = to_obstacle;
        this.node_N = null;
        this.node_S = null;
        this.node_E = null;
        this.node_W = null;
        this.position = to_position;
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

    public RoomNode get_node(Direction dir) {
        switch(dir) {
            case NORTH:
                return node_N;
            case SOUTH:
                return node_S;
            case EAST:
                return node_E;
            case WEST:
                return node_W;
        } return null;
    }

    public void set_node(Direction dir, RoomNode node) {
        switch(dir) {
            case NORTH:
                this.node_N = node;
                adj_list_refresh();
                return;
            case SOUTH:
                this.node_S = node;
                adj_list_refresh();
                return;
            case EAST:
                this.node_E = node;
                adj_list_refresh();
                return;
            case WEST:
                this.node_W = node;
                adj_list_refresh();
                return;
        }
    }

    public ArrayList<RoomNode> get_adj_list() {
        return adj_list;
    }

    public void adj_list_refresh() {
        ArrayList<RoomNode> new_adj_list = new ArrayList<RoomNode>();
        if (this.node_N != null && !this.node_N.is_obstacle())
            new_adj_list.add(this.node_N);
        if (this.node_S != null && !this.node_S.is_obstacle())
            new_adj_list.add(this.node_S);
        if (this.node_E != null && !this.node_E.is_obstacle())
            new_adj_list.add(this.node_E);
        if (this.node_W != null && !this.node_W.is_obstacle())
            new_adj_list.add(this.node_W);
        this.adj_list = new_adj_list;
    }

    public Vector2 get_position() {
        return position;
    }

    public void set_position(Vector2 position) {
        this.position = position;
    }

}

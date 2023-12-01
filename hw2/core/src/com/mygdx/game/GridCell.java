package com.mygdx.game;

public class GridCell {

    private int weight;
    private GridCell north;
    private GridCell south;
    private GridCell east;
    private GridCell west;

    public GridCell(int weight) {
        this.weight = weight;
    }

    public GridCell getNorth() {
        return north;
    }

    public GridCell getSouth() {
        return south;
    }

    public GridCell getEast() {
        return east;
    }

    public GridCell getWest() {
        return west;
    }

    public void setNorth(GridCell north) {
        this.north = north;
    }

    public void setSouth(GridCell south) {
        this.south = south;
    }

    public void setEast(GridCell east) {
        this.east = east;
    }

    public void setWest(GridCell west) {
        this.west = west;
    }

}

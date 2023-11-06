package com.animal.lifesimulation.map;

public class SectionCoordinates {
    protected int coordinateX;
    protected int coordinateY;

    protected int maxMoveLeft;
    protected int maxMoveRight;
    protected int maxMoveUp;
    protected int maxMoveDown;

    public int getX() {
        return coordinateX;
    }

    public void setX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getY() {
        return coordinateY;
    }

    public void setY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getMaxMoveLeft() {
        return maxMoveLeft;
    }

    public void setMaxMoveLeft(int maxMoveLeft) {
        this.maxMoveLeft = maxMoveLeft;
    }

    public int getMaxMoveRight() {
        return maxMoveRight;
    }

    public void setMaxMoveRight(int maxMoveRight) {
        this.maxMoveRight = maxMoveRight;
    }

    public int getMaxMoveUp() {
        return maxMoveUp;
    }

    public void setMaxMoveUp(int maxMoveUp) {
        this.maxMoveUp = maxMoveUp;
    }

    public int getMaxMoveDown() {
        return maxMoveDown;
    }

    public void setMaxMoveDown(int maxMoveDown) {
        this.maxMoveDown = maxMoveDown;
    }
}

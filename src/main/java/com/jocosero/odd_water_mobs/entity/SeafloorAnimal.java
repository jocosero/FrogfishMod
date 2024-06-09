package com.jocosero.odd_water_mobs.entity;

public interface SeafloorAnimal {

    boolean shouldEnterWater();

    boolean shouldLeaveWater();

    boolean shouldStopMoving();

    int getWaterSearchRange();
}

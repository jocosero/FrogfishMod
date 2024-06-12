package com.jocosero.odd_water_mobs.entity.custom.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum FrogfishVariant {
    WHITE(0),
    ORANGE(1),
    YELLOW(2);

    private static final FrogfishVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(FrogfishVariant::getId)).toArray(FrogfishVariant[]::new);
    private final int id;

    FrogfishVariant(int id) { this.id = id;}

    public static FrogfishVariant byId(int id) { return BY_ID[id % BY_ID.length]; }

    public int getId() { return this.id; }
}

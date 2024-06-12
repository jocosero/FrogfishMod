package com.jocosero.odd_water_mobs.entity.custom.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum AnglerfishVariant {
    DEFAULT(0),
    DEFAULT_PUFFED(1);
//    DEEP(2),
//    DEEP_PUFFED(3);

    private static final AnglerfishVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(AnglerfishVariant::getId)).toArray(AnglerfishVariant[]::new);
    private final int id;

    AnglerfishVariant(int id) {
        this.id = id;
    }

    public static AnglerfishVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public int getId() {
        return this.id;
    }
}

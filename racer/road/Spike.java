package com.codegym.games.racer.road;
import com.codegym.engine.cell.*;
import com.codegym.games.racer.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Spike extends RoadObject {

    public Spike(int x, int y) {
        super(RoadObjectType.SPIKE, x, y);
        speed = 0;
    }  
}
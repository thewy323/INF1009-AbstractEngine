package com.inf1009.engine.collision;

import com.inf1009.engine.interfaces.ICollidable;
import java.util.ArrayList;
import java.util.List;

public class CollisionDetection {

    public boolean detect(ICollidable a, ICollidable b) {
        return a != null && b != null && a.getBounds().overlaps(b.getBounds());
    }

    public List<Pair> detectAll(List<ICollidable> collidables) {
        List<Pair> hits = new ArrayList<>();
        if (collidables == null) return hits;

        for (int i = 0; i < collidables.size(); i++) {
            for (int j = i + 1; j < collidables.size(); j++) {
                ICollidable a = collidables.get(i);
                ICollidable b = collidables.get(j);
                if (detect(a, b)) hits.add(new Pair(a, b));
            }
        }
        return hits;
    }

    public static class Pair {
        public final ICollidable a;
        public final ICollidable b;

        public Pair(ICollidable a, ICollidable b) {
            this.a = a;
            this.b = b;
        }
    }
}

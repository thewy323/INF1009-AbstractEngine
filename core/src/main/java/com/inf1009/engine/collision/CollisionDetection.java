package com.inf1009.engine.collision;

import java.util.List;
import com.inf1009.engine.interfaces.ICollidable;

public class CollisionDetection {

    // Checks every pair of collidable objects in the list
    public void detectAll(List<ICollidable> collidables,
                          CollisionHandling handling) {

        // Loop through each object
        for (int i = 0; i < collidables.size(); i++) {

            // Compare current object with the remaining objects only
            for (int j = i + 1; j < collidables.size(); j++) {

                ICollidable a = collidables.get(i);
                ICollidable b = collidables.get(j);

                // If their bounding areas overlap, trigger resolution
                if (a.getBounds().overlaps(b.getBounds())) {
                    handling.resolve(a, b);
                }
            }
        }
    }
}

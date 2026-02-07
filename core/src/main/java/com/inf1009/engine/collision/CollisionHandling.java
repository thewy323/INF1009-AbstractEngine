package com.inf1009.engine.collision;

import com.badlogic.gdx.math.Rectangle;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.ICollidable;

import java.util.List;

public class CollisionHandling {

    public void resolve(List<CollisionPair> pairs) {
        for (CollisionPair p : pairs) {
            ICollidable a = p.getA();
            ICollidable b = p.getB();

            // Dynamic vs Dynamic
            if (a instanceof DynamicEntity && b instanceof DynamicEntity) {
                resolveDynamicDynamic((DynamicEntity) a, (DynamicEntity) b);
                // optional: still notify
                a.onCollision(b);
                b.onCollision(a);
                continue;
            }

            // Dynamic vs Solid
            if (a instanceof DynamicEntity && b.isSolid()) {
                resolveDynamicSolid((DynamicEntity) a, b);
            }
            if (b instanceof DynamicEntity && a.isSolid()) {
                resolveDynamicSolid((DynamicEntity) b, a);
            }

            // notify (game-specific hook)
            a.onCollision(b);
            b.onCollision(a);
        }
    }

    private void resolveDynamicSolid(DynamicEntity d, ICollidable other) {
        Rectangle A = d.getBounds();
        Rectangle B = other.getBounds();

        float ax = A.x, ay = A.y, aw = A.width, ah = A.height;
        float bx = B.x, by = B.y, bw = B.width, bh = B.height;

        float acx = ax + aw / 2f;
        float acy = ay + ah / 2f;
        float bcx = bx + bw / 2f;
        float bcy = by + bh / 2f;

        float dx = acx - bcx;
        float dy = acy - bcy;

        float overlapX = (aw / 2f + bw / 2f) - Math.abs(dx);
        float overlapY = (ah / 2f + bh / 2f) - Math.abs(dy);

        if (overlapX <= 0 || overlapY <= 0) return;

        // Resolve along smallest penetration axis
        if (overlapX < overlapY) {
            // push left/right
            if (dx > 0) d.setPosition(ax + overlapX, ay);
            else d.setPosition(ax - overlapX, ay);

            d.setVelocityX(0f);
        } else {
            // push up/down
            if (dy > 0) {
                // d is above other -> land on top
                float top = by + bh;
                d.landOn(top);
            } else {
                // d is below other -> push down
                d.setPosition(ax, ay - overlapY);
                d.setVelocityY(0f);
            }
        }
    }

    private void resolveDynamicDynamic(DynamicEntity a, DynamicEntity b) {
        Rectangle A = a.getBounds();
        Rectangle B = b.getBounds();

        float ax = A.x, ay = A.y, aw = A.width, ah = A.height;
        float bx = B.x, by = B.y, bw = B.width, bh = B.height;

        float acx = ax + aw / 2f;
        float acy = ay + ah / 2f;
        float bcx = bx + bw / 2f;
        float bcy = by + bh / 2f;

        float dx = acx - bcx;
        float dy = acy - bcy;

        float overlapX = (aw / 2f + bw / 2f) - Math.abs(dx);
        float overlapY = (ah / 2f + bh / 2f) - Math.abs(dy);

        if (overlapX <= 0 || overlapY <= 0) return;

        if (overlapX < overlapY) {
            // split push on X
            float push = overlapX / 2f;
            if (dx > 0) {
                a.setPosition(ax + push, ay);
                b.setPosition(bx - push, by);
            } else {
                a.setPosition(ax - push, ay);
                b.setPosition(bx + push, by);
            }
            a.setVelocityX(0f);
            b.setVelocityX(0f);
        } else {
            // stacking logic
            if (dy > 0 && a.getVelocityY() <= 0f) {
                float top = by + bh;
                a.landOn(top);
                a.setVelocityY(0f);
                return;
            }
            if (dy < 0 && b.getVelocityY() <= 0f) {
                float top = ay + ah;
                b.landOn(top);
                b.setVelocityY(0f);
                return;
            }

            // otherwise split push on Y
            float push = overlapY / 2f;
            if (dy > 0) {
                a.setPosition(ax, ay + push);
                b.setPosition(bx, by - push);
            } else {
                a.setPosition(ax, ay - push);
                b.setPosition(bx, by + push);
            }
            a.setVelocityY(0f);
            b.setVelocityY(0f);
        }
    }
}

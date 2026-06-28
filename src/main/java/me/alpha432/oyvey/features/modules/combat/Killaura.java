package client.module.combat;

import client.module.Category;
import client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Killaura extends Module {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private float range = 4.2f;
    private long lastAttack = 0;
    private long lastJump = 0;
    private boolean critReady = false;

    public Killaura() {
        super("Killaura", Category.COMBAT);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        PlayerEntity target = findTarget();
        if (target == null) return;

        // Aim at target's head
        aimAt(target);

        // Critical hit logic: jump and attack when falling
        long now = System.currentTimeMillis();
        if (mc.player.isOnGround() && !mc.player.isInLava() && !mc.player.isTouchingWater()) {
            // Jump to trigger crit
            mc.player.jump();
            lastJump = now;
            critReady = true;
        }

        // Attack when falling (after jump) to ensure critical hit
        if (critReady && !mc.player.isOnGround() && mc.player.getVelocity().y < 0) {
            if (now - lastAttack >= 200) { // 200ms cooldown to avoid spam
                mc.interactionManager.attackEntity(mc.player, target);
                mc.player.swingHand(Hand.MAIN_HAND);
                lastAttack = now;
                critReady = false;
            }
        }
    }

    private PlayerEntity findTarget() {
        PlayerEntity closest = null;
        double closestDist = range;

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof PlayerEntity && entity != mc.player && entity.isAlive()) {
                double dist = mc.player.distanceTo(entity);
                if (dist < closestDist && mc.player.canSee(entity)) {
                    closest = (PlayerEntity) entity;
                    closestDist = dist;
                }
            }
        }
        return closest;
    }

    private void aimAt(PlayerEntity target) {
        Vec3d headPos = target.getPos().add(0, target.getEyeHeight(target.getPose()), 0);
        Vec3d diff = headPos.subtract(mc.player.getPos().add(0, mc.player.getEyeHeight(mc.player.getPose()), 0));

        double yaw = Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90;
        double pitch = -Math.toDegrees(Math.atan2(diff.y, Math.sqrt(diff.x * diff.x + diff.z * diff.z)));

        mc.player.setYaw((float) yaw);
        mc.player.setPitch((float) pitch);
    }
}

package your.client.modules.movement;

import your.client.module.Module;
import your.client.module.Category;
import your.client.settings.NumberSetting;
import your.client.settings.BooleanSetting;

import net.minecraft.client.Minecraft;

public class Strafe extends Module {

    private final Minecraft mc = Minecraft.getInstance();


    public final NumberSetting speed =
            new NumberSetting(
                    "Speed",
                    1,
                    1,
                    20,
                    1
            );


    public final BooleanSetting onlyMoving =
            new BooleanSetting(
                    "Only Moving",
                    true
            );



    public Strafe() {

        super(
                "Strafe",
                "Changes sideways movement speed",
                Category.MOVEMENT
        );


        addSettings(
                speed,
                onlyMoving
        );
    }



    @Override
    public void onTick() {


        if(mc.player == null)
            return;


        if(onlyMoving.isEnabled()
                && !mc.player.isSprinting()
                && !mc.player.zza.equals(0f)) {

            return;
        }



        float value =
                (float)speed.getValue();



        /*
          Applies strafe movement multiplier

          Uses Minecraft's movement input
        */


        if(mc.player.input.left
                || mc.player.input.right) {


            mc.player.setDeltaMovement(
                    mc.player.getDeltaMovement().x * value,
                    mc.player.getDeltaMovement().y,
                    mc.player.getDeltaMovement().z * value
            );

        }

    }

}

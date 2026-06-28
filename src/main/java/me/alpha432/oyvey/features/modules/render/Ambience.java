package your.client.modules.render;

import your.client.module.Module;
import your.client.module.Category;
import your.client.settings.*;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

import java.awt.Color;


public class Ambience extends Module {


    private final Minecraft mc = Minecraft.getInstance();


    public final BooleanSetting customFog =
            new BooleanSetting(
                    "Custom Fog",
                    true
            );


    public final ColorSetting fogColor =
            new ColorSetting(
                    "Fog Color",
                    new Color(80, 80, 255)
            );


    public final NumberSetting fogDistance =
            new NumberSetting(
                    "Fog Distance",
                    80,
                    5,
                    200,
                    5
            );


    public final BooleanSetting customSky =
            new BooleanSetting(
                    "Custom Sky",
                    false
            );


    public final ColorSetting skyColor =
            new ColorSetting(
                    "Sky Color",
                    new Color(120, 50, 255)
            );


    public final BooleanSetting nightMode =
            new BooleanSetting(
                    "Night Mode",
                    false
            );



    public Ambience() {

        super(
                "Ambience",
                "Changes world atmosphere",
                Category.RENDER
        );


        addSettings(
                customFog,
                fogColor,
                fogDistance,
                customSky,
                skyColor,
                nightMode
        );
    }



    @Override
    public void onTick() {


        if(mc.level == null)
            return;



        Level world = mc.level;



        // Fake night

        if(nightMode.isEnabled()) {

            world.setDayTime(18000);

        }



        // Fog values are handled in render event

        if(customFog.isEnabled()) {


            float r =
                    fogColor.getColor().getRed() / 255f;


            float g =
                    fogColor.getColor().getGreen() / 255f;


            float b =
                    fogColor.getColor().getBlue() / 255f;


            setFog(
                r,
                g,
                b,
                (float) fogDistance.getValue()
            );

        }


    }





    private void setFog(
            float r,
            float g,
            float b,
            float distance
    ) {


        /*
        
        Connect this to your fog render event.

        Example:
        
        RenderSystem.setShaderFogColor(
            r,
            g,
            b,
            1.0f
        );

        RenderSystem.setShaderFogStart(0);
        RenderSystem.setShaderFogEnd(distance);

        */

    }

}

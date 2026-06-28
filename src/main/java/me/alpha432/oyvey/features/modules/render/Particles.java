package your.client.modules.render;

import your.client.module.Module;
import your.client.module.Category;
import your.client.settings.*;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;


public class Particles extends Module {


    private final Minecraft mc = Minecraft.getInstance();

    private final ArrayList<Particle> particles = new ArrayList<>();

    private final Random random = new Random();



    public final NumberSetting amount =
            new NumberSetting(
                    "Amount",
                    50,
                    10,
                    200,
                    10
            );


    public final NumberSetting speed =
            new NumberSetting(
                    "Speed",
                    0.05,
                    0.01,
                    1,
                    0.01
            );


    public final NumberSetting size =
            new NumberSetting(
                    "Size",
                    3,
                    1,
                    10,
                    1
            );


    public final BooleanSetting rainbow =
            new BooleanSetting(
                    "Rainbow",
                    true
            );


    public final ColorSetting color =
            new ColorSetting(
                    "Color",
                    new Color(0,255,255)
            );




    public Particles() {


        super(
                "Particles",
                "Floating particles around you",
                Category.RENDER
        );


        addSettings(
                amount,
                speed,
                size,
                rainbow,
                color
        );

    }





    @Override
    public void onTick() {


        if(mc.player == null)
            return;



        while(particles.size() < amount.getValue()) {


            Vec3 pos = mc.player.position();



            particles.add(
                    new Particle(
                            pos.x,
                            pos.y + 1,
                            pos.z
                    )
            );

        }



        for(Particle p : particles) {


            p.y += p.motionY;

            p.x += p.motionX;

            p.z += p.motionZ;



            p.life--;

        }



        particles.removeIf(
                p -> p.life <= 0
        );

    }






    @Override
    public void onRender() {


        for(Particle p : particles) {


            Color c;


            if(rainbow.isEnabled()) {


                c = Color.getHSBColor(
                        (System.currentTimeMillis()%3000)/3000f,
                        1,
                        1
                );


            } else {


                c = color.getColor();

            }



            /*
              Draw particle here using
              your RenderUtil / BufferBuilder

              Position:
              p.x p.y p.z

              Size:
              size.getValue()

            */

        }

    }







    private class Particle {


        double x;
        double y;
        double z;


        double motionX;
        double motionY;
        double motionZ;


        int life = 100;



        Particle(
                double x,
                double y,
                double z
        ) {


            this.x = x + random.nextDouble() - 0.5;
            this.y = y;
            this.z = z + random.nextDouble() - 0.5;



            motionX =
                    (random.nextDouble()-0.5)
                    * speed.getValue();


            motionY =
                    random.nextDouble()
                    * speed.getValue();


            motionZ =
                    (random.nextDouble()-0.5)
                    * speed.getValue();

        }

    }

}

package your.client.modules.render;

import your.client.module.Module;
import your.client.module.Category;
import your.client.settings.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Trail extends Module {

    private final Minecraft mc = Minecraft.getInstance();

    private final List<Point> points = new ArrayList<>();


    public final NumberSetting length =
            new NumberSetting(
                    "Length",
                    50,
                    10,
                    200,
                    5
            );


    public final NumberSetting width =
            new NumberSetting(
                    "Width",
                    2,
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
                    new Color(0, 255, 255)
            );



    public Trail() {

        super(
                "Trail",
                "Leaves a trail behind the player",
                Category.RENDER
        );


        addSettings(
                length,
                width,
                rainbow,
                color
        );
    }



    @Override
    public void onRender() {


        if(mc.player == null)
            return;


        // save player position

        points.add(
            new Point(
                mc.player.position()
            )
        );


        // remove old points

        if(points.size() > length.getValue()) {
            points.remove(0);
        }


        drawTrail();

    }




    private void drawTrail() {


        if(points.size() < 2)
            return;


        for(int i = 1; i < points.size(); i++) {


            Point p1 = points.get(i-1);
            Point p2 = points.get(i);


            float alpha =
                    (float)i / points.size();



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



            RenderSystem.lineWidth(
                    (float) width.getValue()
            );


            // draw line here
            // connect p1 -> p2 using your render buffer


        }

    }



    private static class Point {

        double x;
        double y;
        double z;


        Point(Vec3 pos){

            x = pos.x;
            y = pos.y;
            z = pos.z;

        }
    }
}

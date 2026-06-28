package your.client.modules.render;

import your.client.module.Module;
import your.client.module.Category;
import your.client.settings.*;

import java.awt.Color;

public class ChinaHat extends Module {

    public final NumberSetting radius =
            new NumberSetting(
                    "Radius",
                    0.45,
                    0.1,
                    2.0,
                    0.05
            );

    public final NumberSetting height =
            new NumberSetting(
                    "Height",
                    0.35,
                    0.1,
                    1.5,
                    0.05
            );

    public final BooleanSetting rainbow =
            new BooleanSetting(
                    "Rainbow",
                    false
            );

    public final BooleanSetting spin =
            new BooleanSetting(
                    "Spin",
                    true
            );

    public final ColorSetting color =
            new ColorSetting(
                    "Color",
                    new Color(255, 0, 0, 150)
            );


    public ChinaHat() {

        super(
                "ChinaHat",
                "A customizable cosmetic hat",
                Category.RENDER
        );


        addSettings(
                radius,
                height,
                rainbow,
                spin,
                color
        );
    }


    @Override
    public void onRender() {

        if (mc.player == null)
            return;


        renderHat(
                mc.player,
                (float) radius.getValue(),
                (float) height.getValue(),
                color.getColor(),
                rainbow.isEnabled(),
                spin.isEnabled()
        );
    }
}

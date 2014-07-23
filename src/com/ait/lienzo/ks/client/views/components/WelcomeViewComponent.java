
package com.ait.lienzo.ks.client.views.components;

import com.ait.lienzo.client.core.shape.GridLayer;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;

public class WelcomeViewComponent extends AbstractViewComponent
{
    private Text m_text = new Text("Hello Lienzo 2.0").setStrokeWidth(1).setFontSize(48).setStrokeColor(ColorName.WHITE).setX(200).setY(400).setScale(3).setFillColor(ColorName.WHITE.getColor().setA(0.20)).setDraggable(true);

    public WelcomeViewComponent()
    {
        LienzoPanel panel = new LienzoPanel();

        Layer layer = new Layer();

        layer.add(m_text);

        panel.add(layer);

        panel.setBackgroundColor("#0433ff");

        panel.setBackgroundLayer(new GridLayer(20, new Line().setAlpha(0.2).setStrokeWidth(1).setStrokeColor(ColorName.WHITE)).setTransformable(false));

        add(panel);
    }
}

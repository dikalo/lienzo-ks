/*
   Copyright (c) 2014,2015 Ahome' Innovation Technologies. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.ait.lienzo.ks.client.views.components;

import java.util.LinkedHashMap;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.PolyLine;
import com.ait.lienzo.client.core.shape.Polygon;
import com.ait.lienzo.client.core.shape.RegularPolygon;
import com.ait.lienzo.client.core.shape.Star;
import com.ait.lienzo.client.core.types.NFastDoubleArrayJSO;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSComboBox;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeEvent;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeHandler;

public class CornerRadiusViewComponent extends AbstractToolBarViewComponent
{
    private final KSButton m_render = new KSButton("Render");

    public CornerRadiusViewComponent()
    {
        final Layer layer = new Layer();

        LinkedHashMap<String, String> pick = new LinkedHashMap<String, String>();

        pick.put("0", "0");

        pick.put("10", "10");

        pick.put("20", "20");

        pick.put("30", "30");

        pick.put("40", "40");

        KSComboBox cbox = new KSComboBox(pick);

        cbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                double radi = 0;

                String valu = event.getNewValue();

                if ("0".equals(valu))
                {
                    radi = 0;
                }
                else if ("10".equals(valu))
                {
                    radi = 10;
                }
                else if ("20".equals(valu))
                {
                    radi = 20;
                }
                else if ("30".equals(valu))
                {
                    radi = 30;
                }
                else if ("40".equals(valu))
                {
                    radi = 40;
                }
                else
                {
                    radi = 0;
                }
                for (IPrimitive<?> prim : layer.getChildNodes().toList())
                {
                    prim.asNode().getAttributes().setCornerRadius(radi);

                    prim.refresh();
                }
                layer.batch();
            }
        });
        getToolBarContainer().add(cbox);

        m_render.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                layer.setListening(false);

                long beg = System.currentTimeMillis();

                layer.draw();

                m_render.setText("Render " + (System.currentTimeMillis() - beg) + "ms");

                layer.setListening(true);

                layer.draw();
            }
        });
        m_render.setWidth(90);

        getToolBarContainer().add(m_render);

        make(layer);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    private void make(final Layer layer)
    {
        final Star star = new Star(5, 50, 100);
        star.setDraggable(true);
        star.setX(135);
        star.setY(130);
        star.setStrokeWidth(3);
        star.setFillColor(ColorName.DARKORCHID);
        star.setFillAlpha(0.50);
        star.setStrokeColor(ColorName.BLACK);
        layer.add(star);

        final RegularPolygon regp = new RegularPolygon(5, 100);
        regp.setDraggable(true);
        regp.setX(350);
        regp.setY(215);
        regp.setStrokeWidth(3);
        regp.setFillColor(ColorName.DARKSALMON);
        regp.setFillAlpha(0.50);
        regp.setStrokeColor(ColorName.BLACK);
        layer.add(regp);
        
        Point2DArray points = Point2DArray.fromNFastDoubleArrayJSO(NFastDoubleArrayJSO.make(0, 0, 60, 0, 60, 60, 100, 125, 0, 125));
        
        final PolyLine line = new PolyLine(points);
        line.setDraggable(true);
        line.setX(40);
        line.setY(270);
        line.setStrokeWidth(9);
        line.setStrokeColor(ColorName.RED);
        layer.add(line);
        
        final Polygon poly = new Polygon(points);
        poly.setDraggable(true);
        poly.setX(180);
        poly.setY(270);
        poly.setFillColor(ColorName.FIREBRICK);
        poly.setFillAlpha(0.50);
        poly.setStrokeWidth(9);
        poly.setStrokeColor(ColorName.RED);
        layer.add(poly);
    }
}

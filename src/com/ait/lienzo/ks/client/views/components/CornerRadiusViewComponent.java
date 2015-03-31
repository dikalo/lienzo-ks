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

import com.ait.lienzo.client.core.event.NodeDragEndEvent;
import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragStartEvent;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.PolyLine;
import com.ait.lienzo.client.core.shape.Polygon;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.RegularPolygon;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.Star;
import com.ait.lienzo.client.core.shape.ToolTip;
import com.ait.lienzo.client.core.shape.Triangle;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSComboBox;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.primitive.NFastDoubleArrayJSO;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeEvent;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeHandler;

public class CornerRadiusViewComponent extends AbstractToolBarViewComponent
{
    private final KSButton m_render = new KSButton("Render");

    private double         m_corner = 0;

    public CornerRadiusViewComponent()
    {
        final Layer layer = new Layer();

        LinkedHashMap<String, String> pick = new LinkedHashMap<String, String>();

        for (int i = 0; i < 45; i += 5)
        {
            pick.put(i + "", i + "");
        }
        KSComboBox cbox = new KSComboBox(pick);

        cbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                m_corner = Double.parseDouble(event.getNewValue());

                for (IPrimitive<?> prim : layer.getChildNodes().toList())
                {
                    prim.asNode().getAttributes().setCornerRadius(m_corner);

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

        final ToolTip tool = new ToolTip(getLienzoPanel().getViewport().getOverLayer());

        for (IPrimitive<?> prim : layer.getChildNodes().toList())
        {
            final Shape<?> shape = prim.asShape();

            if (null != shape)
            {
                shape.addNodeMouseEnterHandler(new NodeMouseEnterHandler()
                {
                    @Override
                    public void onNodeMouseEnter(NodeMouseEnterEvent event)
                    {
                        tool.setValues("Corner(" + m_corner + ")", shape.getShapeType().getValue());

                        final BoundingBox bb = shape.getBoundingBox();

                        tool.show(shape.getX() + bb.getX() + (bb.getWidth() / 2), shape.getY() + bb.getY() + (bb.getHeight() / 2));
                    }
                });
                shape.addNodeMouseExitHandler(new NodeMouseExitHandler()
                {
                    @Override
                    public void onNodeMouseExit(NodeMouseExitEvent event)
                    {
                        tool.hide();
                    }
                });
                shape.addNodeDragStartHandler(new NodeDragStartHandler()
                {
                    @Override
                    public void onNodeDragStart(NodeDragStartEvent event)
                    {
                        tool.hide();
                    }
                });
                shape.addNodeDragEndHandler(new NodeDragEndHandler()
                {
                    @Override
                    public void onNodeDragEnd(NodeDragEndEvent event)
                    {
                        tool.setValues("Corner(" + m_corner + ")", shape.getShapeType().getValue());

                        final BoundingBox bb = shape.getBoundingBox();

                        tool.show(shape.getX() + bb.getX() + (bb.getWidth() / 2), shape.getY() + bb.getY() + (bb.getHeight() / 2));
                    }
                });
            }
        }
    }

    private void make(final Layer layer)
    {
        final Star star = new Star(5, 50, 100);
        star.setDraggable(true);
        star.setX(115);
        star.setY(130);
        star.setStrokeWidth(3);
        star.setFillColor(ColorName.DARKORCHID);
        star.setFillAlpha(0.50);
        star.setStrokeColor(ColorName.BLACK);
        layer.add(star);

        final RegularPolygon regp = new RegularPolygon(5, 100);
        regp.setDraggable(true);
        regp.setX(300);
        regp.setY(195);
        regp.setStrokeWidth(3);
        regp.setFillColor(ColorName.AQUAMARINE);
        regp.setFillAlpha(0.50);
        regp.setStrokeColor(ColorName.BLACK);
        layer.add(regp);

        Point2DArray points = Point2DArray.fromNFastDoubleArrayJSO(NFastDoubleArrayJSO.make(0, 0, 60, 0, 60, 60, 100, 125, 0, 125));

        final PolyLine line = new PolyLine(points);
        line.setDraggable(true);
        line.setX(50);
        line.setY(275);
        line.setStrokeWidth(3);
        line.setStrokeColor(ColorName.BLACK);
        layer.add(line);

        final Polygon poly = new Polygon(points);
        poly.setDraggable(true);
        poly.setX(200);
        poly.setY(315);
        poly.setFillColor(ColorName.YELLOW);
        poly.setFillAlpha(0.50);
        poly.setStrokeWidth(3);
        poly.setStrokeColor(ColorName.BLACK);
        layer.add(poly);

        Triangle tria = new Triangle(new Point2D(0, 0), new Point2D(200, 0), new Point2D(100, 150));
        tria.setDraggable(true);
        tria.setX(370);
        tria.setY(50);
        tria.setFillColor(ColorName.RED);
        tria.setFillAlpha(0.50);
        tria.setStrokeWidth(3);
        tria.setStrokeColor(ColorName.BLACK);
        layer.add(tria);

        Rectangle rect = new Rectangle(200, 200);
        rect.setDraggable(true);
        rect.setX(400);
        rect.setY(230);
        rect.setFillColor(ColorName.BLUE);
        rect.setFillAlpha(0.50);
        rect.setStrokeWidth(3);
        rect.setStrokeColor(ColorName.BLACK);
        layer.add(rect);
    }
}

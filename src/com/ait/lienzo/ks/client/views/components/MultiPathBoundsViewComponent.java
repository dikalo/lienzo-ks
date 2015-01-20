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

import com.ait.lienzo.client.core.event.NodeDragStartEvent;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Polygon;
import com.ait.lienzo.client.core.types.BoundingPoints;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;

public class MultiPathBoundsViewComponent extends AbstractToolBarViewComponent
{
    private Group           m_group;

    private final MultiPath m_multi;

    private final KSButton  m_bound = new KSButton("Bounds");

    public MultiPathBoundsViewComponent()
    {
        final Layer layer = new Layer();

        m_bound.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (null == m_group)
                {
                    BoundingPoints points = m_multi.getBoundingPoints().transform(m_multi.getAbsoluteTransform());

                    if (null != points)
                    {
                        m_group = new Group();

                        for (Point2D p : points.getPoints())
                        {
                            m_group.add(new Circle(3).setX(p.getX()).setY(p.getY()).setFillColor(ColorName.BLACK).setListening(false));
                        }
                        m_group.add(new Polygon(points.getArray()).setStrokeColor(ColorName.BLACK).setStrokeWidth(1).setListening(false)).setListening(false);

                        layer.add(m_group);

                        layer.draw();

                        m_bound.setText("Remove");
                    }
                }
                else
                {
                    layer.remove(m_group);

                    m_group = null;

                    layer.draw();

                    m_bound.setText("Bounds");
                }
            }
        });
        m_bound.setWidth(90);

        getToolBarContainer().add(m_bound);

        m_multi = new MultiPath();

        m_multi.M(500, 500).L(600, 600).L(600, 700).L(550, 700).L(550, 900).L(500, 900).Z();

        m_multi.M(700, 500).L(800, 600).L(800, 700).L(750, 700).L(750, 900).L(700, 900).Z();

        m_multi.setFillColor(ColorName.DEEPPINK).setFillAlpha(0.75).setStrokeColor(ColorName.BLACK).setStrokeWidth(1).setDraggable(true).setX(-400).setY(-400);

        m_multi.addNodeDragStartHandler(new NodeDragStartHandler()
        {
            @Override
            public void onNodeDragStart(NodeDragStartEvent event)
            {
                if (null != m_group)
                {
                    layer.remove(m_group);

                    m_group = null;

                    m_bound.setText("Bounds");
                }
            }
        });
        layer.add(m_multi);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}

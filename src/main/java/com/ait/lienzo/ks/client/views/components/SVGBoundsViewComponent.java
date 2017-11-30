/*
   Copyright (c) 2017 Ahome' Innovation Technologies. All rights reserved.

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
import com.ait.lienzo.client.core.shape.Polygon;
import com.ait.lienzo.client.core.shape.SVGPath;
import com.ait.lienzo.client.core.types.BoundingPoints;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.DragMode;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;

public class SVGBoundsViewComponent extends AbstractToolBarViewComponent
{
    private Group          m_group;

    private final KSButton m_bounds = new KSButton("Bounds");

    public SVGBoundsViewComponent()
    {
        final Layer layer = new Layer();

        final String spec = "M10,15c10,10,10,0,40,0c30,0,30,10,40,0q-10,30-40,30q-30,0-40-30z"; // CASE 2A

        final SVGPath path = new SVGPath(spec).setStrokeColor(ColorName.BLUE).setStrokeWidth(2).setFillColor(ColorName.RED).setX(50).setY(50).setDraggable(true).setDragMode(DragMode.SAME_LAYER).setScale(3);

        layer.add(path);

        path.addNodeDragStartHandler(new NodeDragStartHandler()
        {
            @Override
            public void onNodeDragStart(final NodeDragStartEvent event)
            {
                if (null != m_group)
                {
                    layer.remove(m_group);

                    m_group = null;

                    layer.batch();

                    m_bounds.setText("Bounds");
                }
            }
        });
        m_bounds.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(final ClickEvent event)
            {
                if (null == m_group)
                {
                    final BoundingPoints points = path.getBoundingPoints();

                    if (null != points)
                    {
                        m_group = new Group().setListening(false);

                        for (final Point2D p : points.getPoints())
                        {
                            m_group.add(new Circle(3).setX(p.getX()).setY(p.getY()).setFillColor(ColorName.BLACK));
                        }
                        m_group.add(new Polygon(points.getArray()).setStrokeColor(ColorName.BLACK).setStrokeWidth(1));

                        layer.add(m_group);

                        layer.draw();

                        m_bounds.setText("Remove");
                    }
                }
                else
                {
                    layer.remove(m_group);

                    m_group = null;

                    layer.draw();

                    m_bounds.setText("Bounds");
                }
            }
        });
        m_bounds.setWidth(90);

        getToolBarContainer().add(m_bounds);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}

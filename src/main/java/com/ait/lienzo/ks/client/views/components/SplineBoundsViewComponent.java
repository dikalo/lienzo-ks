/*
   Copyright (c) 2014,2015,2016 Ahome' Innovation Technologies. All rights reserved.

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
import com.ait.lienzo.client.core.shape.Ellipse;
import com.ait.lienzo.client.core.shape.EllipticalArc;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Polygon;
import com.ait.lienzo.client.core.shape.Spline;
import com.ait.lienzo.client.core.types.BoundingPoints;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.client.core.util.Geometry;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.DragMode;
import com.ait.lienzo.shared.core.types.LineCap;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;

public class SplineBoundsViewComponent extends AbstractToolBarViewComponent
{
    private Group          m_group;

    private final Spline   m_curve;

    private final Ellipse  m_ellpse = new Ellipse(300, 200).setX(600).setY(500).setFillColor(ColorName.GREENYELLOW).setStrokeColor(ColorName.BLACK).setStrokeWidth(6).setDraggable(true);

    private final KSButton m_bound  = new KSButton("Bounds");

    public SplineBoundsViewComponent()
    {
        final Layer layer = new Layer();

        m_bound.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_ellpse.setEditable(false == m_ellpse.isEditable());

                if (null == m_group)
                {
                    BoundingPoints points = m_curve.getBoundingPoints();

                    if (null != points)
                    {
                        m_group = new Group().setListening(false);

                        for (Point2D p : points.getPoints())
                        {
                            m_group.add(new Circle(3).setX(p.getX()).setY(p.getY()).setFillColor(ColorName.BLACK));
                        }
                        m_group.add(new Polygon(points.getArray()).setStrokeColor(ColorName.BLACK).setStrokeWidth(1));

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

        m_curve = new Spline(new Point2DArray(new Point2D(300, 100), new Point2D(400, 200), new Point2D(250, 300), new Point2D(600, 100), new Point2D(300, 100))).setStrokeColor(ColorName.DEEPPINK).setStrokeWidth(7).setLineCap(LineCap.ROUND).setDashArray(15, 15).setDraggable(true).setDragMode(DragMode.SAME_LAYER);

        m_curve.addNodeDragStartHandler(new NodeDragStartHandler()
        {
            @Override
            public void onNodeDragStart(NodeDragStartEvent event)
            {
                if (null != m_group)
                {
                    layer.remove(m_group);

                    m_group = null;

                    layer.batch();

                    m_bound.setText("Bounds");
                }
            }
        });
        layer.add(m_curve);

        layer.add(m_ellpse);

        layer.add(new EllipticalArc(150, 100, 0, Geometry.toRadians(180)).setX(600).setY(500).setStrokeColor(ColorName.RED).setStrokeWidth(6).setFillColor(ColorName.AQUA).setDraggable(true));

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}

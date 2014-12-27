/*
   Copyright (c) 2014 Ahome' Innovation Technologies. All rights reserved.

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

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Polygon;
import com.ait.lienzo.client.core.shape.QuadraticCurve;
import com.ait.lienzo.client.core.types.BoundingPoints;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;

public class QuadraticCurveBoundsViewComponent extends AbstractToolBarViewComponent
{
    private Group                m_group;

    private final QuadraticCurve m_curve;

    private final KSButton       m_bound = new KSButton("Bounds");

    public QuadraticCurveBoundsViewComponent()
    {
        final Layer layer = new Layer();

        m_bound.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (null == m_group)
                {
                    BoundingPoints points = m_curve.getBoundingPoints();

                    if (null != points)
                    {
                        m_group = new Group();

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

        m_curve = new QuadraticCurve(120, 160, 220, 260, 220, 40).setStrokeWidth(7).setStrokeColor(ColorName.DEEPPINK);

        layer.add(m_curve);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}

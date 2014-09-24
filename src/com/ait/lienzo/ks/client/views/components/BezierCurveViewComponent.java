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

import com.ait.lienzo.client.core.shape.BezierCurve;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Polygon;
import com.ait.lienzo.client.core.types.BoundingPoints;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSContainer;
import com.ait.lienzo.ks.client.ui.components.KSToolBar;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.Layout;

public class BezierCurveViewComponent extends AbstractViewComponent
{
    private Group             m_group;

    private BezierCurve       m_curve;

    private final KSButton    m_bound  = new KSButton("Bounds");

    private final LienzoPanel m_lienzo = new LienzoPanel();

    public BezierCurveViewComponent()
    {
        KSContainer main = new KSContainer(Layout.BORDER);

        KSToolBar tool = new KSToolBar();

        tool.setRegion(BorderRegion.NORTH);

        tool.setHeight(30);

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
        m_bound.setWidth(80);

        tool.add(m_bound);

        main.add(tool);

        m_curve = new BezierCurve(120, 160, 35, 200, 220, 260, 220, 40).setStrokeWidth(3).setStrokeColor(ColorName.DEEPPINK);

        layer.add(m_curve);

        m_lienzo.add(layer);

        m_lienzo.setBackgroundLayer(getBackgroundLayer());

        KSContainer work = new KSContainer();

        work.setRegion(BorderRegion.CENTER);

        work.add(m_lienzo);

        main.add(work);

        add(main);
    }

    @Override
    public LienzoPanel getLienzoPanel()
    {
        return m_lienzo;
    }
}

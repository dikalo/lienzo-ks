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

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Polygon;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.BoundingPoints;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSComboBox;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeEvent;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeHandler;

public class TextBoundsViewComponent extends AbstractToolBarViewComponent
{
    private Group          m_group;

    private final Text     m_text;

    private final KSButton m_bound = new KSButton("Bounds");

    public TextBoundsViewComponent()
    {
        final Layer layer = new Layer();

        LinkedHashMap<String, String> pick = new LinkedHashMap<String, String>();

        for (TextBaseLine valu : TextBaseLine.getValues())
        {
            pick.put(valu.name(), valu.getValue());
        }
        KSComboBox cbox = new KSComboBox(pick);

        cbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                m_text.setTextBaseLine(TextBaseLine.lookup(event.getNewValue().toLowerCase()));

                if (null != m_group)
                {
                    layer.remove(m_group);

                    m_group = null;

                    m_bound.setText("Bounds");
                }
                layer.draw();
            }
        });
        getToolBarContainer().add(cbox);

        pick = new LinkedHashMap<String, String>();

        for (TextAlign valu : TextAlign.getValues())
        {
            pick.put(valu.name(), valu.getValue());
        }
        cbox = new KSComboBox(pick);

        cbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                m_text.setTextAlign(TextAlign.lookup(event.getNewValue().toLowerCase()));

                if (null != m_group)
                {
                    layer.remove(m_group);

                    m_group = null;

                    m_bound.setText("Bounds");
                }
                layer.draw();
            }
        });
        getToolBarContainer().add(cbox);

        m_bound.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (null == m_group)
                {
                    BoundingPoints points = m_text.getBoundingPoints();

                    if (null != points)
                    {
                        m_group = new Group().setX(500).setY(500);

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

        m_text = new Text("Lienzo is great!").setFillColor(ColorName.DEEPPINK).setX(500).setY(500).setFontSize(70);

        layer.add(m_text);

        layer.add(new Line(0, 500, 2000, 500).setStrokeColor(ColorName.GREEN).setStrokeWidth(1));

        layer.add(new Line(500, 0, 500, 2000).setStrokeColor(ColorName.GREEN).setStrokeWidth(1));

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}

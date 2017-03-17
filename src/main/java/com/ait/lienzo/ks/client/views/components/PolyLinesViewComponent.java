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

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.wires.IControlHandle.ControlHandleStandardType;
import com.ait.lienzo.client.core.shape.wires.IControlHandle.ControlHandleType;
import com.ait.lienzo.client.core.shape.wires.IControlHandleList;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSComboBox;
import com.ait.lienzo.ks.client.ui.components.KSSimple;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeEvent;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class PolyLinesViewComponent extends AbstractToolBarViewComponent
{
    private static final int   ORTH    = 0;

    private static final int   POLY    = 1;

    private static final int   SPLN    = 2;

    private static final int   RADI    = 3;

    private final KSButton     m_draws = new KSButton("Render");

    private final KSButton     m_cancl = new KSButton("Cancel");

    private final KSSimple     m_label = new KSSimple("&nbsp;&nbsp;Alt+Click to Edit", 1);

    private int                m_kind  = ORTH;

    private IControlHandleList m_list;

    private final Layer        m_main  = new Layer();

    public PolyLinesViewComponent()
    {
        LinkedHashMap<String, String> pick = new LinkedHashMap<String, String>();

        pick.put("OrthogonalPolyLine", "OrthogonalPolyLine");

        pick.put("OrthogonalPolyLineRadius", "OrthogonalPolyLineRadius");

        pick.put("PolyLine", "PolyLine");

        pick.put("Spline", "Spline");

        KSComboBox cbox = new KSComboBox(pick);

        cbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                String valu = event.getNewValue();

                if ("OrthogonalPolyLine".equals(valu))
                {
                    m_kind = ORTH;
                }
                else if ("OrthogonalPolyLineRadius".equals(valu))
                {
                    m_kind = RADI;
                }
                else if ("PolyLine".equals(valu))
                {
                    m_kind = POLY;
                }
                else
                {
                    m_kind = SPLN;
                }
                if (null != m_list)
                {
                    m_list.destroy();
                }
                m_main.removeAll();

                test(m_main);

                m_main.draw();
            }
        });
        getToolBarContainer().add(cbox);

        m_draws.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_main.setListening(false);

                long beg = System.currentTimeMillis();

                m_main.draw();

                m_draws.setText("Render " + (System.currentTimeMillis() - beg) + "ms");

                m_main.setListening(true);

                m_main.draw();
            }
        });
        m_draws.setWidth(90);

        m_cancl.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (null != m_list)
                {
                    m_list.destroy();

                    m_list = null;
                }
            }
        });
        m_cancl.setWidth(90);

        getToolBarContainer().add(m_draws);

        getToolBarContainer().add(m_cancl);

        getToolBarContainer().add(m_label);

        test(m_main);

        getLienzoPanel().add(m_main);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    private void test(Layer layer)
    {
        createTest1(layer, 10, 10, 40, 40, 80, 80, 120, 120);
        createTest1(layer, 10, 250, 120, 40, 80, 80, 40, 120);
        createTest1(layer, 10, 470, 120, 120, 80, 80, 40, 40);
        createTest1(layer, 10, 660, 120, 120, 80, 80, 40, 40);
    }

    public void createTest1(Layer layer, double x, double y, double... points)
    {
        double origX = x;
        double origY = y;

        createTest2(layer, x, y, -20, -20, points);
        createTest2(layer, (x += 120), y, 0, -20, points);
        createTest2(layer, (x += 120), y, 20, -20, points);
        createTest2(layer, (x += 120), y, 20, 0, points);
        createTest2(layer, (x += 120), y, 20, 20, points);
        createTest2(layer, (x += 120), y, 0, 20, points);
        createTest2(layer, (x += 120), y, -20, 20, points);
        createTest2(layer, (x += 120), y, -20, 0, points);

        x = origX;
        y = origY + 100;

        createTest3(layer, x, y, -20, -20, points);
        createTest3(layer, (x += 120), y, 0, -20, points);
        createTest3(layer, (x += 120), y, 20, -20, points);
        createTest3(layer, (x += 120), y, 20, 0, points);
        createTest3(layer, (x += 120), y, 20, 20, points);
        createTest3(layer, (x += 120), y, 0, 20, points);
        createTest3(layer, (x += 120), y, -20, 20, points);
        createTest3(layer, (x += 120), y, -20, 0, points);
    }

    public void createTest2(Layer layer, double x, double y, double dx, double dy, double... points)
    {
        int length = points.length;
        double p1x = points[0];
        double p1y = points[1];
        double[] newPoints = new double[length + 2];
        System.arraycopy(points, 0, newPoints, 2, length);
        newPoints[0] = p1x + dx;
        newPoints[1] = p1y + dy;
        createTest(layer, x, y, newPoints);
    }

    public void createTest3(Layer layer, double x, double y, double dx, double dy, double... points)
    {
        int length = points.length;
        double p1x = points[length - 2];
        double p1y = points[length - 1];
        double[] newPoints = new double[length + 2];
        System.arraycopy(points, 0, newPoints, 0, length);
        newPoints[length] = p1x + dx;
        newPoints[length + 1] = p1y + dy;
        createTest(layer, x, y, newPoints);
    }

    public void createTest(final Layer layer, final double x, final double y, double... points)
    {
        AbstractMultiPointShape<?> line;

        final Point2DArray array = Point2DArray.fromArrayOfDouble(points);

        switch (m_kind)
        {
            case ORTH:
                line = new OrthogonalPolyLine(array);
                break;
            case RADI:
                OrthogonalPolyLine poly = new OrthogonalPolyLine(array);
                poly.setCornerRadius(6);
                line = poly;
                break;
            case POLY:
                line = new PolyLine(array);
                break;
            default:
                line = new Spline(array);
                break;
        }
        final AbstractMultiPointShape<?> look = line.setX(x).setY(y).setStrokeWidth(5).setStrokeColor("#0000CC");

        look.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                if (event.isAltKeyDown())
                {
                    if (null != m_list)
                    {
                        m_list.destroy();

                        m_list = null;
                    }
                    Map<ControlHandleType, IControlHandleList> hmap = look.getControlHandles(ControlHandleStandardType.POINT);

                    if (null != hmap)
                    {
                        m_list = hmap.get(ControlHandleStandardType.POINT);

                        if ((null != m_list) && (m_list.isActive()))
                        {
                            m_list.show();
                        }
                    }
                }
            }
        });
        layer.add(look);
    }
}

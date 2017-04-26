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

import java.util.Map;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.AbstractMultiPointShape;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.shape.PolyLine;
import com.ait.lienzo.client.core.shape.Spline;
import com.ait.lienzo.client.core.shape.wires.IControlHandle.ControlHandleStandardType;
import com.ait.lienzo.client.core.shape.wires.IControlHandle.ControlHandleType;
import com.ait.lienzo.client.core.shape.wires.IControlHandleList;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class PolyLinesViewComponent extends AbstractToolBarViewComponent
{
    private static final String ORTHOGONAL_POLY_LINE        = "OrthogonalPolyLine";

    private static final String ORTHOGONAL_POLY_LINE_RADIUS = "OrthogonalPolyLineRadius";

    private static final String POLY_LINE                   = "PolyLine";

    private static final String SPLINE                      = "Spline";

    private final TextBox       m_selectionTextBox          = new TextBox();

    private final TextBox       m_lineWidthTextBox          = new TextBox();

    private ListBox             m_lineTypeListBox           = new ListBox();

    private IControlHandleList  m_lineControls;

    private final Layer         m_layer                     = new Layer();

    public PolyLinesViewComponent()
    {
        m_lineTypeListBox.addItem(ORTHOGONAL_POLY_LINE);
        m_lineTypeListBox.addItem(ORTHOGONAL_POLY_LINE_RADIUS);
        m_lineTypeListBox.addItem(POLY_LINE);
        m_lineTypeListBox.addItem(SPLINE);
        m_lineTypeListBox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                draw();
            }
        });
        getToolBarContainer().add(m_lineTypeListBox);


        getToolBarContainer().add(new Label("   Line width:"));
        m_lineWidthTextBox.setText("1");
        getToolBarContainer().add(m_lineWidthTextBox);


        getToolBarContainer().add(new Label("   Selection area:"));
        m_selectionTextBox.setText("10");
        getToolBarContainer().add(m_selectionTextBox);


        final KSButton renderButton = new KSButton("Render");
        renderButton.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_layer.setListening(false);

                long beg = System.currentTimeMillis();

                draw();

                renderButton.setText("Render " + (System.currentTimeMillis() - beg) + "ms");

                m_layer.setListening(true);
            }
        });
        renderButton.setWidth(90);
        getToolBarContainer().add(renderButton);


        KSButton cancelButton = new KSButton("Cancel");
        cancelButton.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                clearSelection();
            }
        });
        cancelButton.setWidth(90);
        getToolBarContainer().add(cancelButton);


        getToolBarContainer().add(new Label("Click to Edit. Selection bounding works only for OrthogonalPolyLine"));


        addLinesToCanvas();
        getLienzoPanel().add(m_layer);
        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());
        getWorkingContainer().add(getLienzoPanel());
    }

    private void draw() {
        clearSelection();
        m_layer.removeAll();

        addLinesToCanvas();
        m_layer.draw();
    }

    private void clearSelection()
    {
        if (null != m_lineControls)
        {
            m_lineControls.destroy();
            m_lineControls = null;
        }
    }

    private void addLinesToCanvas()
    {
        generateOneColumnOfLines(10, 40, 40, 80, 80, 120, 120);
        generateOneColumnOfLines(250, 120, 40, 80, 80, 40, 120);
        generateOneColumnOfLines(470, 120, 120, 80, 80, 40, 40);
        generateOneColumnOfLines(660, 120, 120, 80, 80, 40, 40);
    }

    private void generateOneColumnOfLines(double y, double... points)
    {
        double origX = 10;
        double origY = y;

        createTest2(origX, y, -20, -20, points);
        createTest2((origX += 120), y, 0, -20, points);
        createTest2((origX += 120), y, 20, -20, points);
        createTest2((origX += 120), y, 20, 0, points);
        createTest2((origX += 120), y, 20, 20, points);
        createTest2((origX += 120), y, 0, 20, points);
        createTest2((origX += 120), y, -20, 20, points);
        createTest2((origX += 120), y, -20, 0, points);

        origX = 10;
        y = origY + 100;

        createTest3(origX, y, -20, -20, points);
        createTest3((origX += 120), y, 0, -20, points);
        createTest3((origX += 120), y, 20, -20, points);
        createTest3((origX += 120), y, 20, 0, points);
        createTest3((origX += 120), y, 20, 20, points);
        createTest3((origX += 120), y, 0, 20, points);
        createTest3((origX += 120), y, -20, 20, points);
        createTest3((origX += 120), y, -20, 0, points);
    }

    private void createTest2(double x, double y, double dx, double dy, double... points)
    {
        int length = points.length;
        double p1x = points[0];
        double p1y = points[1];
        double[] newPoints = new double[length + 2];
        System.arraycopy(points, 0, newPoints, 2, length);
        newPoints[0] = p1x + dx;
        newPoints[1] = p1y + dy;
        addLineToCanvas(x, y, newPoints);
    }

    private void createTest3(double x, double y, double dx, double dy, double... points)
    {
        int length = points.length;
        double p1x = points[length - 2];
        double p1y = points[length - 1];
        double[] newPoints = new double[length + 2];
        System.arraycopy(points, 0, newPoints, 0, length);
        newPoints[length] = p1x + dx;
        newPoints[length + 1] = p1y + dy;
        addLineToCanvas(x, y, newPoints);
    }

    private void addLineToCanvas(final double x, final double y, double... points)
    {
        final AbstractMultiPointShape<?> line;

        final Point2DArray array = Point2DArray.fromArrayOfDouble(points);

        switch (m_lineTypeListBox.getSelectedItemText())
        {
            case ORTHOGONAL_POLY_LINE:
                line = new OrthogonalPolyLine(array)
                        .setSelectionBoundingBoxOffset(getLineBounding())
                        .setFillBoundsForSelection(true);
                break;
            case ORTHOGONAL_POLY_LINE_RADIUS:
                line = new OrthogonalPolyLine(array)
                        .setCornerRadius(6)
                        .setSelectionBoundingBoxOffset(getLineBounding())
                        .setFillBoundsForSelection(true);
                break;
            case POLY_LINE:
                line = new PolyLine(array);
                break;
            default:
                line = new Spline(array);
                break;
        }

        line.setX(x).setY(y).setStrokeWidth(getLineWidth()).setStrokeColor("#0000CC");

        line.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                clearSelection();

                Map<ControlHandleType, IControlHandleList> hmap = line.getControlHandles(ControlHandleStandardType.POINT);
                if (null != hmap)
                {
                    m_lineControls = hmap.get(ControlHandleStandardType.POINT);

                    if ((null != m_lineControls) && (m_lineControls.isActive()))
                    {
                        m_lineControls.show();
                    }
                }
            }
        });
        m_layer.add(line);
    }

    private Double getLineBounding() {
        if (m_selectionTextBox.getText() == null || m_selectionTextBox.getText().length() == 0) {
            return 0d;
        }

        return Double.parseDouble(m_selectionTextBox.getText());
    }

    private Double getLineWidth() {
        if (m_lineWidthTextBox.getText() == null || m_lineWidthTextBox.getText().length() == 0) {
            return 1d;
        }

        return Double.parseDouble(m_lineWidthTextBox.getText());
    }
}

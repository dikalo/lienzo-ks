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

import static com.ait.lienzo.client.core.shape.wires.LayoutContainer.Layout.BOTTOM;
import static com.ait.lienzo.client.core.shape.wires.LayoutContainer.Layout.CENTER;
import static com.ait.lienzo.client.core.shape.wires.LayoutContainer.Layout.LEFT;
import static com.ait.lienzo.client.core.shape.wires.LayoutContainer.Layout.RIGHT;
import static com.ait.lienzo.client.core.shape.wires.LayoutContainer.Layout.TOP;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.shape.wires.IControlHandle;
import com.ait.lienzo.client.core.shape.wires.IControlHandleList;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStartEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStartHandler;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStepEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeStepHandler;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSSimple;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;

public class WiresResizeViewComponent extends AbstractToolBarViewComponent
{
    private static final double SIZE = 100;

    private final static KSSimple LABEL = new KSSimple("&nbsp;&nbsp;Shift+Click to Resize&nbsp;&nbsp;Click to " +
            "hide the resize points&nbsp;&nbsp;", 1);

    private WiresShape m_shape;

    private WiresManager m_manager;

    private Text m_shapeLabel;

    public WiresResizeViewComponent()
    {
        Layer layer = new Layer();

        KSButton left = generateButton(LEFT, "Left", "#CC00CC");
        getToolBarContainer().add(left);

        KSButton right = generateButton(RIGHT, "Right", "#0000CC");
        getToolBarContainer().add(right);

        KSButton center = generateButton(CENTER, "Center", "#CC0000");
        getToolBarContainer().add(center);

        KSButton top = generateButton(TOP, "Top", "#00CC00");
        getToolBarContainer().add(top);

        KSButton bottom = generateButton(BOTTOM, "Bottom", "#CCCC00");
        getToolBarContainer().add(bottom);

        getToolBarContainer().add(LABEL);

        m_manager = WiresManager.get(layer);

        m_shapeLabel = new Text("[]")
                .setFontFamily("Verdana")
                .setFontSize(12)
                .setStrokeWidth(1)
                .setStrokeColor(ColorName.WHITE);

        m_shape = create(CENTER, "#CC0000");

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    private KSButton generateButton(final LayoutContainer.Layout layout, String label, final String color) {
        KSButton button = new KSButton(label);

        button.addClickHandler(new com.ait.toolkit.sencha.ext.client.events.button.ClickHandler()
        {
            @Override
            public void onClick(com.ait.toolkit.sencha.ext.client.events.button.ClickEvent event)
            {
                if (null != m_shape)
                {
                    m_manager.deregister(m_shape);
                    m_shape = create(layout, color);
                    m_shape.refresh();
                }
            }
        });

        button.setWidth(90);
        return button;
    }

    private WiresShape create(LayoutContainer.Layout layout, String color)
    {
        m_shapeLabel.setText("[" + (int) SIZE + ", " + (int) SIZE + "]");
        final MultiPath path = new MultiPath().rect(0, 0, SIZE, SIZE)
                .setStrokeWidth(1)
                .setStrokeColor(color)
                .setFillColor(ColorName.LIGHTGREY);
        final WiresShape wiresShape0 =
                new WiresShape(path)
                        .setX(400)
                        .setY(200)
                        .setDraggable(true)
                        .addChild(new Circle(SIZE / 4).setFillColor(color), layout)
                        .addChild(m_shapeLabel, CENTER);

        m_manager.register(wiresShape0);
        m_manager.getMagnetManager().createMagnets(wiresShape0);
        addResizeHandlers(wiresShape0);
        return wiresShape0;
    }

    private void addResizeHandlers(final WiresShape shape)
    {
        shape
                .setResizable(true)
                .getGroup()
                .addNodeMouseClickHandler(new NodeMouseClickHandler()
                {
                    @Override
                    public void onNodeMouseClick(NodeMouseClickEvent event)
                    {
                        final IControlHandleList controlHandles = shape.loadControls(IControlHandle.ControlHandleStandardType.RESIZE);
                        if (null != controlHandles)
                        {
                            if (event.isShiftKeyDown())
                            {
                                controlHandles.show();
                            } else
                            {
                                controlHandles.hide();
                            }
                        }

                    }
                });

        shape.addWiresResizeStartHandler(new WiresResizeStartHandler()
        {
            @Override
            public void onShapeResizeStart(final WiresResizeStartEvent event)
            {
                onShapeResize(event.getWidth(), event.getHeight());
            }
        });

        shape.addWiresResizeStepHandler(new WiresResizeStepHandler()
        {
            @Override
            public void onShapeResizeStep(final WiresResizeStepEvent event)
            {
                onShapeResize(event.getWidth(), event.getHeight());
            }
        });

        shape.addWiresResizeEndHandler(new WiresResizeEndHandler()
        {
            @Override
            public void onShapeResizeEnd(final WiresResizeEndEvent event)
            {
                onShapeResize(event.getWidth(), event.getHeight());
            }
        });
    }

    private void onShapeResize(final double width, final double height)
    {
        final String t = "[" + width + ", " + height + "]";
        m_shapeLabel.setText(t);
    }

}

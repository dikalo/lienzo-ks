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

import java.util.Map;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.wires.IControlHandle.ControlHandleStandardType;
import com.ait.lienzo.client.core.shape.wires.IControlHandle.ControlHandleType;
import com.ait.lienzo.client.core.shape.wires.IControlHandleList;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSSimple;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;

public class MultiPathResizeViewComponent extends AbstractToolBarViewComponent
{
    private IControlHandleList m_ctrls;

    private final MultiPath    m_multi;

    private final KSButton     m_cancl = new KSButton("Cancel");

    private final KSSimple     m_label = new KSSimple("&nbsp;&nbsp;Shift+Click to Resize,&nbsp;Alt+Click to Edit", 1);

    public MultiPathResizeViewComponent()
    {
        final Layer layer = new Layer();

        m_cancl.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (null != m_ctrls)
                {
                    m_ctrls.destroy();

                    m_ctrls = null;
                }
            }
        });
        m_cancl.setWidth(90);

        getToolBarContainer().add(m_cancl);

        getToolBarContainer().add(m_label);

        m_multi = new MultiPath();

        m_multi.M(100, 100);

        m_multi.L(200, 150);

        m_multi.L(300, 100);

        m_multi.L(250, 200);

        m_multi.L(300, 300);

        m_multi.L(200, 250);

        m_multi.L(100, 300);

        m_multi.L(150, 200);

        m_multi.Z();

        m_multi.setStrokeWidth(5).setStrokeColor("#0000CC").setDraggable(true);

        m_multi.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                if (event.isShiftKeyDown())
                {
                    if (null != m_ctrls)
                    {
                        m_ctrls.destroy();

                        m_ctrls = null;
                    }
                    Map<ControlHandleType, IControlHandleList> hmap = m_multi.getControlHandles(ControlHandleStandardType.RESIZE);

                    if (null != hmap)
                    {
                        m_ctrls = hmap.get(ControlHandleStandardType.RESIZE);

                        if ((null != m_ctrls) && (m_ctrls.isActive()))
                        {
                            m_ctrls.show(layer);
                        }
                    }
                }
                else if (event.isAltKeyDown())
                {
                    if (null != m_ctrls)
                    {
                        m_ctrls.destroy();

                        m_ctrls = null;
                    }
                    Map<ControlHandleType, IControlHandleList> hmap = m_multi.getControlHandles(ControlHandleStandardType.POINT);

                    if (null != hmap)
                    {
                        m_ctrls = hmap.get(ControlHandleStandardType.POINT);

                        if ((null != m_ctrls) && (m_ctrls.isActive()))
                        {
                            m_ctrls.show(layer);
                        }
                    }
                }
            }
        });
        layer.add(m_multi);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}

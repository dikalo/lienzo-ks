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

import java.util.Arrays;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.wires.IControlHandle;
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

    private final KSSimple     m_label = new KSSimple("&nbsp;&nbsp;Shift+Click to Resize", 1);

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

        m_multi.M(200, 200);

        m_multi.L(300, 250);

        m_multi.L(400, 200);

        m_multi.L(350, 300);

        m_multi.L(400, 400);

        m_multi.L(300, 350);

        m_multi.L(200, 400);

        m_multi.L(250, 300);

        m_multi.L(200, 200);

        m_multi.Z();

        m_multi.setStrokeWidth(10).setStrokeColor("#0000CC").setDraggable(false);

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
                    m_ctrls = m_multi.getControlHandles(Arrays.asList(IControlHandle.ControlHandleStandardType.POINT));

                    if ((null != m_ctrls) && (m_ctrls.isActive()))
                    {
                        m_ctrls.display(layer);
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

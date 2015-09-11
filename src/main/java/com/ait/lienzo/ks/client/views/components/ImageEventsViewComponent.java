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

import com.ait.lienzo.client.core.event.NodeDragEndEvent;
import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveEvent;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartEvent;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseDoubleClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseDoubleClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.event.NodeMouseMoveEvent;
import com.ait.lienzo.client.core.event.NodeMouseMoveHandler;
import com.ait.lienzo.client.core.image.PictureLoadedHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Picture;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.DragMode;
import com.ait.lienzo.shared.core.types.ImageSelectionMode;
import com.ait.lienzo.shared.core.types.ImageSerializationMode;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;

public class ImageEventsViewComponent extends AbstractToolBarViewComponent
{
    private final KSButton     m_push = new KSButton("Non-Transparent");

    private Picture            m_html;

    private ImageSelectionMode m_mode = ImageSelectionMode.SELECT_NON_TRANSPARENT;

    public ImageEventsViewComponent()
    {
        final Layer layer = new Layer();

        final Layer stats = new Layer();

        final Text label = new Text("Events and Coordinates").setX(25).setY(75).setFillColor("blue");

        stats.add(label);

        m_push.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (m_mode == ImageSelectionMode.SELECT_NON_TRANSPARENT)
                {
                    m_mode = ImageSelectionMode.SELECT_BOUNDS;

                    m_push.setText("Bounds");
                }
                else
                {
                    m_mode = ImageSelectionMode.SELECT_NON_TRANSPARENT;

                    m_push.setText("Non-Transparent");
                }
                m_html.setImageSelectionMode(m_mode);

                layer.batch();
            }
        });
        m_push.setWidth(100);

        getToolBarContainer().add(m_push);

        new Picture("HTML5_Logo_512_a.png", m_mode).onLoaded(new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(final Picture picture)
            {
                m_html = picture;

                m_html.setX(100).setY(100).setDraggable(true).setDragMode(DragMode.SAME_LAYER);

                m_html.setImageSerializationMode(ImageSerializationMode.DATA_URL);

                m_html.addNodeMouseEnterHandler(new NodeMouseEnterHandler()
                {
                    @Override
                    public void onNodeMouseEnter(NodeMouseEnterEvent event)
                    {
                        label.setText("Mouse Enter (" + event.getX() + "," + event.getY() + ")");

                        stats.batch();
                    }
                });
                m_html.addNodeMouseExitHandler(new NodeMouseExitHandler()
                {
                    @Override
                    public void onNodeMouseExit(NodeMouseExitEvent event)
                    {
                        label.setText("Mouse Exit (" + event.getX() + "," + event.getY() + ")");

                        stats.batch();
                    }
                });
                m_html.addNodeMouseMoveHandler(new NodeMouseMoveHandler()
                {
                    @Override
                    public void onNodeMouseMove(NodeMouseMoveEvent event)
                    {
                        label.setText("Mouse Move (" + event.getX() + "," + event.getY() + ")");

                        stats.batch();
                    }
                });
                m_html.addNodeMouseClickHandler(new NodeMouseClickHandler()
                {
                    @Override
                    public void onNodeMouseClick(NodeMouseClickEvent event)
                    {
                        label.setText("Mouse Click (" + event.getX() + "," + event.getY() + ")");

                        stats.batch();
                    }
                });
                m_html.addNodeMouseDoubleClickHandler(new NodeMouseDoubleClickHandler()
                {
                    @Override
                    public void onNodeMouseDoubleClick(NodeMouseDoubleClickEvent event)
                    {
                        label.setText("Mouse DoubleClick (" + event.getX() + "," + event.getY() + ")");

                        stats.batch();
                    }
                });
                m_html.addNodeDragStartHandler(new NodeDragStartHandler()
                {
                    @Override
                    public void onNodeDragStart(NodeDragStartEvent event)
                    {
                        label.setText("Mouse DragStart (" + event.getX() + "," + event.getY() + ")");

                        stats.batch();
                    }
                });
                m_html.addNodeDragEndHandler(new NodeDragEndHandler()
                {
                    @Override
                    public void onNodeDragEnd(NodeDragEndEvent event)
                    {
                        label.setText("Mouse DragEnd (" + event.getX() + "," + event.getY() + ")");

                        stats.batch();
                    }
                });
                m_html.addNodeDragMoveHandler(new NodeDragMoveHandler()
                {
                    @Override
                    public void onNodeDragMove(NodeDragMoveEvent event)
                    {
                        label.setText("Mouse DragMove (" + event.getX() + "," + event.getY() + ")");

                        stats.batch();
                    }
                });
                layer.add(m_html);

                layer.batch();
            }
        });
        getLienzoPanel().add(layer);

        getLienzoPanel().add(stats);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}

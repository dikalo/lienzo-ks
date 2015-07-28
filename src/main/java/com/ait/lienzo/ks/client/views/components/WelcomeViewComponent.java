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

import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.SCALE;
import static com.ait.lienzo.client.core.animation.AnimationTweener.LINEAR;

import com.ait.lienzo.client.core.Attribute;
import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.event.AttributesChangedEvent;
import com.ait.lienzo.client.core.event.AttributesChangedHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.image.ImageLoader;
import com.ait.lienzo.client.core.mediator.EventFilter;
import com.ait.lienzo.client.core.mediator.MouseWheelZoomMediator;
import com.ait.lienzo.client.core.shape.GridLayer;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.shape.guides.ToolTip;
import com.ait.lienzo.client.core.types.PatternGradient;
import com.ait.lienzo.client.core.types.Transform;
import com.ait.lienzo.ks.client.style.KSStyle;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSSimple;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.FillRepeat;
import com.ait.lienzo.shared.core.types.IColor;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.google.gwt.dom.client.ImageElement;

public class WelcomeViewComponent extends AbstractToolBarViewComponent
{
    private final Text     m_banner = getText("Lienzo");

    private final KSButton m_unzoom = new KSButton("Unzoom");

    private final KSSimple m_zoomlb = new KSSimple("&nbsp;&nbsp;Shift+Mouse Wheel to Zoom", 1);

    public WelcomeViewComponent()
    {
        final Layer layer = new Layer();

        m_unzoom.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                getLienzoPanel().setTransform(new Transform()).draw();
            }
        });
        m_unzoom.setWidth(90);

        getToolBarContainer().add(m_unzoom);

        getToolBarContainer().add(m_zoomlb);

        new ImageLoader(KSStyle.get().crosshatch())
        {
            @Override
            public void onLoad(ImageElement image)
            {
                m_banner.setFillGradient(new PatternGradient(image, FillRepeat.REPEAT)).setFillAlpha(0.70);

                layer.batch();
            }

            @Override
            public void onError(String message)
            {
            }
        };
        final ToolTip tool = new ToolTip().setAutoHideTime(5000);

        layer.add(getRect(ColorName.MEDIUMPURPLE, 1.0, -15.0, tool));

        layer.add(getRect(ColorName.LAWNGREEN, 0.7, 0, tool));

        layer.add(getRect(ColorName.RED, 0.7, 15.0, tool));

        layer.add(getRect(ColorName.YELLOW, 0.7, 30.0, tool));

        m_banner.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                animate();
            }
        });
        layer.add(m_banner);

        layer.add(getLogo("A 2D Structured Graphics", 270));

        layer.add(getLogo("Toolkit for GWT.", 340));

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());

        getLienzoPanel().getViewport().getOverLayer().add(tool);

        getLienzoPanel().getMediators().push(new MouseWheelZoomMediator(EventFilter.SHIFT));

        getLienzoPanel().getViewport().addAttributesChangedHandler(Attribute.TRANSFORM, new AttributesChangedHandler()
        {
            @Override
            public void onAttributesChanged(final AttributesChangedEvent event)
            {
                if (tool.isShowing())
                {
                    tool.hide();
                }
            }
        });
    }

    private final Rectangle getRect(final IColor color, final double alpha, final double rotate, final ToolTip tool)
    {
        final Rectangle rect = new Rectangle(200, 200);

        rect.setX(125);

        rect.setY(100);

        rect.setFillColor(color);

        rect.setFillAlpha(alpha);

        rect.setStrokeWidth(5);

        rect.setCornerRadius(30);

        rect.setRotationDegrees(rotate);

        rect.setStrokeColor(ColorName.BLACK);

        rect.addNodeMouseEnterHandler(new NodeMouseEnterHandler()
        {
            @Override
            public void onNodeMouseEnter(NodeMouseEnterEvent event)
            {
                tool.setValues("Color ( " + color.toString().toUpperCase() + " )", "Tool Tips");

                tool.show(125, 100);
            }
        });
        rect.addNodeMouseExitHandler(new NodeMouseExitHandler()
        {
            @Override
            public void onNodeMouseExit(NodeMouseExitEvent event)
            {
                tool.hide();
            }
        });
        return rect;
    }

    private final static Text getText(String label)
    {
        return new Text(label).setStrokeWidth(5).setFontSize(144).setFontStyle("bold").setStrokeColor(ColorName.WHITE).setX(700).setY(150).setTextAlign(TextAlign.CENTER).setTextBaseLine(TextBaseLine.MIDDLE);
    }

    private final static Text getLogo(String label, double y)
    {
        final Text text = new Text(label).setFontSize(50).setFontStyle("bold").setX(400).setY(y).setTextAlign(TextAlign.LEFT).setTextBaseLine(TextBaseLine.MIDDLE).setFillColor(ColorName.BLACK).setStrokeWidth(1.5).setStrokeColor(ColorName.WHITE);

        text.addNodeMouseEnterHandler(new NodeMouseEnterHandler()
        {
            @Override
            public void onNodeMouseEnter(NodeMouseEnterEvent event)
            {
                text.setFillColor(ColorName.RED);

                text.getLayer().batch();
            }
        });
        text.addNodeMouseExitHandler(new NodeMouseExitHandler()
        {
            @Override
            public void onNodeMouseExit(NodeMouseExitEvent event)
            {
                text.setFillColor(ColorName.BLACK);

                text.getLayer().batch();
            }
        });
        return text;
    }

    private void animate()
    {
        m_banner.getLayer().setListening(false);

        m_banner.animate(LINEAR, AnimationProperties.toPropertyList(SCALE(-1, 1)), 500, new AnimationCallback()
        {
            @Override
            public void onClose(IAnimation animation, IAnimationHandle handle)
            {
                m_banner.animate(LINEAR, AnimationProperties.toPropertyList(SCALE(1, 1)), 500, new AnimationCallback()
                {
                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle)
                    {
                        m_banner.getLayer().setListening(true);

                        m_banner.getLayer().draw();
                    }
                });
            }
        });
    }

    @Override
    public boolean activate()
    {
        if (super.activate())
        {
            animate();

            return true;
        }
        return false;
    }

    @Override
    public GridLayer getBackgroundLayer()
    {
        return new BluePrintBackgroundLayer();
    }
}

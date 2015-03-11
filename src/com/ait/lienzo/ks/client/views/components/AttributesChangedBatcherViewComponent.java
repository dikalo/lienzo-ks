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

import static com.ait.lienzo.client.core.AttributeOp.and;
import static com.ait.lienzo.client.core.AttributeOp.any;
import static com.ait.lienzo.client.core.AttributeOp.all;
import static com.ait.lienzo.client.core.AttributeOp.has;
import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.ROTATION_DEGREES;
import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.SCALE;

import java.util.LinkedHashMap;

import com.ait.lienzo.client.core.Attribute;
import com.ait.lienzo.client.core.AttributeOp.BooleanOp;
import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.event.AnimationFrameAttributesChangedBatcher;
import com.ait.lienzo.client.core.event.AttributesChangedEvent;
import com.ait.lienzo.client.core.event.AttributesChangedHandler;
import com.ait.lienzo.client.core.event.DeferredAttributesChangedBatcher;
import com.ait.lienzo.client.core.event.FinallyAttributesChangedBatcher;
import com.ait.lienzo.client.core.event.HandlerRegistrationManager;
import com.ait.lienzo.client.core.event.IAttributesChangedBatcher;
import com.ait.lienzo.client.core.event.ImmediateAttributesChangedBatcher;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Shadow;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSComboBox;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.DragConstraint;
import com.ait.lienzo.shared.core.types.LineJoin;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeEvent;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeHandler;

public class AttributesChangedBatcherViewComponent extends AbstractToolBarViewComponent
{
    private final KSButton             m_scaled  = new KSButton("Scale");

    private final KSButton             m_rotate  = new KSButton("Rotate");

    private final KSButton             m_doboth  = new KSButton("Both");

    private final KSButton             m_srshow  = new KSButton("Remove S/R");

    private final KSButton             m_xyshow  = new KSButton("Remove X/Y");

    private final KSButton             m_dshorz  = new KSButton("Horizontal");

    private final KSButton             m_dsvert  = new KSButton("Vertical");

    private final KSButton             m_dsnone  = new KSButton("None");

    private long                       m_maxrot  = 0;

    private HandlerRegistrationManager m_srlist  = new HandlerRegistrationManager();

    private HandlerRegistrationManager m_xylist  = new HandlerRegistrationManager();

    private IAttributesChangedBatcher  m_batcher = new ImmediateAttributesChangedBatcher();

    public AttributesChangedBatcherViewComponent()
    {
        final BooleanOp andhas = and(has(Attribute.SCALE), has(Attribute.ROTATION));

        final BooleanOp anyhas = any(Attribute.SCALE, Attribute.ROTATION);

        final BooleanOp allhas = all(Attribute.SCALE, Attribute.ROTATION);

        final Layer layer = new Layer();

        final Text text = new Text("Push an amimate button").setFillColor(ColorName.BLACK).setX(400).setY(100).setFontSize(20).setTextBaseLine(TextBaseLine.TOP);

        final Text labl = new Text(m_batcher.getName()).setFillColor(ColorName.BLACK).setX(400).setY(150).setFontSize(20).setTextBaseLine(TextBaseLine.TOP);

        final Text json = new Text("{}").setFillColor(ColorName.BLACK).setX(400).setY(200).setFontSize(20).setTextBaseLine(TextBaseLine.TOP);

        final Text posn = new Text("{}").setFillColor(ColorName.BLACK).setX(400).setY(250).setFontSize(20).setTextBaseLine(TextBaseLine.TOP);

        LinearGradient lgradient = new LinearGradient(0, 0, 200, 0);

        lgradient.addColorStop(0.0, ColorName.WHITE);

        lgradient.addColorStop(0.1, ColorName.SALMON);

        lgradient.addColorStop(0.9, ColorName.DARKRED);

        lgradient.addColorStop(1.0, ColorName.WHITE);

        final Rectangle rectangle = new Rectangle(200, 300).setX(100).setY(100).setFillGradient(lgradient).setDraggable(true).setShadow(new Shadow(ColorName.BLACK, 10, 5, 5)).setStrokeColor(ColorName.BLACK).setStrokeWidth(10).setLineJoin(LineJoin.ROUND);

        rectangle.setOffset(100, 150);

        rectangle.setAttributesChangedBatcher(m_batcher);

        final LinkedHashMap<String, String> pick = new LinkedHashMap<String, String>();

        pick.put("Immediate", "Immediate");

        pick.put("Deferred", "Deferred");

        pick.put("AnimationFrame", "AnimationFrame");

        pick.put("Finally", "Finally");

        final KSComboBox cbox = new KSComboBox(pick);

        cbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                final String value = pick.get(event.getNewValue());

                if ("Immediate".equals(value))
                {
                    m_batcher = new ImmediateAttributesChangedBatcher();

                    rectangle.setAttributesChangedBatcher(m_batcher);

                    labl.setText(m_batcher.getName());

                    layer.draw();
                }
                else if ("Deferred".equals(value))
                {
                    m_batcher = new DeferredAttributesChangedBatcher();

                    rectangle.setAttributesChangedBatcher(m_batcher);

                    labl.setText(m_batcher.getName());

                    layer.draw();
                }
                else if ("AnimationFrame".equals(value))
                {
                    m_batcher = new AnimationFrameAttributesChangedBatcher();

                    rectangle.setAttributesChangedBatcher(m_batcher);

                    labl.setText(m_batcher.getName());

                    layer.draw();
                }
                else if ("Finally".equals(value))
                {
                    m_batcher = new FinallyAttributesChangedBatcher();

                    rectangle.setAttributesChangedBatcher(m_batcher);

                    labl.setText(m_batcher.getName());

                    layer.draw();
                }
            }
        });
        getToolBarContainer().add(cbox);

        m_scaled.setWidth(90);

        getToolBarContainer().add(m_scaled);

        m_scaled.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_maxrot = 0;

                cbox.disable();

                rectangle.animate(AnimationTweener.BOUNCE, AnimationProperties.toPropertyList(SCALE(0.25, 0.25)), 2000, new AnimationCallback()
                {
                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle)
                    {
                        rectangle.animate(AnimationTweener.BOUNCE, AnimationProperties.toPropertyList(SCALE(1, 1)), 2000, new AnimationCallback()
                        {
                            @Override
                            public void onClose(IAnimation animation, IAnimationHandle handle)
                            {
                                cbox.enable();
                            }
                        });
                    }
                });
            }
        });
        m_rotate.setWidth(90);

        getToolBarContainer().add(m_rotate);

        m_rotate.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_maxrot = 0;

                cbox.disable();

                rectangle.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(ROTATION_DEGREES(360)), 2000, new AnimationCallback()
                {
                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle)
                    {
                        rectangle.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(ROTATION_DEGREES(0)), 2000, new AnimationCallback()
                        {
                            @Override
                            public void onClose(IAnimation animation, IAnimationHandle handle)
                            {
                                cbox.enable();
                            }
                        });
                    }
                });
            }
        });
        m_doboth.setWidth(90);

        getToolBarContainer().add(m_doboth);

        m_doboth.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_maxrot = 0;

                cbox.disable();

                rectangle.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(ROTATION_DEGREES(360), SCALE(0.25, 0.25)), 2000, new AnimationCallback()
                {
                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle)
                    {
                        rectangle.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(ROTATION_DEGREES(0), SCALE(1, 1)), 2000, new AnimationCallback()
                        {
                            @Override
                            public void onClose(IAnimation animation, IAnimationHandle handle)
                            {
                                cbox.enable();
                            }
                        });
                    }
                });
            }
        });
        final AttributesChangedHandler xyhandler = new AttributesChangedHandler()
        {
            @Override
            public void onAttributesChanged(final AttributesChangedEvent event)
            {
                json.setText(event.toJSONString());

                posn.setText(new Point2D(rectangle.getX(), rectangle.getY()).toJSONString());

                layer.batch();
            }
        };
        m_xyshow.setWeight(90);

        getToolBarContainer().add(m_xyshow);

        m_xyshow.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (m_xylist.size() == 0)
                {
                    m_xyshow.setText("Remove X/Y");

                    m_xylist.register(rectangle.addAttributesChangedHandler(Attribute.X, xyhandler));

                    m_xylist.register(rectangle.addAttributesChangedHandler(Attribute.Y, xyhandler));
                }
                else
                {
                    m_xyshow.setText("Register X/Y");

                    m_xylist.destroy();
                }
            }
        });
        final AttributesChangedHandler srhandler = new AttributesChangedHandler()
        {
            @Override
            public void onAttributesChanged(final AttributesChangedEvent event)
            {
                final Point2D scale = rectangle.getScale();

                final long r = Math.round(rectangle.getRotationDegrees());

                if (r > m_maxrot)
                {
                    m_maxrot = r;
                }
                if (null != scale)
                {
                    text.setText("AND:" + event.evaluate(andhas) + ":ANY:" + event.evaluate(anyhas) + ":ALL:" + event.evaluate(allhas) + ":ROTATION:" + event.has(Attribute.ROTATION) + ":" + r + ":" + m_maxrot + ":SCALE:" + event.has(Attribute.SCALE) + ":" + scale.toJSONString());
                }
                else
                {
                    text.setText("AND:" + event.evaluate(andhas) + ":ANY:" + event.evaluate(anyhas) + ":ALL:" + event.evaluate(allhas) + ":ROTATION:" + event.has(Attribute.ROTATION) + ":" + r + ":" + m_maxrot + ":SCALE:" + event.has(Attribute.SCALE) + ":{none}");
                }
                json.setText(event.toJSONString());

                layer.batch();
            }
        };
        m_srshow.setWeight(90);

        getToolBarContainer().add(m_srshow);

        m_srshow.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (m_srlist.size() == 0)
                {
                    m_srshow.setText("Remove S/R");

                    m_srlist.register(rectangle.addAttributesChangedHandler(Attribute.SCALE, srhandler));

                    m_srlist.register(rectangle.addAttributesChangedHandler(Attribute.ROTATION, srhandler));
                }
                else
                {
                    m_srshow.setText("Register S/R");

                    m_srlist.destroy();
                }
            }
        });
        m_dsnone.setWeight(90);

        getToolBarContainer().add(m_dsnone);

        m_dsnone.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                rectangle.setDragConstraint(DragConstraint.NONE);
            }
        });
        m_dshorz.setWeight(90);

        getToolBarContainer().add(m_dshorz);

        m_dshorz.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                rectangle.setDragConstraint(DragConstraint.HORIZONTAL);
            }
        });
        m_dsvert.setWeight(90);

        getToolBarContainer().add(m_dsvert);

        m_dsvert.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                rectangle.setDragConstraint(DragConstraint.VERTICAL);
            }
        });
        m_srlist.register(rectangle.addAttributesChangedHandler(Attribute.SCALE, srhandler));

        m_srlist.register(rectangle.addAttributesChangedHandler(Attribute.ROTATION, srhandler));

        m_xylist.register(rectangle.addAttributesChangedHandler(Attribute.X, xyhandler));

        m_xylist.register(rectangle.addAttributesChangedHandler(Attribute.Y, xyhandler));

        layer.add(rectangle);

        layer.add(text);

        layer.add(labl);

        layer.add(json);

        layer.add(posn);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}

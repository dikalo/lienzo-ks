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

import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.ALPHA;
import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.SCALE;
import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.POSITIONING;
import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.ROTATION_DEGREES;
import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.DASH_OFFSET;
import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.X;
import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.Y;

import com.ait.lienzo.client.core.Attribute;
import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.animation.positioning.AbstractRadialPositioningCalculator;
import com.ait.lienzo.client.core.animation.positioning.IPositioningCalculator;
import com.ait.lienzo.client.core.event.NodeAttributeChangedEvent;
import com.ait.lienzo.client.core.event.NodeAttributeChangedHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Bow;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Spline;
import com.ait.lienzo.client.core.shape.Star;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.client.core.types.Shadow;
import com.ait.lienzo.client.core.util.Geometry;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.IColor;
import com.ait.lienzo.shared.core.types.LineCap;
import com.ait.lienzo.shared.core.types.LineJoin;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class AnimateViewComponent extends AbstractViewComponent
{
    public AnimateViewComponent()
    {
        final Layer layer = new Layer();

        final Bow bow1 = new Bow(80, 100, Geometry.toRadians(0), Geometry.toRadians(270)).setFillColor(ColorName.HOTPINK).setStrokeColor(ColorName.BLACK).setStrokeWidth(2).setDraggable(true).setX(150).setY(150).setShadow(new Shadow(ColorName.BLACK.getColor().setA(0.5), 5, 5, 5));

        layer.add(flippy(bow1, ColorName.HOTPINK));

        LinearGradient lgradient = new LinearGradient(0, 0, 200, 0);

        lgradient.addColorStop(0.0, ColorName.WHITE);

        lgradient.addColorStop(0.1, ColorName.SALMON);

        lgradient.addColorStop(0.9, ColorName.DARKRED);

        lgradient.addColorStop(1.0, ColorName.WHITE);

        final Text text = new Text("Scale:{\"x\":1,\"y\":1}").setFillColor(ColorName.BLACK).setX(400).setY(600);

        final Rectangle rectangle = new Rectangle(200, 300).setX(50).setY(400).setFillGradient(lgradient).setDraggable(true).setShadow(new Shadow(ColorName.BLACK, 10, 5, 5)).setStrokeColor(ColorName.BLACK).setStrokeWidth(10).setLineJoin(LineJoin.ROUND);

        rectangle.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                rectangle.animate(AnimationTweener.BOUNCE, AnimationProperties.toPropertyList(SCALE(0.25, 0.25)), 2000, new AnimationCallback()
                {
                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle)
                    {
                        rectangle.animate(AnimationTweener.BOUNCE, AnimationProperties.toPropertyList(SCALE(1, 1)), 2000);
                    }
                });
            }
        });
        rectangle.addNodeAttributeChangedHandler(Attribute.SCALE, new NodeAttributeChangedHandler()
        {
            @Override
            public void onNodeAttributeChanged(NodeAttributeChangedEvent event)
            {
                Point2D scale = rectangle.getScale();

                if (null != scale)
                {
                    text.setText("Scale:" + scale.toJSONString());
                }
                else
                {
                    text.setText("Scale:{\"x\":1,\"y\":1}");
                }
                layer.batch();
            }
        });
        final int x = 400;

        final int y = 400;

        final Star sun = new Star(13, 70, 100).setX(x).setY(y).setStrokeColor(ColorName.RED).setStrokeWidth(3).setFillColor(ColorName.YELLOW).setAlpha(0.75);

        final IPositioningCalculator orbit = new AbstractRadialPositioningCalculator()
        {
            @Override
            public double getX()
            {
                return sun.getX();
            }

            @Override
            public double getY()
            {
                return sun.getY();
            }

            @Override
            public double getRadius()
            {
                return sun.getOuterRadius() + 200;
            }
        };
        final Circle earth = new Circle(50).setX(x + 100 + 200).setY(y).setStrokeColor(ColorName.BLACK).setStrokeWidth(2).setFillColor(ColorName.DEEPSKYBLUE).setShadow(new Shadow(ColorName.BLACK, 10, 5, 5));

        final Circle moon = new Circle(20).setX(x + 100 + 200 + 50 + 40).setY(y).setStrokeColor(ColorName.BLACK).setStrokeWidth(2).setFillColor(ColorName.DARKGRAY).setShadow(new Shadow(ColorName.BLACK, 10, 5, 5));

        sun.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                sun.getLayer().setListening(false);

                final IPositioningCalculator calc = new AbstractRadialPositioningCalculator()
                {
                    @Override
                    public double getX()
                    {
                        return earth.getX();
                    }

                    @Override
                    public double getY()
                    {
                        return earth.getY();
                    }

                    @Override
                    public double getRadius()
                    {
                        return earth.getRadius() + 40;
                    }
                };
                sun.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(X(x + 500), Y(y - 300), ROTATION_DEGREES(360 * 3)), 5000, new AnimationCallback()
                {
                    @Override
                    public void onStart(IAnimation animation, IAnimationHandle handle)
                    {
                        doRotation(earth, moon, orbit, calc);
                    }

                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle)
                    {
                        sun.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(X(x), Y(y), ROTATION_DEGREES(0)), 5000, new AnimationCallback()
                        {
                            @Override
                            public void onStart(IAnimation animation, IAnimationHandle handle)
                            {
                                doRotation(earth, moon, orbit, calc);
                            }

                            @Override
                            public void onClose(IAnimation animation, IAnimationHandle handle)
                            {
                                sun.getLayer().setListening(true);

                                sun.getLayer().draw();
                            }
                        });
                    }
                });
            }
        });
        layer.add(rectangle);

        layer.add(text);

        Point2DArray points = new Point2DArray(new Point2D(300, 100), new Point2D(400, 200), new Point2D(250, 300), new Point2D(600, 100), new Point2D(650, 150));

        final Spline spline = new Spline(points).setStrokeColor(ColorName.BLUE).setStrokeWidth(7).setLineCap(LineCap.ROUND).setDashArray(15, 15);

        spline.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                spline.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(DASH_OFFSET(300)), 5000, new AnimationCallback()
                {
                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle)
                    {
                        spline.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(DASH_OFFSET(0)), 5000);
                    }
                });
            }
        });
        layer.add(spline);

        for (int i = 0; i < points.size(); i++)
        {
            Point2D p = points.get(i);

            Circle c = new Circle(10).setFillColor(ColorName.BLACK).setAlpha(0.5).setX(p.getX()).setY(p.getY());

            layer.add(c);
        }
        Layer space = new Layer();

        space.add(sun);

        space.add(earth);

        space.add(moon);

        getLienzoPanel().add(layer);

        getLienzoPanel().add(space);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    private Group m_shadey = null;

    public Bow flippy(final Bow prim, final IColor color)
    {
        prim.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                prim.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(SCALE(1, -1)), 500, new AnimationCallback()
                {
                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle)
                    {
                        prim.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(SCALE(1, 1)), 500, new AnimationCallback()
                        {
                            @Override
                            public void onClose(IAnimation animation, IAnimationHandle handle)
                            {
                                shadey(prim.getLayer(), color);
                            }
                        });
                    }
                });
            }
        });
        return prim;
    }

    public void shadey(Layer layer, IColor color)
    {
        final Rectangle[] r = new Rectangle[5];

        if (null != m_shadey)
        {
            layer.remove(m_shadey);

            layer.draw();
        }
        m_shadey = new Group().setX(400).setY(50);

        for (int i = 0; i < 5; i++)
        {
            r[i] = new Rectangle(900, 40).setFillColor(color).setStrokeColor(ColorName.BLACK).setStrokeWidth(2).setAlpha(0).setX(0).setY((i * 50)).setScale(1, 0).setShadow(new Shadow(ColorName.BLACK.getColor().setA(0.5), 5, 5, 5));

            m_shadey.add(r[i]);
        }
        layer.add(m_shadey);

        RepeatingCommand command = new RepeatingCommand()
        {
            @Override
            public boolean execute()
            {
                forward(0, r);

                return false;
            }
        };
        Scheduler.get().scheduleFixedDelay(command, 100);
    }

    public void forward(final int i, final Rectangle[] r)
    {
        if (i < 5)
        {
            RepeatingCommand command = new RepeatingCommand()
            {
                @Override
                public boolean execute()
                {
                    forward(i + 1, r);

                    r[i].addNodeMouseClickHandler(new NodeMouseClickHandler()
                    {
                        @Override
                        public void onNodeMouseClick(NodeMouseClickEvent event)
                        {
                            r[i].animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(AnimationProperty.Properties.X(r[i].getX() + 900)), 300, new AnimationCallback()
                            {
                                @Override
                                public void onClose(IAnimation animation, IAnimationHandle handle)
                                {
                                    r[i].setVisible(false);

                                    r[i].getLayer().draw();

                                    for (int j = i + 1; j < 5; j++)
                                    {
                                        if (r[j].isVisible())
                                        {
                                            r[j].animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(AnimationProperty.Properties.Y(r[j].getY() - 50)), 300);
                                        }
                                    }
                                }
                            });
                        }
                    });
                    return false;
                }
            };
            Scheduler.get().scheduleFixedDelay(command, 100);

            r[i].animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(1), AnimationProperty.Properties.SCALE(1, 1)), 300);
        }
    }

    private void doRotation(final Circle earth, final Circle moon, final IPositioningCalculator orbit, final IPositioningCalculator calc)
    {
        earth.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(POSITIONING(orbit)), 2500, new AnimationCallback()
        {
            @Override
            public void onStart(IAnimation animation, IAnimationHandle handle)
            {
                moon.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(POSITIONING(calc), ALPHA(0.25)), 1250, new AnimationCallback()
                {
                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle)
                    {
                        moon.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(POSITIONING(calc), ALPHA(1.0)), 1250);
                    }
                });
            }

            @Override
            public void onClose(IAnimation animation, IAnimationHandle handle)
            {
                earth.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(POSITIONING(orbit)), 2500, new AnimationCallback()
                {
                    @Override
                    public void onStart(IAnimation animation, IAnimationHandle handle)
                    {
                        moon.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(POSITIONING(calc), ALPHA(0.25)), 1250, new AnimationCallback()
                        {
                            @Override
                            public void onClose(IAnimation animation, IAnimationHandle handle)
                            {
                                moon.animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(POSITIONING(calc), ALPHA(1.0)), 1250);
                            }
                        });
                    }
                });
            }
        });
    }
}

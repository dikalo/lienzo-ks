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
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.types.ImageData;
import com.ait.lienzo.client.core.util.ScratchPad;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.core.client.JavaScriptObject;

public class MandelbrotComponent extends AbstractToolBarViewComponent
{
    private final KSButton    m_push    = new KSButton("Reset");

    private int               m_iter    = 64;

    private int               m_wide    = 1600;

    private int               m_high    = 1000;

    private int[]             m_rbuf;

    private int[]             m_gbuf;

    private int[]             m_bbuf;

    private double            m_zoom    = 400.0;

    private double            m_xpos    = -m_wide / 2;

    private double            m_ypos    = -m_high / 2;

    private double            m_xpan    = 50.0;

    private double            m_ypan    = 50.0;

    private ScratchPad        m_temp      = new ScratchPad(m_wide, m_high);

    private ImageData         m_data      = m_temp.getContext().getImageData(0, 0, m_wide, m_high);

    private AnimationCallback m_animate = null;

    public MandelbrotComponent()
    {
        doColors();

        final Layer layer = new Layer();

        m_push.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_zoom = 400.0;

                m_xpos = -m_wide / 2;

                m_ypos = -m_high / 2;

                m_xpan = 50.0;

                m_ypan = 50.0;

                draw(layer);
            }
        });
        m_push.setWidth(100);

        getToolBarContainer().add(m_push);

        getLienzoPanel().add(layer);

        layer.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                zoom(event.getX(), event.getY(), event.isShiftKeyDown() ? 1 : 2, false == event.isAltKeyDown());

                draw(layer);
            }
        });
        getWorkingContainer().add(getLienzoPanel());

        AnimationScheduler.get().requestAnimationFrame(getAnimationCallback(layer));
    }

    private final AnimationCallback getAnimationCallback(final Layer layer)
    {
        if (null == m_animate)
        {
            m_animate = new AnimationCallback()
            {
                @Override
                public void execute(double time)
                {
                    if (init(layer))
                    {
                        draw(layer);
                    }
                    else
                    {
                        AnimationScheduler.get().requestAnimationFrame(m_animate);
                    }
                }
            };
        }
        return m_animate;
    }

    public boolean init(final Layer layer)
    {
        m_wide = layer.getWidth();

        m_high = layer.getHeight();

        if ((m_wide < 1) || (m_high < 1))
        {
            return false;
        }
        m_xpos = -m_wide / 2;

        m_ypos = -m_high / 2;

        m_temp = new ScratchPad(m_wide, m_high);

        m_data = m_temp.getContext().getImageData(0, 0, m_wide, m_high);

        return true;
    }

    public void zoom(double x, double y, double factor, boolean zoom)
    {
        if (zoom)
        {
            m_zoom *= factor;

            m_xpan = factor * (x + m_xpos + m_xpan);

            m_ypan = factor * (y + m_ypos + m_ypan);
        }
        else
        {
            m_zoom /= factor;

            m_xpan = (x + m_xpos + m_xpan) / factor;

            m_ypan = (y + m_ypos + m_ypan) / factor;
        }
    }

    public void doColors()
    {
        m_rbuf = new int[m_iter];

        m_gbuf = new int[m_iter];

        m_bbuf = new int[m_iter];

        for (int i = 0; i < m_iter; i++)
        {
            final Color rgbc = Color.fromNormalizedHSL(1.0 - (i / (double) m_iter), 1, i / (i + 8d));

            m_rbuf[i] = rgbc.getR();

            m_gbuf[i] = rgbc.getG();

            m_bbuf[i] = rgbc.getB();
        }
    }

    public void draw(final Layer layer)
    {
        layer.clear();

        draw_0(m_data.getData(), m_wide, m_high, m_zoom, m_xpos, m_ypos, m_xpan, m_ypan, m_iter, m_rbuf, m_gbuf, m_bbuf);

        layer.getContext().putImageData(m_data, 0, 0);
    }

    private final native void draw_0(JavaScriptObject data, int w, int h, double z, double mx, double my, double xp, double yp, int it, int[] ra, int[] ga, int[] ba)
    /*-{
		for (var y = 0; y < h; y++) {
			for (var x = 0; x < w; x++) {
				var x0 = (x + mx + xp) / z;
				var y0 = (y + my + yp) / z;
				var px = (y * w + x) * 4;
				var na = 0;
				var nb = 0;
				var rx = 0;
				var ry = 0;
				var lo = 0;
				while (lo < it && (rx * rx + ry * ry <= 4)) {
					rx = na * na - nb * nb + x0;
					ry = 2 * na * nb + y0;
					na = rx;
					nb = ry;
					lo++;
				}
				if (lo < it) {
					data[px] = ra[lo];
					data[px + 1] = ga[lo];
					data[px + 2] = ba[lo];
					data[px + 3] = 255;
				} else {
					data[px] = 0;
					data[px + 1] = 0;
					data[px + 2] = 0;
					data[px + 3] = 255;
				}
			}
		}
    }-*/;
}

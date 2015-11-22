/*
   Copyright (c) 2014,2015,2016 Ahome' Innovation Technologies. All rights reserved.

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

import com.ait.lienzo.client.core.event.NodeMouseWheelEvent;
import com.ait.lienzo.client.core.event.NodeMouseWheelHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.types.ImageData;
import com.ait.lienzo.client.core.util.ScratchPad;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.google.gwt.core.client.JavaScriptObject;

public class MandelbrotComponent extends AbstractToolBarViewComponent
{
    private final KSButton m_push = new KSButton("Draw");

    private int            m_iter = 64;

    private int[]          m_rbuf;

    private int[]          m_gbuf;

    private int[]          m_bbuf;

    private double         m_zoom = 1.0;

    private double         m_xpos = -.5;

    private double         m_ypos = 0.0;

    private boolean        m_wait = false;

    ScratchPad             temp   = new ScratchPad(1600, 1000);

    final ImageData        buff   = temp.getContext().getImageData(0, 0, 1600, 1000);

    public MandelbrotComponent()
    {
        doColors();

        final Layer layer = new Layer();

        m_push.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_zoom = 1.0;

                m_xpos = -.5;

                m_ypos = 0.0;

                draw(layer);
            }
        });
        m_push.setWidth(100);

        getToolBarContainer().add(m_push);

        getLienzoPanel().add(layer);

        layer.addNodeMouseWheelHandler(new NodeMouseWheelHandler()
        {
            @Override
            public void onNodeMouseWheel(NodeMouseWheelEvent event)
            {
                if (false == m_wait)
                {
                    if (event.isShiftKeyDown())
                    {
                        if (event.isSouth())
                        {
                            m_zoom *= (1 / (1.1));
                        }
                        else
                        {
                            m_zoom *= (1 * (1.1));
                        }
                        m_xpos = (((event.getX() / 1600.0) * 3.5) - 2.5);

                        m_ypos = (((event.getY() / 1000.0) * 2.0) - 1.0);

                        m_wait = true;

                        draw(layer);

                        m_wait = false;
                    }
                }
            }
        });
        getWorkingContainer().add(getLienzoPanel());

        draw(layer);
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

    public void draw(Layer layer)
    {
        layer.clear();

        draw_0(buff.getData(), 1600, 1000, m_zoom, m_xpos, m_ypos, m_iter, m_rbuf, m_gbuf, m_bbuf);

        layer.getContext().putImageData(buff, 0, 0);
    }

    private final native void draw_0(JavaScriptObject data, int w, int h, double z, double mx, double my, int it, int[] ra, int[] ga, int[] ba)
    /*-{
		for (var y = 0; y < h; y++) {
			for (var x = 0; x < w; x++) {
				var px = (y * w + x) * 4;
				var pi = (y - h / 2) / (0.5 * z * h) + my;
				var pr = 1.5 * (x - w / 2) / (0.5 * z * w) + mx;
				var ne = 0;
				var oe = 0;
				var nm = 0;
				var om = 0;
				var lo = 0;
				for (lo = 0; lo < it; lo++) {
					oe = ne;
					om = nm;
					nm = 2 * oe * om + pi;
					ne = oe * oe - om * om + pr;
					if ((ne * ne + nm * nm) > 4) {
						break;
					}
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

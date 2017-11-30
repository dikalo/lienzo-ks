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

package com.ait.lienzo.ks.client.ui.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.ait.lienzo.ks.shared.XSS;
import com.ait.toolkit.sencha.ext.client.core.Component;
import com.ait.toolkit.sencha.ext.client.layout.ContainerLayout;
import com.ait.toolkit.sencha.ext.client.layout.Layout;
import com.ait.toolkit.sencha.ext.client.ui.Panel;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class KSPanel extends Panel
{
    public KSPanel()
    {
        setLayout(Layout.FIT);
    }

    public KSPanel(final ContainerLayout layout)
    {
        super(layout);
    }

    public KSPanel(final Layout layout)
    {
        super(layout);
    }

    public KSPanel(final String title)
    {
        this();

        setTitle(title);
    }

    public KSPanel(final Element element)
    {
        super(element);

        setLayout(Layout.FIT);
    }

    public KSPanel(final String title, final ContainerLayout layout)
    {
        super(title, layout);
    }

    public KSPanel(final String title, final Layout layout)
    {
        super(title, layout);
    }

    @Override
    public boolean remove(final Widget w)
    {
        if (w instanceof Component)
        {
            super.remove((Component) w, false);
        }
        else
        {
            w.removeFromParent();
        }
        return true;
    }

    @Override
    public Iterator<Widget> iterator()
    {
        final Component[] items = getComponents();

        final ArrayList<Widget> list = new ArrayList<Widget>(items.length);

        for (final Component item : items)
        {
            list.add(item);
        }
        return Collections.unmodifiableList(list).iterator();
    }

    @Override
    public void setTitle(final String title)
    {
        super.setTitle(XSS.get().clean(title));
    }

    @Override
    public void setTitle(final String title, final String iconcls)
    {
        super.setTitle(XSS.get().clean(title), iconcls);
    }
}

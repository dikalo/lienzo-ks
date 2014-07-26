/*
   Copyright (c) 2014 Ahome' Innovation Technologies. All rights reserved.

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
import com.ait.toolkit.sencha.ext.client.ui.Container;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class KSContainer extends Container
{
    public KSContainer()
    {
        setLayout(Layout.FIT);
    }

    public KSContainer(Element element)
    {
        super(element);
        
        setLayout(Layout.FIT);
    }

    public KSContainer(ContainerLayout layout)
    {
        this();

        setLayout(layout);
    }

    public KSContainer(Layout layout)
    {
        this();

        setLayout(layout);
    }

    public KSContainer(String title)
    {
        this();

        setTitle(title);
    }

    public KSContainer(String title, ContainerLayout layout)
    {
        this();

        setTitle(title);

        setLayout(layout);
    }

    public KSContainer(String title, Layout layout)
    {
        this();

        setTitle(title);

        setLayout(layout);
    }

    @Override
    public boolean remove(Widget w)
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
        Component[] items = getComponents();

        ArrayList<Widget> list = new ArrayList<Widget>(items.length);

        for (int i = 0; i < items.length; i++)
        {
            list.add(items[i]);
        }
        return Collections.unmodifiableList(list).iterator();
    }

    @Override
    public void setTitle(String title)
    {
        super.setTitle(XSS.get().clean(title));
    }
}

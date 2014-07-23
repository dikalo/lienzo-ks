
package com.ait.lienzo.ks.client.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.ait.toolkit.sencha.ext.client.core.Component;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.Layout;
import com.ait.toolkit.sencha.ext.client.ui.Container;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractViewComponent extends Container implements IViewComponent
{
    private boolean m_active = false;

    protected AbstractViewComponent()
    {
        setAutoScroll(false);

        setRegion(BorderRegion.CENTER);

        setLayout(Layout.FIT);
    }

    @Override
    public Component asViewComponent()
    {
        return this;
    }

    @Override
    public boolean isActive()
    {
        return m_active;
    }

    @Override
    public boolean activate()
    {
        if (false == m_active)
        {
            m_active = true;

            return true;
        }
        return false;
    }

    @Override
    public boolean suspend()
    {
        if (true == m_active)
        {
            m_active = false;

            return true;
        }
        return false;
    }

    @Override
    public String getSourceURL()
    {
        return "classpath:" + getClass().getName().replace('.', '/') + ".java";
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
}

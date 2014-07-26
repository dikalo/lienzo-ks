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

package com.ait.lienzo.ks.client.views;

import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.ui.components.KSContainer;
import com.ait.toolkit.sencha.ext.client.core.Component;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.Layout;

public abstract class AbstractViewComponent extends KSContainer implements IViewComponent
{
    private boolean     m_active = false;

    private LienzoPanel m_lienzo = null;

    protected AbstractViewComponent()
    {
        setAutoScroll(false);

        setRegion(BorderRegion.CENTER);

        setLayout(Layout.FIT);

        getElement().getStyle().setBackgroundColor("white");
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
        return getClass().getName().replace('.', '/') + ".java";
    }

    public void add(LienzoPanel lienzo)
    {
        super.add(m_lienzo = lienzo);
    }

    @Override
    public LienzoPanel getLienzoPanel()
    {
        return m_lienzo;
    }
}

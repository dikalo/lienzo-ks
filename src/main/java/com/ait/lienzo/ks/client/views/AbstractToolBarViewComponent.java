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

package com.ait.lienzo.ks.client.views;

import com.ait.lienzo.ks.client.ui.components.KSContainer;
import com.ait.lienzo.ks.client.ui.components.KSToolBar;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.Layout;

public abstract class AbstractToolBarViewComponent extends AbstractViewComponent
{
    private final KSToolBar   m_tool = new KSToolBar();

    private final KSContainer m_work = new KSContainer();

    protected AbstractToolBarViewComponent()
    {
        KSContainer main = new KSContainer(Layout.BORDER);

        m_tool.setRegion(BorderRegion.NORTH);

        m_tool.setHeight(30);

        main.add(m_tool);

        m_work.setRegion(BorderRegion.CENTER);

        main.add(m_work);

        add(main);
    }

    public KSToolBar getToolBarContainer()
    {
        return m_tool;
    }

    @Override
    public KSContainer getWorkingContainer()
    {
        return m_work;
    }
}

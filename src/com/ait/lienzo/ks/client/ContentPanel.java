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

package com.ait.lienzo.ks.client;

import com.ait.lienzo.ks.client.ui.components.KSPanel;
import com.ait.lienzo.ks.client.views.IViewComponent;
import com.ait.lienzo.ks.client.views.IViewFactoryCallback;
import com.ait.lienzo.ks.client.views.ViewFactoryInstance;
import com.ait.lienzo.ks.shared.KSViewNames;
import com.ait.lienzo.ks.shared.StringOps;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

public class ContentPanel extends KSPanel implements KSViewNames
{
    private String         m_link = null;

    private IViewComponent m_last = null;

    public ContentPanel()
    {
        setAutoScroll(false);

        setTitle("&nbsp;");

        setRegion(BorderRegion.CENTER);

        History.addValueChangeHandler(new ValueChangeHandler<String>()
        {
            @Override
            public void onValueChange(ValueChangeEvent<String> event)
            {
                String link = StringOps.toTrimOrNull(event.getValue());

                if (null != link)
                {
                    if (false == link.equals(m_link))
                    {
                        if (ViewFactoryInstance.get().isDefined(link))
                        {
                            m_link = link;

                            ViewFactoryInstance.get().make(link, new IViewFactoryCallback()
                            {
                                @Override
                                public void accept(IViewComponent component)
                                {
                                    doComponent(component);
                                }
                            });
                        }
                    }
                }
            }
        });
        String name = StringOps.toTrimOrNull(History.getToken());

        if ((null != name) && (ViewFactoryInstance.get().isDefined(name)))
        {
            History.fireCurrentHistoryState();
        }
        else
        {
            History.newItem(WELCOME);
        }
    }

    private final void doComponent(IViewComponent component)
    {
        if (null != m_last)
        {
            m_last.suspend();

            remove(m_last.asViewComponent());
        }
        add(component.asViewComponent());

        component.activate();

        m_last = component;
    }
}

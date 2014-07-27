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

import java.util.HashMap;

import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.ui.components.KSPanel;
import com.ait.lienzo.ks.client.ui.components.KSTabPanel;
import com.ait.lienzo.ks.client.views.IViewComponent;
import com.ait.lienzo.ks.client.views.IViewFactoryCallback;
import com.ait.lienzo.ks.client.views.ViewFactoryInstance;
import com.ait.lienzo.ks.shared.KSViewNames;
import com.ait.lienzo.ks.shared.StringOps;
import com.ait.toolkit.sencha.ext.client.core.Component;
import com.ait.toolkit.sencha.ext.client.events.tab.TabPanelBeforeTabChangeHandler;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.ui.TabPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;

public class ContentPanel extends KSPanel implements KSViewNames
{
    private static final GetSourceServiceAsync SERVICE = makeGetSourceService();

    private String                             m_link  = null;

    private ContentTabPanel                    m_last  = null;

    private HashMap<String, ContentTabPanel>   m_list  = new HashMap<String, ContentTabPanel>();

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
                            doProcessLink(link);
                        }
                    }
                }
            }
        });
    }

    public final void run()
    {
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

    private final void doProcessLink(String link)
    {
        m_link = link;

        ContentTabPanel component = m_list.get(m_link);

        if (null != component)
        {
            doReplaceView(component);
        }
        else
        {
            ViewFactoryInstance.get().make(link, new IViewFactoryCallback()
            {
                @Override
                public void accept(IViewComponent component)
                {
                    ContentTabPanel view = new ContentTabPanel(Example.getTextByLink(m_link), m_link, component);

                    m_list.put(m_link, view);

                    doReplaceView(view);
                }
            });
        }
    }

    private final void doReplaceView(ContentTabPanel component)
    {
        if (null != m_last)
        {
            m_last.suspend();

            remove(m_last);
        }
        add(component);

        doLayout();

        component.activate();

        m_last = component;
    }

    private final static GetSourceServiceAsync makeGetSourceService()
    {
        GetSourceServiceAsync service = GWT.create(GetSourceService.class);

        ((ServiceDefTarget) service).setServiceEntryPoint("GetSourceService.rpc");

        return service;
    }

    private final static class ContentTabPanel extends KSTabPanel
    {
        private final IViewComponent m_component;

        private final CodePanel      m_code;

        public ContentTabPanel(String title, String link, IViewComponent component)
        {
            m_component = component;

            KSPanel view = new KSPanel();

            view.setTitle(title);

            view.add(m_component.asViewComponent());

            add(view);

            m_code = new CodePanel(link, m_component.getSourceURL());

            add(m_code);

            addBeforeTabChangeHandler(new TabPanelBeforeTabChangeHandler()
            {
                @Override
                public boolean onEvent(TabPanel panel, Component npan, Component opan)
                {
                    Scheduler.get().scheduleDeferred(new ScheduledCommand()
                    {
                        @Override
                        public void execute()
                        {
                            m_code.highlight();
                        }
                    });
                    return true;
                }
            });
            LienzoPanel lienzo = m_component.getLienzoPanel();

            if (null != lienzo)
            {
                String json = toJSONString(lienzo.getViewport().toJSONObject().getJavaScriptObject());

                if (json != null)
                {
                    add(new JSONPanel(json));
                }
            }
        }

        public final void activate()
        {
            m_component.activate();
        }

        public final void suspend()
        {
            m_component.suspend();
        }

        final static native String toJSONString(JavaScriptObject value)
        /*-{
			return $wnd.JSON.stringify(value, null, '\t');
        }-*/;
    }

    private final static class CodePanel extends KSPanel
    {
        private final String m_link;

        public boolean       m_highlighted = false;

        public CodePanel(String link, String url)
        {
            m_link = link;

            setTitle("Source");

            setAutoScroll(true);

            SERVICE.getSource(url, new AsyncCallback<String>()
            {
                @Override
                public void onFailure(Throwable caught)
                {

                }

                @Override
                public void onSuccess(String result)
                {
                    add(new HTML("<pre name=\"" + m_link + "\" class=\"java:nocontrols\">" + result + "</pre>"));
                }
            });
        }

        public final void highlight()
        {
            if (false == m_highlighted)
            {
                m_highlighted = true;

                highlight(m_link);
            }
        }

        private native void highlight(String link)
        /*-{
			$wnd.dp.SyntaxHighlighter.HighlightAll(link);
        }-*/;
    }

    private final static class JSONPanel extends KSPanel
    {
        public JSONPanel(String json)
        {
            setTitle("JSON");

            setAutoScroll(true);

            add(new HTML("<pre>" + json + "</pre>"));
        }
    }
}

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

package com.ait.lienzo.ks.client;

import java.util.HashMap;

import com.ait.lienzo.client.core.config.LienzoCore;
import com.ait.lienzo.client.core.shape.Viewport;
import com.ait.lienzo.client.core.shape.json.IJSONSerializable;
import com.ait.lienzo.client.core.shape.json.JSONDeserializer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.analytics.GoogleAnalytics;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSContainer;
import com.ait.lienzo.ks.client.ui.components.KSPanel;
import com.ait.lienzo.ks.client.ui.components.KSTabPanel;
import com.ait.lienzo.ks.client.ui.components.KSToolBar;
import com.ait.lienzo.ks.client.views.IViewComponent;
import com.ait.lienzo.ks.client.views.IViewFactoryCallback;
import com.ait.lienzo.ks.client.views.KSViewNames;
import com.ait.lienzo.ks.client.views.ViewFactoryInstance;
import com.ait.lienzo.ks.shared.StringOps;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.events.tab.TabChangeEvent;
import com.ait.toolkit.sencha.ext.client.events.tab.TabChangeHandler;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.Layout;
import com.ait.toolkit.sencha.ext.client.ui.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
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

        GoogleAnalytics.get().sendEvent("view", link).go();

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

        private final JSONPanel      m_json;

        public ContentTabPanel(String title, String link, IViewComponent component)
        {
            m_component = component;

            KSPanel view = new KSPanel();

            view.setTitle(title);

            view.add(m_component.asViewComponent());

            add(view);

            m_code = new CodePanel(link, m_component);

            add(m_code);

            m_json = new JSONPanel(link, m_component);

            add(m_json);

            addTabChangeHandler(new TabChangeHandler()
            {
                @Override
                public boolean onTabChange(TabChangeEvent event)
                {
                    Scheduler.get().scheduleDeferred(new ScheduledCommand()
                    {
                        @Override
                        public void execute()
                        {
                            m_code.highlight();

                            m_json.highlight();
                        }
                    });
                    return true;
                }
            });
        }

        public final void activate()
        {
            m_component.activate();
        }

        public final void suspend()
        {
            m_component.suspend();
        }
    }

    private final static class CodePanel extends KSPanel
    {
        private final String m_link;

        private int          m_srcattempts = 0;

        private boolean      m_getsourceok = false;

        private boolean      m_highlighted = false;

        public CodePanel(String link, IViewComponent component)
        {
            m_link = "code_" + link;

            setTitle("Source");

            setAutoScroll(true);

            SERVICE.getSource(component.getSourceURL(), new AsyncCallback<String>()
            {
                @Override
                public void onFailure(Throwable caught)
                {
                }

                @Override
                public void onSuccess(String result)
                {
                    add(new HTML("<pre name=\"" + m_link + "\" class=\"java:nocontrols\">" + SafeHtmlUtils.htmlEscape(result) + "</pre>"));

                    m_getsourceok = true;
                }
            });
        }

        public final void highlight()
        {
            if (m_getsourceok)
            {
                if (false == m_highlighted)
                {
                    m_highlighted = true;

                    highlight(m_link);
                }
            }
            else
            {
                if (m_srcattempts < 10)
                {
                    m_srcattempts++;

                    RepeatingCommand retry = new RepeatingCommand()
                    {
                        @Override
                        public boolean execute()
                        {
                            highlight();

                            return false;
                        }
                    };
                    Scheduler.get().scheduleFixedDelay(retry, 100);
                }
            }
        }

        private native void highlight(String link)
        /*-{
			$wnd.dp.SyntaxHighlighter.HighlightAll(link);
        }-*/;
    }

    private final static class JSONPanel extends KSPanel
    {
        private final String         m_link;

        private final IViewComponent m_component;

        private KSPanel              m_ofjson = null;

        private KSContainer          m_main   = new KSContainer(Layout.BORDER);

        public JSONPanel(String link, IViewComponent component)
        {
            KSToolBar tool = new KSToolBar();

            tool.setRegion(BorderRegion.NORTH);

            tool.setHeight(30);

            m_link = "json_" + link;

            setTitle("JSON");

            setAutoScroll(true);

            final KSButton show = new KSButton("JSON Deserialize");
            
            show.disable();

            show.setWidth(90);

            tool.add(show);

            m_main.add(tool);

            add(m_main);

            m_component = component;

            Scheduler.get().scheduleDeferred(new ScheduledCommand()
            {
                @Override
                public void execute()
                {
                    show.enable();

                    show.addClickHandler(new ClickHandler()
                    {
                        @Override
                        public void onClick(ClickEvent event)
                        {
                            Window wind = new Window(Layout.FIT);

                            wind.setWidth(m_main.getWidth());

                            wind.setHeight(m_main.getHeight());

                            try
                            {
                                IJSONSerializable<?> node = JSONDeserializer.get().fromString(m_component.getLienzoPanel().getViewport().toJSONString());

                                if (null != node)
                                {
                                    if (node instanceof Viewport)
                                    {
                                        LienzoPanel lienzo = new LienzoPanel((Viewport) node);

                                        lienzo.setBackgroundLayer(m_component.getBackgroundLayer());

                                        wind.add(lienzo);
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                                LienzoCore.get().error(e.getMessage());
                            }
                            wind.setTitle("JSON Deserialized");

                            wind.show(m_main);
                        }
                    });
                }
            });
        }

        public final void highlight()
        {
            if (null != m_ofjson)
            {
                m_main.remove(m_ofjson, true);

                m_ofjson = null;
            }
            LienzoPanel lienzo = m_component.getLienzoPanel();

            if (null != lienzo)
            {
                String json = toJSONString(lienzo.getViewport().toJSONObject().getJavaScriptObject());

                LienzoCore.get().log(json);

                if (json != null)
                {
                    m_ofjson = new KSPanel();

                    m_ofjson.setRegion(BorderRegion.CENTER);

                    m_ofjson.setAutoScroll(true);

                    StringBuilder builder = new StringBuilder();

                    builder.append("/*\n");

                    builder.append("\tThis is a JSON representation of the node structure in the source tab for ");

                    builder.append(m_component.getSimpleClassName());

                    builder.append("\n*/\n");

                    builder.append(json);

                    m_ofjson.add(new HTML("<pre name=\"" + m_link + "\" class=\"js:nocontrols\">" + builder.toString() + "</pre>"));

                    m_main.add(m_ofjson);

                    highlight(m_link);
                }
            }
        }

        final static native String toJSONString(JavaScriptObject value)
        /*-{
			return $wnd.JSON.stringify(value, null, '\t');
        }-*/;

        private native void highlight(String link)
        /*-{
			$wnd.dp.SyntaxHighlighter.HighlightAll(link);
        }-*/;
    }
}

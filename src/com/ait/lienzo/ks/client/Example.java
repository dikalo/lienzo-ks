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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ait.lienzo.ks.shared.KSViewNames;
import com.ait.lienzo.ks.shared.StringOps;
import com.ait.toolkit.data.client.BaseTreeModel;

public final class Example extends BaseTreeModel implements KSViewNames
{
    public static final String             TEXT_FIELD = "text";

    public static BaseTreeModel            m_base     = null;

    private static HashMap<String, String> m_link     = new HashMap<String, String>();

    private static HashMap<String, String> m_text     = new HashMap<String, String>();

    public Example(String text, String link)
    {
        setText(text);

        setLeaf(true);

        if (null != (link = StringOps.toTrimOrNull(link)))
        {
            m_link.put(text, link);

            m_text.put(link, text);
        }
    }

    public Example(String text)
    {
        this(text, null);
    }

    public static void init()
    {
        if (null == m_base)
        {
            m_base = new BaseTreeModel();

            m_base.setChildren(getExamples());
        }
    }

    public static String getLinkByText(String text)
    {
        init();

        return m_link.get(text);
    }

    public static String getTextByLink(String link)
    {
        init();

        return m_text.get(link);
    }

    public static final BaseTreeModel getExamplesTreeModel()
    {
        init();

        return m_base;
    }

    private static List<Example> getExamples()
    {
        List<Example> examples = new ArrayList<Example>();

        examples.add(getOffsiteLinks());

        examples.add(new Example("Welcome", WELCOME));

        examples.add(new Example("Pie Chart", PIE_CHART));

        examples.add(new Example("Simple Image Filters", SIMPLE_IMAGE_FILTERS));

        return examples;
    }

    private static Example getOffsiteLinks()
    {
        Example root = new Example("Offsite Lienzo Links");

        root.setLeaf(false);

        List<Example> examples = new ArrayList<Example>();

        examples.add(new Example("Lienzo GitHub", "https://github.com/ahome-it/lienzo-core"));

        examples.add(new Example("Lienzo GitHub Issues", "https://github.com/ahome-it/lienzo-core/issues?milestone=1"));

        examples.add(new Example("Lienzo Wiki", "https://github.com/ahome-it/lienzo-core/wiki"));

        examples.add(new Example("Lienzo JavaDoc", "http://www.lienzo-core.com/javadoc/"));

        examples.add(new Example("About Ahomé", "http://opensource.ahome-it.com"));

        examples.add(new Example("Ahomé Google+", "https://plus.google.com/u/0/communities/106380618381566688303"));

        root.setChildren(examples);

        return root;
    }
}

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
import com.ait.toolkit.data.client.BaseTreeModel;

public final class Example extends BaseTreeModel implements KSViewNames
{
    public static final String             TEXT_FIELD = "text";

    private static HashMap<String, String> m_link     = new HashMap<String, String>();

    private static HashMap<String, String> m_text     = new HashMap<String, String>();

    public Example(String text, String link)
    {
        setText(text);

        setLeaf(true);

        if (null != link)
        {
            m_link.put(text, link);

            m_text.put(link, text);
        }
    }

    public Example(String text)
    {
        this(text, null);
    }

    public static String getLinkByText(String text)
    {
        return m_link.get(text);
    }

    public static String getTextByLink(String link)
    {
        return m_text.get(link);
    }

    public static List<Example> getExamples()
    {
        List<Example> examples = new ArrayList<Example>();

        Example about = new Example("Welcome to Lienzo", WELCOME);

        examples.add(about);

        return examples;
    }
}


package com.ait.lienzo.ks.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ait.lienzo.ks.shared.ViewNames;
import com.ait.toolkit.data.client.BaseTreeModel;

public final class Example extends BaseTreeModel implements ViewNames
{
    public static final String             TEXT_FIELD = "text";

    private static HashMap<String, String> m_link     = new HashMap<String, String>();

    public Example(String name, String link)
    {
        setText(name);

        setLeaf(true);

        if (null != link)
        {
            m_link.put(name, link);
        }
    }

    public Example(String name)
    {
        this(name, null);
    }

    public static String getLink(String name)
    {
        return m_link.get(name);
    }

    public static List<Example> getExamples()
    {
        List<Example> examples = new ArrayList<Example>();

        Example about = new Example("Welcome to Lienzo", WELCOME);

        examples.add(about);

        return examples;
    }
}

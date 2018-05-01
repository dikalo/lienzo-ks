/*
 * Copyright (c) 2018 Ahome' Innovation Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ait.lienzo.ks.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ait.lienzo.ks.client.views.KSViewNames;
import com.ait.lienzo.ks.shared.StringOps;
import com.ait.toolkit.data.client.BaseTreeModel;

public final class Example extends BaseTreeModel implements KSViewNames
{
    public static final String             TEXT_FIELD = "text";

    public static BaseTreeModel            m_base     = null;

    private static HashMap<String, String> m_link     = new HashMap<>();

    private static HashMap<String, String> m_text     = new HashMap<>();

    private Example(final String text, String link)
    {
        setText(text);

        setLeaf(true);

        if (null != (link = StringOps.toTrimOrNull(link)))
        {
            m_link.put(text, link);

            m_text.put(link, text);
        }
    }

    private Example(final String text)
    {
        this(text, null);
    }

    private static final void init()
    {
        if (null == m_base)
        {
            m_base = new BaseTreeModel();

            m_base.setChildren(getExamples());
        }
    }

    public static final String getLinkByText(final String text)
    {
        init();

        return m_link.get(text);
    }

    public static final String getTextByLink(final String link)
    {
        init();

        return m_text.get(link);
    }

    public static final BaseTreeModel getExamplesTreeModel()
    {
        init();

        return m_base;
    }

    private static final List<Example> getExamples()
    {
        final List<Example> examples = new ArrayList<>();

        examples.add(getOffsiteLinks());

        examples.add(new Example("Welcome", WELCOME));

        /*
        examples.add(new Example("Pie Chart", PIE_CHART));

        examples.add(new Example("Bar Chart", BAR_CHART));

        examples.add(new Example("Line Chart", LINE_CHART));
         */

        examples.add(new Example("SVG Tiger", TIGER));

        examples.add(new Example("Polygon Lion", LION));

        examples.add(new Example("Animation Playpen", PLAYPEN));

        examples.add(new Example("Attributes Batched", ATTRIBUTES_BATCHED));

        examples.add(new Example("Sprites", SPRITES));

        examples.add(new Example("Poly Lines", POLYLINES));

        examples.add(new Example("Corner Radius", CORNER_RADIUS));

        examples.add(new Example("MultiPath Resize", MULTIPATH_RESIZE));

        examples.add(getWiresExamples());

        examples.add(getPicturesMovie());

        examples.add(getBoundingBoxes());

        examples.add(getOtherExamples());

        return examples;
    }

    private static final Example getOffsiteLinks()
    {
        final Example root = new Example("Offsite Lienzo Links");

        root.setLeaf(false);

        final List<Example> examples = new ArrayList<>();

        examples.add(new Example("Lienzo GitHub", "https://github.com/ahome-it/lienzo-core"));

        examples.add(new Example("Lienzo GitHub Issues", "https://github.com/ahome-it/lienzo-core/issues"));

        examples.add(new Example("Lienzo Wiki", "https://github.com/ahome-it/lienzo-core/wiki"));

        examples.add(new Example("Lienzo JavaDoc", "http://docs.themodernway.com/documents/javadoc/lienzo-core/"));

        examples.add(new Example("Lienzo Kitchen Sink GitHub", "https://github.com/ahome-it/lienzo-ks"));

        examples.add(new Example("About Ahomé", "http://opensource.ahome-it.com"));

        examples.add(new Example("Ahomé Google+", "https://plus.google.com/u/0/communities/106380618381566688303"));

        root.setChildren(examples);

        return root;
    }

    private static final Example getBoundingBoxes()
    {
        final Example root = new Example("Bounding Boxes");

        root.setLeaf(false);

        root.setExpanded(true);

        final List<Example> examples = new ArrayList<>();

        examples.add(new Example("Bezier Curve", BEZIER_BOUNDING));

        examples.add(new Example("Quadratic Curve", QUADRATIC_BOUNDING));

        examples.add(new Example("Spline", SPLINE_BOUNDING));

        examples.add(new Example("Text", TEXT_BOUNDING));

        examples.add(new Example("MultiPath", MULTIPATH_BOUNDING));

        examples.add(new Example("SVGPath", SVG_BOUNDING));

        root.setChildren(examples);

        return root;
    }

    private static final Example getPicturesMovie()
    {
        final Example root = new Example("Picture and Movie");

        root.setLeaf(false);

        root.setExpanded(true);

        final List<Example> examples = new ArrayList<>();

        examples.add(new Example("Image Events", IMAGE_EVENTS));

        examples.add(new Example("Picture Filters", PICTURE_FILTERS));

        examples.add(new Example("Movie Filters", MOVIE_FILTERS));

        examples.add(new Example("Green Screen", GREEN_SCREEN));

        root.setChildren(examples);

        return root;
    }

    private static final Example getOtherExamples()
    {
        final Example root = new Example("Other Examples");

        root.setLeaf(false);

        root.setExpanded(true);

        final List<Example> examples = new ArrayList<>();

        examples.add(new Example("Rectangle Events", RECTANGLE_CLICK_TEST));

        examples.add(new Example("Mandelbrot Explorer", MANDELBROT));

        root.setChildren(examples);

        return root;
    }

    private static final Example getWiresExamples()
    {
        final Example root = new Example("Wires Examples");

        root.setLeaf(false);

        root.setExpanded(true);

        final List<Example> examples = new ArrayList<>();

        examples.add(new Example("Align Distribute", ALIGN_DISTRIBUTE));

        examples.add(new Example("Cardinal Intersect", CARDINAL_INTERSECT));

        examples.add(new Example("Wires with Arrows", WIRES_ARROWS));

        examples.add(new Example("Wires with Squares", WIRES_SQUARES));

        examples.add(new Example("Wires Resize", WIRES_RESIZE));

        examples.add(new Example("Wires Docking", WIRES_DOCKING));

        root.setChildren(examples);

        return root;
    }
}

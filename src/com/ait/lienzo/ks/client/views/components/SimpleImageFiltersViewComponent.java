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

package com.ait.lienzo.ks.client.views.components;

import java.util.LinkedHashMap;

import com.ait.lienzo.client.core.image.PictureFilteredHandler;
import com.ait.lienzo.client.core.image.PictureLoadedHandler;
import com.ait.lienzo.client.core.image.filter.AlphaScaleColorImageDataFilter;
import com.ait.lienzo.client.core.image.filter.AverageGrayScaleImageDataFilter;
import com.ait.lienzo.client.core.image.filter.BrightnessImageDataFilter;
import com.ait.lienzo.client.core.image.filter.BumpImageDataFilter;
import com.ait.lienzo.client.core.image.filter.ColorLuminosityImageDataFilter;
import com.ait.lienzo.client.core.image.filter.ContrastImageDataFilter;
import com.ait.lienzo.client.core.image.filter.DiffusionImageDataFilter;
import com.ait.lienzo.client.core.image.filter.EdgeDetectImageDataFilter;
import com.ait.lienzo.client.core.image.filter.EmbossImageDataFilter;
import com.ait.lienzo.client.core.image.filter.ExposureImageDataFilter;
import com.ait.lienzo.client.core.image.filter.GainImageDataFilter;
import com.ait.lienzo.client.core.image.filter.GammaImageDataFilter;
import com.ait.lienzo.client.core.image.filter.HueImageDataFilter;
import com.ait.lienzo.client.core.image.filter.ImageDataFilterChain;
import com.ait.lienzo.client.core.image.filter.InvertColorImageDataFilter;
import com.ait.lienzo.client.core.image.filter.LightnessGrayScaleImageDataFilter;
import com.ait.lienzo.client.core.image.filter.LuminosityGrayScaleImageDataFilter;
import com.ait.lienzo.client.core.image.filter.PosterizeImageDataFilter;
import com.ait.lienzo.client.core.image.filter.SharpenImageDataFilter;
import com.ait.lienzo.client.core.image.filter.SharpenImageDataFilter.SharpenType;
import com.ait.lienzo.client.core.image.filter.SolarizeImageDataFilter;
import com.ait.lienzo.client.core.image.filter.StackBlurImageDataFilter;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Picture;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.ui.components.KSComboBox;
import com.ait.lienzo.ks.client.ui.components.KSContainer;
import com.ait.lienzo.ks.client.ui.components.KSToolBar;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.ImageSelectionMode;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeEvent;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeHandler;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.Layout;

public class SimpleImageFiltersViewComponent extends AbstractViewComponent
{
    private Picture           m_original;

    private Picture           m_modified;

    private Text              m_captions;

    private final LienzoPanel m_lienzo = new LienzoPanel();

    public SimpleImageFiltersViewComponent()
    {
        KSContainer main = new KSContainer(Layout.BORDER);

        KSToolBar tool = new KSToolBar();

        tool.setRegion(BorderRegion.NORTH);

        tool.setHeight(30);

        final LinkedHashMap<String, String> pick = new LinkedHashMap<String, String>();

        pick.put("-- Select --", "NONE");

        pick.put("Stack Blur", "BLUR");

        pick.put("Sharpen Hard", "SHARPEN_HARD");

        pick.put("Sharpen Soft", "SHARPEN_SOFT");

        pick.put("Gray Luminosity", "GRAY_LUMINOSITY");

        pick.put("Gray Lightness", "GRAY_LIGHTNESS");

        pick.put("Gray Average", "GRAY_AVERAGE");

        pick.put("Gray + Stack Blur", "GRAY_BLUR");

        pick.put("Gray + Sharpen", "GRAY_SHARPEN");

        pick.put("Sepia Tone", "SEPIA");

        pick.put("Brighten", "BRIGHTEN");

        pick.put("Darken", "DARKEN");

        pick.put("Invert", "INVERT");

        pick.put("Emboss", "EMBOSS");

        pick.put("Edge Detect", "EDGE");

        pick.put("Diffusion", "DIFFUSION");

        pick.put("Contrast", "CONTRAST");

        pick.put("Exposure", "EXPOSURE");

        pick.put("Hue", "HUE");

        pick.put("Gain", "GAIN");

        pick.put("Posterize", "POSTERIZE");

        pick.put("Solarize", "SOLARIZE");

        pick.put("Bump", "BUMP");

        pick.put("Gamma 0.3", "GAMMA_03");

        pick.put("Gamma 2.0", "GAMMA_20");

        pick.put("Alpha", "ALPHA");

        pick.put("Alpha Inverted", "ALPHA_INVERTED");

        KSComboBox cbox = new KSComboBox(pick);

        cbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                filter(pick.get(event.getNewValue()));
            }
        });
        tool.add(cbox);

        main.add(tool);

        final Layer layer = new Layer();

        new Picture("Lena_512.png", ImageSelectionMode.SELECT_BOUNDS).onLoaded(new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(Picture picture)
            {
                m_original = picture;

                m_original.setX(10).setY(10);

                layer.add(m_original);

                layer.batch();
            }
        });
        new Picture("Lena_512.png", ImageSelectionMode.SELECT_BOUNDS).onLoaded(new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(Picture picture)
            {
                m_modified = picture;

                m_modified.setX(530).setY(10);

                layer.add(m_modified);

                layer.batch();
            }
        });
        m_captions = new Text("No filter active").setFillColor(ColorName.BLACK).setX(6).setY(600);

        layer.add(m_captions);

        m_lienzo.add(layer);

        m_lienzo.setBackgroundLayer(new StandardBackgroundLayer());

        KSContainer work = new KSContainer();

        work.setRegion(BorderRegion.CENTER);

        work.add(m_lienzo);

        main.add(work);

        add(main);
    }

    public void filter(String value)
    {
        PictureFilteredHandler handler = new PictureFilteredHandler()
        {
            @Override
            public void onPictureFiltered(Picture picture)
            {
                picture.getLayer().batch();
            }
        };
        if ((null != value) && (false == value.isEmpty()))
        {
            if ("NONE".equals(value))
            {
                m_captions.setText("No filter active");

                m_modified.clearFilters().reFilter(handler);
            }
            else if ("BLUR".equals(value))
            {
                m_captions.setText("Stack blur radius 4");

                m_modified.setFilters(new StackBlurImageDataFilter(4)).reFilter(handler);
            }
            else if ("SHARPEN_HARD".equals(value))
            {
                m_captions.setText("Sharpen hard by convolve");

                m_modified.setFilters(new SharpenImageDataFilter(SharpenType.HARD)).reFilter(handler);
            }
            else if ("SHARPEN_SOFT".equals(value))
            {
                m_captions.setText("Sharpen soft by convolve");

                m_modified.setFilters(new SharpenImageDataFilter(SharpenType.SOFT)).reFilter(handler);
            }
            else if ("GRAY_LUMINOSITY".equals(value))
            {
                m_captions.setText("Grayscale by luminosity");

                m_modified.setFilters(new LuminosityGrayScaleImageDataFilter()).reFilter(handler);
            }
            else if ("GRAY_LIGHTNESS".equals(value))
            {
                m_captions.setText("Grayscale by lightness");

                m_modified.setFilters(new LightnessGrayScaleImageDataFilter()).reFilter(handler);
            }
            else if ("GRAY_AVERAGE".equals(value))
            {
                m_captions.setText("Grayscale by average");

                m_modified.setFilters(new AverageGrayScaleImageDataFilter()).reFilter(handler);
            }
            else if ("GRAY_BLUR".equals(value))
            {
                m_captions.setText("Grayscale by luminosity + Stack blur");

                m_modified.setFilters(new LuminosityGrayScaleImageDataFilter(), new StackBlurImageDataFilter(4)).reFilter(handler);
            }
            else if ("GRAY_SHARPEN".equals(value))
            {
                m_captions.setText("Grayscale by luminosity + Sharpen");

                m_modified.setFilters(new ImageDataFilterChain(new LuminosityGrayScaleImageDataFilter(), new SharpenImageDataFilter(SharpenType.HARD))).reFilter(handler);
            }
            else if ("SEPIA".equals(value))
            {
                m_captions.setText("Color replacement by luminosity");

                m_modified.setFilters(new ColorLuminosityImageDataFilter(ColorName.PEACHPUFF.getColor().brightness(0.1))).reFilter(handler);
            }
            else if ("BRIGHTEN".equals(value))
            {
                m_captions.setText("Brighten by 30%");

                m_modified.setFilters(new BrightnessImageDataFilter(0.3)).reFilter(handler);
            }
            else if ("DARKEN".equals(value))
            {
                m_captions.setText("Darken by 30%");

                m_modified.setFilters(new BrightnessImageDataFilter(-0.3)).reFilter(handler);
            }
            else if ("INVERT".equals(value))
            {
                m_captions.setText("Invert colors");

                m_modified.setFilters(new InvertColorImageDataFilter()).reFilter(handler);
            }
            else if ("EMBOSS".equals(value))
            {
                m_captions.setText("Emboss image (experimental)");

                m_modified.setFilters(new EmbossImageDataFilter()).reFilter(handler);
            }
            else if ("EDGE".equals(value))
            {
                m_captions.setText("Edge Detect (experimental)");

                m_modified.setFilters(new EdgeDetectImageDataFilter()).reFilter(handler);
            }
            else if ("DIFFUSION".equals(value))
            {
                m_captions.setText("Diffusion 8 (experimental)");

                m_modified.setFilters(new DiffusionImageDataFilter(8)).reFilter(handler);
            }
            else if ("CONTRAST".equals(value))
            {
                m_captions.setText("Contrast 1.5 (experimental)");

                m_modified.setFilters(new ContrastImageDataFilter(1.5)).reFilter(handler);
            }
            else if ("EXPOSURE".equals(value))
            {
                m_captions.setText("Exposure 4.0 (experimental)");

                m_modified.setFilters(new ExposureImageDataFilter(4)).reFilter(handler);
            }
            else if ("GAIN".equals(value))
            {
                m_captions.setText("Gain 0.20 0.45 (experimental)");

                m_modified.setFilters(new GainImageDataFilter(0.20, 0.45)).reFilter(handler);
            }
            else if ("HUE".equals(value))
            {
                m_captions.setText("Hue 0.5 (experimental)");

                m_modified.setFilters(new HueImageDataFilter(0.5)).reFilter(handler);
            }
            else if ("POSTERIZE".equals(value))
            {
                m_captions.setText("Posterize 6 (experimental)");

                m_modified.setFilters(new PosterizeImageDataFilter(6)).reFilter(handler);
            }
            else if ("SOLARIZE".equals(value))
            {
                m_captions.setText("Solarize (experimental)");

                m_modified.setFilters(new SolarizeImageDataFilter()).reFilter(handler);
            }
            else if ("BUMP".equals(value))
            {
                m_captions.setText("Bump (experimental)");

                m_modified.setFilters(new BumpImageDataFilter()).reFilter(handler);
            }
            else if ("GAMMA_03".equals(value))
            {
                m_captions.setText("Gamma 0.3 (experimental)");

                m_modified.setFilters(new GammaImageDataFilter(0.3)).reFilter(handler);
            }
            else if ("GAMMA_20".equals(value))
            {
                m_captions.setText("Gamma 2.0 (experimental)");

                m_modified.setFilters(new GammaImageDataFilter(2.0)).reFilter(handler);
            }
            else if ("ALPHA".equals(value))
            {
                m_captions.setText("Luminosity sets alpha + BLUE");

                m_modified.setFilters(new AlphaScaleColorImageDataFilter(ColorName.BLUE)).reFilter(handler);
            }
            else if ("ALPHA_INVERTED".equals(value))
            {
                m_captions.setText("Luminosity inverts alpha + BLUE");

                m_modified.setFilters(new AlphaScaleColorImageDataFilter(ColorName.BLUE, true)).reFilter(handler);
            }
        }
    }

    @Override
    public LienzoPanel getLienzoPanel()
    {
        return m_lienzo;
    }
}

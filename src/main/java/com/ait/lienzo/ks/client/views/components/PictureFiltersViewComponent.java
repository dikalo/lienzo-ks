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

package com.ait.lienzo.ks.client.views.components;

import com.ait.lienzo.client.core.image.PictureFilteredHandler;
import com.ait.lienzo.client.core.image.PictureLoadedHandler;
import com.ait.lienzo.client.core.image.filter.*;
import com.ait.lienzo.client.core.image.filter.SharpenImageDataFilter.SharpenType;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Picture;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.ks.client.ui.components.KSComboBox;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.ImageSelectionMode;
import com.ait.lienzo.shared.core.types.ImageSerializationMode;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeEvent;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeHandler;

import java.util.LinkedHashMap;

public class PictureFiltersViewComponent extends AbstractToolBarViewComponent
{
    private Picture m_lena_orig;

    private Picture m_lena_mods;

    private Picture m_blog_orig;

    private Picture m_blog_mods;

    private Picture m_this_mods;

    private Text    m_captions;

    private String  m_fi_value = "NONE";

    public PictureFiltersViewComponent()
    {
        final LinkedHashMap<String, String> mode = new LinkedHashMap<String, String>();

        mode.put("-- Select --", "NONE");

        mode.put("Stack Blur", "BLUR");

        mode.put("Sharpen Hard", "SHARPEN_HARD");

        mode.put("Sharpen Soft", "SHARPEN_SOFT");

        mode.put("Gray Luminosity", "GRAY_LUMINOSITY");

        mode.put("Gray Lightness", "GRAY_LIGHTNESS");

        mode.put("Gray Average", "GRAY_AVERAGE");

        mode.put("Gray + Stack Blur", "GRAY_BLUR");

        mode.put("Gray + Sharpen", "GRAY_SHARPEN");

        mode.put("Sepia Tone", "SEPIA");

        mode.put("Brighten", "BRIGHTEN");

        mode.put("Darken", "DARKEN");

        mode.put("Invert", "INVERT");

        mode.put("Emboss", "EMBOSS");

        mode.put("Edge Detect", "EDGE");

        mode.put("Diffusion", "DIFFUSION");

        mode.put("Contrast", "CONTRAST");

        mode.put("Exposure", "EXPOSURE");

        mode.put("Hue", "HUE");

        mode.put("Gain", "GAIN");

        mode.put("Posterize", "POSTERIZE");

        mode.put("Solarize", "SOLARIZE");

        mode.put("Bump", "BUMP");

        mode.put("Gamma 0.3", "GAMMA_03");

        mode.put("Gamma 2.0", "GAMMA_20");

        mode.put("Alpha", "ALPHA");

        mode.put("Alpha Inverted", "ALPHA_INVERTED");

        KSComboBox mbox = new KSComboBox(mode);

        mbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                m_fi_value = mode.get(event.getNewValue());

                filter(m_fi_value, m_this_mods);
            }
        });
        getToolBarContainer().add(mbox);

        final LinkedHashMap<String, String> what = new LinkedHashMap<String, String>();

        what.put("Lena Test", "LENA");

        what.put("Blog Logo", "BLOG");

        KSComboBox wbox = new KSComboBox(what);

        wbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                String valu = what.get(event.getNewValue());

                if ("BLOG".equals(valu))
                {
                    m_lena_orig.setVisible(false);

                    m_lena_mods.setVisible(false);

                    m_blog_orig.setVisible(true);

                    m_this_mods = m_blog_mods.setVisible(true);

                    m_captions.setY(320);
                }
                else
                {
                    m_blog_orig.setVisible(false);

                    m_blog_mods.setVisible(false);

                    m_lena_orig.setVisible(true);

                    m_this_mods = m_lena_mods.setVisible(true);

                    m_captions.setY(600);
                }
                filter(m_fi_value, m_this_mods);
            }
        });
        getToolBarContainer().add(wbox);

        final Layer layer = new Layer();

        new Picture("Lena_512.png", new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(Picture picture)
            {
                m_lena_orig = picture;

                m_lena_orig.setX(10).setY(10).setListening(false);

                m_lena_orig.setImageSerializationMode(ImageSerializationMode.DATA_URL);

                layer.add(m_lena_orig);

                layer.batch();
            }
        }, ImageSelectionMode.SELECT_BOUNDS);
        new Picture("Lena_512.png", new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(Picture picture)
            {
                m_lena_mods = picture;

                m_lena_mods.setX(530).setY(10).setListening(false);

                layer.add(m_lena_mods);

                m_this_mods = m_lena_mods;

                layer.batch();
            }
        }, ImageSelectionMode.SELECT_BOUNDS);
        new Picture("blogjet256x256.png", new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(Picture picture)
            {
                m_blog_orig = picture;

                m_blog_orig.setX(10).setY(10).setVisible(false).setListening(false);

                m_blog_orig.setImageSerializationMode(ImageSerializationMode.DATA_URL);

                layer.add(m_blog_orig);
            }
        }, ImageSelectionMode.SELECT_BOUNDS);
        new Picture("blogjet256x256.png", new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(Picture picture)
            {
                m_blog_mods = picture;

                m_blog_mods.setX(280).setY(10).setVisible(false).setListening(false);

                layer.add(m_blog_mods);
            }
        }, ImageSelectionMode.SELECT_BOUNDS);
        m_captions = new Text("No filter active").setFillColor(ColorName.BLACK).setX(6).setY(600).setFontSize(48);

        layer.add(m_captions);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    public void filter(String value, final Picture pict)
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

                pict.clearFilters().reFilter(handler);
            }
            else if ("BLUR".equals(value))
            {
                m_captions.setText("Stack blur radius 4");

                pict.setFilters(new StackBlurImageDataFilter(4)).reFilter(handler);
            }
            else if ("SHARPEN_HARD".equals(value))
            {
                m_captions.setText("Sharpen hard by convolve");

                pict.setFilters(new SharpenImageDataFilter(SharpenType.HARD)).reFilter(handler);
            }
            else if ("SHARPEN_SOFT".equals(value))
            {
                m_captions.setText("Sharpen soft by convolve");

                pict.setFilters(new SharpenImageDataFilter(SharpenType.SOFT)).reFilter(handler);
            }
            else if ("GRAY_LUMINOSITY".equals(value))
            {
                m_captions.setText("Grayscale by luminosity");

                pict.setFilters(new LuminosityGrayScaleImageDataFilter()).reFilter(handler);
            }
            else if ("GRAY_LIGHTNESS".equals(value))
            {
                m_captions.setText("Grayscale by lightness");

                pict.setFilters(new LightnessGrayScaleImageDataFilter()).reFilter(handler);
            }
            else if ("GRAY_AVERAGE".equals(value))
            {
                m_captions.setText("Grayscale by average");

                pict.setFilters(new AverageGrayScaleImageDataFilter()).reFilter(handler);
            }
            else if ("GRAY_BLUR".equals(value))
            {
                m_captions.setText("Grayscale by luminosity + Stack blur");

                pict.setFilters(new LuminosityGrayScaleImageDataFilter(), new StackBlurImageDataFilter(4)).reFilter(handler);
            }
            else if ("GRAY_SHARPEN".equals(value))
            {
                m_captions.setText("Grayscale by luminosity + Sharpen");

                pict.setFilters(new ImageDataFilterChain(new LuminosityGrayScaleImageDataFilter(), new SharpenImageDataFilter(SharpenType.HARD))).reFilter(handler);
            }
            else if ("SEPIA".equals(value))
            {
                m_captions.setText("Color replacement by luminosity");

                pict.setFilters(new ColorLuminosityImageDataFilter(ColorName.PEACHPUFF.getColor().brightness(0.1))).reFilter(handler);
            }
            else if ("BRIGHTEN".equals(value))
            {
                m_captions.setText("Brighten by 30%");

                pict.setFilters(new BrightnessImageDataFilter(0.3)).reFilter(handler);
            }
            else if ("DARKEN".equals(value))
            {
                m_captions.setText("Darken by 30%");

                pict.setFilters(new BrightnessImageDataFilter(-0.3)).reFilter(handler);
            }
            else if ("INVERT".equals(value))
            {
                m_captions.setText("Invert colors");

                pict.setFilters(new InvertColorImageDataFilter()).reFilter(handler);
            }
            else if ("EMBOSS".equals(value))
            {
                m_captions.setText("Emboss");

                pict.setFilters(new EmbossImageDataFilter()).reFilter(handler);
            }
            else if ("EDGE".equals(value))
            {
                m_captions.setText("Edge Detect");

                pict.setFilters(new EdgeDetectImageDataFilter()).reFilter(handler);
            }
            else if ("DIFFUSION".equals(value))
            {
                m_captions.setText("Diffusion 8");

                pict.setFilters(new DiffusionImageDataFilter(8)).reFilter(handler);
            }
            else if ("CONTRAST".equals(value))
            {
                m_captions.setText("Contrast 1.5");

                pict.setFilters(new ContrastImageDataFilter(1.5)).reFilter(handler);
            }
            else if ("EXPOSURE".equals(value))
            {
                m_captions.setText("Exposure 4.0");

                pict.setFilters(new ExposureImageDataFilter(4)).reFilter(handler);
            }
            else if ("GAIN".equals(value))
            {
                m_captions.setText("Gain 0.20 0.45");

                pict.setFilters(new GainImageDataFilter(0.20, 0.45)).reFilter(handler);
            }
            else if ("HUE".equals(value))
            {
                m_captions.setText("Hue 0.5");

                pict.setFilters(new HueImageDataFilter(0.5)).reFilter(handler);
            }
            else if ("POSTERIZE".equals(value))
            {
                m_captions.setText("Posterize 6");

                pict.setFilters(new PosterizeImageDataFilter(6)).reFilter(handler);
            }
            else if ("SOLARIZE".equals(value))
            {
                m_captions.setText("Solarize");

                pict.setFilters(new SolarizeImageDataFilter()).reFilter(handler);
            }
            else if ("BUMP".equals(value))
            {
                m_captions.setText("Bump");

                pict.setFilters(new BumpImageDataFilter()).reFilter(handler);
            }
            else if ("GAMMA_03".equals(value))
            {
                m_captions.setText("Gamma 0.3");

                pict.setFilters(new GammaImageDataFilter(0.3)).reFilter(handler);
            }
            else if ("GAMMA_20".equals(value))
            {
                m_captions.setText("Gamma 2.0");

                pict.setFilters(new GammaImageDataFilter(2.0)).reFilter(handler);
            }
            else if ("ALPHA".equals(value))
            {
                m_captions.setText("Luminosity sets alpha + BLUE");

                pict.setFilters(new AlphaScaleColorImageDataFilter(ColorName.BLUE)).reFilter(handler);
            }
            else if ("ALPHA_INVERTED".equals(value))
            {
                m_captions.setText("Luminosity inverts alpha + BLUE");

                pict.setFilters(new AlphaScaleColorImageDataFilter(ColorName.BLUE, true)).reFilter(handler);
            }
        }
    }
}

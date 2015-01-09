/*
   Copyright (c) 2014,2015 Ahome' Innovation Technologies. All rights reserved.

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

import com.ait.lienzo.client.core.config.LienzoCore;
import com.ait.lienzo.client.core.image.filter.AverageGrayScaleImageDataFilter;
import com.ait.lienzo.client.core.image.filter.BrightnessImageDataFilter;
import com.ait.lienzo.client.core.image.filter.BumpImageDataFilter;
import com.ait.lienzo.client.core.image.filter.ColorLuminosityImageDataFilter;
import com.ait.lienzo.client.core.image.filter.ContrastImageDataFilter;
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
import com.ait.lienzo.client.core.shape.Movie;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSComboBox;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeEvent;
import com.ait.toolkit.sencha.ext.client.events.form.ChangeHandler;

public class MovieFiltersViewComponent extends AbstractToolBarViewComponent
{
    private final Movie    m_movie;

    private final Text     m_captions;

    private final KSButton m_play = new KSButton("Play");

    public MovieFiltersViewComponent()
    {
        m_play.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (m_movie.isPaused())
                {
                    m_movie.play();

                    m_play.setText("Pause");
                }
                else
                {
                    m_movie.pause();

                    m_play.setText("Play");
                }
            }
        });
        m_play.setWidth(90);

        getToolBarContainer().add(m_play);

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

        pick.put("Contrast", "CONTRAST");

        pick.put("Exposure", "EXPOSURE");

        pick.put("Hue", "HUE");

        pick.put("Gain", "GAIN");

        pick.put("Posterize", "POSTERIZE");

        pick.put("Solarize", "SOLARIZE");

        pick.put("Bump", "BUMP");

        pick.put("Gamma 0.3", "GAMMA_03");

        pick.put("Gamma 2.0", "GAMMA_20");

        KSComboBox cbox = new KSComboBox(pick);

        final Layer label = new Layer();

        cbox.addChangeHandler(new ChangeHandler()
        {
            @Override
            public void onChange(ChangeEvent event)
            {
                filter(pick.get(event.getNewValue()));
            }
        });
        getToolBarContainer().add(cbox);

        final Layer layer = new Layer();

        layer.setClearLayerBeforeDraw(false);

        m_movie = new Movie(LienzoCore.get().isFirefox() ? "BigBuck.WebM" : "BigBuck.mp4").setWidth(640).setHeight(360).setX(10).setY(10).setShowPoster(true).setFillColor(ColorName.BLACK).setListening(false).setLoop(true);

        layer.add(m_movie);

        m_captions = new Text("").setFillColor(ColorName.BLACK).setX(6).setY(430);

        label.add(m_captions);

        getLienzoPanel().add(layer, label);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    public void filter(String value)
    {
        if ((null != value) && (false == value.isEmpty()))
        {
            if ("NONE".equals(value))
            {
                m_captions.setText("").getLayer().draw();

                m_movie.clearFilters();
            }
            else if ("BLUR".equals(value))
            {
                m_captions.setText("Stack blur radius 4").getLayer().draw();

                m_movie.setFilters(new StackBlurImageDataFilter(4));
            }
            else if ("SHARPEN_HARD".equals(value))
            {
                m_captions.setText("Sharpen hard by convolve").getLayer().draw();

                m_movie.setFilters(new SharpenImageDataFilter(SharpenType.HARD));
            }
            else if ("SHARPEN_SOFT".equals(value))
            {
                m_captions.setText("Sharpen soft by convolve").getLayer().draw();

                m_movie.setFilters(new SharpenImageDataFilter(SharpenType.SOFT));
            }
            else if ("GRAY_LUMINOSITY".equals(value))
            {
                m_captions.setText("Grayscale by luminosity").getLayer().draw();

                m_movie.setFilters(new LuminosityGrayScaleImageDataFilter());
            }
            else if ("GRAY_LIGHTNESS".equals(value))
            {
                m_captions.setText("Grayscale by lightness").getLayer().draw();

                m_movie.setFilters(new LightnessGrayScaleImageDataFilter());
            }
            else if ("GRAY_AVERAGE".equals(value))
            {
                m_captions.setText("Grayscale by average").getLayer().draw();

                m_movie.setFilters(new AverageGrayScaleImageDataFilter());
            }
            else if ("GRAY_BLUR".equals(value))
            {
                m_captions.setText("Grayscale by luminosity + Stack blur").getLayer().draw();

                m_movie.setFilters(new LuminosityGrayScaleImageDataFilter(), new StackBlurImageDataFilter(4));
            }
            else if ("GRAY_SHARPEN".equals(value))
            {
                m_captions.setText("Grayscale by luminosity + Sharpen").getLayer().draw();

                m_movie.setFilters(new ImageDataFilterChain(new LuminosityGrayScaleImageDataFilter(), new SharpenImageDataFilter(SharpenType.HARD)));
            }
            else if ("SEPIA".equals(value))
            {
                m_captions.setText("Color replacement by luminosity").getLayer().draw();

                m_movie.setFilters(new ColorLuminosityImageDataFilter(ColorName.PEACHPUFF.getColor().brightness(0.1)));
            }
            else if ("BRIGHTEN".equals(value))
            {
                m_captions.setText("Brighten by 30%").getLayer().draw();

                m_movie.setFilters(new BrightnessImageDataFilter(0.3));
            }
            else if ("DARKEN".equals(value))
            {
                m_captions.setText("Darken by 30%").getLayer().draw();

                m_movie.setFilters(new BrightnessImageDataFilter(-0.3));
            }
            else if ("INVERT".equals(value))
            {
                m_captions.setText("Invert colors").getLayer().draw();

                m_movie.setFilters(new InvertColorImageDataFilter());
            }
            else if ("EMBOSS".equals(value))
            {
                m_captions.setText("Emboss").getLayer().draw();

                m_movie.setFilters(new EmbossImageDataFilter());
            }
            else if ("EDGE".equals(value))
            {
                m_captions.setText("Edge Detect").getLayer().draw();

                m_movie.setFilters(new EdgeDetectImageDataFilter());
            }
            else if ("CONTRAST".equals(value))
            {
                m_captions.setText("Contrast 1.5").getLayer().draw();

                m_movie.setFilters(new ContrastImageDataFilter(1.5));
            }
            else if ("EXPOSURE".equals(value))
            {
                m_captions.setText("Exposure 4.0").getLayer().draw();

                m_movie.setFilters(new ExposureImageDataFilter(4));
            }
            else if ("GAIN".equals(value))
            {
                m_captions.setText("Gain 0.20 0.45").getLayer().draw();

                m_movie.setFilters(new GainImageDataFilter(0.20, 0.45));
            }
            else if ("HUE".equals(value))
            {
                m_captions.setText("Hue 0.5").getLayer().draw();

                m_movie.setFilters(new HueImageDataFilter(0.5));
            }
            else if ("POSTERIZE".equals(value))
            {
                m_captions.setText("Posterize 6").getLayer().draw();

                m_movie.setFilters(new PosterizeImageDataFilter(6));
            }
            else if ("SOLARIZE".equals(value))
            {
                m_captions.setText("Solarize").getLayer().draw();

                m_movie.setFilters(new SolarizeImageDataFilter());
            }
            else if ("BUMP".equals(value))
            {
                m_captions.setText("Bump").getLayer().draw();

                m_movie.setFilters(new BumpImageDataFilter());
            }
            else if ("GAMMA_03".equals(value))
            {
                m_captions.setText("Gamma 0.3").getLayer().draw();

                m_movie.setFilters(new GammaImageDataFilter(0.3));
            }
            else if ("GAMMA_20".equals(value))
            {
                m_captions.setText("Gamma 2.0").getLayer().draw();

                m_movie.setFilters(new GammaImageDataFilter(2.0));
            }
        }
    }

    @Override
    public boolean suspend()
    {
        if (super.suspend())
        {
            if (false == m_movie.isPaused())
            {
                m_movie.pause();

                m_play.setText("Play");
            }
            return true;
        }
        return false;
    }
}

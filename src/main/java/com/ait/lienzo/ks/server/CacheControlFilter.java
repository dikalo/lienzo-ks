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

package com.ait.lienzo.ks.server;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CacheControlFilter extends AbstractHTTPFilter
{
    private static final long WEEK_IN_SECONDS      = 604800L;

    private static final long WEEK_IN_MILLISECONDS = 604800000L;

    private static final long YEAR_IN_SECONDS      = 31536000L;

    private static final long YEAR_IN_MILLISECONDS = 31536000000L;

    @Override
    public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException
    {
        final String url = request.getRequestURI();

        if (url.endsWith(".rpc"))
        {
            doNoCache(request, response);
        }
        else if (url.indexOf(".nocache.") > 0)
        {
            doNoCache(request, response);
        }
        else if (url.indexOf(".cache.") > 0)
        {
            doFarFuture(request, response);
        }
        else if (url.endsWith(".js"))
        {
            doWeekFuture(request, response);
        }
        else if (url.endsWith(".jpg"))
        {
            doWeekFuture(request, response);
        }
        else if (url.endsWith(".png"))
        {
            doWeekFuture(request, response);
        }
        else if (url.endsWith(".gif"))
        {
            doWeekFuture(request, response);
        }
        else if (url.endsWith(".html"))
        {
            doNothing(request, response);
        }
        else if (url.endsWith(".jsp"))
        {
            doNoCache(request, response);
        }
        else if (url.endsWith(".css"))
        {
            if (url.contains("LienzoKS"))
            {
                doNoCache(request, response);
            }
            else
            {
                doWeekFuture(request, response);
            }
        }
        else if (url.endsWith(".swf"))
        {
            doWeekFuture(request, response);
        }
        else if (url.endsWith(".mp4"))
        {
            doWeekFuture(request, response);
        }
        else
        {
            doNoCache(request, response);
        }
        chain.doFilter(request, response);
    }

    protected void doNothing(final HttpServletRequest request, final HttpServletResponse response)
    {
    }

    protected void doNoCache(final HttpServletRequest request, final HttpServletResponse response)
    {
        final long time = System.currentTimeMillis();

        response.setDateHeader("Date", time);

        response.setDateHeader("Expires", time - YEAR_IN_SECONDS);

        response.setHeader("Pragma", "no-cache");

        response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
    }

    protected void doFarFuture(final HttpServletRequest request, final HttpServletResponse response)
    {
        response.setHeader("Cache-Control", "max-age=" + YEAR_IN_SECONDS);

        response.setDateHeader("Expires", System.currentTimeMillis() + YEAR_IN_MILLISECONDS);
    }

    protected void doWeekFuture(final HttpServletRequest request, final HttpServletResponse response)
    {
        response.setHeader("Cache-Control", "max-age=" + WEEK_IN_SECONDS);

        response.setDateHeader("Expires", System.currentTimeMillis() + WEEK_IN_MILLISECONDS);
    }
}

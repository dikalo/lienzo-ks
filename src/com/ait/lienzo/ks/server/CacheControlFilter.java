
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
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        String url = request.getRequestURI();

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
            doWeekFuture(request, response);
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

    protected void doNothing(HttpServletRequest request, HttpServletResponse response)
    {

    }

    protected void doNoCache(HttpServletRequest request, HttpServletResponse response)
    {
        long time = System.currentTimeMillis();

        response.setDateHeader("Date", time);

        response.setDateHeader("Expires", time - YEAR_IN_SECONDS);

        response.setHeader("Pragma", "no-cache");

        response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
    }

    protected void doFarFuture(HttpServletRequest request, HttpServletResponse response)
    {
        response.setHeader("Cache-Control", "max-age=" + YEAR_IN_SECONDS);

        response.setDateHeader("Expires", System.currentTimeMillis() + YEAR_IN_MILLISECONDS);
    }

    protected void doWeekFuture(HttpServletRequest request, HttpServletResponse response)
    {
        response.setHeader("Cache-Control", "max-age=" + WEEK_IN_SECONDS);

        response.setDateHeader("Expires", System.currentTimeMillis() + WEEK_IN_MILLISECONDS);
    }
}

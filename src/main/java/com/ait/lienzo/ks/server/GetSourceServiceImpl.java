/*
   Copyright (c) 2017 Ahome' Innovation Technologies. All rights reserved.

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

package com.ait.lienzo.ks.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.ait.lienzo.ks.client.GetSourceService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class GetSourceServiceImpl extends RemoteServiceServlet implements GetSourceService
{
    public GetSourceServiceImpl()
    {
    }

    @Override
    public String getSource(String url) throws IllegalArgumentException
    {
        if (false == url.startsWith("com/ait/lienzo/ks"))
        {
            throw new IllegalArgumentException("bad source " + url);
        }
        String result = "";

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(url)));

            StringBuilder buffer = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);

                buffer.append("\n");
            }
            reader.close();

            result = buffer.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}

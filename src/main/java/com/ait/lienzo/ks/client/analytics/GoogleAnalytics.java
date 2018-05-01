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

package com.ait.lienzo.ks.client.analytics;

import com.arcbees.analytics.shared.Analytics;
import com.arcbees.analytics.shared.AnalyticsPlugin;
import com.google.gwt.core.client.GWT;

public final class GoogleAnalytics
{
    private static Analytics                        UA_ANALYTICS = null;

    private static final GoogleAnalyticsGinInjector GIN_INJECTOR = GWT.create(GoogleAnalyticsGinInjector.class);

    private GoogleAnalytics()
    {
    }

    public static final Analytics get()
    {
        if (null == UA_ANALYTICS)
        {
            UA_ANALYTICS = GIN_INJECTOR.analytics();

            UA_ANALYTICS.create().createOptions().cookieDomain(GoogleAnalyticsConstants.UA_ADDRESS).generalOptions().go();

            UA_ANALYTICS.enablePlugin(AnalyticsPlugin.DISPLAY);
        }
        return UA_ANALYTICS;
    }
}

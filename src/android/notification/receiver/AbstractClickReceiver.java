/*
 * Apache 2.0 License
 *
 * Copyright (c) Sebastian Katzer 2017
 * Contributor Bhumin Bhandari
 *
 * This file contains Original Code and/or Modifications of Original Code
 * as defined in and that are subject to the Apache License
 * Version 2.0 (the 'License'). You may not use this file except in
 * compliance with the License. Please obtain a copy of the License at
 * http://opensource.org/licenses/Apache-2.0/ and read it before using this
 * file.
 *
 * The Original Code and all software distributed under the License are
 * distributed on an 'AS IS' basis, WITHOUT WARRANTY OF ANY KIND, EITHER
 * EXPRESS OR IMPLIED, AND APPLE HEREBY DISCLAIMS ALL SUCH WARRANTIES,
 * INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, QUIET ENJOYMENT OR NON-INFRINGEMENT.
 * Please see the License for the specific language governing rights and
 * limitations under the License.
 */

package de.appplant.cordova.plugin.notification.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import de.appplant.cordova.plugin.notification.Manager;
import de.appplant.cordova.plugin.notification.Notification;

import static de.appplant.cordova.plugin.notification.action.Action.CLICK_ACTION_ID;
import static de.appplant.cordova.plugin.notification.action.Action.EXTRA_ID;

/**
 * Abstract content receiver activity for local notifications. Creates the
 * local notification and calls the event functions for further proceeding.
 */
abstract public class AbstractClickReceiver extends NotificationTrampolineActivity {

    public AbstractClickReceiver() {
      super();
    }

    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      onHandleIntent(getIntent());
    }

    /**
     * Called when local notification was clicked to launch the main intent.
     */
    protected void onHandleIntent(Intent intent) {
      // Holds a reference to the intent to handle.

        if (intent == null)
            return;

        Bundle bundle      = intent.getExtras();
        Context context    = getApplicationContext();

        if (bundle == null)
            return;

        int toastId        = bundle.getInt(Notification.EXTRA_ID);
        Notification toast = Manager.getInstance(context).get(toastId);

        if (toast == null)
            return;

        onClick(toast, bundle);
    }

    /**
     * Called when local notification was clicked by the user.
     *
     * @param notification Wrapper around the local notification.
     * @param bundle The bundled extras.
     */
    abstract public void onClick (Notification notification, Bundle bundle);

    /**
     * The invoked action.
     */
    protected String getAction() {
        return getIntent().getExtras().getString(EXTRA_ID, CLICK_ACTION_ID);
    }
}

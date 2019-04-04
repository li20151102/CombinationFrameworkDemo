/*
 Copyright © 2015, 2016 Jenly Yu <a href="mailto:jenly1314@gmail.com">Jenly</a>

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
package com.example.administrator.myapplication.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.myapplication.app.config.SiteControl;

/**
 * @author Jenly
 */
public class ToastUtils {

    private static Toast toast;

    private ToastUtils() {
        throw new AssertionError();
    }

    public static void showToast(int resId) {
        showToast(SiteControl.getApplicationContext().getResources().getString(resId));
    }

    public static void showToast(int resId, int duration) {
        showToast(SiteControl.getApplicationContext().getResources().getString(resId), duration);
    }

    public static void showToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String text, int duration, Object... args) {
        showToast(context, String.format(text, args), duration);
    }

    public static void showToast(CharSequence text, int duration) {

        if (toast == null) {
            toast = Toast.makeText(SiteControl.getApplicationContext(), text, duration);
        } else {
            toast.setText(text);
            toast.setDuration(duration);
        }
        toast.show();
    }
}

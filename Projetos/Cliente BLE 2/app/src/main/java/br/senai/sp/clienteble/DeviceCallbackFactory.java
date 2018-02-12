package br.senai.sp.clienteble;

import android.content.Context;
import android.os.Build;

public class DeviceCallbackFactory {
    public static DeviceCallback getCallback(Context context, DeviceDelegate delegate) {
        if(Build.VERSION.SDK_INT >= 21) {
            return new NewCallback(context, delegate);
        } else {
            return new OldCallback(context, delegate);
        }
    }
}

package br.senai.sp.clienteble;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.util.Log;

@TargetApi(18)
@SuppressWarnings("deprecation")
public class OldCallback implements DeviceCallback, BluetoothAdapter.LeScanCallback {
    private Context context;
    private DeviceDelegate delegate;
    private Device device;

    public OldCallback(Context context, DeviceDelegate delegate) {
        Log.d("OldCallback", "new");
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    public void onLeScan(BluetoothDevice bleDevice, int rssi, byte[] scanRecord) {
        Log.d("OldCallback", "onLeScan");
        device = new Device(context, bleDevice, delegate);
        //getManager().getAdapter().stopLeScan(this);
    }

    private BluetoothManager getManager() {
        return (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    @Override
    public ScanCallback getCallback() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BluetoothAdapter.LeScanCallback getLeCallback() {
        return this;
    }

    @Override
    public Device getDevice() {
        return device;
    }
}

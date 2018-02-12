package br.senai.sp.clienteble;


import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;

@TargetApi(21)
public class NewCallback extends ScanCallback implements DeviceCallback {
    private Context context;
    private DeviceDelegate delegate;
    private Device device;

    public NewCallback(Context context, DeviceDelegate delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);

        BluetoothDevice bleDevice = result.getDevice();

        device = new Device(context, bleDevice, delegate);
    }

    @Override
    public ScanCallback getCallback() {
        return this;
    }

    @Override
    public BluetoothAdapter.LeScanCallback getLeCallback() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Device getDevice() {
        return device;
    }
}

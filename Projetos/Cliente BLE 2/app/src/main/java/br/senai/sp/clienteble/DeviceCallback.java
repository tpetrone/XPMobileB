package br.senai.sp.clienteble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanCallback;

public interface DeviceCallback {
    ScanCallback getCallback();
    BluetoothAdapter.LeScanCallback getLeCallback();
    Device getDevice();
}

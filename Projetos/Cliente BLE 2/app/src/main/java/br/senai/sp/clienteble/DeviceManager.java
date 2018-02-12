package br.senai.sp.clienteble;


import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.ParcelUuid;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

interface DeviceManagerDelegate {
    void hasDiscoveryDevice(Device device);
}

@TargetApi(21)
@SuppressWarnings("deprecation")
public class DeviceManager implements DeviceDelegate {
    private Context context;
    private BluetoothAdapter adapter;
    private DeviceManagerDelegate delegate;
    private BluetoothLeScanner scanner;
    private DeviceCallback callback;

    public DeviceManager(Context context, DeviceManagerDelegate delegate) {
        this.context = context;
        this.delegate = delegate;

        if(isBLESupported()) {
            Log.d("DeviceManager", "BLE is Supported");
            adapter = getManager().getAdapter();
        }
    }

    public boolean isBLESupported() {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    private BluetoothManager getManager() {
        return (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    public BluetoothAdapter getAdapter() {
        return adapter;
    }

    public void startDiscovery() {
        Log.d("DeviceManager", "starting discovery");
        callback = DeviceCallbackFactory.getCallback(context, this);
        if(Build.VERSION.SDK_INT >= 21) {
            List<ScanFilter> filters = new ArrayList<>();
            filters.add(new ScanFilter.Builder()
                    .setServiceUuid(ParcelUuid.fromString(UUIDs.serviceUUID.toString())).build());
            ScanSettings settings = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .setReportDelay(ScanSettings.CALLBACK_TYPE_ALL_MATCHES).build();
            scanner = adapter.getBluetoothLeScanner();
            Log.d("DeviceManager", "startScan");
            scanner.startScan(filters, settings, callback.getCallback());
        } else {
            Log.d("DeviceManager", "startLeScan");
            adapter.startLeScan(callback.getLeCallback());
        }
    }

    @Override
    public void hasDiscoveryService(BluetoothGattService service) {
        Log.d("DeviceManager", "hasDiscovereyServices");
        if(delegate != null) {
            delegate.hasDiscoveryDevice(callback.getDevice());
//            if(Build.VERSION.SDK_INT >= 21) {
//                scanner.stopScan(callback.getCallback());
//            } else {
//                adapter.stopLeScan(callback.getLeCallback());
//            }
        }
    }
}

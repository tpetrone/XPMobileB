package br.senai.sp.clienteble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Arrays;
import java.util.Random;

interface DeviceDelegate {
    void hasDiscoveryService(BluetoothGattService service);
}

public class Device extends BluetoothGattCallback {
    private Integer id;
    private int rssi = 999;
    private BluetoothGattService service;
    private DeviceDelegate delegate;
    private static Random random = new Random();

    public Device(Context context, BluetoothDevice device, DeviceDelegate delegate) {
        this.delegate = delegate;
        this.id = random.nextInt(9999);

        final Context  ctx = context;
        final BluetoothDevice dev = device;


        Handler run = new Handler(Looper.getMainLooper());
        run.post(new Runnable() {
            @Override
            public void run() {
                dev.connectGatt(ctx, false, getCallback());
            }
        });
    }

    private BluetoothGattCallback getCallback() {
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return service.getUuid().toString();
    }

    public int getRSSI() {
        return rssi;
    }

    public String getUUID() {
        return service.getUuid().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;

        Device device = (Device) o;

        return !(id != null ? !id.equals(device.id) : device.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", rssi=" + rssi +
                '}';
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        Log.d("Device", "status: " + status + " state: " + newState);
        switch (newState) {
            case BluetoothProfile.STATE_CONNECTED:
                Log.d("Device", " state: Connected");
                gatt.readRemoteRssi();
                gatt.discoverServices();
                break;
            case BluetoothProfile.STATE_DISCONNECTED:
                Log.d("Device", " state: Disconnected");
                gatt.connect();
                break;
            case BluetoothProfile.STATE_CONNECTING:
                Log.d("Device", " state: Connecting");
                break;
            case BluetoothProfile.STATE_DISCONNECTING:
                Log.d("Device", " state: Disconnecting");
                break;
            default:
                Log.d("Device", " state: Unknown");
                break;
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        Log.d("Device", " Discovered: " + status);
        if(status == BluetoothGatt.GATT_SUCCESS) {
            service = gatt.getService(UUIDs.serviceUUID);
            Log.d("Device", "Service: " + service.getUuid());
            if(service.getCharacteristic(UUIDs.characteristicInfoUUID) != null) {
                BluetoothGattCharacteristic characteristic =
                        service.getCharacteristic(UUIDs.characteristicDataUUID);
                Log.d("Device", "Characteristic: " + characteristic.getUuid());
                characteristic.setValue("1022827");
                characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                gatt.writeCharacteristic(characteristic);
            }
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.d("Device", " Characteristic Read: " + status);
        if(status == BluetoothGatt.GATT_SUCCESS) {
            if(characteristic.getUuid().equals(UUIDs.characteristicInfoUUID)) {
                Log.i("Devide Info", Arrays.toString(characteristic.getValue()));
            } else if(characteristic.getUuid().equals(UUIDs.characteristicDataUUID)) {
                characteristic.setValue("1022827");
                characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                gatt.writeCharacteristic(characteristic);
            }
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.d("Device", " Characteristi Write: " + status);
        if(status == BluetoothGatt.GATT_SUCCESS) {
            if(characteristic.getUuid().equals(UUIDs.characteristicDataUUID)) {
                if(delegate != null) {
                    Log.i("Device Info", "Registrado");
                    delegate.hasDiscoveryService(service);
                }
            }
        } else {
            Log.d("Device Info", "Not registred");
        }
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        Log.d("Device", " Read RSSI: " + status);
        if(status == BluetoothGatt.GATT_SUCCESS) {
            this.rssi = rssi;
        }
    }

}

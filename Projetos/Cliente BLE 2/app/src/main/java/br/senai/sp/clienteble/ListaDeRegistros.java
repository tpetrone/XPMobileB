package br.senai.sp.clienteble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaDeRegistros extends Activity implements DeviceManagerDelegate {
    private static final int REQUEST_ENABLE_BLE = 1;
    private List<Device> devices = new ArrayList<>();
    private DeviceAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_layout);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new DeviceAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DeviceManager manager = new DeviceManager(this, this);

        if (manager.isBLESupported()) {
            if (manager.getAdapter() != null && !manager.getAdapter().isEnabled()) {
                Intent bleIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(bleIntent, REQUEST_ENABLE_BLE);
            }

            manager.startDiscovery();
        } else {
            Toast.makeText(this, "Bluetooth LE não é suportado\nneste dispositivo",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BLE && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void hasDiscoveryDevice(Device device) {
        devices.add(device);
        Handler run = new Handler(Looper.getMainLooper());
        run.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    class DeviceAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return devices.size();
        }

        @Override
        public Object getItem(int id) {
            return devices.get(devices.indexOf(id));
        }

        @Override
        public long getItemId(int i) {
            return (long) devices.get(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RelativeLayout layout;

            if (view == null) {
                layout = new RelativeLayout(viewGroup.getContext());
                LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.detalhe_registros, layout, true);
            } else {
                layout = (RelativeLayout) view;
            }

            Device obj = devices.get(i);
            if(obj != null) {
                TextView tvNome = (TextView) layout.findViewById(R.id.tvNome);
                tvNome.setText(obj.getNome());
                TextView tvUUID = (TextView) layout.findViewById(R.id.tvUUID);
                tvUUID.setText(obj.getUUID());
                TextView tvRssi = (TextView) layout.findViewById(R.id.tvRssi);
                tvRssi.setText(String.format("RSSI: %ddb", obj.getRSSI()));
            } else {
                Log.d("BaseAdapser", "Obj: null");
            }

            return layout;
        }
    }
}

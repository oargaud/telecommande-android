package example.com.telecommande40.ecran;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import example.com.telecommande40.Objet.BluetoothReceiver;
import example.com.telecommande40.Objet.CustomListAdapter;
import example.com.telecommande40.Objet.ObjetPeripheriqueBluetooth;
import example.com.telecommande40.R;
import example.com.telecommande40.backup.Memoire;

import java.util.ArrayList;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;


public class Bluetooth extends AppCompatActivity
{

    private ProgressDialog progressDialog;

    private Button scanBlue;
    private Button selectBlue;
    private Button refresh;
    private Button favori;
    private Button retour;

    public AppCompatActivity cettePage = this;

    public ObjetPeripheriqueBluetooth bluetoothSelectionne;
    public int indexBluetoothSelectionne;

    public BluetoothAdapter bluetoothAdapter;
    public BluetoothReceiver bluetoothReceiver = null;

    public ListView listBlueDevicesFound;

    public CustomListAdapter monBlueAdapter;

    public ArrayList<ObjetPeripheriqueBluetooth> listPeriphBlue = new ArrayList<>();


    private int REQUEST_ENABLE_BT = 1;

    private UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private int mBufferSize = 50000; //Default
    public static final String DEVICE_EXTRA = "com.example.lightcontrol.SOCKET";
    public static final String DEVICE_UUID = "com.example.lightcontrol.uuid";
    private static final String DEVICE_LIST = "com.example.lightcontrol.devicelist";
    private static final String DEVICE_LIST_SELECTED = "com.example.lightcontrol.devicelistselected";
    public static final String BUFFER_SIZE = "com.example.lightcontrol.buffersize";
    private static final String TAG = "BlueTest5-MainActivity";


    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scan_bluetooth);





        scanBlue = (Button)findViewById(R.id.btn_scan);
        selectBlue = (Button)findViewById(R.id.btn_select);
        refresh = (Button)findViewById(R.id.btn_refresh);
        favori = (Button)findViewById(R.id.btn_favori);
        retour = (Button)findViewById(R.id.btn_retour_scan);

        listBlueDevicesFound = (ListView)findViewById(R.id.list_view_periph);

        monBlueAdapter =  new CustomListAdapter(Bluetooth.this,Memoire.listPeriphBlue);


        listBlueDevicesFound.setAdapter(monBlueAdapter);


        scanBlue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clicScanBlue();
            }
        });

        selectBlue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clicSelectBlue();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clicRefresh();
            }
        });

        favori.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                togleFavori();
            }
        });

        retour.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clicRetour();
            }
        });

        // When the user clicks on the ListItem
        listBlueDevicesFound.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {
                ObjetPeripheriqueBluetooth periph = (ObjetPeripheriqueBluetooth) listBlueDevicesFound.getItemAtPosition(position);
                bluetoothSelectionne = periph;
                indexBluetoothSelectionne = position;
                //Toast.makeText(Bluetooth.this, "Selected :" + "\n " + periph.getNom()+ "\n " +periph.getAdresse(), Toast.LENGTH_LONG).show();
            }
        });



        activeBluetooth();

    }

    void activeBluetooth()
    {

        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


    }



    public void clicScanBlue()
    {
        //lanceUnScan();
        new ScanBluetooth().execute();
  //      progressDialog = ProgressDialog.show(Bluetooth.this, "attend un peu", "Scan en cours");// http://stackoverflow.com/a/11130220/1287554

    }

    public void clicSelectBlue()
    {
        if(bluetoothSelectionne!=null)
        {
            Memoire.bluetoothSelectionne = bluetoothSelectionne;
            Toast.makeText(Bluetooth.this, "peripherique selectionné", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(Bluetooth.this, "Aucun peripherique selectionné", Toast.LENGTH_LONG).show();
        }

    }



    public void clicRefresh()
    {
//        progressDialog = ProgressDialog.show(Bluetooth.this, "attend un peu", "Scan en cours");// http://stackoverflow.com/a/11130220/1287554


/*
        try
        {
            if(Memoire.listPeriphBlue!=null && monBlueAdapter!=null)
            {
                Memoire.listPeriphBlue.clear();
                monBlueAdapter.notifyDataSetChanged();
            }
        }
        catch (Exception e)
        {

        }

        lanceUnScan();
*/

    }

    public void togleFavori()
    {
        if(Memoire.bluetoothSelectionne!=null)
        {
            if(Memoire.bluetoothSelectionne.isFavori())
            {
                Memoire.bluetoothSelectionne.setFavori(false);
//                Memoire.listPeriphBlue.get(Memoire.indexBluetoothSelectionne).setFavori(false);
                msg("fav on");
            }
            else
            {
                Memoire.bluetoothSelectionne.setFavori(true);
//                Memoire.listPeriphBlue.get(Memoire.indexBluetoothSelectionne).setFavori(true);
                msg("fav off");
            }

            monBlueAdapter.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(Bluetooth.this, "Aucun peripherique selectionné", Toast.LENGTH_LONG).show();
        }

    }

    public void lanceUnScan()
    {

        // on affiche un message pour dire qu'on lance le scan
        //Toast.makeText(cettePage, R.string.scanLance, Toast.LENGTH_SHORT).show();
        // On enregistre le "broadcast receiver" une unique fois
        if ( bluetoothReceiver == null )
        {
            //un message pour dire que le receiver etait null
            //Toast.makeText(cettePage, R.string.receiverNull, Toast.LENGTH_SHORT).show();
            // on creer un nouveau receiver et on l'enregistre
            bluetoothReceiver = new BluetoothReceiver(monBlueAdapter);
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(bluetoothReceiver, filter);
        }

        // Si un scan bluetooth est en cours, on le coupe
        if (bluetoothAdapter.isDiscovering())
        {
            // on petit message
            Toast.makeText(cettePage, R.string.bluetooth_scanEnCour, Toast.LENGTH_SHORT).show();
            bluetoothAdapter.cancelDiscovery();
        }
        // On lance un nouveau scan bluetooth
        bluetoothAdapter.startDiscovery();


    }


    public void clicRetour()
    {
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);

    }


    private class ScanBluetooth extends AsyncTask<Void, Void, Void>
    {


        @Override
        protected void onPreExecute()
        {

            msg("pre exectute");

            progressDialog = ProgressDialog.show(Bluetooth.this, "Hold on", "Scan en cours");// http://stackoverflow.com/a/11130220/1287554

        }

        @Override
        protected Void doInBackground(Void... devices)
        {

            try
            {
                if ( bluetoothReceiver == null )
                {
                    //un message pour dire que le receiver etait null
                    //Toast.makeText(cettePage, R.string.receiverNull, Toast.LENGTH_SHORT).show();
                    // on creer un nouveau receiver et on l'enregistre
                    bluetoothReceiver = new BluetoothReceiver(monBlueAdapter);
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(bluetoothReceiver, filter);
                }

                // Si un scan bluetooth est en cours, on le coupe
                if (bluetoothAdapter.isDiscovering())
                {
                    // on petit message
                    Toast.makeText(cettePage, R.string.bluetooth_scanEnCour, Toast.LENGTH_SHORT).show();
                    bluetoothAdapter.cancelDiscovery();
                }
                // On lance un nouveau scan bluetooth
                bluetoothAdapter.startDiscovery();

                while (bluetoothAdapter.isDiscovering())
                {


                }
            }
            catch (Exception e)
            {
                // Unable to connect to device`
                // e.printStackTrace();




            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            msg("post exectute");

            progressDialog.dismiss();
        }

    }


}

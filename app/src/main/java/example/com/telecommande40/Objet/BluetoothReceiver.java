package example.com.telecommande40.Objet;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import example.com.telecommande40.backup.Memoire;

import java.util.ArrayList;


public class BluetoothReceiver extends BroadcastReceiver
{

    public CustomListAdapter monBlueAdapter;


    public BluetoothReceiver( CustomListAdapter monBlueAdapter)
    {
        this.monBlueAdapter = monBlueAdapter;
    }


    public void onReceive(Context context, Intent intent)
    {

        String action = intent.getAction();

        if (BluetoothDevice.ACTION_FOUND.equals(action))
        {
            // Un récupère le périphérique bluetooth détecté durant le scan
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            ObjetPeripheriqueBluetooth periphTemp = new ObjetPeripheriqueBluetooth(device,new Handler());

            if (device.getType()== 1||device.getType()== 2||device.getType()== 3)
            {

                if (!testExisteDeja(device,Memoire.listPeriphBlue))
                {
                    Memoire.listPeriphBlue.add(periphTemp);
                    // on actualise la liste avec ce que l'on a trouvé dans le scan

                    monBlueAdapter.notifyDataSetChanged();
                }

            }
            else
            {
                Toast.makeText(context, "on sais pas a quelle espece ca appartient ce machin la "+device.getType(), Toast.LENGTH_LONG).show();
            }

        }

    }



    public boolean testExisteDeja(BluetoothDevice device, ArrayList<ObjetPeripheriqueBluetooth> listPeriph)
    {
        boolean existeDeja =false;

        for (int i =0;i<listPeriph.size();i++)
        {

            if (device.getAddress().contains(listPeriph.get(i).getAdresse()))
            {
                existeDeja = true;
                // Toast.makeText(context, "on l'a deja celui la "+device.getAddress() , Toast.LENGTH_LONG).show();
            }
        }

        return existeDeja;
    }


}

package example.com.telecommande40.backup;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.widget.ListView;

import example.com.telecommande40.Objet.BluetoothReceiver;
import example.com.telecommande40.Objet.CustomListAdapter;
import example.com.telecommande40.Objet.ObjetPeripheriqueBluetooth;

import java.util.ArrayList;
import java.util.UUID;

public final class Memoire
{

    public static String toto = "toto";



    public static ObjetPeripheriqueBluetooth bluetoothSelectionne = null;;
    public static int indexBluetoothSelectionne = -1;

    public static BluetoothGattCallback gattCallback;
    public static BluetoothGatt gatt;

    public static ArrayList <BluetoothGattService> listService = new ArrayList<>();
    public static UUID UUID_SERVICE_COM = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    public static BluetoothGattService serviceCom;
    public static ArrayList <BluetoothGattCharacteristic> listCharacteristic = new ArrayList<>();
    public static UUID UUID_CHARACTERISTIC_COM = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    public static BluetoothGattCharacteristic characteristicCom;

    public static BluetoothAdapter bluetoothAdapter = null;
    public static BluetoothReceiver bluetoothReceiver = null;

    public static ListView listBlueDevicesFound = null;

    public static CustomListAdapter monBlueAdapter = null;

    public static ArrayList<ObjetPeripheriqueBluetooth> listPeriphBlue =  new ArrayList<>();
    public static ArrayList<ObjetPeripheriqueBluetooth> listPeriphBlueFavori = new ArrayList<>();


    public static int REQUEST_ENABLE_BT = 1;

    public static UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static int mBufferSize = 50000; //Default
    public static final String DEVICE_EXTRA = "com.example.lightcontrol.SOCKET";
    public static final String DEVICE_UUID = "com.example.lightcontrol.uuid";
    public static final String DEVICE_LIST = "com.example.lightcontrol.devicelist";
    public static final String DEVICE_LIST_SELECTED = "com.example.lightcontrol.devicelistselected";
    public static final String BUFFER_SIZE = "com.example.lightcontrol.buffersize";
    public static final String TAG = "BlueTest5-MainActivity";







    public static void test()
    {

    }


}

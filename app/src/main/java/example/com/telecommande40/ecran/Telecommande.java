package example.com.telecommande40.ecran;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import example.com.telecommande40.R;
import example.com.telecommande40.backup.Memoire;
import example.com.telecommande40.fonction.Connexion;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;


public class Telecommande extends AppCompatActivity
{

    private static final String TAG = "BlueTest5-Controlling";
    private int mMaxChars = 50000;//Default//change this to string..........
    private UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket mBTSocket = null;
    private ReadInput mReadThread = null;

    private boolean mIsUserInitiatedDisconnect = false;
    private boolean mIsBluetoothConnected = false;

    private ProgressDialog progressDialog;

    private BluetoothGattCallback gattCallback;
    private BluetoothGattCharacteristic characteristic;

    public UUID UUID_SERVICE_COM = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    public UUID UUID_CHARACTERISTIC_COM = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");

    public UUID HEART_RATE_SERVICE_UUID = convertFromInteger(0x180D);
    public UUID HEART_RATE_MEASUREMENT_CHAR_UUID = convertFromInteger(0x2A37);
    public UUID HEART_RATE_CONTROL_POINT_CHAR_UUID = convertFromInteger(0x2A39);
    public UUID CLIENT_CHARACTERISTIC_CONFIG_UUID = convertFromInteger(0x2902);
    public UUID TEST = convertFromInteger(0x2902);


    private TextView nom;
    private TextView adresse;

    private Button connect;
    private Button bouttonVert;
    private Button bouttonBleu;
    private Button bouttonRouge;
    private Button boutton1;
    private Button boutton2;
    private Button boutton3;
    private Button bouttonRetour;


    private EditText champ1;
    private EditText champ2;
    private EditText champ3;

    Context context;

    public Telecommande()
    {

        context = this;

    }
    public Context getContext()
    {

        return context;
    }
//--------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_telecommande);


        nom = (TextView) findViewById(R.id.layout_telecommande_nom_periph_selectionne);
        adresse = (TextView) findViewById(R.id.layout_telecommande_adresse_mac_periph_selectionne);

        connect  =(Button)findViewById(R.id.layout_telecommande_btn_connect);
        boutton1=(Button)findViewById(R.id.layout_telecommande_bouton1);
        boutton2=(Button)findViewById(R.id.layout_telecommande_bouton2);
        boutton3=(Button)findViewById(R.id.layout_telecommande_bouton3);

        bouttonVert=(Button)findViewById(R.id.layout_telecommande_boutonVert);
        bouttonBleu=(Button)findViewById(R.id.layout_telecommande_boutonBleu);
        bouttonRouge=(Button)findViewById(R.id.layout_telecommande_boutonRouge);

        bouttonRetour=(Button)findViewById(R.id.layout_telecommande_retour);

        champ1 = (EditText) findViewById(R.id.layout_telecommande_text_champ_1);
        champ2 = (EditText) findViewById(R.id.layout_telecommande_text_champ_2);
        champ3 = (EditText) findViewById(R.id.layout_telecommande_text_champ_3);


        try
        {
            if (Memoire.bluetoothSelectionne!=null)
            {
                nom.setText(Memoire.bluetoothSelectionne.getNom());
                adresse.setText(Memoire.bluetoothSelectionne.getAdresse());
            }
            else
            {
                msg("aucun peripherique selectionné dans memoire");
            }

        }
        catch (Exception e)
        {
            msg("boulette au chargement periph");
        }



//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//-------------------------------  CONNECT  ---------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------




        connect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (Memoire.bluetoothSelectionne!=null)
                {
                    // bluetooth classic ou dual
                    if (Memoire.bluetoothSelectionne.getDevice().getType() == 1 || Memoire.bluetoothSelectionne.getDevice().getType() == 3)
                    {
                        try
                        {
                            if (mBTSocket == null || !mIsBluetoothConnected)
                            {
                                new ConnectBT().execute();
                            }
                            else
                            {
                                new DisConnectBT().execute();
                            }
                        }
                        catch (Exception e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    // bluetooth LE
                    else if(Memoire.bluetoothSelectionne.getDevice().getType() == 2 )
                    {
                        if (Memoire.gattCallback == null)
                        {
                            creationCallBack();

//                            try
//                            {
////                                while(Memoire.gatt == null)
////                                {
////
////                                }
//                                Thread.sleep(500);
//                                msg("on a attendu 500 mil");
////                                boutton2.callOnClick();
//                            }
//                            catch (InterruptedException e)
//                            {
//                                e.printStackTrace();
//                            }



                        }

                        if (mIsBluetoothConnected)
                        {
                            deconnectBle();
                        }
                        else
                        {
                            connectBle();
                        }
                    }
                }

            }
        });


//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------  BOUTON 1  ------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------


        boutton1.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if (Memoire.bluetoothSelectionne!=null)
                {
                    if (Memoire.bluetoothSelectionne.getDevice().getType() == 1 || Memoire.bluetoothSelectionne.getDevice().getType() == 3)
                    {
                        try
                        {

                            // mBTSocket.getOutputStream().write("avance".getBytes());
                            mBTSocket.getOutputStream().write( champ1.getText().toString().getBytes());
                            msg("message envoyé"+champ1.getText().toString());
                        }
                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    else if(Memoire.bluetoothSelectionne.getDevice().getType() == 2 )
                    {
                        try
                        {
                            Memoire.characteristicCom.setValue(champ1.getText().toString());
                            Memoire.characteristicCom.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                            Memoire.gatt.writeCharacteristic(Memoire.characteristicCom);
                            msg("carateristic mise a "+champ1.getText().toString());
                        }
                        catch(Exception e)
                        {
                            msg("c'est pas encore ca l'ecriture");
                        }
                    }
                }

            }
        });



//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------BOUTON 2------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------


        boutton2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if(Memoire.bluetoothSelectionne.getDevice()!=null)
                {
                    // bluetooth classic ou dual
                    if (Memoire.bluetoothSelectionne.getDevice().getType() == 1 || Memoire.bluetoothSelectionne.getDevice().getType() == 3)
                    {
                        try
                        {
                            mBTSocket.getOutputStream().write(champ1.getText().toString().getBytes());

                        }
                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    //bluetooth LE
                    else if(Memoire.bluetoothSelectionne.getDevice().getType() == 2 )
                    {
                        /*
                        try
                        {
                            Memoire.characteristicCom.setValue(champ2.getText().toString());
                            Memoire.characteristicCom.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                            Memoire.gatt.writeCharacteristic(Memoire.characteristicCom);
                            msg("carateristic mise a avance");
                        }
                        catch(Exception e)
                        {
                            msg("c'est pas encore ca l'ecriture");
                        }
                        */

                        if(Memoire.gatt != null)
                        {
                            Memoire.gatt.discoverServices();
//                            msg("on cherche des services");

                            try
                            {
                                Memoire.listService = (ArrayList)Memoire.gatt.getServices();
                                String message = "";
                                for(int i =0;i<Memoire.listService.size();i++)
                                {
                                    message += Memoire.listService.get(i).getUuid().toString()+"\n";

                            /*
                                liste des service
                                00001800-0000-8000-00805f9b34fb  "Generic Access"
                                00001801-0000-8000-00805f9b34fb  "Generic Attribute"
                                0000180a-0000-8000-00805f9b34fb  "Device Information"
                                0000ffe0-0000-8000-00805f9b34fb  SimpleKeyService ????

                            */

                                }
                                msg(message);


                            }
                            catch(Exception e)
                            {
                                msg("le scan service marche po");
                            }


                        }
                        else
                        {
                            msg("le gatt est null");
                        }

                    }
                }

            }
        });


//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------BOUTON 3------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------


        boutton3.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub

                if(Memoire.bluetoothSelectionne.getDevice()!=null)
                {

                    if (Memoire.bluetoothSelectionne.getDevice().getType() == 1 || Memoire.bluetoothSelectionne.getDevice().getType() == 3)
                    {
                        try
                        {
                            mBTSocket.getOutputStream().write(champ1.getText().toString().getBytes());

                        }
                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    else if (Memoire.bluetoothSelectionne.getDevice().getType() == 2)
                    {
                        /*
                        try
                        {
                            Memoire.characteristicCom.setValue(champ3.getText().toString());
                            Memoire.characteristicCom.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                            Memoire.gatt.writeCharacteristic(Memoire.characteristicCom);
                            msg("carateristic mise a avance");
                        }
                        catch(Exception e)
                        {
                            msg("c'est pas encore ca l'ecriture");
                        }
                        */


                            try
                            {
                                BluetoothGattService service = Memoire.gatt.getService(UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb"));
                                Memoire.listCharacteristic = (ArrayList<BluetoothGattCharacteristic>) service.getCharacteristics();
                                String message2 = "";
                                for(int i =0;i<Memoire.listCharacteristic.size();i++)
                                {
                                    message2 += Memoire.listCharacteristic.get(i).getUuid().toString()+"\n";

                                /*
                                    liste des charcateristic
                                    0000ffe1-0000-1000-8000-00805f9b34fb  SimpleKeyService ????

                                */

                                }
                                msg(message2);

                                Memoire.characteristicCom = service.getCharacteristic(UUID_CHARACTERISTIC_COM);



                            }
                            catch (Exception e)
                            {
                                msg("ca bug dans le service");
                            }



                    }

                }
            }
        });








//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//-------------------------------  BOUTON VERT  -----------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------


        bouttonVert.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if (Memoire.bluetoothSelectionne!=null)
                {
                    if (Memoire.bluetoothSelectionne.getDevice().getType() == 1 || Memoire.bluetoothSelectionne.getDevice().getType() == 3)
                    {
                        try
                        {

                            // mBTSocket.getOutputStream().write("avance".getBytes());
                            mBTSocket.getOutputStream().write( "vert".getBytes());
                            msg("message envoyé vert");
                        }
                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    else if(Memoire.bluetoothSelectionne.getDevice().getType() == 2 )
                    {
                        try
                        {
                            Memoire.characteristicCom.setValue("vert");
                            Memoire.characteristicCom.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                            Memoire.gatt.writeCharacteristic(Memoire.characteristicCom);
                            msg("carateristic mise a vert");
                        }
                        catch(Exception e)
                        {
                            msg("c'est pas encore ca l'ecriture");
                        }
                    }
                }

            }
        });



//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//-------------------------------  BOUTON BLEU  -----------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------


        bouttonBleu.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if (Memoire.bluetoothSelectionne!=null)
                {
                    if (Memoire.bluetoothSelectionne.getDevice().getType() == 1 || Memoire.bluetoothSelectionne.getDevice().getType() == 3)
                    {
                        try
                        {

                            // mBTSocket.getOutputStream().write("avance".getBytes());
                            mBTSocket.getOutputStream().write( "bleu".getBytes());
                            msg("message envoyé bleu");
                        }
                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    else if(Memoire.bluetoothSelectionne.getDevice().getType() == 2 )
                    {
                        try
                        {
                            Memoire.characteristicCom.setValue("bleu");
                            Memoire.characteristicCom.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                            Memoire.gatt.writeCharacteristic(Memoire.characteristicCom);
                            msg("carateristic mise a bleu");
                        }
                        catch(Exception e)
                        {
                            msg("c'est pas encore ca l'ecriture");
                        }
                    }
                }

            }
        });




//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//-------------------------------  BOUTON ROUGE  -----------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------


        bouttonRouge.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                if (Memoire.bluetoothSelectionne!=null)
                {
                    if (Memoire.bluetoothSelectionne.getDevice().getType() == 1 || Memoire.bluetoothSelectionne.getDevice().getType() == 3)
                    {
                        try
                        {

                            // mBTSocket.getOutputStream().write("avance".getBytes());
                            mBTSocket.getOutputStream().write( "rouge".getBytes());
                            msg("message envoyé rouge");
                        }
                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    else if(Memoire.bluetoothSelectionne.getDevice().getType() == 2 )
                    {
                        try
                        {
                            Memoire.characteristicCom.setValue("rouge");
                            Memoire.characteristicCom.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                            Memoire.gatt.writeCharacteristic(Memoire.characteristicCom);
                            msg("carateristic mise a rouge");
                        }
                        catch(Exception e)
                        {
                            msg("c'est pas encore ca l'ecriture");
                        }
                    }
                }

            }
        });






//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//-------------------------------  RETOUR  ----------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------


        bouttonRetour.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);

//               new Connexion();

            }
        });

    }




//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------


    @Override
    protected void onPause()
    {
        try
        {
            if(Memoire.bluetoothSelectionne.getDevice()!=null)
            {
                if (Memoire.bluetoothSelectionne.getDevice().getType() == 1 || Memoire.bluetoothSelectionne.getDevice().getType() == 3)
                {
                    if (mBTSocket != null && mIsBluetoothConnected)
                    {
                        new DisConnectBT().execute();
                    }
                }
                else if(Memoire.bluetoothSelectionne.getDevice().getType() == 2 )
                {

                    deconnectBle();
                }
            }

        }
        catch(Exception e)
        {

        }

        //Log.d(TAG, "Paused");
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        try
        {
            if (Memoire.bluetoothSelectionne.getDevice()!=null)
            {
                if (Memoire.bluetoothSelectionne.getDevice().getType() == 1 || Memoire.bluetoothSelectionne.getDevice().getType() == 3)
                {
                    msg("bluetooth classique");
                   /* if (mBTSocket == null || !mIsBluetoothConnected)
                    {
                        new ConnectBT().execute();
                    }*/
                }
                else if(Memoire.bluetoothSelectionne.getDevice().getType() == 2 )
                {
                    msg ("bluetooth BLE");

                    //connectBle();

                }
            }
        }
        catch(Exception e )
        {

        }


        // Log.d(TAG, "Resumed");
        super.onResume();
    }

    @Override
    protected void onStop()
    {
        // Log.d(TAG, "Stopped");
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }




//--------------------------------------------------------------------------------------------------------------




    private class ReadInput implements Runnable
    {

        private boolean bStop = false;
        private Thread t;

        public ReadInput()
        {
            t = new Thread(this, "Input Thread");
            t.start();
        }

        public boolean isRunning()
        {
            return t.isAlive();
        }

        @Override
        public void run()
        {
            InputStream inputStream;

            try
            {
                inputStream = mBTSocket.getInputStream();
                while (!bStop)
                {
                    byte[] buffer = new byte[256];
                    if (inputStream.available() > 0)
                    {
                        inputStream.read(buffer);
                        int i = 0;

                        /*
                         * This is needed because new String(buffer) is taking the entire buffer i.e. 256 chars on Android 2.3.4 http://stackoverflow.com/a/8843462/1287554
                         */

                        for (i = 0; i < buffer.length && buffer[i] != 0; i++)
                        {
                        }
                        final String strInput = new String(buffer, 0, i);


                        /*
                         * If checked then receive text, better design would probably be to stop thread if unchecked and free resources, but this is a quick fix
                         */


                    }
                    Thread.sleep(500);
                }
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        public void stop()
        {
            bStop = true;
        }

    }



//--------------------------------------------------------------------------------------------------------------



    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean mConnectSuccessful = true;


        @Override
        protected void onPreExecute()
        {

            progressDialog = ProgressDialog.show(Telecommande.this, "Hold on", "Connecting");// http://stackoverflow.com/a/11130220/1287554

        }

        @Override
        protected Void doInBackground(Void... devices)
        {

            try
            {
                if (mBTSocket == null || !mIsBluetoothConnected)
                {
                    mBTSocket = Memoire.bluetoothSelectionne.getDevice().createInsecureRfcommSocketToServiceRecord(mDeviceUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();// a revoir pour cancel le scan en cour
                    mBTSocket.connect();
                }
            }
            catch (IOException e)
            {
                // Unable to connect to device`
                // e.printStackTrace();
                mConnectSuccessful = false;



            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!mConnectSuccessful)
            {
                Toast.makeText(getApplicationContext(), "Could not connect to device.Please turn on your Hardware", Toast.LENGTH_LONG).show();
                finish();
            }
            else
            {
                msg("Connected to device");
                mIsBluetoothConnected = true;
               // mReadThread = new ReadInput(); // Kick off input reader
                connect.setText("deconnecter");
            }

            progressDialog.dismiss();
        }

    }


    private class DisConnectBT extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
        }

        @Override
        protected Void doInBackground(Void... params) {//cant inderstand these dotss

            if (mReadThread != null)
            {
                mReadThread.stop();
                while (mReadThread.isRunning())
                {
                    ; // Wait until it stops
                }
                mReadThread = null;

            }

            try
            {
                mBTSocket.close();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            mIsBluetoothConnected = false;
            connect.setText("connecter");
            if (mIsUserInitiatedDisconnect)
            {
                finish();
            }
        }

    }



//--------------------------------------------------------------------------------------------------------------



    public void connectBle()
    {
        // connection
        try
        {


            Memoire.bluetoothSelectionne.getDevice().connectGatt(Telecommande.this,true,Memoire.gattCallback);
            mIsBluetoothConnected = true;
            connect.setText("Deconnecter");


        }
        catch (Exception e)
        {
            msg("Probleme de connexion");
        }


    }

    public void deconnectBle()
    {
        try
        {
            if (Memoire.gatt!=null)
            {
                Memoire.gatt.disconnect();
                Memoire.gatt.close();
                mIsBluetoothConnected = false;
                connect.setText("Connecter");
            }
        }
        catch (Exception e)
        {
            msg("Probleme de deconnexion");
        }


    }



//--------------------------------------------------------------------------------------------------------------



    public void creationCallBack()
    {
//        msg("on creer legattcallback");
        Memoire.gattCallback = new BluetoothGattCallback()
        {

            @Override
            public void onReliableWriteCompleted(BluetoothGatt gatt, int status)
            {
                super.onReliableWriteCompleted(gatt, status);
                msg("onReliableWriteCompleted");
            }

            @Override
            public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status)
            {
                super.onReadRemoteRssi(gatt, rssi, status);
                msg("onReadRemoteRssi");
            }

            @Override
            public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status)
            {
                super.onPhyUpdate(gatt, txPhy, rxPhy, status);
                msg("onPhyUpdate");
            }

            @Override
            public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status)
            {
                super.onPhyRead(gatt, txPhy, rxPhy, status);
                msg("onPhyRead");
            }

            @Override
            public void onMtuChanged(BluetoothGatt gatt, int mtu, int status)
            {
                super.onMtuChanged(gatt, mtu, status);
                msg("onMtuChanged");
            }

            @Override
            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status)
            {
                super.onDescriptorWrite(gatt, descriptor, status);
                msg("onDescriptorWrite");
            }

            @Override
            public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status)
            {
                super.onDescriptorRead(gatt, descriptor, status);
                msg("onDescriptorRead");
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
            {
                super.onCharacteristicChanged(gatt, characteristic);
                msg("on change de caracteristique");
            }

            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState)
            {
                super.onConnectionStateChange(gatt, status, newState);

                Memoire.gatt = gatt;
                connect.setText("status "+status+"!");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connect.setText("toto");

                msg("la connexion change d'etat");
                connect.setText("sdfhsdjfs");
                test();

                connect.setText("tata");

                if (newState == STATE_CONNECTED)
                {
                    msg("on est connecté");
               //

                }
                msg("on change d'etat de connexion");
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status)
            {
                msg("onservicediscovery");
                //super.onServicesDiscovered(gatt, status);

//                Memoire.gatt = gatt;

                /*
                Memoire.listService = (ArrayList)Memoire.gatt.getServices();
                msg("on scan les services");
                for(int i =0;i<Memoire.listService.size();i++)
                {
                    msg(Memoire.listService.get(i).getUuid().toString()+"/n");
                }*/
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
            {
                //super.onCharacteristicRead(gatt, characteristic, status);
                msg("on read\n\n\n");
//                Memoire.gatt = gatt;
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
            {
                //super.onCharacteristicWrite(gatt, characteristic, status);
                msg("on write\n\n\n\n\n\n\n");
//                Memoire.gatt = gatt;
            }
        };


//        msg("click bouton 2");
//        boutton2.callOnClick();

    }

    public void test()
    {


        Memoire.gatt.discoverServices();
//                            msg("on cherche des services");

        try
        {
            Memoire.listService = (ArrayList)Memoire.gatt.getServices();
            String message = "";
            for(int i =0;i<Memoire.listService.size();i++)
            {
                message += Memoire.listService.get(i).getUuid().toString()+"\n";

                            /*
                                liste des service
                                00001800-0000-8000-00805f9b34fb  "Generic Access"
                                00001801-0000-8000-00805f9b34fb  "Generic Attribute"
                                0000180a-0000-8000-00805f9b34fb  "Device Information"
                                0000ffe0-0000-8000-00805f9b34fb  SimpleKeyService ????

                            */

            }
            msg(message);


        }
        catch(Exception e)
        {
            msg("le scan service marche po");
        }

        connect.setText("titi");
    }

//--------------------------------------------------------------------------------------------------------------


    public UUID convertFromInteger(int i)
    {
        final long MSB = 0x0000000000001000L;
        final long LSB = 0x800000805f9b34fbL;
        long value = i & 0xFFFFFFFF;

        return new UUID(MSB | (value << 32), LSB);
    }


    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }



}

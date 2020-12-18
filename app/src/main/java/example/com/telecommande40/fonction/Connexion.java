package example.com.telecommande40.fonction;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import example.com.telecommande40.backup.Memoire;
import example.com.telecommande40.ecran.Telecommande;


public class Connexion extends AsyncTask<Void, Void, Void>
{

    private boolean mConnectSuccessful = true;

    public ProgressDialog progressDialog;


    @Override
    protected void onPreExecute()
    {

        progressDialog = ProgressDialog.show(new Telecommande().getContext() , "Hold on", "Connecting");// http://stackoverflow.com/a/11130220/1287554

    }

    @Override
    protected Void doInBackground(Void... devices)
    {
        try
        {

            Memoire.bluetoothSelectionne.getDevice().connectGatt(new Telecommande().getContext(),true,Memoire.gattCallback);

            msg("connexion");

        }
        catch (Exception e)
        {
            msg("Probleme de connexion");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {

        progressDialog.dismiss();


    }

    private void msg(String s)
    {
        Toast.makeText(new Telecommande().getContext(), s, Toast.LENGTH_SHORT).show();
    }



}



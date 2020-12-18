package example.com.telecommande40.fonction;

import android.os.AsyncTask;

public class Deconnexion extends AsyncTask<Void, Void, Void>
{

    @Override
    protected void onPreExecute()
    {


    }



    @Override
    protected Void doInBackground(Void... voids)
    {
     /*   if (mReadThread != null)
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
*/
        return null;
    }


    @Override
    protected void onPostExecute(Void result)
    {

  /*      super.onPostExecute(result);
        mIsBluetoothConnected = false;
        connect.setText("connecter");
        if (mIsUserInitiatedDisconnect)
        {
            finish();
        }
*/
    }






}


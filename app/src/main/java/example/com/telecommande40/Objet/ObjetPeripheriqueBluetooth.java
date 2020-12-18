package example.com.telecommande40.Objet;


import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.widget.Button;

public class ObjetPeripheriqueBluetooth
{
    private String nom;
    private String adresse;
    private Handler handler = null;
    private BluetoothDevice device = null;
    private boolean deconnect = true;
    private String type ="";
    private boolean favori = false;
    private boolean selected = false;
    private Button btnFavori;

    private Button btnfav;


    public ObjetPeripheriqueBluetooth(BluetoothDevice device, Handler handler)
    {
        if(device != null)
        {
            this.device = device;
            this.nom = device.getName();
            this.adresse = device.getAddress();
            this.handler = handler;


            switch (device.getType())
            {
                case 0:
                {
                    this.type = "Unknown";
                    break;
                }
                case 1:
                {
                    this.type = "Classic";
                    break;
                }
                case 2:
                {
                    this.type = "BLE";
                    break;
                }
                case 3:
                {
                    this.type = "Dual";
                    break;
                }
                default:
                {
                    break;
                }
            }


        }
        else
        {
            this.device = device;
            this.nom = "Aucun";
            this.adresse = "";
            this.type = "";
            this.handler = handler;
        }






        // TODO
    }

    public String getNom()
    {
        return nom;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public BluetoothDevice getDevice()
    {
        return device;
    }

    public String getType()
    {
        return type;
    }

    public boolean isFavori()
    {
        return favori;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public boolean estConnecte()
    {
        // TODO

        return false;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public void setFavori(boolean favori)
    {
        this.favori=favori;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public String toString()
    {
        return "\nNom : " + nom + "\nAdresse : " + adresse;
    }

    public void envoyer(String data)
    {
        // TODO
    }

    public void connecter()
    {
        // TODO
    }

    public boolean deconnecter()
    {
        // TODO
        return deconnect;
    }
}
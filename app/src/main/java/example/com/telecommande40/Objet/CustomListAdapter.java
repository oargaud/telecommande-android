package example.com.telecommande40.Objet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import example.com.telecommande40.R;

import java.util.List;


public class CustomListAdapter  extends BaseAdapter
{

    private List <ObjetPeripheriqueBluetooth> listData;
    private LayoutInflater layoutInflater;
    private Context context;


    public CustomListAdapter(Context aContext,  List <ObjetPeripheriqueBluetooth> listData)
    {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount()
    {
        return listData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        ObjetPeripheriqueBluetooth periphBluetooth = this.listData.get(position);

        ViewHolder holder;

        if (convertView == null)
        {

            convertView = layoutInflater.inflate(R.layout.layout_objet_peripherique_bluetooth, null);

            holder = new ViewHolder();
            holder.flagView = (ImageView) convertView.findViewById(R.id.imageView_pictograme);
            holder.deviceNameView = (TextView) convertView.findViewById(R.id.textView_deviceName);
            holder.adresseMacView = (TextView) convertView.findViewById(R.id.textView_adresseMac);
            holder.typrView= (TextView) convertView.findViewById(R.id.textView_type);
            holder.favoriView = (ImageView) convertView.findViewById(R.id.imageView_favori);


            convertView.setTag(holder);


        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.deviceNameView.setText(periphBluetooth.getNom());
        holder.adresseMacView.setText("Adresse: " + periphBluetooth.getAdresse());
        holder.typrView.setText( periphBluetooth.getType());
       // int imageId = this.getMipmapResIdByName(periphBluetooth.getFlagName());
        if(periphBluetooth.isFavori())
        {
            holder.favoriView.setImageResource(R.mipmap.etoile_jaune_1024_1024);
        }
        else
        {
            holder.favoriView.setImageResource(R.mipmap.etoile_vide_1024_1024);
        }

        holder.flagView.setImageResource( R.mipmap.logo_bluetooth);

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)
    {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder
    {
        ImageView flagView;
        TextView deviceNameView;
        TextView adresseMacView;
        TextView typrView;
        ImageView favoriView;

    }



}
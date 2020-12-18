package example.com.telecommande40.ecran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import example.com.telecommande40.R;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity
{

    private Button btnScanBluetooth;
    private Button btnTelecommande;
    private Button btnMenu3;
    private Button btnMenu4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        btnScanBluetooth = (Button) findViewById(R.id.btn_scan_bluetooth);

        btnScanBluetooth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Bluetooth.class);
                startActivity(intent);
            }
        });

        btnTelecommande = (Button) findViewById(R.id.btn_2);

        btnTelecommande.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Telecommande.class);
                startActivity(intent);
            }
        });

        btnMenu3 = (Button) findViewById(R.id.btn_3);

        btnMenu3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            /*    Intent intent = new Intent(getApplicationContext(), Bluetooth.class);
                startActivity(intent);*/
            }
        });

        btnMenu4 = (Button) findViewById(R.id.btn_4);

        btnMenu4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*Intent intent = new Intent(getApplicationContext(), Bluetooth.class);
                startActivity(intent);*/
            }
        });
    }
}

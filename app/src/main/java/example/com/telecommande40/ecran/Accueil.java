package example.com.telecommande40.ecran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import example.com.telecommande40.R;

import androidx.appcompat.app.AppCompatActivity;

public class Accueil extends AppCompatActivity
{
    private Button btnEntrer;

    private  View pageAcceil;

    private ImageView smile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_anim_accueil);

        pageAcceil = (View) findViewById(R.id.layout_anim_accueil);

        smile = (ImageView) findViewById(R.id.image_accueil);

        btnEntrer = (Button) findViewById(R.id.btn_entrer_accueil);


        btnEntrer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // On crée un utilitaire de configuration pour cette animation
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_accueil);
                // On l'affecte au widget désiré, et on démarre l'animation

                animation.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation)
                    {
                        Intent intent = new Intent(getApplicationContext(), Menu.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {

                    }
                });

                smile.startAnimation(animation);
            }
        });




    }




}

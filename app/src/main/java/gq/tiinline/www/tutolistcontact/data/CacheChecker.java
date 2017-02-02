package gq.tiinline.www.tutolistcontact.data;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jairo Duarte on 31/01/2017.
 */
/*
* */
public class CacheChecker extends AsyncTask<String
/*type de données pour travailler*/,Integer/* avancement en percentage*/ ,String /* le résultat*/>{

    private final TextView ui_status;

    public CacheChecker(TextView statusV) {
        ui_status = statusV;

    }

    //Créer un tableau de string de 3 elements
    @Override
    protected String doInBackground(String... params) {
        int cont =0;
        for (String param:params) {
            Log.i("CacheC","Starting:" +param);
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Jairo Duarte");
            cont++;
            int progress = (int)(cont/(float)params.length*100);
            publishProgress(progress);
            Log.i("CacheC","Ended:"+param);

        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ui_status.setText("Cache loading finished");
        ui_status.setVisibility(View.GONE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ui_status.setText("Cache loading started");

        ObjectAnimator heightAnimation = ObjectAnimator.ofInt(ui_status,/*propriete qui va varier*/"height",/*valeur de dêpart*/0,/*valeur d'arrivé*/300);
        heightAnimation.setDuration(4000);
        ObjectAnimator alphaAnimation =ObjectAnimator.ofFloat(ui_status,"alpha",0f,1f);
        alphaAnimation.setDuration(4000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimation).with(alphaAnimation);
        animatorSet.start();
    }
//compile 'com.android.volley:volley:1.0.0'
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ui_status.setText(values[0]+"%");
    }
}

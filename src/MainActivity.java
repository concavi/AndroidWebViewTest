package org.jellylab.testwview;

import android.content.Context;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.webkit.JavascriptInterface;
        import android.webkit.WebView;
        import android.widget.Toast;

        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    WebView mWebViewView;
    private float accX = 0, accY = 0, accZ = 0;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private final float NOISE = (float) 2.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebViewView = (WebView)findViewById(R.id.webView); //Aggancio WebView
        mWebViewView.getSettings().setJavaScriptEnabled(true);  //Abilito il Javascritp
        mWebViewView.addJavascriptInterface(new WebAppMiddleware(this), "Android"); //Abilito la classe che mi permette di far comunicare la pagina web con l'app
        mWebViewView.loadUrl("http://192.168.1.66/android-webapp.html");    //Dico dove andare a prendere la pagina

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // per ora inutile
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        accX = x;
        accY = y;
        accZ = z;
    }

    //Classe che serve racchide i metodi che possono essere chiamati dal codice javascript nella pagina
    public class WebAppMiddleware {

        Context c;  //Mi serve per usare i Toast

        WebAppMiddleware(Context c) {
            this.c = c; //Aggancio il Context dell'app
        }

        @JavascriptInterface
        public void showToast(String message){
            Toast.makeText(c, message, Toast.LENGTH_SHORT).show(); //MOstro il Toast
        }

        // restituisce l'accelerazione sull'asse x
        public float getAccX(){
            return accX;
        }

        // restituisce un array[3] di float con i valori dell'accelerazione x,y,z
        public float[] getAcceleration(){
            return new float[]{accX, accY, accZ};
        }
    }
}

package com.sebagarcia.tennis;



import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> empresaList;


    // url to get all products list
    private static String url_all_empresas = "http://tennis.zafiroweb.cl/tennischallenge/get_all_usuarios.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "usuarios";
    private static final String TAG_ID = "id";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_PUNTAJE = "puntaje";

    // products JSONArray
    JSONArray products = null;

    ListView lista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hashmap para el ListView
        empresaList = new ArrayList<HashMap<String, String>>();

        // Cargar los productos en el Background Thread
        new LoadAllProducts().execute();
        lista = (ListView) findViewById(R.id.listAllProducts);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }//fin onCreate


    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando comercios. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todos los productos
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List params = new ArrayList();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_empresas, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NOMBRE);
                        String puntaje = c.getString(TAG_PUNTAJE);

                        // creating new HashMap
                        HashMap map = new HashMap();

                        // adding each child node to HashMap key => value
                        puntaje = "Puntaje: "+puntaje;
                        map.put(TAG_ID, id);
                        map.put(TAG_NOMBRE, name);
                        map.put(TAG_PUNTAJE, puntaje);

                        empresaList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this,
                            empresaList,
                            R.layout.single_post,
                            new String[] {
                                    TAG_ID,
                                    TAG_NOMBRE,
                                    TAG_PUNTAJE,
                            },
                            new int[] {
                                    R.id.single_post_tv_id,
                                    R.id.single_post_tv_nombre,
                                    R.id.single_post_tv_puntaje,
                            });
                    // updating listview
                    //setListAdapter(adapter);
                    lista.setAdapter(adapter);
                }
            });
        }
    }
}
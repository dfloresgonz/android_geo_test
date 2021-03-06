package Servicios;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import Beans.BeanCombo;
import Beans.GetResponse;
import Beans.Utiles;

/**
 * Created by dflores on 02/03/2016.
 */
public class ComboService extends AsyncTask<String, Void, String> {

    ArrayList<String> comboList;
    ArrayList<BeanCombo> objBeanCombo;
    public GetResponse getResponse = null;

    protected String doInBackground(String... urls) {
        return Utiles.readJSONFeed(urls[0]);
    }

    protected void onPostExecute(String result) {
        try {
            Log.d("BUHOO", "result:::::: " + result);
            JSONObject mainResponseObject = new JSONObject(result);
            String error = mainResponseObject.getString("error");
            if ("0".equals(error)) {
                JSONArray objArry = mainResponseObject.getJSONArray("objeto");
                objBeanCombo = new ArrayList<BeanCombo>();
                comboList = new ArrayList<String>();
                for (int i = 0; i < objArry.length(); ++i) {
                    JSONObject comunidad = objArry.getJSONObject(i);
                    String desc = comunidad.getString("label_combo");
                    String id   = comunidad.getString("id");
                    BeanCombo combo = new BeanCombo(Integer.parseInt(id), desc);
                    objBeanCombo.add(combo);
                    comboList.add(desc);
                }
                getResponse.getDataCombo(comboList, objBeanCombo, mainResponseObject.getString("tipoCombo"));
            } else {
                Log.d("CREATION", " ---- error inesperdo: ");
            }
        } catch (Exception e) {
            Utiles.printearErrores(e, "ComboService - onPostExecute");
        }
    }
}
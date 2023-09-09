package com.company.intecap.apitienda.response;

import java.util.ArrayList;
import java.util.HashMap;

//Metadata es la informacion que se envia en el header de la peticion, para saber como respondio el servicio
public class ResponseRest {

    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();
    //clave valor  key, value... respuesta para el usuario
    //metodo publico que devuelve un arraylist de hashmap

    public ArrayList<HashMap<String, String>> getMetadata() {
        return metadata;
    }
    //metodo para agregar un hashmap a la lista de hashmap
    public void setMetadata(String tipo, String codigo, String date) {
        HashMap<String, String> mapa = new HashMap<>();
        mapa.put("tipo", tipo);
        mapa.put("codigo", codigo);
        mapa.put("date", date);
        metadata.add(mapa);
    }
}

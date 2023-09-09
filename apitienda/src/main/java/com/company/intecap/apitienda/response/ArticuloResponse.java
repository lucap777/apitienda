package com.company.intecap.apitienda.response;

import com.company.intecap.apitienda.model.Articulo;
import java.util.List;
public class ArticuloResponse {

    private List<Articulo> articulo;

    public List<Articulo> getArticulos() {
        return articulo;
    }
    public void setArticulos(List<Articulo> articulo) {
        this.articulo = articulo;
    }
}

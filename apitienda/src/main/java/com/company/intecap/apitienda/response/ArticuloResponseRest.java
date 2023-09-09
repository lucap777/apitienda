package com.company.intecap.apitienda.response;

public class ArticuloResponseRest extends ResponseRest {
    //extends ResponseRest para heredar los metodos de la clase padre

    private ArticuloResponse articuloResponse = new ArticuloResponse();
    //metodo que devuelve una lista de fabricantes hereada de la clase FabricanteResponse

    public ArticuloResponse getArticuloResponse() {
        return articuloResponse;
    }

    public void setArticuloResponse(ArticuloResponse articuloResponse) {
        this.articuloResponse = articuloResponse;
    }

}


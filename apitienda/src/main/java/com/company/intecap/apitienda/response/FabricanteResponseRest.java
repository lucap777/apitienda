package com.company.intecap.apitienda.response;

public class FabricanteResponseRest extends ResponseRest {
    //extends ResponseRest para heredar los metodos de la clase padre

    private FabricanteResponse fabricanteResponse = new FabricanteResponse();
    //metodo que devuelve una lista de fabricantes hereada de la clase FabricanteResponse

    public FabricanteResponse getFabricanteResponse() {
        return fabricanteResponse;
    }

    public void setFabricanteResponse(FabricanteResponse fabricanteResponse) {
        this.fabricanteResponse = fabricanteResponse;
    }

}

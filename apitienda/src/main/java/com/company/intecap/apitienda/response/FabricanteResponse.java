package com.company.intecap.apitienda.response;

import com.company.intecap.apitienda.model.Fabricante;

import java.util.List;

public class FabricanteResponse {
    private List<Fabricante> fabricantes;

    public List<Fabricante> getFabricantes() {
        return fabricantes;
    }
    public void setFabricantes(List<Fabricante> fabricantes) {
        this.fabricantes = fabricantes;
    }
}

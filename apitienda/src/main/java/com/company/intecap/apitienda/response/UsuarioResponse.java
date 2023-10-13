package com.company.intecap.apitienda.response;

import com.company.intecap.apitienda.model.Role;
import com.company.intecap.apitienda.model.Usuario;

import java.util.List;

public class UsuarioResponse {
    private List<Usuario> usuarios;

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(List<Usuario> roles) {
        this.usuarios = usuarios;
    }
}

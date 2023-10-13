package com.company.intecap.apitienda.response;

public class UsuarioResponseRest  extends ResponseRest {
    private UsuarioResponse usuarioResponse = new UsuarioResponse();

    public UsuarioResponse getUsuarioResponse() {
        return usuarioResponse;
    }

    public void setUsuarioResponse(UsuarioResponse usuarioResponse) {
        this.usuarioResponse = usuarioResponse;
    }
}

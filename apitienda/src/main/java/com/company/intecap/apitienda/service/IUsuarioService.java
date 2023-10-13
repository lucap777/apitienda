package com.company.intecap.apitienda.service;

import com.company.intecap.apitienda.model.Usuario;
import com.company.intecap.apitienda.response.UsuarioResponseRest;
import org.springframework.http.ResponseEntity;

public interface IUsuarioService {
    public ResponseEntity<UsuarioResponseRest> buscarUsuarios();
    public ResponseEntity<UsuarioResponseRest> buscarPorId(Long id);

    public ResponseEntity<UsuarioResponseRest> crear(Usuario usuario);

    public ResponseEntity<UsuarioResponseRest> actualizar(Usuario usuario, Long id);

    public ResponseEntity<UsuarioResponseRest> eliminar(Long id);
}

package com.dicasa.pedidos.service;

import com.dicasa.pedidos.model.Usuario;
import com.dicasa.pedidos.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario criar(Usuario usuario) {
        String nomeUsuario = usuario.getCredenciais() != null ? usuario.getCredenciais().getUsuario() : null;
        if (nomeUsuario != null && repository.existsByCredenciaisUsuario(nomeUsuario)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este nome de usuário já está em uso. Escolha outro.");
        }
        if (usuario.getEmail() != null && !usuario.getEmail().isBlank() && repository.existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
        }
        return repository.save(usuario);
    }

    public Usuario atualizar(String id, Usuario usuario) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        usuario.setId(id);
        return repository.save(usuario);
    }

    public Usuario login(Map<String, String> credenciais) {
        String nomeUsuario = credenciais.get("usuario");
        String senha = credenciais.get("senha");

        return repository.findByCredenciaisUsuario(nomeUsuario)
                .filter(u -> u.getCredenciais() != null && senha.equals(u.getCredenciais().getSenha()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos"));
    }
}

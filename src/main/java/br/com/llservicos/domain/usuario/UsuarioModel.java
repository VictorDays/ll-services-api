package br.com.llservicos.domain.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UsuarioModel {
    @Id
    private Long id;
    private String email;
    private String senha;
    private Perfil perfil;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}

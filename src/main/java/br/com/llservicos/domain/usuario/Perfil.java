package br.com.llservicos.domain.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;

public enum Perfil {
        USER(1, "Usuário"),
        ADMIN(2, "Admin");

        private final Integer id;
        private final String label;

        Perfil(Integer id, String label) {
            this.id = id;
            this.label = label;
        }

        public Integer getId() {
            return id;
        }

        public String getLabel() {
            return label;
        }

        public static Perfil valueOf(Integer id) throws IllegalArgumentException {
            if (id == null)
                return null;
            for (Perfil perfil : Perfil.values()) {
                if (perfil.getId().equals(id))
                    return perfil;
            }
            throw new IllegalArgumentException("Id inválido" + id);
        }

    }
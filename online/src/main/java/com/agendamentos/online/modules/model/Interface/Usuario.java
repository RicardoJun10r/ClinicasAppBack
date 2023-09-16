package com.agendamentos.online.modules.model.Interface;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class Usuario extends Pessoa {

    @Column(unique = true, name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(nullable = false, name = "cargo")
    private Boolean cargo;

}

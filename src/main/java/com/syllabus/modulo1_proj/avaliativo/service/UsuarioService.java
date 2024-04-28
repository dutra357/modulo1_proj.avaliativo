package com.syllabus.modulo1_proj.avaliativo.service;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario.DtoUsuarioRequest;
import com.syllabus.modulo1_proj.avaliativo.dtoUtils.usuario.DtoUsuarioResponse;

public interface UsuarioService {

    DtoUsuarioResponse criarUsuario(DtoUsuarioRequest novoUsuario);

}

package com.breda.redesocial.service;

import com.breda.redesocial.dto.AuthenticationRequest;
import com.breda.redesocial.dto.AuthenticationResponse;

public interface AuthenticationService {

  AuthenticationResponse auhtenticate(AuthenticationRequest authenticationRequest);

  String getApelidoUsuarioLogado();
}

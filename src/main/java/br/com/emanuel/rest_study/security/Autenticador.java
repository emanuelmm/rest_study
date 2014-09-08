package br.com.emanuel.rest_study.security;

import br.com.emanuel.rest_study.jpa.ChaveUsuarioJPA;
import br.com.emanuel.rest_study.jpa.UsuarioJPA;
import br.com.emanuel.rest_study.jpa.entity.ChaveUsuario;
import br.com.emanuel.rest_study.jpa.entity.Usuario;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.security.auth.login.LoginException;

/**
 * @author Max Lam - http://www.developerscrappad.com/1814/java/java-ee/rest-jax-rs/java-ee-7-jax-rs-2-0-simple-rest-api-authentication-authorization-with-custom-http-header/
 * @author Emanuel Cordeiro
 */
@Singleton
public class Autenticador {

    @Inject private UsuarioJPA usuarioJPA;
    @Inject private ChaveUsuarioJPA chaveUsuarioJPA;
    private final Map<String, String> authorizationTokensStorage = new HashMap();

    private Autenticador() {

    }

    public String login(String chaveUsuario, String login, String senha) throws LoginException {

        ChaveUsuario chaveFounded = chaveUsuarioJPA.buscarPorChave(chaveUsuario);
        if (chaveFounded != null && chaveFounded.getUsuario().equals(login)) {
            Usuario usuario = usuarioJPA.buscarPorLogin(login);
            if (usuario != null && usuario.getSenha().equals(senha)) {
                String authToken = UUID.randomUUID().toString();
                authorizationTokensStorage.put(authToken, login);
                return authToken;
            }
        }

        throw new LoginException("Usuário sem permissão!");
    }

    public boolean isAuthTokenValid(String serviceKey, String authToken) {
        ChaveUsuario chaveUsuario = chaveUsuarioJPA.buscarPorChave(serviceKey);
        if (chaveUsuario != null && authorizationTokensStorage.containsKey(authToken)) {
            String usernameMatch2 = authorizationTokensStorage.get(authToken);
            if (chaveUsuario.getUsuario().equals(usernameMatch2)) {
                return true;
            }
        }
        return false;
    }

    public void logout(String serviceKey, String authToken) throws GeneralSecurityException {

        ChaveUsuario chaveUsuario = chaveUsuarioJPA.buscarPorChave(serviceKey);
        if (chaveUsuario != null) {
            String usernameMatch1 = chaveUsuario.getUsuario();
            if (authorizationTokensStorage.containsKey(authToken)) {
                String usernameMatch2 = authorizationTokensStorage.get(authToken);
                if (usernameMatch1.equals(usernameMatch2)) {
                    authorizationTokensStorage.remove(authToken);
                    return;
                }
            }
        }
        throw new GeneralSecurityException("Chave do serviço ou token inválido");
    }

    public boolean isServiceKeyValid(String serviceKey) {
        return chaveUsuarioJPA.buscarPorChave(serviceKey)!=null;
    }
    
    
}

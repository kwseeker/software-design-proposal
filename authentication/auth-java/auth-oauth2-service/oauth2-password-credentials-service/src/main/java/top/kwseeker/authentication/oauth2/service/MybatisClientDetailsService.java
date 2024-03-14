package top.kwseeker.authentication.oauth2.service;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class MybatisClientDetailsService implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        //TODO
        return null;
    }
}

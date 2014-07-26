package it.valeriovaudi.support;

import it.valeriovaudi.factory.SecurityUserFactory;
import it.valeriovaudi.web.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.annotation.PostConstruct;

/**
 * Created by Valerio on 26/07/2014.
 */
public class DefaultUserStarterSupport {
    private SecurityUserFactory securityUserFactory;
    private UserDetailsManager userDetailsManager;

    @Autowired
    public void setUserDetailsManager(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @Autowired
    public void setSecurityUserFactory(SecurityUserFactory securityUserFactory) {
        this.securityUserFactory = securityUserFactory;
    }

    @PostConstruct
    private void initUsers(){
        UserDTO userDTO = new UserDTO();

        userDTO.setUserName("admin");
        userDTO.setPassword("admin");

        UserDetails user = securityUserFactory.createUser(userDTO);
        userDetailsManager.createUser(user);
    }
}

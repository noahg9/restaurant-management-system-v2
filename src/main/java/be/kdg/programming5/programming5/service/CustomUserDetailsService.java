package be.kdg.programming5.programming5.service;

import be.kdg.programming5.programming5.domain.Chef;
import be.kdg.programming5.programming5.domain.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The type Custom user details service.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ChefService chefService;

    /**
     * Instantiates a new Custom user details service.
     *
     * @param chefService the chef repository
     */
    public CustomUserDetailsService(ChefService chefService) {
        this.chefService = chefService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Chef chef = chefService.getChefByUsername(username);
        if (chef != null) {
            var authorities = new ArrayList<SimpleGrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(chef.getRole().getCode()));
            // Spring's `User`
            return new CustomUserDetails(chef.getUsername(), chef.getPassword(), authorities, chef.getId());
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }
}

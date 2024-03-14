package top.kwseeker.authentication.oauth2.service.test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

//仅仅用于测试
@Service("defaultUserDetailService")
public class DefaultUserDetailService  implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Test
        if (!"Arvin".equalsIgnoreCase(username)) {
            throw new UsernameNotFoundException("not found username: " + username);
        }
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                GrantedAuthority authority = new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ROLE_USER";
                    }
                };
                return Collections.singletonList(authority);
            }

            @Override
            public String getPassword() {
                return "123456";
            }

            @Override
            public String getUsername() {
                return "Arvin";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}

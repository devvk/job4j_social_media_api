package ru.job4j.socialmedia.security.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.job4j.socialmedia.model.User;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private final Long id;

    private final String username;

    private final String email;

    @JsonIgnore
    private final String passwordHash;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String passwordHash,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                authorities);
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }
}

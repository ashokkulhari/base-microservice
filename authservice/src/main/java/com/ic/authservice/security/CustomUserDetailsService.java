package com.ic.authservice.security;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;

public class CustomUserDetailsService{

}
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                user.isEnabled(),
//                true, true, true,
//                convertRolesToAuthorities(user.getRoles()) // Assuming roles are properly loaded here
//        );
//    }
//    private Collection<GrantedAuthority> convertRolesToAuthorities(List<Role> roles) {
//        return roles.stream()
//                .map(role -> role.getName())
//                .map(SimpleGrantedAuthority::new) // Convert each role to SimpleGrantedAuthority
//                .collect(Collectors.toList());
//    }
//    public User saveUser(User user) {
//        user.setPassword(encoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }
//}


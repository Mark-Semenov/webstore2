package ru.gb.mark.webstore.service;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.dto.UserDTO;
import ru.gb.mark.webstore.entity.Cart;
import ru.gb.mark.webstore.entity.User;
import ru.gb.mark.webstore.entity.UserWrapper;
import ru.gb.mark.webstore.repository.RoleRepository;
import ru.gb.mark.webstore.repository.UserRepository;
import ru.gb.mark.webstore.service.cart.UserCart;

import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserCart userCart;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;


    @Transactional
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            if (user.get().getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_USER"))) {

                Cart cart = user.get().getCart();

                if (!cart.getProducts().isEmpty()) {
                    userCart.loadSavedProducts(cart.getProducts());
                }
            }
        }

        return user.map(usr -> new org.springframework.security.core.userdetails.User(
                usr.getEmail(),
                usr.getPassword(),
                usr.getRoles())).orElseThrow();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public String registerNewUserAccount(UserDTO user) {

        User u = UserWrapper.builder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setDate(user.getDate())
                .setPhone(user.getPhone())
                .setEmail(user.getEmail())
                .setPassword(encoder.encode(user.getPassword()))
                .setRoles(roleRepository.findByName("ROLE_USER"))
                .setCart(new Cart())
                .build();


        userRepository.save(u);
        saveUserCartWithProducts(u.getCart());
        userCart.clearCart();

        return "redirect:/login";

    }

    public void saveUserCartWithProducts(Cart cart) {
        userCart.saveCart(cart);
    }
}

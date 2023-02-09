package ru.gb.mark.webstore.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.dto.EntityMapper;
import ru.gb.mark.webstore.dto.UserDTO;
import ru.gb.mark.webstore.dto.AppRoles;
import ru.gb.mark.webstore.entity.Cart;
import ru.gb.mark.webstore.entity.Role;
import ru.gb.mark.webstore.entity.User;
import ru.gb.mark.webstore.repository.RoleRepository;
import ru.gb.mark.webstore.repository.UserRepository;
import ru.gb.mark.webstore.service.cart.UserCart;

import java.util.List;
import java.util.Optional;

@Log4j2
@Component
@Data
public class UserService implements UserDetailsService {

    private final UserCart userCart;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    private final EntityMapper<User, UserDTO> mapper;


    @Transactional
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            if (user.get()
                    .getRoles()
                    .stream()
                    .findFirst()
                    .orElseThrow()
                    .getName()
                    .equals(AppRoles.ROLE_USER.name())) {

                Cart cart = user.get().getCart();
                log.info(cart);

                if (!cart.getProducts().isEmpty()) {
                    userCart.loadSavedProducts(cart.getProducts());
                }
            }
        }

        return user.map(usr -> new org.springframework.security.core.userdetails.User(
                usr.getEmail(),
                usr.getPassword(),
                usr.getRoles())).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Transactional
    public String registerNewUserAccount(UserDTO userDTO) {

        if (userDTO.getRole() == null) {
            userDTO.setRole(AppRoles.USER.name());
        }

        List<Role> roles = roleRepository.findByName(userDTO.buildRole());
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        userDTO.setCart(new Cart());
        userDTO.setRoles(roles);
        User user = mapper.mapDtoToEntity(userDTO);

        userRepository.save(user);
        saveUserCartWithProducts(user.getCart());
        userCart.clearCart();

        return "redirect:/login";

    }

    public void saveUserCartWithProducts(Cart cart) {
        userCart.saveCart(cart);
    }
}

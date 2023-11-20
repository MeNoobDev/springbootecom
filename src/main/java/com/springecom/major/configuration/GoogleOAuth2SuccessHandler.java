package com.springecom.major.configuration;

import com.springecom.major.model.Role;
import com.springecom.major.model.User;
import com.springecom.major.repository.RoleRepository;
import com.springecom.major.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        // Check if the 'email' attribute is present in the principal attributes
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        if (attributes != null && attributes.containsKey("email")) {
            String email = attributes.get("email").toString();

            Optional<User> existingUser = userRepository.findUserByEmail(email);

            if (!existingUser.isPresent()) {
                User user = new User();
                user.setFirstName(attributes.get("given_name") != null ? attributes.get("given_name").toString() : "");
                user.setLastName(attributes.get("family_name") != null ? attributes.get("family_name").toString() : "");
                user.setEmail(email);

                // Assuming Role with ID 2 exists in your database
                Role role = roleRepository.findById(2).orElse(null);

                if (role != null) {
                    List<Role> roles = new ArrayList<>();
                    roles.add(role);
                    user.setRoles(roles);
                } else {
                    // Handle the case where the Role with ID 2 doesn't exist
                }

                userRepository.save(user);
            }

            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
        } else {
            // Handle the case where 'email' attribute is missing
            // You can log an error or redirect the user to an error page
            // For example:
            // response.sendRedirect("/error-page");
        }
    }
}

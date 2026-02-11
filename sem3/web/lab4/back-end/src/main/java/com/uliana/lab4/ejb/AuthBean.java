package com.uliana.lab4.ejb;

import com.uliana.lab4.entity.UserEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Stateless
public class AuthBean {

    @PersistenceContext(unitName = "lab4PU")
    private EntityManager em;

    // ===== PUBLIC API =====

    public UserEntity register(String login, String password, String firstName, String lastName) {
        if (findByLogin(login).isPresent()) throw new IllegalArgumentException("User already exists");

        UserEntity user = new UserEntity();
        user.setLogin(login);
        user.setPasswordHash(hash(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);

        em.persist(user);
        return user;
    }


    public UserEntity getCurrentUser(javax.servlet.http.HttpServletRequest request) {
        javax.servlet.http.HttpSession session = request.getSession(false);
        if (session == null) return null;

        Object idObj = session.getAttribute("userId");
        if (idObj == null) return null;

        Long userId;
        if (idObj instanceof Long) userId = (Long) idObj;
        else if (idObj instanceof Integer) userId = ((Integer) idObj).longValue();
        else return null;

        return em.find(UserEntity.class, userId);
    }


    public Optional<UserEntity> login(String login, String password) {
        Optional<UserEntity> userOpt = findByLogin(login);
        if (userOpt.isEmpty()) return Optional.empty();

        UserEntity user = userOpt.get();
        if (user.getPasswordHash().equals(hash(password))) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    // ===== INTERNAL =====

    private Optional<UserEntity> findByLogin(String login) {
        TypedQuery<UserEntity> q = em.createQuery(
                "SELECT u FROM UserEntity u WHERE u.login = :login",
                UserEntity.class
        );
        q.setParameter("login", login);
        return q.getResultStream().findFirst();
    }

    private String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing error", e);
        }
    }

    public UserEntity updateProfile(Long userId, String firstName, String lastName) {
        UserEntity user = em.find(UserEntity.class, userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);

        return em.merge(user);
    }

}

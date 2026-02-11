package com.uliana.lab4.ejb;

import com.uliana.lab4.entity.PointEntity;
import com.uliana.lab4.entity.UserEntity;
import com.uliana.lab4.util.HitChecker;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Stateless
public class PointBean {

    @PersistenceContext(unitName = "lab4PU")
    private EntityManager em;

    public PointEntity addPoint(UserEntity user, double x, BigDecimal y, int r) {
        long start = System.nanoTime();

        boolean hit = HitChecker.check(x, y, r);

        long end = System.nanoTime();

        PointEntity p = new PointEntity();
        p.setUser(user);
        p.setX(x);
        p.setY(y);
        p.setR(r);
        p.setHit(hit);
        p.setTimeDoing((end - start) + "ns");
        p.setTimeNow(LocalTime.now());

        em.persist(p);
        return p;
    }

    public List<PointEntity> getPoints(UserEntity user) {
        return em.createQuery(
                        "SELECT p FROM PointEntity p WHERE p.user = :user ORDER BY p.id DESC",
                        PointEntity.class
                )
                .setParameter("user", user)
                .getResultList();
    }

    public void clearPoints(UserEntity user) {
        em.createQuery("DELETE FROM PointEntity p WHERE p.user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }

    public void deletePoint(UserEntity user, Long id) {
        PointEntity p = em.find(PointEntity.class, id);
        if (p == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        if (p.getUser() == null || p.getUser().getId() == null || !p.getUser().getId().equals(user.getId())) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        em.remove(p);
    }
}

package com.uliana.lab4.rest;

import com.uliana.lab4.dto.PointDTO;
import com.uliana.lab4.ejb.PointBean;
import com.uliana.lab4.entity.PointEntity;
import com.uliana.lab4.entity.UserEntity;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Path("/points")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PointsResource {

    @EJB
    private PointBean pointBean;

    @PersistenceContext(unitName = "lab4PU")
    private EntityManager em;

    @Context
    private HttpServletRequest request;

    // SSE
    @Context
    private Sse sse;

    @Inject
    private PointsSseRegistry registry;

    @POST
    @Path("/add")
    public Response addPoint(PointDTO dto) {
        UserEntity user = getUserOr401();

        PointEntity p = pointBean.addPoint(
                user,
                dto.getX(),
                dto.getY(),
                dto.getR()
        );

        PointDTO outDto = toDto(p);

        // пушим всем вкладкам/браузерам этого пользователя
        Long userId = user.getId();
        for (SseEventSink sink : registry.get(userId)) {
            if (sink == null || sink.isClosed()) {
                registry.remove(userId, sink);
                continue;
            }
            try {
                sink.send(
                        sse.newEventBuilder()
                                .name("point")
                                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                                .data(PointDTO.class, outDto)
                                .build()
                );
            } catch (Exception e) {
                registry.remove(userId, sink);
            }
        }

        return Response.ok(outDto).build();
    }

    @GET
    @Path("/list")
    public Response list() {
        UserEntity user = getUserOr401();
        List<PointEntity> points = pointBean.getPoints(user);

        List<PointDTO> out = points.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return Response.ok(out).build();
    }

    @DELETE
    @Path("/clear")
    public Response clear() {
        UserEntity user = getUserOr401();
        pointBean.clearPoints(user);
        return Response.ok().build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        UserEntity user = getUserOr401();
        pointBean.deletePoint(user, id);
        return Response.ok().build();
    }

    // SSE stream
    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void stream(@Context SseEventSink sink) {
        UserEntity user = getUserOr401();
        Long userId = user.getId();

        registry.add(userId, sink);

        // В Resteasy нет sink.onClose, поэтому чистим в момент отправки (в addPoint)
        // Можно дополнительно сразу отправить "hello", чтобы проверить, что канал живой:
        try {
            sink.send(
                    sse.newEventBuilder()
                            .name("hello")
                            .data(String.class, "connected")
                            .build()
            );
        } catch (Exception e) {
            registry.remove(userId, sink);
            try { sink.close(); } catch (Exception ignored) {}
        }
    }


    private PointDTO toDto(PointEntity p) {
        PointDTO dto = new PointDTO();
        dto.setId(p.getId());
        dto.setX(p.getX());
        dto.setY(p.getY());
        dto.setR(p.getR());
        dto.setHit(p.isHit());
        dto.setTimeDoing(p.getTimeDoing());

        LocalTime t = p.getTimeNow();
        dto.setTimeNow(t == null ? null : t.toString());

        return dto;
    }

    private UserEntity getUserOr401() {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        Long userId = (Long) session.getAttribute("userId");
        UserEntity u = em.find(UserEntity.class, userId);

        if (u == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        return u;
    }
}

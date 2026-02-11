package com.uliana.lab4.rest;

import javax.ejb.Singleton;
import javax.ws.rs.sse.SseEventSink;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class PointsSseRegistry {

    private final Map<Long, CopyOnWriteArrayList<SseEventSink>> sinks = new ConcurrentHashMap<>();

    public void add(Long userId, SseEventSink sink) {
        sinks.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(sink);
    }

    public void remove(Long userId, SseEventSink sink) {
        CopyOnWriteArrayList<SseEventSink> list = sinks.get(userId);
        if (list == null) return;
        list.remove(sink);
        if (list.isEmpty()) sinks.remove(userId);
    }

    public List<SseEventSink> get(Long userId) {
        return sinks.getOrDefault(userId, new CopyOnWriteArrayList<>());
    }
}

package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.BoardInvitation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@CommonsLog
@AllArgsConstructor
public class InvitationsRepository {
    private final static String REDIS_INVITATIONS_HASH = "invitations";
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public BoardInvitation findInvitationsByCode(String code) {
        String rawInvitation = (String) redisTemplate.opsForHash().get(REDIS_INVITATIONS_HASH, code);
        return objectMapper.readValue(rawInvitation, BoardInvitation.class);
    }

    @SneakyThrows
    public void removeByCode(String code) {
        redisTemplate.opsForHash().delete(REDIS_INVITATIONS_HASH, code);
    }

    @SneakyThrows
    public void putInvitation(String code, BoardInvitation invitation) {
        redisTemplate.opsForHash().put(REDIS_INVITATIONS_HASH, code,
                objectMapper.writeValueAsString(invitation));
    }

    @SneakyThrows
    public List<BoardInvitation> findInvitationByEmail(String email) {
        List<BoardInvitation> invitations = new ArrayList<>();
        try{
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(REDIS_INVITATIONS_HASH);
            for (Map.Entry<Object, Object> entry : entries.entrySet()) {
                String val = (String) entry.getValue();
                BoardInvitation found = objectMapper.readValue(val, BoardInvitation.class);
                if (Objects.equals(found.getEmail(), email)) {
                    invitations.add(found);
                }
            }
        } catch (Exception e) {
            log.error("error reading from redis");
        }
        return invitations;
    }
}

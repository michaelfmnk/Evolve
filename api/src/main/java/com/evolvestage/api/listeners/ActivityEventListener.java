package com.evolvestage.api.listeners;

import com.evolvestage.api.entities.Activity;
import com.evolvestage.api.listeners.events.Event;
import com.evolvestage.api.repositories.ActivityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.transaction.Transactional;

@Service
@CommonsLog
@AllArgsConstructor
public class ActivityEventListener {
    private final ActivityRepository repository;

    @Transactional
    @TransactionalEventListener(value = Event.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void onEvent(Event event) {
        Activity saved = repository.save(Activity.builder()
                .actorId(event.getUserId())
                .boardId(event.getBoardId())
                .type(event.getType())
                .data(event.getData())
                .build());
        log.info("event was logged " + saved.toString());
    }

}

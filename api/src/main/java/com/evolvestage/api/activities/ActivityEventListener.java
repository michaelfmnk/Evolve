package com.evolvestage.api.activities;

import com.evolvestage.api.activities.events.ActivityEvent;
import com.evolvestage.api.entities.Activity;
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
    @TransactionalEventListener(value = ActivityEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void onEvent(ActivityEvent event) {
        Activity saved = repository.save(Activity.builder()
                .actorId(event.getUserId())
                .boardId(event.getBoardId())
                .type(event.getType())
                .data(event.getData())
                .build());
        log.info("event was logged " + saved.toString());
    }

}

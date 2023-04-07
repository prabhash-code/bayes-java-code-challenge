package gg.bayes.challenge.service.processor.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import lombok.Getter;

@Getter
public class HeroKilledEvent extends Event {
    private String hero;
    private String target;
    private CombatLogEntryEntity.Type type;

    public HeroKilledEvent(String timestamp, String hero, String target, CombatLogEntryEntity.Type type) {
        this.timestamp = timestamp;
        this.hero = hero;
        this.target = target;
        this.type = type;
    }

}

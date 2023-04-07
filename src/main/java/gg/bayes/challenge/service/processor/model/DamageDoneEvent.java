package gg.bayes.challenge.service.processor.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import lombok.Getter;

@Getter
public class DamageDoneEvent extends Event {
    private String hero;
    private String target;
    private String ability;
    private String damage;
    private CombatLogEntryEntity.Type type;

    public DamageDoneEvent(String timestamp, String hero, String target, String ability, String damage, CombatLogEntryEntity.Type type) {
        this.timestamp = timestamp;
        this.hero = hero;
        this.target = target;
        this.ability = ability;
        this.damage = damage;
        this.type = type;
    }
}

package gg.bayes.challenge.service.processor.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import lombok.Getter;


@Getter
public class SpellCastEvent extends Event {

    private String hero;
    private String ability;
    private String abilityLevel;
    private String target;
    private CombatLogEntryEntity.Type type;

    public SpellCastEvent(String timestamp, String hero, String ability, String abilityLevel, String target, CombatLogEntryEntity.Type type) {
        this.timestamp = timestamp;
        this.hero = hero;
        this.ability = ability;
        this.abilityLevel = abilityLevel;
        this.target = target;
        this.type = type;
    }

}

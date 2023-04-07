package gg.bayes.challenge.service.processor.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import lombok.Getter;

@Getter
public class ItemPurchasedEvent extends Event {

    private String hero;
    private String item;
    private CombatLogEntryEntity.Type type;

    public ItemPurchasedEvent(String timestamp, String hero, String item, CombatLogEntryEntity.Type type) {
        this.timestamp = timestamp;
        this.hero = hero;
        this.item = item;
        this.type = type;
    }
}

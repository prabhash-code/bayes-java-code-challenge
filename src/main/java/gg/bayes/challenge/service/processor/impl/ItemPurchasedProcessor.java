package gg.bayes.challenge.service.processor.impl;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.service.processor.LogLineProcessor;
import gg.bayes.challenge.service.processor.model.Event;
import gg.bayes.challenge.service.processor.model.ItemPurchasedEvent;

import java.util.regex.Matcher;

public class ItemPurchasedProcessor implements LogLineProcessor {

    private static ItemPurchasedProcessor instance;

    private ItemPurchasedProcessor() {
        // private constructor to prevent instantiation
    }

    public static ItemPurchasedProcessor getInstance() {
        if (instance == null) {
            instance = new ItemPurchasedProcessor();
        }
        return instance;
    }

    @Override
    public Event processLine(Matcher matcher) {
        matcher.matches();
        return new ItemPurchasedEvent(matcher.group(1), matcher.group(2), matcher.group(3),
                CombatLogEntryEntity.Type.ITEM_PURCHASED);
    }
}

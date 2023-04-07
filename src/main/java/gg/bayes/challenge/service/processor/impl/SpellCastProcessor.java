package gg.bayes.challenge.service.processor.impl;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.service.processor.LogLineProcessor;
import gg.bayes.challenge.service.processor.model.Event;
import gg.bayes.challenge.service.processor.model.SpellCastEvent;

import java.util.regex.Matcher;

public class SpellCastProcessor  implements LogLineProcessor {

    private static SpellCastProcessor instance;

    private SpellCastProcessor() { }

    public static SpellCastProcessor getInstance() {
        if (instance == null) {
            instance = new SpellCastProcessor();
        }
        return instance;
    }
    @Override
    public Event processLine(Matcher matcher) {
        matcher.matches();
        return new SpellCastEvent(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4),
                matcher.group(5), CombatLogEntryEntity.Type.SPELL_CAST);
    }
}

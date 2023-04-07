package gg.bayes.challenge.service.processor.impl;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.service.processor.LogLineProcessor;
import gg.bayes.challenge.service.processor.model.DamageDoneEvent;
import gg.bayes.challenge.service.processor.model.Event;

import java.util.regex.Matcher;

public class DamageDoneProcessor  implements LogLineProcessor {

    private static DamageDoneProcessor instance;

    private DamageDoneProcessor() {
        // private constructor to prevent instantiation
    }

    public static DamageDoneProcessor getInstance() {
        if (instance == null) {
            instance = new DamageDoneProcessor();
        }
        return instance;
    }
    @Override
    public Event processLine(Matcher matcher) {
        matcher.matches();
        return new DamageDoneEvent(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4),
                matcher.group(5), CombatLogEntryEntity.Type.DAMAGE_DONE);
    }
}

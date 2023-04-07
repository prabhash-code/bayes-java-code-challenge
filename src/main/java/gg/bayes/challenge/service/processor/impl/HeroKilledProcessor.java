package gg.bayes.challenge.service.processor.impl;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.service.processor.LogLineProcessor;
import gg.bayes.challenge.service.processor.model.Event;
import gg.bayes.challenge.service.processor.model.HeroKilledEvent;

import java.util.regex.Matcher;

public class HeroKilledProcessor  implements LogLineProcessor {

    private static HeroKilledProcessor instance;

    private HeroKilledProcessor() {
        // private constructor to prevent instantiation
    }

    public static HeroKilledProcessor getInstance() {
        if (instance == null) {
            instance = new HeroKilledProcessor();
        }
        return instance;
    }
    @Override
    public Event processLine(Matcher matcher) {
        matcher.matches();
        return new HeroKilledEvent(matcher.group(1), matcher.group(2), matcher.group(3),
                CombatLogEntryEntity.Type.HERO_KILLED);
    }
}

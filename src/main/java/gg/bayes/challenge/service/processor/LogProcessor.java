package gg.bayes.challenge.service.processor;

import gg.bayes.challenge.mapper.EventMapper;
import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.service.processor.model.*;
import gg.bayes.challenge.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogProcessor {
    private final EventMapper eventMapper;
    private MatchEntity match;

    public List<CombatLogEntryEntity> process(MatchEntity match, String combatLog) {
        this.match = match;
        List<Event> stream = convert(combatLog);
        return processEvents(stream);
    }

    private List<Event> convert(String combatLog) {
        return combatLog.lines()
                .map(s -> getEvent(s))
                .filter(event -> Objects.nonNull(event))
                .collect(Collectors.toList());
    }

    private Event getEvent(String line) {
        LogLineProcessor lineProcessor = null;
        Event event = null;

        if (Constants.ITEM_PURCHASED_PATTERN.matcher(line).find()) {
            lineProcessor = LogProcessorFactory.getLogLineProcessor(CombatLogEntryEntity.Type.ITEM_PURCHASED);
            event = lineProcessor.processLine(Constants.ITEM_PURCHASED_PATTERN.matcher(line));
        }

        if (Constants.SPELL_CAST_PATTERN.matcher(line).find()) {
            lineProcessor = LogProcessorFactory.getLogLineProcessor(CombatLogEntryEntity.Type.SPELL_CAST);
            event = lineProcessor.processLine(Constants.SPELL_CAST_PATTERN.matcher(line));
        }

        if (Constants.DAMAGE_DONE_PATTERN.matcher(line).find()) {
            lineProcessor = LogProcessorFactory.getLogLineProcessor(CombatLogEntryEntity.Type.DAMAGE_DONE);
            event = lineProcessor.processLine(Constants.DAMAGE_DONE_PATTERN.matcher(line));
        }

        if (Constants.HERO_KILLED_PATTERN.matcher(line).find()){
            lineProcessor = LogProcessorFactory.getLogLineProcessor(CombatLogEntryEntity.Type.HERO_KILLED);
            event = lineProcessor.processLine(Constants.HERO_KILLED_PATTERN.matcher(line));
        }

        return event;
    }

    private List<CombatLogEntryEntity> processEvents(List<Event> events) {
        return events.stream()
                .map(event -> getEntity(event))
                .filter(entry -> Objects.nonNull(entry))
                .peek(entry -> entry.setMatch(this.match))
                .collect(Collectors.toList());
    }

    private CombatLogEntryEntity getEntity(Event event) {
        CombatLogEntryEntity combatLogEntryEntity = null;
        if (event.getClass().equals(ItemPurchasedEvent.class))
            combatLogEntryEntity = eventMapper.toDomain((ItemPurchasedEvent) event);

        if (event.getClass().equals(DamageDoneEvent.class))
            combatLogEntryEntity = eventMapper.toDomain((DamageDoneEvent) event);

        if (event.getClass().equals(HeroKilledEvent.class))
            combatLogEntryEntity = eventMapper.toDomain((HeroKilledEvent) event);

        if (event.getClass().equals(SpellCastEvent.class))
            combatLogEntryEntity = eventMapper.toDomain((SpellCastEvent) event);

        return combatLogEntryEntity;
    }
}

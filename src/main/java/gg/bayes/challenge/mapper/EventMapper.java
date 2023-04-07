package gg.bayes.challenge.mapper;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.service.processor.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "event", target = "timestamp", qualifiedByName = "mapTimestamp")
    @Mapping(source = "hero", target = "actor")
    CombatLogEntryEntity toDomain(ItemPurchasedEvent event);
    @Mapping(source = "event", target = "timestamp", qualifiedByName = "mapTimestamp")
    @Mapping(source = "hero", target = "actor")
    CombatLogEntryEntity toDomain(DamageDoneEvent event);
    @Mapping(source = "event", target = "timestamp", qualifiedByName = "mapTimestamp")
    @Mapping(source = "hero", target = "actor")
    CombatLogEntryEntity toDomain(HeroKilledEvent event);
    @Mapping(source = "event", target = "timestamp", qualifiedByName = "mapTimestamp")
    @Mapping(source = "ability", target = "ability", qualifiedByName = "mapAbility")
    @Mapping(source = "hero", target = "actor")
    CombatLogEntryEntity toDomain(SpellCastEvent event);

    @Named("mapTimestamp")
    default Long mapTimestamp(Event event) {
        LocalTime localTime = LocalTime.parse(event.getTimestamp());
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    @Named("mapAbility")
    default String mapAbility(String value) {
        String npcDotaHero = "npc_dota_hero_";
        String npcDota = "npc_dota_";
        if(value.startsWith(npcDotaHero)) return value.substring(npcDotaHero.length());
        if(value.startsWith(npcDota)) return value.substring(npcDota.length());
        return value;
    }

}

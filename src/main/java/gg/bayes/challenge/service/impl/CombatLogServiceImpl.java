package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.persistence.repository.CombatLogEntryRepository;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.CombatLogService;
import gg.bayes.challenge.service.MatchService;
import gg.bayes.challenge.service.processor.LogProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CombatLogServiceImpl implements CombatLogService {

    private final MatchService matchService;
    private final LogProcessor logProcessor;
    private final CombatLogEntryRepository combatLogEntryRepository;

    @Override
    @Transactional
    public Long ingestCombatLog(String combatLog) {
        MatchEntity match = matchService.save();
        List<CombatLogEntryEntity> logEntryEntities = logProcessor.process(match, combatLog);
        combatLogEntryRepository.saveAll(logEntryEntities);

        return match.getId();
    }

    @Override
    public List<HeroKills> getHeroKills(Long matchId) {
        return combatLogEntryRepository.getHeroKills(CombatLogEntryEntity.Type.HERO_KILLED, matchId);
    }

    @Override
    public List<HeroItem> getHeroItems(Long matchId, String heroName) {
        return combatLogEntryRepository.getHeroItems(CombatLogEntryEntity.Type.ITEM_PURCHASED, matchId, heroName);
    }

    @Override
    public List<HeroSpells> getHeroSpells(Long matchId, String heroName) {
        return combatLogEntryRepository.getHeroSpells(CombatLogEntryEntity.Type.SPELL_CAST, matchId, heroName);
    }

    @Override
    public List<HeroDamage> getHeroDamages(Long matchId, String heroName) {
        return combatLogEntryRepository.getHeroDamages(CombatLogEntryEntity.Type.DAMAGE_DONE, matchId, heroName);
    }
}

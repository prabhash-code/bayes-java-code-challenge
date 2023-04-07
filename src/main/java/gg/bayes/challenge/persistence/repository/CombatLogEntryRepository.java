package gg.bayes.challenge.persistence.repository;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombatLogEntryRepository extends JpaRepository<CombatLogEntryEntity, Long> {
    @Query("SELECT new gg.bayes.challenge.rest.model.HeroKills(clee.actor, cast(count(clee.target) as int)) " +
            "FROM CombatLogEntryEntity clee " +
            "WHERE clee.match.id = :id AND clee.type = :type " +
            "GROUP BY clee.actor")
    List<HeroKills> getHeroKills(@Param("type") CombatLogEntryEntity.Type type, @Param("id") Long matchId);

    @Query("SELECT new gg.bayes.challenge.rest.model.HeroItem(clee.item, clee.timestamp) " +
            "FROM CombatLogEntryEntity clee " +
            "WHERE clee.match.id = :id AND clee.type = :type AND clee.actor = :actor")
    List<HeroItem> getHeroItems(@Param("type") CombatLogEntryEntity.Type type, @Param("id") Long matchId, @Param("actor")String heroName);

    @Query("SELECT new gg.bayes.challenge.rest.model.HeroSpells(clee.ability, cast(count(clee.ability) as int)) " +
            "FROM CombatLogEntryEntity clee " +
            "WHERE clee.match.id = :id AND clee.type = :type AND clee.actor = :actor " +
            "GROUP BY clee.ability")
    List<HeroSpells> getHeroSpells(@Param("type") CombatLogEntryEntity.Type type, @Param("id") Long matchId, @Param("actor")String heroName);

    @Query("SELECT new gg.bayes.challenge.rest.model.HeroDamage(clee.target, cast(count(clee.ability) as int), cast(sum(clee.damage) as int)) " +
            "FROM CombatLogEntryEntity clee " +
            "WHERE clee.match.id = :id AND clee.type = :type AND clee.actor = :actor " +
            "GROUP BY clee.target")
    List<HeroDamage> getHeroDamages(@Param("type") CombatLogEntryEntity.Type type, @Param("id") Long matchId, @Param("actor")String heroName);
}

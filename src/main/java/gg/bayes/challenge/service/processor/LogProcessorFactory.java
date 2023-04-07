package gg.bayes.challenge.service.processor;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.service.processor.impl.DamageDoneProcessor;
import gg.bayes.challenge.service.processor.impl.HeroKilledProcessor;
import gg.bayes.challenge.service.processor.impl.ItemPurchasedProcessor;
import gg.bayes.challenge.service.processor.impl.SpellCastProcessor;

public class LogProcessorFactory {

    public static LogLineProcessor getLogLineProcessor(CombatLogEntryEntity.Type type) {
        switch (type){
            case ITEM_PURCHASED:
                return ItemPurchasedProcessor.getInstance();
            case SPELL_CAST:
                return SpellCastProcessor.getInstance();
            case DAMAGE_DONE:
                return DamageDoneProcessor.getInstance();
            case HERO_KILLED:
                return HeroKilledProcessor.getInstance();
            default:
                return null;
        }
    }
}

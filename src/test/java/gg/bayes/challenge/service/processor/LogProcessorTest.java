package gg.bayes.challenge.service.processor;

import gg.bayes.challenge.mapper.EventMapper;
import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class LogProcessorTest {

    @Mock
    private MatchEntity matchEntity;

    @Autowired
    private LogProcessor logProcessor;

    @Test
    public void testProcess() {
        String logContent = getLogContent();

        List<CombatLogEntryEntity> entries = logProcessor.process(matchEntity, logContent);
        assertNotNull(entries);
        assertEquals(16, entries.size());

        List<CombatLogEntryEntity> itemPurchaseList = getSubList(entries, CombatLogEntryEntity.Type.ITEM_PURCHASED);
        assertNotNull(itemPurchaseList);
        assertEquals(4, itemPurchaseList.size());

        List<CombatLogEntryEntity> heroKilledList = getSubList(entries, CombatLogEntryEntity.Type.HERO_KILLED);
        assertNotNull(heroKilledList);
        assertEquals(3, heroKilledList.size());

        List<CombatLogEntryEntity> damageDoneList = getSubList(entries, CombatLogEntryEntity.Type.DAMAGE_DONE);
        assertNotNull(damageDoneList);
        assertEquals(5, damageDoneList.size());

        List<CombatLogEntryEntity> spellCastList = getSubList(entries, CombatLogEntryEntity.Type.SPELL_CAST);
        assertNotNull(spellCastList);
        assertEquals(4, spellCastList.size());



    }

    private List<CombatLogEntryEntity> getSubList(List<CombatLogEntryEntity> entries, CombatLogEntryEntity.Type type) {
        return entries.stream()
                .filter(entry -> entry.getType() == type)
                .collect(Collectors.toList());
    }

    private String getLogContent(){
        return "[00:26:40.664] npc_dota_hero_bloodseeker buys item item_hyperstone\n" +
                "[00:26:46.462] npc_dota_hero_bane buys item item_ward_dispenser\n" +
                "[00:26:46.462] npc_dota_hero_bane buys item item_ward_observer\n" +
                "[00:26:47.029] npc_dota_hero_dragon_knight buys item item_recipe_mjollnir\n" +
                "[00:27:00.126] npc_dota_hero_abyssal_underlord is killed by npc_dota_hero_bloodseeker\n" +
                "[00:27:03.658] npc_dota_hero_snapfire is killed by npc_dota_hero_bloodseeker\n" +
                "[00:27:05.458] npc_dota_hero_pangolier is killed by npc_dota_hero_rubick\n" +
                "[00:26:35.931] npc_dota_hero_puck casts ability puck_dream_coil (lvl 1) on dota_unknown\n" +
                "[00:26:38.431] npc_dota_hero_dragon_knight casts ability dragon_knight_breathe_fire (lvl 4) on dota_unknown\n" +
                "[00:26:42.263] npc_dota_hero_bloodseeker casts ability bloodseeker_bloodrage (lvl 2) on npc_dota_hero_bloodseeker\n" +
                "[00:26:49.961] npc_dota_hero_dragon_knight casts ability dragon_knight_breathe_fire (lvl 4) on npc_dota_creep_badguys_melee\n" +
                "[00:26:55.360] npc_dota_hero_bloodseeker hits npc_dota_hero_abyssal_underlord with bloodseeker_rupture for 17 damage (922->905)\n" +
                "[00:26:58.226] npc_dota_hero_rubick hits npc_dota_hero_abyssal_underlord with rubick_fade_bolt for 109 damage (766->657)\n" +
                "[00:26:59.126] npc_dota_hero_bloodseeker hits npc_dota_hero_abyssal_underlord with bloodseeker_rupture for 58 damage (519->461)\n" +
                "[00:26:59.226] npc_dota_hero_bloodseeker hits npc_dota_hero_abyssal_underlord with dota_unknown for 63 damage (463->400)\n" +
                "[00:27:00.759] npc_dota_hero_pangolier hits npc_dota_hero_mars with dota_unknown for 21 damage (1778->1757)\n" +
                "[00:27:01.459] npc_dota_hero_pangolier uses item_veil_of_discord\n" +
                "[00:26:39.664] npc_dota_hero_bane is killed by npc_dota_creep_goodguys_melee\n" +
                "[00:27:04.058] npc_dota_hero_pangolier's item_magic_wand heals npc_dota_hero_pangolier for 300 health (254->554)\n" +
                "[00:27:58.778] npc_dota_goodguys_tower2_mid is killed by npc_dota_hero_bloodseeker\n" +
                "[00:00:04.999] game state is now 2";
    }

}

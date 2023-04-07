package gg.bayes.challenge.util;

import java.util.regex.Pattern;

public class Constants {

    public static final Pattern ITEM_PURCHASED_PATTERN = Pattern.compile("^\\[(.*)\\] npc_dota_hero_(.*) buys item item_(.*)$");
    public static final Pattern SPELL_CAST_PATTERN = Pattern.compile("^\\[(.*)\\] npc_dota_hero_(.*) casts ability (.*) \\(lvl (\\d+)\\) on (.*)$");
    public static final Pattern DAMAGE_DONE_PATTERN = Pattern.compile("^\\[(.*)\\] npc_dota_hero_(.*) hits npc_dota_hero_(.*) with (.*) for (\\d+) damage.*$");
    public static final Pattern HERO_KILLED_PATTERN = Pattern.compile("^\\[(.*)\\] npc_dota_hero_(.*) is killed by npc_dota_hero_(.*)");

}

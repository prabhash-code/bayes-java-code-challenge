package gg.bayes.challenge.service;

import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;

import java.util.List;

public interface MatchService {

    MatchEntity save();
}

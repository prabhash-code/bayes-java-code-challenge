package gg.bayes.challenge.service.processor;

import gg.bayes.challenge.service.processor.model.Event;

import java.util.regex.Matcher;

public interface LogLineProcessor {
    Event processLine(Matcher matcher);
}

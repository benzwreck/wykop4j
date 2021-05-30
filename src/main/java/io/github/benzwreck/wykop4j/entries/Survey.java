package io.github.benzwreck.wykop4j.entries;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a survey added to an entry.
 */
public class Survey {
    private final String question;
    private final List<Answer> answers;
    private final Integer userAnswer;


    public Survey(String question, List<Answer> answers, Integer userAnswer) {
        this.question = question;
        this.answers = answers;
        this.userAnswer = userAnswer;
    }

    /**
     * Gets asked question.
     */
    public String question() {
        return question;
    }

    /**
     * Gets list of available answers.
     */
    public List<Answer> answers() {
        return Collections.unmodifiableList(answers);
    }

    /**
     * Gets possible user's answer id. If it is your own survey, that option won't be enabled.
     */
    public Optional<Integer> userAnswer() {
        return Optional.ofNullable(userAnswer);
    }

    @Override
    public String toString() {
        return "Survey{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", userAnswer=" + userAnswer +
                '}';
    }
}

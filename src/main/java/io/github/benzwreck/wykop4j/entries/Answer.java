package io.github.benzwreck.wykop4j.entries;

/**
 * This class represents {@link Survey}'s answer.
 */
public class Answer {
    private final Integer id;
    private final String answer;
    private final Integer count;
    private final Float percentage;

    public Answer(Integer id, String answer, Integer count, Float percentage) {
        this.id = id;
        this.answer = answer;
        this.count = count;
        this.percentage = percentage;
    }

    /**
     * Gets the id of the answer.
     */
    public Integer id() {
        return id;
    }

    /**
     * Gets the answer's body.
     */
    public String answer() {
        return answer;
    }

    /**
     * Gets the count of that answer.
     */
    public Integer count() {
        return count;
    }

    /**
     * Gets the answer's percentage. (This answer/all answers)
     */
    public Float percentage() {
        return percentage;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", count=" + count +
                ", percentage=" + percentage +
                '}';
    }
}

package io.github.benzwreck.wykop4j.entries;

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

    public Integer id() {
        return id;
    }

    public String answer() {
        return answer;
    }

    public Integer count() {
        return count;
    }

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

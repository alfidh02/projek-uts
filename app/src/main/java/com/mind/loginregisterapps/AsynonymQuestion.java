package com.mind.loginregisterapps;

public class AsynonymQuestion {
    private int id; //deklarasi
    private String group_id;
    private String question;
    private String answer;
    private String synonym_1;
    private String synonym_2;
    private String description;

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSynonym_1() {
        return synonym_1;
    }

    public void setSynonym_1(String synonym_1) {
        this.synonym_1 = synonym_1;
    }

    public String getsynonym_2() {
        return synonym_2;
    }

    public void setsynonym_2(String synonym_2) {
        this.synonym_2 = synonym_2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

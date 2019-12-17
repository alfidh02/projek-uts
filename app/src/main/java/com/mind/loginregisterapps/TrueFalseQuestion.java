package com.mind.loginregisterapps;

public class TrueFalseQuestion {
    private int id;
    private String group_id;
    private String correct_statement;
    private String incorrect_statement;
    private String remark;
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

    public String getcorrect_statement() {
        return correct_statement;
    }

    public void setcorrect_statement(String correct_statement) {
        this.correct_statement = correct_statement;
    }

    public String getincorrect_statement() {
        return incorrect_statement;
    }

    public void setincorrect_statement(String incorrect_statement) {
        this.incorrect_statement = incorrect_statement;
    }

    public String get_remark() {
        return remark;
    }

    public void set_remark(String remark) {
        this.remark = remark;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

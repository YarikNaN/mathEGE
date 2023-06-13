package com.dragikgames.mathege;

public class User {

    int userId, taskId, userAnswer;

    public User(int userId, int taskId, int userAnswer) {
        this.userId = userId;
        this.taskId = taskId;
        this.userAnswer = userAnswer;
    }


    public int getuserId() {
        return userId;
    }

    public void setuserId(int id) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }
}










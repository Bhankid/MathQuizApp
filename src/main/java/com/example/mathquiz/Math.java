package com.example.mathquiz;

public class Math {
    private final String problem;
    private final int answer;

    public Math(String problem, int answer) {
        this.problem = problem;
        this.answer = answer;
    }


    public String getProblem() {
        return problem;
    }

    public int getAnswer() {
        return answer;
    }
}

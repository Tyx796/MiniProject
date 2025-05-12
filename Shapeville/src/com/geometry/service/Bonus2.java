package com.geometry.service;

import com.geometry.entity.SectorArea;
import javax.swing.Timer;
import java.util.*;

/**
 * Bonus2任务的业务逻辑类 - 扇形面积计算
 */
public class Bonus2 {
    private List<Integer> remainingQuestions;  // 剩余未完成的题目
    private SectorArea currentQuestion;        // 当前题目
    private int attempts;                      // 当前题目的尝试次数
    private Timer timer;                       // 计时器
    private int timeRemaining;                 // 剩余时间（秒）
    private static final int TIME_LIMIT = 300; // 时间限制（5分钟）
    private static final int MAX_ATTEMPTS = 3; // 最大尝试次数

    /**
     * 构造函数
     */
    public Bonus2() {
        initializeQuestions();
    }

    /**
     * 初始化题目列表
     */
    private void initializeQuestions() {
        remainingQuestions = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {  // Bonus2有8道题
            remainingQuestions.add(i);
        }
        Collections.shuffle(remainingQuestions); // 随机打乱题目顺序
    }

    /**
     * 设置当前题目
     * @param questionNumber 题目编号（1-8）
     */
    public void setCurrentQuestion(int questionNumber) {
        if (questionNumber < 1 || questionNumber > 8) {
            throw new IllegalArgumentException("题目编号必须在1-8之间");
        }
        currentQuestion = new SectorArea(questionNumber);
        attempts = 0;
        startTimer();
    }

    /**
     * 开始新的题目
     * @return 如果还有未完成的题目返回true，否则返回false
     */
    public boolean startNewQuestion() {
        if (remainingQuestions.isEmpty()) {
            return false;
        }

        int questionNumber = remainingQuestions.get(0);
        currentQuestion = new SectorArea(questionNumber);
        attempts = 0;
        startTimer();
        return true;
    }

    /**
     * 检查用户答案
     * @param userAnswer 用户输入的答案
     * @param unit 用户输入的单位
     * @return 返回检查结果（正确/错误/已达到最大尝试次数）
     */
    public Result checkAnswer(double userAnswer, String unit) {
        attempts++;
        
        // 检查单位是否正确
        if (!unit.equals(currentQuestion.getUnit())) {
            if (attempts >= MAX_ATTEMPTS) {
                remainingQuestions.remove(0);
                stopTimer();
                return Result.MAX_ATTEMPTS_REACHED;
            }
            return Result.INCORRECT;
        }
        
        if (currentQuestion.checkAnswer(userAnswer)) {
            // 根据剩余尝试次数计算得分
            int attemptsUsed = MAX_ATTEMPTS - (MAX_ATTEMPTS - attempts) + 1;
            com.geometry.entity.User.addScores("Advanced", attemptsUsed); // Bonus任务使用Advanced级别的分数
            
            remainingQuestions.remove(0);
            stopTimer();
            return Result.CORRECT;
        }
        
        if (attempts >= MAX_ATTEMPTS) {
            remainingQuestions.remove(0);
            stopTimer();
            return Result.MAX_ATTEMPTS_REACHED;
        }
        
        return Result.INCORRECT;
    }

    /**
     * 开始计时器
     */
    private void startTimer() {
        timeRemaining = TIME_LIMIT;
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, e -> {
            timeRemaining--;
            if (timeRemaining <= 0) {
                stopTimer();
                remainingQuestions.remove(0);
            }
        });
        timer.start();
    }

    /**
     * 停止计时器
     */
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    /**
     * 获取剩余时间的格式化字符串
     */
    public String getTimeRemainingFormatted() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * 获取当前题目
     */
    public SectorArea getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * 获取剩余时间（秒）
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * 获取当前尝试次数
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * 获取剩余题目数量
     */
    public int getRemainingQuestionsCount() {
        return remainingQuestions.size();
    }

    /**
     * 获取当前题目的正确答案（带单位）
     */
    public String getCurrentQuestionFormattedArea() {
        return currentQuestion != null ? currentQuestion.getFormattedArea() : "";
    }

    /**
     * 获取当前题目的单位
     */
    public String getCurrentQuestionUnit() {
        return currentQuestion != null ? currentQuestion.getUnit() : "";
    }

    /**
     * 答题结果枚举
     */
    public enum Result {
        CORRECT,
        INCORRECT,
        MAX_ATTEMPTS_REACHED
    }
}

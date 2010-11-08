/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package guitarjava.game;

import guitarjava.graphics.DrawData;
import java.awt.Color;

/**
 *
 * @author brunojadami
 */
public class Score extends GameObject
{
    private int score;
    private int streak;

    /**
     * Constructor.
     */
    public Score()
    {
        super();
        DrawData data = new DrawData(0);
        data.createAs2DText("SCORE: 0");
        data.setColor(Color.WHITE);
        data.setPosition(10, 540, 2);
        drawDatas.add(data);
        data = new DrawData(0);
        data.createAs2DText("STREAK: 0");
        data.setColor(Color.WHITE);
        data.setPosition(10, 510, 2);
        drawDatas.add(data);
    }

    /**
     * Logic.
     * @param deltaTime
     */
    @Override
    public void think(float deltaTime)
    {
        drawDatas.getFirst().createAs2DText("SCORE: " + score);
        drawDatas.get(1).createAs2DText("STREAK: " + streak);
    }

    /**
     * Adds to score.
     */
    public void addToScore(int points)
    {
        score += points * streak;
    }

    /**
     * Gets the score.
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Inc streak.
     */
    public void incStreak()
    {
        streak++;
    }

    /**
     * Resets streak.
     */
    public void resetStreak()
    {
        streak = 0;
    }

}

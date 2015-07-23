/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drumsmith.game;

import drumsmith.graphics.DrawData;
import java.awt.Color;

/**
 *
 * @author brunojadami
 */
public class Score extends GameObject {

    private static String Cr = "Cr";
    private static String Ch = "Ch";
    private static String Cx = "Cx";
    private static String Su = "Su";
    private static String Bu = "Bu";
    private int score;
    private int streak;
    private String name;

    /**
     * Constructor.
     */
    public Score(Music music) {
        super();
        this.name = music.getName();

        DrawData data = new DrawData(0);
        data.createAs2DText(name);
        data.setColor(Color.WHITE);
        data.setPosition(10, 540, 20);
        drawDatas.add(data);

        data = new DrawData(0);
        data.createAs2DText("SCORE: 0");
        data.setColor(Color.WHITE);
        data.setPosition(10, 510, 20);
        drawDatas.add(data);

        data = new DrawData(0);
        data.createAs2DText("STREAK: 0");
        data.setColor(Color.WHITE);
        data.setPosition(10, 480, 20);
        drawDatas.add(data);

        data = new DrawData(0);
        data.createAs2DText(Cr);
        data.setColor(Color.GREEN);
        data.setPosition(140, 65, 20);
        drawDatas.add(data);

        data = new DrawData(0);
        data.createAs2DText(Ch);
        data.setColor(Color.RED);
        data.setPosition(260, 65, 20);
        drawDatas.add(data);

        data = new DrawData(0);
        data.createAs2DText(Cx);
        data.setColor(Color.YELLOW);
        data.setPosition(385, 65, 20);
        drawDatas.add(data);

        data = new DrawData(0);
        data.createAs2DText(Su);
        data.setColor(Color.BLUE);
        data.setPosition(510, 65, 20);
        drawDatas.add(data);

        data = new DrawData(0);
        data.createAs2DText(Bu);
        data.setColor(Color.ORANGE);
        data.setPosition(630, 65, 20);
        drawDatas.add(data);
    }

    /**
     * Logic.
     *
     * @param deltaTime
     */
    @Override
    public void think(float deltaTime) {
        drawDatas.get(1).createAs2DText("SCORE: " + score);
        drawDatas.get(2).createAs2DText("STREAK: " + streak);
    }

    /**
     * Adds to score.
     */
    public void addToScore(int points) {
        score += points * streak;
    }

    /**
     * Gets the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Inc streak.
     */
    public void incStreak() {
        streak++;
    }

    /**
     * Resets streak.
     */
    public void resetStreak() {
        streak = 0;
    }
}

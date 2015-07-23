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
public class Neck extends GameObject {

    /**
     * Constructor.
     */
    public Neck() {
        super();
        createGraphic();
    }

    /**
     * Create the neck graphics.
     */
    private void createGraphic() {
        DrawData neck = new DrawData(Constant.CACHEID_NECK);
        neck.createAs2DFilledRect(400, 2200);
        neck.setColor(new Color(80, 80, 100));
        
        neck.setPosition(Constant.WINDOW_WIDTH / 2, -500, -1);
        drawDatas.add(neck);
    }

    /*
     * Logic.
     */
    @Override
    public void think(float deltaTime) {
    }
}

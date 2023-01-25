package game.demos;

import engine.graphics.Texture;
import engine.physics.Particle;
import engine.state.GameState;
import engine.system.Renderer;
import engine.utility.Util;

import java.awt.event.KeyEvent;

public class Pseudo3D extends GameState {
    private double speed;
    private double farClip;
    private Particle player;
    private Texture tileTexFloor;
    private Texture tileTexCeil;
    private void renderFloorAndCeiling(Renderer renderer)
    {
        int texPower = 10;
        for (int y = 0; y < Engine.getHeight(); y++)
        {
            double ceil = (y - Engine.getHeight() / 2.0) / Engine.getHeight();
            if (ceil > -farClip && ceil < farClip) continue;
            boolean floor = ceil >= 0;
            if (!floor)
                ceil = -ceil;
            double z = (1 << (texPower -1))/ceil;
            for (int x = 0; x < Engine.getWidth(); x++)
            {
                double depth = (x - Engine.getWidth() / 2.0) / Engine.getHeight();
                depth *= z;
                int xPos = (int)Math.floor(depth + player.x) * 2 & ((1 << texPower) - 1);
                int yPos = (int)Math.floor(z + player.y) * 2 & ((1 << texPower) - 1);
                double fog = Math.abs(ceil)/0.5;
                renderer.setPixel(x, y, Util.colorIntensity((floor?tileTexFloor:tileTexCeil).getPixelNormalized(xPos/(float)((1 << texPower) - 1), yPos/(float)((1 << texPower) - 1)), fog<=0.15?-(int)(110-110*(fog/0.15f)):0), true);
            }
        }
    }
    @Override
    public void init() {
        speed = 25f;
        farClip = 0.01;
        player = new Particle(0,0);
        tileTexFloor = new Texture("/Textures/Bricks/CLAYBRICKS.png");
        tileTexCeil = new Texture("/Textures/Elements/BIGLEAVES.png");
    }

    @Override
    public void update() {
        if (Engine.isKey(KeyEvent.VK_W))
            player.y += speed;
        if (Engine.isKey(KeyEvent.VK_S))
            player.y -= speed;
        if (Engine.isKey(KeyEvent.VK_A))
            player.x -= speed;
        if (Engine.isKey(KeyEvent.VK_D))
            player.x += speed;
    }

    @Override
    public void render(Renderer renderer) {
        renderFloorAndCeiling(renderer);
    }

    @Override
    public void dispose() {

    }
}

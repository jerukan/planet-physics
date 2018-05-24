package io.github.jerukan.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import io.github.jerukan.Main;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Planet Physics";
        config.width = 1200;
        config.height = 1100;
        new LwjglApplication(new Main(), config);
    }
}

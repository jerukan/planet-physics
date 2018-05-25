package io.github.jerukan.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.jerukan.planetdata.PlanetState;
import io.github.jerukan.util.Assets;

public class UIManager {

    private PlanetState planetState;
    private ScreenViewport viewport = new ScreenViewport();
    public Stage stage;

    private Label infoLabel= new Label("None", Assets.uiSkin, "default");
    private Label disclaimer = new Label("Disclaimer: This simulation does not have proper physics.\nI'm too lazy to actually do it.", Assets.uiSkin, "default");
    //    private val font: BitmapFont = BitmapFont()

    public UIManager(SpriteBatch batch, PlanetState planetState) {
        this.planetState = planetState;
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.setWidth(100f);
        table.add(infoLabel).pad(20f).row();
        table.add(disclaimer);
        table.setPosition(20f, Gdx.graphics.getHeight()- 40f);
        table.align(Align.topLeft);

        stage.addActor(table);
    }

    public void render() {
        infoLabel.setText("Use WASD to move around\nScroll to zoom\nPress F to add a planet at the mouse's position\nCurrent warp speed: " + planetState.warp + "\nPress <COMMA> or <PERIOD> to slow down warp \nor speed up warp respectively.");
        stage.draw();
    }
}
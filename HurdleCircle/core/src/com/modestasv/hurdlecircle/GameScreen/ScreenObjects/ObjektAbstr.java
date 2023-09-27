package com.modestasv.hurdlecircle.GameScreen.ScreenObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.modestasv.hurdlecircle.Assets;

/**
 * eine abstrakte Klasse die von Actor erbt um sie im LibGDX Stage 2d nutzen zu können
 * (Framework für Spielobjekte mit z.b. Möglichten zur Animation mithilfe von Actions siehe MainMenuScreen)
 *
 * Generalisierung wird genutzt um redundanten Code zu vermeiden
 *
 */
public abstract class ObjektAbstr extends Actor {

    private Vector2 velocity;
    private Sprite objekt;
    private float groesse;
    private Circle boundingCircle;

    public ObjektAbstr ( Vector2 vel, Vector2 pos, String path, float groesse) {
        this.velocity = vel;
        setX(pos.x);
        setY(pos.y);
        this.groesse = groesse;

        boundingCircle = new Circle();

        objekt = new Sprite(Assets.get().getTexture(path));
        objekt.setSize(groesse,groesse);
        objekt.setOrigin(objekt.getWidth() / 2, objekt.getHeight() / 2);
        setOrigin(objekt.getWidth() / 2, objekt.getHeight() / 2);
        setBounds(getPos().x, getPos().y, getObj().getWidth(), getObj().getHeight());

        getObj().setPosition(
                getPos().x - getObj().getWidth() / 2,
                getPos().y - getObj().getHeight() / 2
        );
    }

    public void refresh(Vector2 vel, Vector2 pos, String path, float groesse) {
        this.velocity = vel;
        setX(pos.x);
        setY(pos.y);
        this.groesse = groesse;

        boundingCircle = new Circle();

        objekt = new Sprite(Assets.get().getTexture(path));
        objekt.setSize(groesse,groesse);
        objekt.setOrigin(objekt.getWidth() / 2, objekt.getHeight() / 2);
        setOrigin(objekt.getWidth() / 2, objekt.getHeight() / 2);
        setBounds(getPos().x, getPos().y, getObj().getWidth(), getObj().getHeight());

        getObj().setPosition(
                getPos().x - getObj().getWidth() / 2,
                getPos().y - getObj().getHeight() / 2
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float alpha){
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        batch.draw(getObj(), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    }

    public void draw(SpriteBatch batch) {
        getObj().draw(batch);
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public Sprite getObj() {
        return objekt;
    }
    public Vector2 getPos() {
        return new Vector2(getX(), getY());
    }
    public Vector2 getVel() {
        return velocity;
    }
    public void setPos(Vector2 position) {
        setX(position.x);
        setY(position.y);
    }
    public Vector2 getDir() {
        return new Vector2(getX(),getY()).cpy().add(velocity);
    }
    public float getGroe() {
        return groesse;
    }
    public void setGroe(float groesse) {
        this.groesse = groesse;
    }



}

package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.texturedModel;
import toolBox.maths;

public class entity {
private texturedModel model;
protected Vector3f position;
private float rotX,rotY,rotZ;
private float scale;
private float scaleX,scaleY,scaleZ;
protected entity parent;  // Ajoutez un parent

public entity(texturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,float scaleX,float scaleY,float scaleZ) {
	super();
	this.model = model;
	this.position = position;
	this.rotX = rotX;
	this.rotY = rotY;
	this.rotZ = rotZ;
	this.scale = scale;
	this.scaleX = scaleX;
	this.scaleY = scaleY;
	this.scaleZ = scaleZ;
	this.parent = null;  // Par défaut, pas de parent
}

public entity getParent() {
    return parent;
}

public void setParent(entity parent,int offset) {
    this.parent = parent;
    this.position = new Vector3f((float) ((float) (parent.getPosition().x)+Math.cos(Math.toRadians(-parent.rotY))*offset),parent.getPosition().y+5,(float) ((float) (parent.getPosition().z)+Math.sin(Math.toRadians(-parent.rotY))*offset));
    this.rotY=parent.rotY;
}

public Matrix4f getGlobalTransformation() {
    // Créer la transformation locale
    Matrix4f localTransformation = maths.createTransformationMatrix(position, rotX, rotY, rotZ, scale);
    
    if (parent != null) {
        // Combiner la transformation locale avec celle du parent
        Matrix4f parentTransform = parent.getGlobalTransformation();
        Matrix4f result = new Matrix4f();
        Matrix4f.mul(parentTransform, localTransformation, result);
        return result; // Transformation globale avec le parent
    }

    return localTransformation; // Transformation locale si pas de parent
}

// Getter pour le modèle, position, etc.
// (Les autres getters et setters sont inchangés)


public void increasePosition(float dx,float dy,float dz)
{
	this.position.x+=dx;
	this.position.y+=dy;
	this.position.z+=dz;
	
	
}
public void increaseRotation(float dx,float dy,float dz)
{
	this.rotX+=dx;
	this.rotY+=dy;
	this.rotZ+=dz;
	
}
public texturedModel getModel() {
	return model;
}

public void setModel(texturedModel model) {
	this.model = model;
}

public Vector3f getPosition() {
	return position;
}

public void setPosition(Vector3f position) {
	this.position = position;
}

public float getRotX() {
	return rotX;
}

public void setRotX(float rotX) {
	this.rotX = rotX;
}

public float getRotY() {
	return rotY;
}

public void setRotY(float rotY) {
	this.rotY = rotY;
}

public float getRotZ() {
	return rotZ;
}


public void setRotZ(float rotZ) {
	this.rotZ = rotZ;
}

public void setIncrementeRotZ(float rotZ) {
	this.rotZ += rotZ;
}

public void setIncrementeRotY(float rotY) {
	this.rotY += rotY;
}

public float getScale() {
	return scale;
}


public void setScale(float scale) {
	this.scale = scale;
}
public  float getScaleX()
{
	return scaleX;
}
public float getScaleY()
{
	return scaleY;
}
public float getScaleZ()
{
	return scaleZ;
}

}

package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class camera {

private float dist=190;
private float turnAround=60;
private Vector3f position=new Vector3f(0,5,0);
private float pitch=15;
private float yaw;
private float roll;
private float zoomLevel;
private entity p;

public camera(entity player_)
{
	this.p=player_;
}
public void move()
{
	
	calculateZoom();
	calculatePitch();
	calculateAngle();
	limit();
	
	float horizontal=calculateHorizontalDist();
	float vertical=calculateVerticalDist();
	calculateCamPosition(horizontal,vertical);
	this.yaw=180 - (p.getRotY()+turnAround);
	/*
	if(Keyboard.isKeyDown(Keyboard.KEY_W))
	{
		position.z-=1f;
		
	}
	if(Keyboard.isKeyDown(Keyboard.KEY_Z))
	{
		position.z+=1f;
		
	}
	if(Keyboard.isKeyDown(Keyboard.KEY_D))
	{
		position.x+=1f;
		
	}
	if(Keyboard.isKeyDown(Keyboard.KEY_A))
	{
		position.x-=1f;
		
	}
	if(Keyboard.isKeyDown(Keyboard.KEY_R))
	{
		position.y+=1f;
		
	}
	if(Keyboard.isKeyDown(Keyboard.KEY_F))
	{
		position.y-=1f;
		
	}
	*/
}
private void limit()
{
	if(this.yaw>360.0)
	{
		this.yaw-=360.0;
	}
	if(this.yaw<0)
	{
		this.yaw+=360.0;
	}
	if(this.pitch>90)
	{
		this.pitch=90;
	}
	if(this.pitch<0)
	{
		this.pitch=0;
	}
	if(this.dist>800)
	{
		this.dist=800;
	}
	if(this.dist<10)
	{
		this.dist=10;
	}
}
private void calculateCamPosition(float horiz,float vert)
{
float theta=p.getRotY()+turnAround;
float offsetX=(float) (horiz*Math.sin(Math.toRadians(theta)));
float offsetZ=(float) (horiz*Math.cos(Math.toRadians(theta)));

position.x=p.getPosition().x-offsetX;
position.z=p.getPosition().z-offsetZ;
position.y=p.getPosition().y+vert;	
}
public Vector3f getPosition() {
	return position;
}

public float getPitch() {
	return pitch;
}

public float getYaw() {
	return yaw;
}

public float getRoll() {
	return roll;
}
private void calculateZoom()
{
	zoomLevel=Mouse.getDWheel()*0.1f;
	dist-=zoomLevel;
}

private float calculateHorizontalDist()
{
	return (float) (dist*Math.cos(Math.toRadians(pitch)));
}

private float calculateVerticalDist()
{
	return (float) (dist*Math.sin(Math.toRadians(pitch)));
}

private void calculatePitch()
{
	if(Mouse.isButtonDown(1))
	{
		float pitchChange=Mouse.getDY()*0.1f;
		pitch-=pitchChange;
	}
}

private void calculateAngle()
{
	if(Mouse.isButtonDown(0))
	{
		float angleChange=Mouse.getDX()*0.3f;
		turnAround-=angleChange;
	}
}
public void invertPitch()
{
	this.pitch=-pitch;
}
}

package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.texturedModel;
import renderEngine.DisplayManager;
import terrains.terrain;

public class player extends entity {
	
	private  static final float turn_speed=80;
	private static final float gravity=-50f;
	//private static final float terrain_height=0;
	private  float currentSpeed=0;
	private  float currentTurnSpeed=0;
	private float upwardspeed=0;
	private float maxSpeed=180.0f;
	private float acc=0.48f;
	private float dec=0.15f;
	private float dec2=0.85f;
	private int up,down,right,left,space=0;
	
	public player(texturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,float scaleX,float scaleY,float scaleZ) {
		super(model, position, rotX, rotY, rotZ, scale,scaleX,scaleY,scaleZ);

		// TODO Auto-generated constructor stub
	}
	public float getCurrentSpeed()
	{
		return currentSpeed;
	}
	public void setSpeed(float s)
	{
		currentSpeed=s;
	}
	
	public void move(terrain t,List<roues> ww,List<roues> rr)
	{
		System.out.print(currentSpeed);
		checkInputs(ww,rr);
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getTimeFrameSeconds(), 0);
		float dist=currentSpeed*DisplayManager.getTimeFrameSeconds();
		float dx= (float) (dist*Math.sin(Math.toRadians(super.getRotY())));
		float dz= (float) (dist*-Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dz, 0, dx);
		upwardspeed+=gravity*DisplayManager.getTimeFrameSeconds();
		super.increasePosition(0, upwardspeed*DisplayManager.getTimeFrameSeconds(), 0);
		float terrainHeights=t.getHeightTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y<terrainHeights+2)
		{
			upwardspeed=0;
			super.getPosition().y=terrainHeights+2;
		}
	}
	
	private void control(List<roues> ww,List<roues> rr)
	{
		ww.get(0).turnRotZ();
		ww.get(1).turnRotZ();
		rr.get(0).turnRotZ();
		rr.get(1).turnRotZ();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			up=1;
			
		
			ww.get(0).turnRotY();
			rr.get(0).turnRotY();
			
		}
		
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)==false)
		{
			up=0;
			
		
			ww.get(0).turnRotY();
			rr.get(0).turnRotY();
			
		}
		
		 if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			down=1;
			
			ww.get(0).turnRotY();
			rr.get(0).turnRotY();
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_W)==false)
		{
			down=0;
		
			ww.get(0).turnRotY();
			rr.get(0).turnRotY();
			
		}
		
		 if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
		
			right=1;
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)==false)
		{
		
			right=0;
			
		}
		 if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			left=1;
		
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)==false)
		{
			left=0;
			
			
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			//jump();
			space=1;
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)==false)
		{
			//jump();
			space=0;
			
		}
		
	}
	
	private void checkInputs(List<roues> ww,List<roues> rr)
	{
		control(ww, rr);
		
		if(up==1 && currentSpeed<maxSpeed)
		{
			if(currentSpeed<0)
			{
				currentSpeed+=dec;
		 }
			else
			{
				currentSpeed+=acc;
			}
		}
		if(down==1 && currentSpeed>-maxSpeed)
		{
			if(currentSpeed>0)
			{
				currentSpeed-=dec;
			}
			else
			{
				currentSpeed-=acc;
			}
			
		}
		
		if(up==0 && down==0)
		{
			if(currentSpeed-dec>0)
			{
				currentSpeed-=dec;
			}
			else if(currentSpeed+dec<0)
			{
				currentSpeed+=dec;
			}
			else
			{
				currentSpeed=0;
			}
		}
		
			if(space==1)
		{
			if(currentSpeed-dec2>0)
			{
				currentSpeed-=dec2;
			}
			else if(currentSpeed+dec2<0)
			{
				currentSpeed+=dec2;
			}
			else
			{
				currentSpeed=0;
			}
		}
			if(right==1)
			{
				currentTurnSpeed=turn_speed*(currentSpeed/maxSpeed);
				//angle=turnSpeed;
			//	+=turnSpeed*(speed/maxSpeed);
			}
		
			else if(left==1)
			{
				currentTurnSpeed=-turn_speed*(currentSpeed/maxSpeed);
				//angle=turnSpeed;
			//	+=turnSpeed*(speed/maxSpeed);
			}
			else if(left==0 || right==0)
			{
				currentTurnSpeed=0;
				//angle=turnSpeed;
			//	+=turnSpeed*(speed/maxSpeed);
			}

	}
	
}

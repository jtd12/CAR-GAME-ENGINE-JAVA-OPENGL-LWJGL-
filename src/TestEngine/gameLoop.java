package TestEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.AABB;
import entities.camera;
import entities.collision;
import entities.entity;
import entities.light;
import entities.player;
import entities.roues;
import models.rawModel;
import models.texturedModel;
import postprocessing.Fbo;
import postprocessing.PostProcessing;
import renderEngine.DisplayManager;
import renderEngine.loader;
import renderEngine.masterRenderer;
import renderEngine.objloader;
import shaders.shaderProgram;
import shaders.staticShader;
import terrains.terrain;
import textures.modelTexture;
import textures.terrainTexture;
import textures.terrainTexturePack;
import water.WaterShader;
import water.WaterTile;
import water.waterFrameBufferObject;
import water.waterRenderer;




public class gameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Display.setResizable(true);
		loader loader = new loader();
		//terrain terrains=new terrain(0,0,loader,new modelTexture(loader.loadTexture("grass")));
		//terrain terrains2=new terrain(1,0,loader,new modelTexture(loader.loadTexture("grass")));
		//renderer renderer = new renderer();
		//staticShader shader=new staticShader();
		//renderer renderer = new renderer(shader);
		rawModel model=objloader.loadObjModel("componentUnity/tree_01", loader);
		rawModel model2=objloader.loadObjModel("componentUnity/tree_02", loader);
		rawModel model3=objloader.loadObjModel("componentUnity/tree_03", loader);
		rawModel model4=objloader.loadObjModel("componentUnity/tree_04", loader);
		rawModel model5=objloader.loadObjModel("componentUnity/tree_05", loader);
		rawModel model6=objloader.loadObjModel("mur", loader);
		//rawModel model6=objloader.loadObjModel("componentUnity/tree_06", loader);
		texturedModel texture=new texturedModel(model,new modelTexture(loader.loadTextureTGA("textures_objectUnity/LowPoly/Sycamore_MiddleEastern_A")));
		texturedModel texture2=new texturedModel(model2,new modelTexture(loader.loadTextureTGA("textures_objectUnity/Beech_American_A")));
		texturedModel texture3=new texturedModel(model3,new modelTexture(loader.loadTextureTGA("textures_objectUnity/Beech_European_A")));
		texturedModel texture4=new texturedModel(model4,new modelTexture(loader.loadTextureTGA("textures_objectUnity/Billboard_Beech_American")));
		texturedModel texture5=new texturedModel(model5,new modelTexture(loader.loadTextureTGA("textures_objectUnity/WoodPainted_A")));
		texturedModel texture6=new texturedModel(model6,new modelTexture(loader.loadTextureTGA("textures_objectUnity/WoodPainted_A")));
		
		texture.getModel_texture().setHasTransparency(true);
		texture2.getModel_texture().setHasTransparency(true);
		texture3.getModel_texture().setHasTransparency(true);
		texture4.getModel_texture().setHasTransparency(true);
		
		texture.getModel_texture().setFakeLight(true);
		texture2.getModel_texture().setFakeLight(true);
		texture3.getModel_texture().setFakeLight(true);
		texture4.getModel_texture().setFakeLight(true);
		
		terrainTexture backGroundTexture=new terrainTexture(loader.loadTextureJPG("grass"));
		
		terrainTexture rTexture=new terrainTexture(loader.loadTextureJPG("dirt"));
		terrainTexture gTexture=new terrainTexture(loader.loadTextureJPG("grass2"));
		terrainTexture bTexture=new terrainTexture(loader.loadTextureJPG("path"));
		
		terrainTexturePack texturePack=new terrainTexturePack(backGroundTexture,rTexture,gTexture,bTexture);
		terrainTexture blendMap=new terrainTexture(loader.loadTexture("blendMap"));
		
		collision collid=new collision();
		Fbo fbo2=new Fbo(Display.getWidth(),Display.getHeight(),Fbo.DEPTH_RENDER_BUFFER);
		PostProcessing.init(loader);
		

		
		/*
		float[] vertices = {
				-0.5f, 0.5f, 0f,//v0
				-0.5f, -0.5f, 0f,//v1
				0.5f, -0.5f, 0f,//v2
				0.5f, 0.5f, 0f,//v3
		};
		
		int[] indices = {
				0,1,3,//top left triangle (v0, v1, v3)
				3,1,2//bottom right triangle (v3, v1, v2)
		};
		
		float[] textureCoords= {
				
				0,0,
				0,1,
				1,1,
				1,0
				
		};
		*/
		//rawModel model = objloader.loadObjModel("model", loader);
		//modelTexture texture=new modelTexture(loader.loadTexture("model"));
		//texturedModel textureModel=new texturedModel(model,new modelTexture(loader.loadTexture("model")));
		//modelTexture texture=textureModel.getModel_texture();
		//texture.setShineDamper(10);
		//texture.setRelectivity(1);
		//entity entities=new entity(textureModel,new Vector3f(0,0,-50),0,0,0,1);
		light light_=new light(new Vector3f(500,2,20),new Vector3f(3.1f,1.5f,1));
		List<entity> entities=new ArrayList<entity>();
		List<entity> murs=new ArrayList<entity>();
		List<player> p=new ArrayList<player>();
		List<roues> roues_=new ArrayList<roues>();
		List<roues> roues2_=new ArrayList<roues>();
		List<roues> roues3_=new ArrayList<roues>();
		List<roues> roues4_=new ArrayList<roues>();
		List<terrain> terrains=new ArrayList<terrain>();
		List<entity> boundingboxCar=new ArrayList<entity>();
		
        
		terrains.add(new terrain(0,0,loader,texturePack,blendMap,"heightmap2"));
		terrains.add(new terrain(-1,0,loader,texturePack,blendMap,"heightmap2"));
		terrains.add(new terrain(0,-1,loader,texturePack,blendMap,"heightmap2"));
		terrains.add(new terrain(-1,-1,loader,texturePack,blendMap,"heightmap2"));
		//terrain terrains2=new terrain(1,0,loader,texturePack,blendMap,"heightmap2");
		//terrain terrains3=new terrain(0,1,loader,texturePack,blendMap,"heightmap2");
		//terrain terrains4=new terrain(1,1,loader,texturePack,blendMap,"heightmap2");
		
		murs.add(new entity(texture6,new Vector3f(100,-75,360),0,0,0,1,250,25,20));
		murs.add(new entity(texture6,new Vector3f(100,-75,560),0,0,0,1,100,25,20));
		murs.add(new entity(texture6,new Vector3f(350,-75,1000),0,0,0,1,20,25,200));
		murs.add(new entity(texture6,new Vector3f(180,-50,1400),0,0,0,1,20,25,200));
		murs.add(new entity(texture6,new Vector3f(350,-75,1800),0,0,0,1,20,25,500));
		murs.add(new entity(texture6,new Vector3f(600,-55,600),0,0,0,1,250,25,20));
		murs.add(new entity(texture6,new Vector3f(800,-45,1800),0,0,0,1,20,25,300));
		murs.add(new entity(texture6,new Vector3f(300,-45,760),0,0,0,1,150,25,20));
		murs.add(new entity(texture6,new Vector3f(420,-55,1600),0,0,0,1,20,25,200));
		murs.add(new entity(texture6,new Vector3f(420,-55,3300),0,0,0,1,20,105,500));
		murs.add(new entity(texture6,new Vector3f(800,-55,4600),0,0,0,1,20,105,800));
		murs.add(new entity(texture6,new Vector3f(10,-55,2100),0,0,0,1,400,155,20));
		murs.add(new entity(texture6,new Vector3f(150,-55,2600),0,0,0,1,400,155,20));
		murs.add(new entity(texture6,new Vector3f(750,-45,2600),0,0,0,1,400,155,20));
		murs.add(new entity(texture6,new Vector3f(-600,-45,2600),0,0,0,1,400,155,20));
		murs.add(new entity(texture6,new Vector3f(-1500,-45,2600),0,0,0,1,700,155,20));
		murs.add(new entity(texture6,new Vector3f(-2200,-45,2600),0,0,0,1,800,155,20));
		murs.add(new entity(texture6,new Vector3f(-1300,-45,2000),0,0,0,1,1200,155,20));
		murs.add(new entity(texture6,new Vector3f(-2500,-45,3800),0,0,0,1,20,300,730));
		murs.add(new entity(texture6,new Vector3f(-3000,-45,7000),0,0,0,1,20,300,1500));
		murs.add(new entity(texture6,new Vector3f(-1600,-45,360),0,0,0,1,1500,300,20));
		murs.add(new entity(texture6,new Vector3f(-1300,-45,560),0,0,0,1,1300,200,20));
		
		Random rand=new Random();
		
		for(int i=0;i<60;i++)
		{
			float x=rand.nextFloat()*800;
			float z=rand.nextFloat()*600;
			float y=terrains.get(0).getHeightTerrain(x,z);
			
			entities.add(new entity(texture,new Vector3f(x,y,z),0,0,0,0.9f,1,1,1));
		
		}
		for(int i=0;i<40;i++)
		{
			float x=rand.nextFloat()*-2200;
			float z=rand.nextFloat()*-2200;
			float y=terrains.get(3).getHeightTerrain(x,z);
			
			entities.add(new entity(texture,new Vector3f(x,y,z),0,0,0,1.8f,1,1,1));
		
		}
		
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*-2000;
			float z=rand.nextFloat()*-2000;
			float y=terrains.get(3).getHeightTerrain(x,z);
			
			entities.add(new entity(texture2,new Vector3f(x,y,z),0,0,0,3.8f,1,1,1));
		
		}
		
		
		for(int i=0;i<40;i++)
		{
			float x=rand.nextFloat()*2200;
			float z=rand.nextFloat()*-2200;
			float y=terrains.get(2).getHeightTerrain(x,z);
			
			entities.add(new entity(texture,new Vector3f(x,y,z),0,0,0,1.8f,1,1,1));
		
		}
		
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*2000;
			float z=rand.nextFloat()*-2000;
			float y=terrains.get(2).getHeightTerrain(x,z);
			
			entities.add(new entity(texture2,new Vector3f(x,y,z),0,0,0,4.8f,1,1,1));
		
		}
		
		for(int i=0;i<40;i++)
		{
			float x=rand.nextFloat()*-2200;
			float z=rand.nextFloat()*2200;
			float y=terrains.get(1).getHeightTerrain(x,z);
			
			entities.add(new entity(texture,new Vector3f(x,y,z),0,0,0,1.8f,1,1,1));
		
		}
		
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*-2000;
			float z=rand.nextFloat()*2000;
			float y=terrains.get(1).getHeightTerrain(x,z);
			
			entities.add(new entity(texture3,new Vector3f(x,y,z),0,0,0,2.8f,1,1,1));
		
		}
		
		
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*2000;
			float z=rand.nextFloat()*2000 ;
			float y=terrains.get(0).getHeightTerrain(x,z);
			
			entities.add(new entity(texture2,new Vector3f(x,y,z),0,0,0,0.9f,1,1,1));
		
		}
		
	
		
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*2000;
			float z=rand.nextFloat()*2000;
			float y=terrains.get(0).getHeightTerrain(x,z);
			
			entities.add(new entity(texture3,new Vector3f(x,y,z),0,0,0,1,1,1,1));
		
		}
		
		for(int i=0;i<25;i++)
		{
			float x=rand.nextFloat()*2000;
			float z=rand.nextFloat()*2000;
			float y=terrains.get(0).getHeightTerrain(x,z);
			
			entities.add(new entity(texture4,new Vector3f(x,y,z),0,0,0,2,1,1,1));
		
		}
		
		for(int i=0;i<10;i++)
		{
			float x=rand.nextFloat()*2000;
			float z=rand.nextFloat()*2000;
			float y=terrains.get(0).getHeightTerrain(x,z);
			
			entities.add(new entity(texture5,new Vector3f(x,y,z),0,0,0,10,1,1,1));
			
		}
	
		for(int i=0;i<20;i++)
		{
			float x=rand.nextFloat()*-2000;
			float z=rand.nextFloat()*-2000;
			float y=terrains.get(3).getHeightTerrain(x,z);
			
			entities.add(new entity(texture5,new Vector3f(x,y,z),0,0,0,20,1,1,1));
			
		}
		
	

		rawModel Model=objloader.loadObjModel("vehicule", loader);
		texturedModel textureModel=new texturedModel(Model,new modelTexture(loader.loadTextureJPG("map_vehicule")));
		
		rawModel ModelRoue=objloader.loadObjModel("roues", loader);
		texturedModel textureModelRoue=new texturedModel(ModelRoue,new modelTexture(loader.loadTextureJPG("map_vehicule")));
		
		rawModel ModelRoue2=objloader.loadObjModel("roues2", loader);
		texturedModel textureModelRoue2=new texturedModel(ModelRoue2,new modelTexture(loader.loadTextureJPG("map_vehicule")));
		
		p.add(new player(p,textureModel,new Vector3f(100,-75,360),0,0,0,1,2,2,2));

		 
		for(int i=0;i<500;i++)
		 roues_.add(new roues(textureModelRoue,new Vector3f(380,25,360),0,0,0,2));
		
	
		// roues_.add(new roues(textureModelRoue,new Vector3f(380,25,360),0,0,0,2));
		for(int i=0;i<500;i++)
		 roues2_.add(new roues(textureModelRoue2,new Vector3f(380,25,360),0,0,0,2));
		
		for(int i=0;i<500;i++)
			 roues3_.add(new roues(textureModelRoue,new Vector3f(380,25,360),0,0,0,2));
		
		for(int i=0;i<500;i++)
			 roues4_.add(new roues(textureModelRoue2,new Vector3f(380,25,360),0,0,0,2));
		//roues2_.add(new roues(textureModelRoue2,new Vector3f(380,25,360),0,0,0,2));
		
		boundingboxCar.add(new player(p,textureModel,new Vector3f(100,-75,360),0,0,0,1,2,2,2));
		boundingboxCar.add(new player(p,textureModel,new Vector3f(100,-75,360),0,0,0,1,2,2,2));
		
		for(int i=1;i<7;i++)
			for(int j=1;j<7;j++)
					p.add(new player(p,textureModel,new Vector3f(0+(i*1),-75,(j*75)+160),0,0,0,1,2,2,2));
			
		camera cam=new camera(p.get(0));
		
		Vector3f playerPos=p.get(0).getPosition();
		waterFrameBufferObject fbo=new waterFrameBufferObject();
		masterRenderer master=new masterRenderer(loader,cam);
		WaterShader waterShader=new WaterShader();
		
		
		waterRenderer waterRender=new waterRenderer(loader,waterShader,master.getProjection(),fbo);
		WaterTile water=new WaterTile(800,800,-90);
		List<WaterTile> waters=new ArrayList<WaterTile>();
		waters.add(water);
		
		while (!Display.isCloseRequested()) {
			// game logic
			//entities.increaseRotation(0,1,0);
			cam.move();
			boundingboxCar.get(0).setParent(p.get(0),15);
			boundingboxCar.get(1).setParent(p.get(0),-30);

			//collid.isCollideDroite(player_.getPosition(),new Vector3f(player_.getScaleX(),player_.getScaleY(),player_.getScaleZ()),entities.get(0).getPosition(), new Vector3f(entities.get(0).getScaleX(),entities.get(0).getScaleY(),entities.get(0).getScaleZ()),player_,1);
			//collid.isCollideGauche(player_.getPosition(),new Vector3f(player_.getScaleX(),player_.getScaleY(),player_.getScaleZ()),entities.get(1).getPosition(), new Vector3f(entities.get(1).getScaleX(),entities.get(1).getScaleY(),entities.get(1).getScaleZ()),player_,-1);
			/*if(collid.isCollideDroite(player_.getPosition(),new Vector3f(player_.getScaleX(),player_.getScaleY(),player_.getScaleZ()),entities.get(0).getPosition(), new Vector3f(entities.get(0).getScaleX(),entities.get(0).getScaleY(),entities.get(0).getScaleZ()),player_))
			{
				
			}
			*/
			master.renderShadowMap(entities,murs,p,light_);
			
			
		
		    
		     
		
			
			for(int i=1;i<p.size();i++)
			{
			  Vector3f playerPosAI=p.get(i).getPosition();
			  
			  if(playerPosAI.getX()>0 && playerPosAI.getX()<3600 && playerPosAI.getZ()>0 && playerPosAI.getZ()<3600)
				{
					 p.get(i).setTerrain1(true);
					 p.get(i).setTerrain2(false);
					 p.get(i).setTerrain3(false);
					 p.get(i).setTerrain4(false);
					 System.out.println("terrain1AI!");
				}
				if(playerPosAI.getX()>-3600 && playerPosAI.getX()<0 && playerPosAI.getZ()>0 && playerPosAI.getZ()<3600)
				{
					 p.get(i).setTerrain1(false);
					 p.get(i).setTerrain2(true);
					 p.get(i).setTerrain3(false);
					 p.get(i).setTerrain4(false);
					 System.out.println("terrain2AI!");
				}
				if(playerPosAI.getX()>0 && playerPosAI.getX()<3600 && playerPosAI.getZ()>-3600 && playerPosAI.getZ()<0)
				{
					p.get(i).setTerrain1(false);
					p.get(i).setTerrain2(false);
					p.get(i).setTerrain3(true);
					p.get(i).setTerrain4(false);
					 System.out.println("terrain3AI!");
				}
				if(playerPosAI.getX()>-3600 && playerPosAI.getX()<0 && playerPosAI.getZ()>-3600 && playerPosAI.getZ()<0)
				{
					p.get(i).setTerrain1(false);
					p.get(i).setTerrain2(false);
					p.get(i).setTerrain3(false);
					p.get(i).setTerrain4(true);
					 System.out.println("terrain4AI!");
				}
				
				if(p.get(i).getTerrain1())
				{
			
					roues_.get(i+1).setParent(p.get(i),terrains.get(0),9,14);
					roues2_.get(i+3).setParent(p.get(i),terrains.get(0),9,14);
					roues3_.get(i+1).setParent(p.get(i),terrains.get(0),-6.5f,-1.5f);
					roues4_.get(i+3).setParent(p.get(i),terrains.get(0),-6.5f,-1.5f);
				    p.get(i).updateAI(terrains.get(0));
				    p.get(i).applySeparation();
				    
				  
				    
				   /* for (player carAI : p) {
				        if (carAI != carPlayer) {  // Ignorer carPlayer lui-même
				            // Obtenez les boîtes englobantes (AABB) des voitures
				            AABB boxPlayer = carPlayer.getBoundingBox();
				            AABB boxAI = carAI.getBoundingBox();

				            // Vérifiez la collision
				            if (carPlayer.checkCollision(boxPlayer, boxAI)) {
				                System.out.println("Collision détectée entre le carPlayer et un carAI");

				                // Gérez la collision, par exemple en appelant handleCollision
				                carPlayer.handleCollision(carPlayer, carAI);

				                // Facultatif : réduire la vitesse du carPlayer
				                carPlayer.setSpeed(0);
				            }
				        }
				    }
				    */
				        
				        
				    for (int j = 1; j < p.size(); j++) {
				        player car1 = p.get(i);
				        for (int y = j + 1; y < p.size(); y++) {
				            player car2 = p.get(j);

				            // Vérification de collision
				            if (car1.checkCollision(car1.getBoundingBox(), car2.getBoundingBox())) {
				                // Gérer la collision ici (réponse physique, ajustement de trajectoire, etc.)
				            	car1.setSpeed(0.01f);
				            }
				        }
				    }
				    
				}
				
				if(p.get(i).getTerrain2())
				{
					roues_.get(i+1).setParent(p.get(i),terrains.get(1),9,14);
					roues2_.get(i+3).setParent(p.get(i),terrains.get(1),9,14);
					roues3_.get(i+1).setParent(p.get(i),terrains.get(1),-6.5f,-1.5f);
					roues4_.get(i+3).setParent(p.get(i),terrains.get(1),-6.5f,-1.5f);
					p.get(i).updateAI(terrains.get(1));
					p.get(i).applySeparation();
					
					/* player carPlayer=p.get(0);
					 
					 for (player carAI : p) {
					        if (carAI != carPlayer) {  // Ignorer carPlayer lui-même
					            // Obtenez les boîtes englobantes (AABB) des voitures
					            AABB boxPlayer = carPlayer.getBoundingBox();
					            AABB boxAI = carAI.getBoundingBox();

					            // Vérifiez la collision
					            if (carPlayer.checkCollision(boxPlayer, boxAI)) {
					                System.out.println("Collision détectée entre le carPlayer et un carAI");

					                // Gérez la collision, par exemple en appelant handleCollision
					                carPlayer.handleCollision(carPlayer, carAI);

					                // Facultatif : réduire la vitesse du carPlayer
					                carPlayer.setSpeed(0);
					            }
					        }
					    }
					 */
					 
					 
					 for (int j = 1; j < p.size(); j++) {
					        player car1 = p.get(i);
					        for (int y = j + 1; y < p.size(); y++) {
					            player car2 = p.get(j);

					            // Vérification de collision
					            if (car1.checkCollision(car1.getBoundingBox(), car2.getBoundingBox())) {
					                // Gérer la collision ici (réponse physique, ajustement de trajectoire, etc.)
					            	car1.setSpeed(0.01f);
					            }
					        }
					    }
				}
				
				if(p.get(i).getTerrain3())
				{
					roues_.get(i+1).setParent(p.get(i),terrains.get(2),9,14);
					roues2_.get(i+3).setParent(p.get(i),terrains.get(2),9,14);
					roues3_.get(i+1).setParent(p.get(i),terrains.get(2),-6.5f,-1.5f);
					roues4_.get(i+3).setParent(p.get(i),terrains.get(2),-6.5f,-1.5f);
					p.get(i).updateAI(terrains.get(2));
					p.get(i).applySeparation();
					
					 for (int j = 1; j < p.size(); j++) {
					        player car1 = p.get(i);
					        for (int y = j + 1; y < p.size(); y++) {
					            player car2 = p.get(j);

					            // Vérification de collision
					            if (car1.checkCollision(car1.getBoundingBox(), car2.getBoundingBox())) {
					                // Gérer la collision ici (réponse physique, ajustement de trajectoire, etc.)
					                car1.setSpeed(0.01f);
					            }
					        }
					    }
				}
				
				if(p.get(i).getTerrain4())
				{
					roues_.get(i+1).setParent(p.get(i),terrains.get(3),9,14);
					roues2_.get(i+3).setParent(p.get(i),terrains.get(3),9,14);
					roues3_.get(i+1).setParent(p.get(i),terrains.get(3),-6.5f,-1.5f);
					roues4_.get(i+3).setParent(p.get(i),terrains.get(3),-6.5f,-1.5f);
					p.get(i).updateAI(terrains.get(3));
					p.get(i).applySeparation();
				
			
				 for (int j = 1; j < p.size(); j++) {
				        player car1 = p.get(i);
				        for (int y = j + 1; y < p.size(); y++) {
				            player car2 = p.get(j);

				            // Vérification de collision
				            if (car1.checkCollision(car1.getBoundingBox(), car2.getBoundingBox())) {
				                // Gérer la collision ici (réponse physique, ajustement de trajectoire, etc.)
				                car1.setSpeed(0.01f);
				            }
				        }
				    }
				
				}
			}
			
			
			
			if(playerPos.getX()>0 && playerPos.getX()<3600 && playerPos.getZ()>0 && playerPos.getZ()<3600)
			{
				 p.get(0).setTerrain1(true);
				 p.get(0).setTerrain2(false);
				 p.get(0).setTerrain3(false);
				 p.get(0).setTerrain4(false);
				 System.out.println("terrain1!");
			}
			if(playerPos.getX()>-3600 && playerPos.getX()<0 && playerPos.getZ()>0 && playerPos.getZ()<3600)
			{
				p.get(0).setTerrain1(false);
				p.get(0).setTerrain2(true);
				p.get(0).setTerrain3(false);
				p.get(0).setTerrain4(false);
				 System.out.println("terrain2!");
			}
			if(playerPos.getX()>0 && playerPos.getX()<3600 && playerPos.getZ()>-3600 && playerPos.getZ()<0)
			{
				p.get(0).setTerrain1(false);
				p.get(1).setTerrain2(false);
				p.get(2).setTerrain3(true);
				p.get(3).setTerrain4(false);
				 System.out.println("terrain3!");
			}
			if(playerPos.getX()>-3600 && playerPos.getX()<0 && playerPos.getZ()>-3600 && playerPos.getZ()<0)
			{
				p.get(0).setTerrain1(false);
				p.get(1).setTerrain2(false);
				p.get(2).setTerrain3(false);
				p.get(3).setTerrain4(true);
				 System.out.println("terrain4!");
			}
			
			if(p.get(0).getTerrain1())
			{
			roues_.get(0).setParent(p.get(0),terrains.get(0),9,14);
			roues_.get(1).setParent(p.get(0),terrains.get(0),-6.5f,-1.5f);
			roues2_.get(2).setParent(p.get(0),terrains.get(0),9,14);
			roues2_.get(3).setParent(p.get(0),terrains.get(0),-6.5f,-1.5f);
			for(int i=0;i<murs.size();i++)
			{
				if(collid.isCollideDroite(boundingboxCar.get(0).getPosition(),new Vector3f(boundingboxCar.get(0).getScaleX(),boundingboxCar.get(0).getScaleY(),boundingboxCar.get(0).getScaleZ()),murs.get(i).getPosition(), new Vector3f(murs.get(i).getScaleX(),murs.get(i).getScaleY(),murs.get(i).getScaleZ())))
				{p.get(0).setSpeed(10.0f);}
			if(collid.isCollideDroite(boundingboxCar.get(1).getPosition(),new Vector3f(boundingboxCar.get(1).getScaleX(),boundingboxCar.get(1).getScaleY(),boundingboxCar.get(1).getScaleZ()),murs.get(i).getPosition(), new Vector3f(murs.get(i).getScaleX(),murs.get(i).getScaleY(),murs.get(i).getScaleZ())))
			{

				p.get(0).setSpeed(-10.0f);

			}
			}
			
			for (player carAI : p) {
		        if (carAI != p.get(0)) {  // Ignorer carPlayer lui-même
		            // Obtenez les boîtes englobantes (AABB) des voitures
		            AABB boxPlayer = p.get(0).getBoundingBox();
		            AABB boxAI = carAI.getBoundingBox();

		            // Vérifiez la collision
		            if (p.get(0).checkCollision(boxPlayer, boxAI)) {
		                System.out.println("Collision détectée entre le carPlayer et un carAI");
		       
		                p.get(0).setSpeed(-1.5f);
		   
		           
		            }
		          
		         
		        }
		    	
		    }
			
		
			p.get(0).move(terrains.get(0),roues_,roues2_);
	
			
			}
			
			if(p.get(0).getTerrain2())
			{
			roues_.get(0).setParent(p.get(0),terrains.get(1),9,14);
			roues_.get(1).setParent(p.get(0),terrains.get(0),-6.5f,-1.5f);
			roues2_.get(0).setParent(p.get(0),terrains.get(0),9,14);
			roues2_.get(1).setParent(p.get(0),terrains.get(0),-6.5f,-1.5f);
			for(int i=0;i<murs.size();i++)
			{
				if(collid.isCollideDroite(boundingboxCar.get(0).getPosition(),new Vector3f(boundingboxCar.get(0).getScaleX(),boundingboxCar.get(0).getScaleY(),boundingboxCar.get(0).getScaleZ()),murs.get(i).getPosition(), new Vector3f(murs.get(i).getScaleX(),murs.get(i).getScaleY(),murs.get(i).getScaleZ())))
				{
					p.get(0).setSpeed(10.0f);
				}
			if(collid.isCollideDroite(boundingboxCar.get(1).getPosition(),new Vector3f(boundingboxCar.get(1).getScaleX(),boundingboxCar.get(1).getScaleY(),boundingboxCar.get(1).getScaleZ()),murs.get(i).getPosition(), new Vector3f(murs.get(i).getScaleX(),murs.get(i).getScaleY(),murs.get(i).getScaleZ())))
			{

				p.get(0).setSpeed(-10.0f);

			}
		 }
			
			for (player carAI : p) {
		        if (carAI != p.get(0)) {  // Ignorer carPlayer lui-même
		            // Obtenez les boîtes englobantes (AABB) des voitures
		            AABB boxPlayer = p.get(0).getBoundingBox();
		            AABB boxAI = carAI.getBoundingBox();

		            // Vérifiez la collision
		            if (p.get(0).checkCollision(boxPlayer, boxAI)) {
		                System.out.println("Collision détectée entre le carPlayer et un carAI");
		       
		                p.get(0).setSpeed(0);
		     
		            }
		          
		         
		        }
		    	
		    }
			
			p.get(0).move(terrains.get(1),roues_,roues2_);
			}
			
			if(p.get(0).getTerrain3())
			{
			roues_.get(0).setParent(p.get(0),terrains.get(2),9,14);
			roues_.get(1).setParent(p.get(0),terrains.get(0),-6.5f,-1.5f);
			roues2_.get(0).setParent(p.get(0),terrains.get(0),9,14);
			roues2_.get(1).setParent(p.get(0),terrains.get(0),-6.5f,-1.5f);
			for(int i=0;i<murs.size();i++)
			{
				if(collid.isCollideDroite(boundingboxCar.get(0).getPosition(),new Vector3f(boundingboxCar.get(0).getScaleX(),boundingboxCar.get(0).getScaleY(),boundingboxCar.get(0).getScaleZ()),murs.get(i).getPosition(), new Vector3f(murs.get(i).getScaleX(),murs.get(i).getScaleY(),murs.get(i).getScaleZ())))
				{
					p.get(0).setSpeed(10.0f);
				}
			if(collid.isCollideDroite(boundingboxCar.get(1).getPosition(),new Vector3f(boundingboxCar.get(1).getScaleX(),boundingboxCar.get(1).getScaleY(),boundingboxCar.get(1).getScaleZ()),murs.get(i).getPosition(), new Vector3f(murs.get(i).getScaleX(),murs.get(i).getScaleY(),murs.get(i).getScaleZ())))
			{

				p.get(0).setSpeed(-10.0f);

			}
			}
			p.get(0).move(terrains.get(2),roues_,roues2_);
			}
			if(p.get(0).getTerrain4())
			{
			roues_.get(0).setParent(p.get(0),terrains.get(0),9,14);
			roues_.get(1).setParent(p.get(0),terrains.get(3),-6.5f,-1.5f);
			roues2_.get(0).setParent(p.get(0),terrains.get(0),9,14);
			roues2_.get(1).setParent(p.get(0),terrains.get(0),-6.5f,-1.5f);
			for(int i=0;i<murs.size();i++)
			{
				if(collid.isCollideDroite(boundingboxCar.get(0).getPosition(),new Vector3f(boundingboxCar.get(0).getScaleX(),boundingboxCar.get(0).getScaleY(),boundingboxCar.get(0).getScaleZ()),murs.get(i).getPosition(), new Vector3f(murs.get(i).getScaleX(),murs.get(i).getScaleY(),murs.get(i).getScaleZ())))
				{p.get(0).setSpeed(10.0f);}
			if(collid.isCollideDroite(boundingboxCar.get(1).getPosition(),new Vector3f(boundingboxCar.get(1).getScaleX(),boundingboxCar.get(1).getScaleY(),boundingboxCar.get(1).getScaleZ()),murs.get(i).getPosition(), new Vector3f(murs.get(i).getScaleX(),murs.get(i).getScaleY(),murs.get(i).getScaleZ())))
			{

				p.get(0).setSpeed(-10.0f);

			}
			}
			p.get(0).move(terrains.get(3),roues_,roues2_);
			}
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			//player_.move(terrains2);
			
			float distance=3*(cam.getPosition().y-water.getHeight());
			cam.getPosition().y-=distance;
			cam.invertPitch();
			
			//fbo.bindReflectionFrameBuffer();
			master.renderScene(roues_,roues2_,roues3_,roues4_,p,entities,murs, terrains, light_, cam,new Vector4f(0,1,0,-waters.get(0).getHeight()+1));
			
			cam.getPosition().y+=distance;
			cam.invertPitch();
		
			fbo.bindReflectionFrameBuffer();
		    master.renderScene(roues_,roues2_,roues3_,roues4_,p,entities,murs, terrains, light_, cam,new Vector4f(0,1,0,-water.getHeight()));
		
			fbo.bindRefractionFrameBuffer();
			master.renderScene(roues_,roues2_,roues3_,roues4_,p,entities, murs,terrains, light_, cam,new Vector4f(0,-1,0,waters.get(0).getHeight()+0.2f));
			
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);

			
			fbo2.bindFrameBuffer();
			master.renderScene(roues_,roues2_,roues3_,roues4_,p,entities,murs, terrains, light_, cam,new Vector4f(0,-1,0,10000));
			
			waterRender.render(waters, cam);
			fbo2.unbindFrameBuffer();
			
			PostProcessing.doPostProcessing(fbo2.getColourTexture());
			//master.render(light_, cam);
			
			//entities.increaseRotation(0,1,0);
			//renderer.prepare();
			//shader.start();
			//shader.loadLight(light_);
			//shader.loadViewMatrix(cam);
			//renderer.render(entities,shader);
			//shader.stop();
			//master.render(light_,cam);
			DisplayManager.updateDisplay();
		}

		//shader.cleanUp();
		PostProcessing.cleanUp();
		fbo.cleanUp();
		fbo2.cleanUp();
		waterShader.cleanUp();
		master.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	     }
		
		
		
	  
	}


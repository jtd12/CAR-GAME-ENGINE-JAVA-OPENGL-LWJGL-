package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import models.texturedModel;
import shaders.staticShader;
import shaders.terrainShader;
import shadows.ShadowMapMasterRenderer;
import skybox.skyboxRenderer;
import terrains.terrain;
import water.WaterShader;
import water.WaterTile;
import water.waterFrameBufferObject;
import water.waterRenderer;
import entities.camera;
import entities.entity;
import entities.light;
import entities.player;
import entities.roues;


public class masterRenderer {

	public static final float fov=70;
	public static final float nearPlane=0.1f;
	public static final float farPlane=250000.0f;
	public static final float RED=0.35f;
	public static final float BLUE=0.4f;
	public static final float GREEN=0.35f;
	
	private Matrix4f projectionMatrix;
	private staticShader shader=new staticShader();
	private entityRenderer render;
	private terrainRenderer terrainRender;
	private terrainShader terrainShader_=new terrainShader();
	private List<terrain> terrains=new ArrayList<terrain>();
	private Map<texturedModel,List<entity>> entities=new HashMap<texturedModel,List<entity>>();
	private Map<texturedModel,List<player>> players =new HashMap<texturedModel,List<player>>();
	//private Map<texturedModel,List<entityWater>> waters =new HashMap<texturedModel,List<entityWater>>();
	private skyboxRenderer skyboxRender;
	private static ShadowMapMasterRenderer shadows;

	
public masterRenderer(loader l,camera cam)
{
	//GL11.glEnable(GL11.GL_CULL_FACE);
	//GL11.glCullFace(GL11.GL_BACK);
	enableCulling();
	createProjectionMatrix();

	render=new entityRenderer(shader,projectionMatrix);
	terrainRender=new terrainRenderer(terrainShader_,projectionMatrix);
	skyboxRender=new skyboxRenderer(l,projectionMatrix);
	this.shadows=new ShadowMapMasterRenderer(cam);
}
public static void enableCulling()
{
	GL11.glEnable(GL11.GL_CULL_FACE);
	GL11.glCullFace(GL11.GL_BACK);
}
public static void disableCulling()
{
	GL11.glDisable(GL11.GL_CULL_FACE);
}
public Matrix4f getProjection()
{
	return this.projectionMatrix;
}
public void renderScene(List<roues> roues_,List<roues> roues2_,List<roues> roues3_,List<roues> roues4_,List<player> p,List<entity> entities,List<entity> murs, List<terrain> terrains, light lights,
		camera camera,Vector4f clipPlane) {
	for (terrain terrain : terrains) {
		processTerrain(terrain);
	}
	for (entity entity : entities) {
		processEntity(entity);
	}
	for (entity entity : murs) {
		processEntity(entity);
	}
	for(player player_:p)
	{
		processEntity(player_);
	}
	for(roues roues__:roues_)
	{
		processEntity(roues__);
	}
	for(roues roues2__:roues2_)
	{
		processEntity(roues2__);
	}
	for(roues roues3__:roues3_)
	{
		processEntity(roues3__);
	}
	for(roues roues4__:roues4_)
	{
		processEntity(roues4__);
	}
	render(lights, camera,clipPlane);
}

public void render(light Sun,camera cam,Vector4f clipPlane)
{
	
	prepare();
	shader.start();
	shader.loadClipPlane(clipPlane);
	shader.loadSkyColor(RED, GREEN, BLUE);
	shader.loadLight(Sun);
	shader.loadViewMatrix(cam);
	render.render(entities);
	shader.stop();
	terrainShader_.start();
	terrainShader_.loadClipPlane(clipPlane);
	terrainShader_.loadSkyColor(RED, GREEN, BLUE);
	terrainShader_.loadLight(Sun);
	terrainShader_.loadViewMatrix(cam);
	terrainRender.render(terrains,shadows.getToShadowMapSpaceMatrix());
	terrainShader_.stop();
	skyboxRender.render(cam);
	terrains.clear();
	entities.clear();
}
public void processTerrain(terrain terrain_)
{
	terrains.add(terrain_);
}
public void processEntity(entity entity_)
{
	texturedModel entityModel=entity_.getModel();
	List<entity> batch=entities.get(entityModel);
	if(batch!=null)
	{
		batch.add(entity_);
	}
	else
	{
		ArrayList<entity> newBatch=new ArrayList<entity>();
		newBatch.add(entity_);
		entities.put(entityModel, newBatch);
	}
	
}



public static void prepare() {
	GL11.glEnable(GL11.GL_DEPTH_TEST);
	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	GL11.glClearColor(RED, GREEN, BLUE, 0.9f);
	GL13.glActiveTexture(GL13.GL_TEXTURE5);
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowTextureMap());
}

private void createProjectionMatrix()
{
	/*float aspectRatio=(float)Display.getWidth()/(float)Display.getHeight();
	float yScale=(float) ((1f/Math.tan(Math.toRadians(fov/2f)))*aspectRatio);
	float xScale=yScale/aspectRatio;
	float frustrumLength=farPlane-nearPlane;
	
	projectionMatrix=new Matrix4f();
	projectionMatrix.m00=xScale;
	projectionMatrix.m11=yScale;
	projectionMatrix.m22=-((farPlane+nearPlane)/frustrumLength);
	projectionMatrix.m23=-1;
	projectionMatrix.m32=-((2*nearPlane*farPlane)/frustrumLength);
	projectionMatrix.m33=0;
	*/
	
	float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
	float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f)))*aspectRatio);
	float x_scale = y_scale / aspectRatio;
	float frustum_length = farPlane - nearPlane;
	
	projectionMatrix = new Matrix4f();
	projectionMatrix.m00 = x_scale;
	projectionMatrix.m11 = y_scale;
	projectionMatrix.m22 = -((farPlane + nearPlane) / frustum_length);
	projectionMatrix.m23 = -1;
	projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustum_length);
	projectionMatrix.m33 = 0;
	
	
}

public void renderShadowMap(List<entity> entityList,List<entity> mursList,List<player> p,light sun)
{// add shader for player
	for(entity entities:entityList)
	{
		processEntity(entities);
	}
	for(entity murs:mursList)
	{
		processEntity(murs);
	}
	for(player player_:p)
	{
		processEntity(player_);
	}
	shadows.render(entities, sun);

	entities.clear();
}
public static int getShadowTextureMap()
{
	return shadows.getShadowMap();
}
public void cleanUp()
{


	terrainShader_.cleanUp();
	shader.cleanUp();
	shadows.cleanUp();
}
}

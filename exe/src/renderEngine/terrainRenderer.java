package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.entity;
import models.rawModel;
import models.texturedModel;
import shaders.staticShader;
import shaders.terrainShader;
import terrains.terrain;
import textures.modelTexture;
import textures.terrainTexturePack;
import toolBox.maths;

public class terrainRenderer {

	private terrainShader shader;
	
	public terrainRenderer(terrainShader shader,Matrix4f projectionMatrix)
	{
		this.shader=shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render(List<terrain> terrains,Matrix4f shadowSpace)
	{
		shader.loadToShadowMapSpaceMatrix(shadowSpace);
		for(terrain terrain_:terrains)
		{
			prepareTerrain(terrain_);
			loadModelMatrix(terrain_);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain_.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	private void prepareTerrain(terrain model)
	{
		rawModel rawModel=model.getModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		bindTexturedModel(model);
		shader.loadShineVariables(1,0);

	}
	
	private void bindTexturedModel(terrain t)
	{
		terrainTexturePack texture=t.getTexturePack();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getBackGroundTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getrTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getgTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getbTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t.getBlendMap().getTextureID());
	}
	
	private void unbindTexturedModel()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	private void loadModelMatrix(terrain terrain_ )
	{
		
		Matrix4f transformationMatrix=maths.createTransformationMatrix(new Vector3f(terrain_.getX(),0,terrain_.getZ()),0,
				0,0,1);
		shader.loadTransformMatrix(transformationMatrix);
	
	}
	/*
	public void render(entity entities,staticShader shader) {
		texturedModel model=entities.getModel();
		rawModel rawModel=model.getModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix=maths.createTransformationMatrix(entities.getPosition(), entities.getRotX(), entities.getRotY(), entities.getRotZ(), entities.getScale());
		shader.loadTransformMatrix(transformationMatrix);
		modelTexture texture=model.getModel_texture();
		shader.loadShineVariables(texture.getShineDamper(), texture.getRelectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getModel_texture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	*/
}

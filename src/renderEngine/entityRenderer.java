package renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
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
import textures.modelTexture;
import toolBox.maths;

public class entityRenderer {

		//private static final float fov=70;
		//private static final float nearPlane=0.01f;
		//private static final float farPlane=5000.0f;
		//private Matrix4f projectionMatrix;
		private staticShader shader;
		
		public entityRenderer(staticShader shader,Matrix4f projectionMatrix)
		{
			this.shader=shader;
			//GL11.glEnable(GL11.GL_CULL_FACE);
			//GL11.glCullFace(GL11.GL_BACK);
			//createProjectionMatrix();
			shader.start();
			shader.loadProjectionMatrix(projectionMatrix);
			shader.stop();
		}
		/*
		public static void prepare() {
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(1, 0, 0, 1);
		}
*/
		
		 
		 
		 
		public void render(Map<texturedModel,List<entity>> entities)
		{
			
			
			for(texturedModel model:entities.keySet())
			{
				prepareTexturedModel(model);
				List<entity> batch=entities.get(model);
				for(entity entity_:batch)
				{
					prepareInstance(entity_);
					GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				}
				unbindTexturedModel();
				}
			
			
			
		}
		
		private void prepareTexturedModel(texturedModel model)
		{
			rawModel rawModel=model.getModel();
			GL30.glBindVertexArray(rawModel.getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			modelTexture texture=model.getModel_texture();
			if(texture.isHasTransparency())
			{
				masterRenderer.disableCulling();
			}
			shader.loadFakeLight(texture.isFakeLight());
			shader.loadShineVariables(texture.getShineDamper(), texture.getRelectivity());
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getModel_texture().getID());
		}
		private void unbindTexturedModel()
		{
			masterRenderer.enableCulling();
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
		}
		
		public static Matrix4f scale(float x, float y, float z) {
	        Matrix4f scaling = new Matrix4f();

	        scaling.m00 = x;
	        scaling.m11 = y;
	        scaling.m22 = z;

	        return scaling;
	}
	
		
		private void prepareInstance(entity entities )
		{
			Matrix4f scaleMatrix=scale(entities.getScaleX(),entities.getScaleY(),entities.getScaleZ());
			Matrix4f transformationMatrix=maths.createTransformationMatrix(entities.getPosition(), entities.getRotX(), entities.getRotY(), entities.getRotZ(), entities.getScale());
			Matrix4f MVP = Matrix4f.mul(transformationMatrix, scaleMatrix, null);
			shader.loadTransformMatrix(MVP);
			//Matrix4f transformationMatrix=maths.createTransformationMatrix(entities.getPosition(), entities.getRotX(), entities.getRotY(), entities.getRotZ(), entities.getScale());
			//shader.loadTransformMatrix(transformationMatrix);
		
		}
		
		/*public void render(entity entities,staticShader shader) {
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
		/*
		private void createProjectionMatrix()
		{
			float aspectRatio=(float)Display.getWidth()/(float)Display.getHeight();
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
			
			
			
		}*/
	
}

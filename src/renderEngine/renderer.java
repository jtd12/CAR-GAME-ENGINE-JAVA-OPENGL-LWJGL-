package renderEngine;

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
import toolBox.maths;

public class renderer {

		private static final float fov=70;
		private static final float nearPlane=0.01f;
		private static final float farPlane=5000.0f;
		private Matrix4f projectionMatrix;
		
	
		public void prepare() {
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glClearColor(1, 0, 0, 1);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		}

		 

		public renderer(staticShader shader)
		{
			createProjectionMatrix();
			shader.start();
			shader.loadProjectionMatrix(projectionMatrix);
			shader.stop();
		}
		
		public static Matrix4f scale(float x, float y, float z) {
	        Matrix4f scaling = new Matrix4f();

	        scaling.m00 = x;
	        scaling.m11 = y;
	        scaling.m22 = z;

	        return scaling;
	}
	
		
		public void render(entity entities,staticShader shader) {
			texturedModel model=entities.getModel();
			rawModel rawModel=model.getModel();
			GL30.glBindVertexArray(rawModel.getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			
			Matrix4f scaleMatrix=scale(entities.getScaleX(),entities.getScaleY(),entities.getScaleZ());
			Matrix4f globalTransformationMatrix = Matrix4f.mul(entities.getGlobalTransformation(),scaleMatrix,null);
			
			Matrix4f transformationMatrix=maths.createTransformationMatrix(entities.getPosition(), entities.getRotX(), entities.getRotY(), entities.getRotZ(), entities.getScale());
			Matrix4f MVP = Matrix4f.mul(globalTransformationMatrix, scaleMatrix, null);
			shader.loadTransformMatrix(globalTransformationMatrix);
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getModel_texture().getID());
			GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
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
			
			
			
		}
	
}

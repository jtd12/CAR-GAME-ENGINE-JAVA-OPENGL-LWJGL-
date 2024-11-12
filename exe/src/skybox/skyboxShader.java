package skybox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import entities.camera;

import shaders.shaderProgram;
import toolBox.maths;

public  class skyboxShader extends shaderProgram{

	private static final String VERTEX_FILE = "src/skybox/skyboxVertexShader.vert";
	private static final String FRAGMENT_FILE = "src/skybox/skyboxFragmentShader.frag";
	
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_plane;
	
	public skyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	public void loadClipPlane(Vector4f plane)
	{
		super.loadVector(location_plane, plane);
	}
	public void loadViewMatrix(camera camera){
		Matrix4f matrix = maths.createViewMatrix(camera);
		matrix.m30=0;
		matrix.m31=0;
		matrix.m32=0;
		super.loadMatrix(location_viewMatrix, matrix);
	}
	
	@Override
	protected void getAllUniformLocation() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_plane=super.getUniformLocation("plane");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
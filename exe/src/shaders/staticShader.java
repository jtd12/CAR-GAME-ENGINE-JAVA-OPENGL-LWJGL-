package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.camera;
import entities.light;
import toolBox.maths;

public class staticShader extends shaderProgram {
	private static final String VERTEX_FILE = "src/shaders/vertexShader.vert";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.frag";
	private int local_transformMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPos;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLight;
	private int location_skyColor;
	private int location_plane;
	
	public staticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocation() {
		// TODO Auto-generated method stub
		local_transformMatrix=super.getUniformLocation("transformationMatrix");
		location_projectionMatrix=super.getUniformLocation("projectionMatrix");
		location_viewMatrix=super.getUniformLocation("viewMatrix");
		location_lightPos=super.getUniformLocation("lightPos");
		location_lightColor=super.getUniformLocation("lightColor");
		location_shineDamper=super.getUniformLocation("shineDamper");
		location_reflectivity=super.getUniformLocation("reflectivity");
		location_useFakeLight=super.getUniformLocation("useFakeLight");
		location_skyColor=super.getUniformLocation("skyColor");
		location_plane=super.getUniformLocation("plane");
	}
	
	public void loadClipPlane(Vector4f plane)
	{
		super.loadVector(location_plane, plane);
	}
	public void loadSkyColor(float r,float g,float b)
	{
		super.loadVector(location_skyColor, new Vector3f(r,g,b));
	}
	public void loadFakeLight(boolean fakeLight)
	{
		super.loadBoolean(location_useFakeLight, fakeLight);
	}
	public void loadShineVariables(float damper,float reflectivity)
	{
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	public void loadTransformMatrix(Matrix4f matrix)
	{
		super.loadMatrix(local_transformMatrix,matrix);
	}
	public void loadProjectionMatrix(Matrix4f projection)
	{
		super.loadMatrix(location_projectionMatrix,projection);
	}
	public void loadViewMatrix(camera cam)
	{
		Matrix4f viewMatrix=maths.createViewMatrix(cam);
		super.loadMatrix(location_viewMatrix,viewMatrix);
	}
	
	public void loadLight(light l)
	{
	super.loadVector(location_lightPos, l.getPosition());	
	super.loadVector(location_lightColor, l.getColor());
	}
}

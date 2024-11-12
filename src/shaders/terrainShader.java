package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.camera;
import entities.light;
import toolBox.maths;

public class terrainShader extends shaderProgram {
	
	private static final String VERTEX_FILE = "src/shaders/terrainShaderVert.vert";
	private static final String FRAGMENT_FILE = "src/shaders/terrainShaderFrag.frag";
	private int local_transformMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPos;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;
	private int location_shadowMapSpace;
	private int location_shadowMap;
	private int location_plane;
	
	public terrainShader() {
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
		location_skyColor=super.getUniformLocation("skyColor");
		location_backgroundTexture=super.getUniformLocation("backgroundTextureSampler");
		location_rTexture=super.getUniformLocation("rTextureSampler");
		location_gTexture=super.getUniformLocation("gTextureSampler");
		location_bTexture=super.getUniformLocation("bTextureSampler");
		location_blendMap=super.getUniformLocation("blendMapTextureSampler");
		location_shadowMapSpace=super.getUniformLocation("toShadowMapSpace");
		location_shadowMap=super.getUniformLocation("shadowMap");
		location_plane=super.getUniformLocation("plane");
	}
	
	public void connectTextureUnits()
	{
		super.loadInt(location_backgroundTexture,  0);
		super.loadInt(location_rTexture, 1);
		super.loadInt(location_gTexture, 2);
		super.loadInt(location_bTexture, 3);
		super.loadInt(location_blendMap, 4);
		super.loadInt(location_shadowMap,5);
	}
	
	public  void loadClipPlane(Vector4f plane)
	{
		super.loadVector(location_plane, plane);
	}
	
	public void loadToShadowMapSpaceMatrix(Matrix4f matrix)
	{
	super.loadMatrix(location_shadowMapSpace, matrix);	
	}
	
	public void loadSkyColor(float r,float g,float b)
	{
		super.loadVector(location_skyColor, new Vector3f(r,g,b));
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

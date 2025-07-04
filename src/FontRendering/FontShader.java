package FontRendering;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import shaders.shaderProgram;

public class FontShader extends shaderProgram{

	private static final String VERTEX_FILE = "src/FontRendering/fontVertex.txt";
	private static final String FRAGMENT_FILE = "src/FontRendering/fontFragment.txt";
	
	private int location_colour;
	private int location_translation;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_colour = super.getUniformLocation("colour");
		location_translation = super.getUniformLocation("translation");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	protected void loadColour(Vector3f colour){
		super.loadVector(location_colour, colour);
	}
	
	protected void loadTranslation(Vector2f translation){
		super.load2DVector(location_translation, translation);
	}

	@Override
	protected void getAllUniformLocation() {
		// TODO Auto-generated method stub
		
	}


}

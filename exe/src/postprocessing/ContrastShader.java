package postprocessing;

import shaders.shaderProgram;

public class ContrastShader extends shaderProgram {

	private static final String VERTEX_FILE = "src/postprocessing/contrastVertex.txt";
	private static final String FRAGMENT_FILE = "src/postprocessing/contrastFragment.txt";
	
	public ContrastShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {	
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocation() {
		// TODO Auto-generated method stub
		
	}

}

package postprocessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class contrastChanger {
private ImageRenderer image;
private ContrastShader shader;

public contrastChanger()
{
	shader=new ContrastShader();
	image=new ImageRenderer();
	
	
}

public void render(int texture)
{
	shader.start();
	GL13.glActiveTexture(GL13.GL_TEXTURE0);
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	image.renderQuad();
	shader.stop();
}

public void cleanUp()
{
	image.cleanUp();
	shader.cleanUp();
}
}

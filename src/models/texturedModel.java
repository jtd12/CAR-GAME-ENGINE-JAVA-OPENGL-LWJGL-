package models;

import textures.modelTexture;

public class texturedModel {
private rawModel model;
private modelTexture model_texture;

public texturedModel(rawModel model,modelTexture modelTexture)
{
	this.model=model;
	this.model_texture=modelTexture;
}

public rawModel getModel() {
	return model;
}

public modelTexture getModel_texture() {
	return model_texture;
}

}

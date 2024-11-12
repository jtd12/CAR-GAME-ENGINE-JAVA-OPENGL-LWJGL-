package textures;

public class terrainTexturePack {
	
private terrainTexture backGroundTexture;
private terrainTexture rTexture;
private terrainTexture gTexture;
private terrainTexture bTexture;

public terrainTexturePack(terrainTexture backGroundTexture, terrainTexture rTexture, terrainTexture gTexture,
		terrainTexture bTexture) {
	super();
	this.backGroundTexture = backGroundTexture;
	this.rTexture = rTexture;
	this.gTexture = gTexture;
	this.bTexture = bTexture;
}

public terrainTexture getBackGroundTexture() {
	return backGroundTexture;
}

public terrainTexture getrTexture() {
	return rTexture;
}

public terrainTexture getgTexture() {
	return gTexture;
}

public terrainTexture getbTexture() {
	return bTexture;
}


}

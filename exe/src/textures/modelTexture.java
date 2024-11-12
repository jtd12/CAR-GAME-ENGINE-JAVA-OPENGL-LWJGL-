package textures;

public class modelTexture {
private int textureID;
private  float shineDamper=1;
private float relectivity=0;
private boolean hasTransparency=false;
private boolean fakeLight=false;

public modelTexture(int id)
{
	this.textureID=id;
}
public int getID()
{
	return this.textureID;
}
public float getShineDamper() {
	return shineDamper;
}
public void setShineDamper(float shineDamper) {
	this.shineDamper = shineDamper;
}
public float getRelectivity() {
	return relectivity;
}
public void setRelectivity(float relectivity) {
	this.relectivity = relectivity;
}
public boolean isHasTransparency() {
	return hasTransparency;
}
public void setHasTransparency(boolean hasTransparency) {
	this.hasTransparency = hasTransparency;
}
public boolean isFakeLight() {
	return fakeLight;
}
public void setFakeLight(boolean fakeLight) {
	this.fakeLight = fakeLight;
}

}

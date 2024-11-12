package terrains;

import models.rawModel;
import renderEngine.loader;
import textures.modelTexture;
import textures.terrainTexture;
import textures.terrainTexturePack;
import toolBox.maths;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class terrain {
private static final float SIZE=3600;
//private static final int VERTEX_COUNT=128;
private static final float max_height=100;
private static final float max_pixel_color=256*256*256;
private float x;
private float z;
private rawModel model;
private terrainTexturePack texturePack;
private terrainTexture blendMap;
private float[][] terrainsHeights;
private boolean terrain1;
private boolean terrain2;
private boolean terrain3;
private boolean terrain4;


public terrain(int gridX,int gridZ,loader l,terrainTexturePack texturePack,terrainTexture blendMap,
		String heightMap)
{
	this.texturePack=texturePack;
	this.blendMap=blendMap;
	this.x=gridX*SIZE;
	this.z=gridZ*SIZE;
	this.model=generateTerrain(l,heightMap);
}

public float getHeightTerrain(float worldX,float worldZ)
{
float terrainX=worldX-this.x;
float terrainZ=worldZ-this.z;
float gridSquareSize=SIZE/((float)terrainsHeights.length -1);
int gridX=(int) Math.floor(terrainX/ gridSquareSize);
int gridZ=(int) Math.floor(terrainZ/ gridSquareSize);

if(gridX>=terrainsHeights.length-1 || gridZ>=terrainsHeights.length-1 || gridX<0 || gridZ<0)
{
	return 0;
}
float xCoord=(terrainX%gridSquareSize)/gridSquareSize;
float zCoord=(terrainZ%gridSquareSize)/gridSquareSize;
float answer;
if(xCoord<=(1-zCoord))
{
	answer = maths
			.barryCentric(new Vector3f(0, terrainsHeights[gridX][gridZ], 0), new Vector3f(1,
					terrainsHeights[gridX + 1][gridZ], 0), new Vector3f(0,
							terrainsHeights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
}
else
{
	answer = maths
			.barryCentric(new Vector3f(1, terrainsHeights[gridX + 1][gridZ], 0), new Vector3f(1,
					terrainsHeights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
							terrainsHeights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
}
return answer;
}

private rawModel generateTerrain(loader loader,String heightMap){
	
	BufferedImage image=null;
	try {
		image=ImageIO.read(new File("res/"+heightMap+".png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	int VERTEX_COUNT=image.getHeight();
	terrainsHeights=new float[VERTEX_COUNT][VERTEX_COUNT];
	int count = VERTEX_COUNT * VERTEX_COUNT;
	float[] vertices = new float[count * 3];
	float[] normals = new float[count * 3];
	float[] textureCoords = new float[count*2];
	int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
	int vertexPointer = 0;
	for(int i=0;i<VERTEX_COUNT;i++){
		for(int j=0;j<VERTEX_COUNT;j++){
			vertices[vertexPointer*3] =  (float)j/((float)VERTEX_COUNT - 1) * SIZE;
			float height=getHeight(j,i,image);
			terrainsHeights[j][i]=height;
			vertices[vertexPointer*3+1] = height;
			vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
			Vector3f normal=calculateNormals(j,i,image);
			
			normals[vertexPointer*3] = normal.x;
			normals[vertexPointer*3+1] = normal.y;
			normals[vertexPointer*3+2] =normal.z;
			textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
			textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
			vertexPointer++;
		}
	}
	int pointer = 0;
	for(int gz=0;gz<VERTEX_COUNT-1;gz++){
		for(int gx=0;gx<VERTEX_COUNT-1;gx++){
			int topLeft = (gz*VERTEX_COUNT)+gx;
			int topRight = topLeft + 1;
			int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
			int bottomRight = bottomLeft + 1;
			indices[pointer++] = topLeft;
			indices[pointer++] = bottomLeft;
			indices[pointer++] = topRight;
			indices[pointer++] = topRight;
			indices[pointer++] = bottomLeft;
			indices[pointer++] = bottomRight;
		}
	}
	return loader.loadToVAO(vertices, textureCoords, normals, indices);
}

private float getHeight(int x, int z,BufferedImage image)
{
	if(x<0 || x>=image.getHeight() || z<0  || z>=image.getHeight())
	{
		return 0;
	}
	float height=image.getRGB(x,z);
	height+=max_pixel_color/2f;
	height/=max_pixel_color/2f;
	height*=max_height;
	return height;
	
}
private Vector3f calculateNormals(int x, int z, BufferedImage img)
{

	float heightL=getHeight(x-1,z,img);
	float heightR=getHeight(x+1,z,img);
	float heightD=getHeight(x,z-1,img);
	float heightU=getHeight(x,z+1,img);
	Vector3f normal=new Vector3f(heightL-heightR,2f,heightD-heightU);
	normal.normalise();
	return normal;
}
public float getX() {
	return x;
}
public void setX(float x) {
	this.x = x;
}
public float getZ() {
	return z;
}
public void setZ(float z) {
	this.z = z;
}
public rawModel getModel() {
	return model;
}
public void setModel(rawModel model) {
	this.model = model;
}


public terrainTexturePack getTexturePack() {
	return texturePack;
}
public void setTexturePack(terrainTexturePack texturePack) {
	this.texturePack = texturePack;
}
public terrainTexture getBlendMap() {
	return blendMap;
}
public void setBlendMap(terrainTexture blendMap) {
	this.blendMap = blendMap;
}
public static float getSize() {
	return SIZE;
}

public void setTerrain1(boolean t)
{
	terrain1=t;
}
public void setTerrain2(boolean t)
{
	terrain2=t;
}
public void setTerrain3(boolean t)
{
	terrain3=t;
}
public void setTerrain4(boolean t)
{
	terrain4=t;
}
public boolean getTerrain1()
{
	return terrain1;
}
public boolean getTerrain2()
{
	return terrain2;
}
public boolean getTerrain3()
{
	return terrain3;
}
public boolean getTerrain4()
{
	return terrain4;
}
}

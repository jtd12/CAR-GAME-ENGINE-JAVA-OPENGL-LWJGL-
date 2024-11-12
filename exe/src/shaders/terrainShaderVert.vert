#version 330

in vec3 position;
in vec2 textureCoords;
out vec2 pass_coords;
out vec3 surfaceNormal;
out vec3 lightVector;
out vec4 shadowCoords;
//out vec3 color;
uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPos;
in vec3 normal;
out vec3 cameraVector;
out float visibility;
const float density=0.0025;
const float gradient=1.0;
uniform mat4 toShadowMapSpace;
uniform vec4 plane=vec4(0,-1,0,15);

void main(void){
	
	vec4 worldPosition=transformationMatrix*vec4(position,1.0);
	shadowCoords=toShadowMapSpace * worldPosition;
	gl_ClipDistance[0]=dot(worldPosition,plane);
	vec4 positionRelative=viewMatrix*worldPosition;
	gl_Position = projectionMatrix*positionRelative;
	//color = vec3(position.x+0.5,0.0,position.y+0.5);
	pass_coords=textureCoords;
	surfaceNormal=(transformationMatrix*vec4(normal,0.0)).xyz;
	lightVector=lightPos - worldPosition.xyz;
	cameraVector=(inverse(viewMatrix)*vec4(0.0,0.0,0.0,1.0)).xyz-worldPosition.xyz;
	
	float dist=length(positionRelative.xyz);
	visibility=exp(-pow((dist*density),gradient));
	visibility=clamp(visibility,0.0,0.8);
}
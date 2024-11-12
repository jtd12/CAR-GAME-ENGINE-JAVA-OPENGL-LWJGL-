#version 330

//in vec3 color;

out vec4 out_Color;
in vec3 surfaceNormal;
in vec3 lightVector;
in vec2  pass_coords;
uniform sampler2D backgroundTextureSampler;
uniform sampler2D rTextureSampler;
uniform sampler2D gTextureSampler;
uniform sampler2D bTextureSampler;
uniform sampler2D blendMapTextureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;
vec3 totalDiffuse=vec3(0.0);
in vec3 cameraVector;
in float visibility;
in vec4 shadowCoords;
uniform sampler2D shadowMap;

void main(void){

float objectNearLight=texture(shadowMap,shadowCoords.xy).r;
float lightFactor=1.0;

if(shadowCoords.z>objectNearLight)
{
lightFactor=1.0-0.4;
}

vec4 blendMapColor=texture(blendMapTextureSampler,pass_coords);
float backTextureAmount=1-(blendMapColor.r+blendMapColor.g+blendMapColor.b);
vec2 titleCoords=pass_coords*40.0;
vec4  backgroundTextureColor=texture(backgroundTextureSampler,titleCoords)*backTextureAmount;
vec4 rTextureColor=texture(rTextureSampler,titleCoords)*blendMapColor.r;
vec4 gTextureColor=texture(gTextureSampler,titleCoords)*blendMapColor.g;
vec4 bTextureColor=texture(bTextureSampler,titleCoords)*blendMapColor.b;

vec4 totalColor=backgroundTextureColor+rTextureColor+gTextureColor+bTextureColor;

vec3 unitNormal=normalize(surfaceNormal);
vec3 unitLightVector=normalize(lightVector);
float dot1=dot(unitNormal,unitLightVector);
float brightness=max(dot1,0.2);
vec3 diffuse=brightness*lightColor;
vec3 unitVectorCamera=normalize(cameraVector);
vec3 lightDirection=-unitLightVector;
vec3 reflectedLight=reflect(lightDirection,unitNormal);
float specularFac=dot(reflectedLight,unitVectorCamera);
specularFac=max(specularFac,0.0);
float dampFactor=pow(specularFac,shineDamper);
vec3 finalSpecular=dampFactor*reflectivity*lightColor;
totalDiffuse=totalDiffuse+(brightness*lightColor);
totalDiffuse=max(totalDiffuse,0.4)*lightFactor;

out_Color = vec4(totalDiffuse,1.0) * totalColor + vec4(finalSpecular,1.0);
out_Color=mix(vec4(skyColor,1.0),out_Color,visibility);
}
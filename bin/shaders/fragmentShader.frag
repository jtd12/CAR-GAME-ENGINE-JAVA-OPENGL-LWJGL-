#version 330

//in vec3 color;

out vec4 out_Color;
in vec3 surfaceNormal;
in vec3 lightVector;
in vec2  pass_coords;
uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;
in vec3 cameraVector;
in float visibility;

void main(void){

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
vec4 texColor=texture(textureSampler,pass_coords);

if(texColor.a<0.5)
{
discard;
}
out_Color = vec4(diffuse,1.0) * texture(textureSampler,pass_coords) + vec4(finalSpecular,1.0);
out_Color=mix(vec4(skyColor,1.0),out_Color,visibility);
}
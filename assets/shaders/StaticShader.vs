#version 400 core

in vec3 position;
in vec2 uvIn;

out vec2 uv;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 viewMat;

void main(void){

	gl_Position = projection*viewMat*transform*vec4(position,1.0);
	uv=uvIn;
}
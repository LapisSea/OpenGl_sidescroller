#version 400 core

in vec3 position;
in vec2 uvIn;
in mat4 viewMat;

out vec2 uv;

uniform mat4 projection;

void main(void){

	gl_Position = projection*viewMat*vec4(position,1.0);
	uv=uvIn;
}
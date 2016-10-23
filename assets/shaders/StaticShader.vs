#version 400 core

in vec3 position;
in vec2 uvIn;

out vec2 uv;

void main(void){
	uv=uvIn;
	gl_Position = vec4(position,1.0);
}
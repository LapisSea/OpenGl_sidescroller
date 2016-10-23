#version 400 core

in vec2 uv;

out vec4 result;

uniform sampler2D sampler1;

void main(void){
	result =texture(sampler1,uv);
}

attribute vec3 a_position;
attribute vec2 a_texCoord;
attribute vec2 a_normal;

uniform mat4 u_modelToWorld;
uniform mat4 u_worldToClip;

varying vec2 passTextureCoord;

void main(void) {

    // forward texture coords to the fragment shader
    passTextureCoord = a_texCoord;

    // rotate then move the object to its position in the world
    // then from world coord to clip coord
    gl_Position = u_worldToClip * u_modelToWorld * vec4(a_position, 1.0);
    
}

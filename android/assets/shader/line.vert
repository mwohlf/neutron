
attribute vec3 a_position;

uniform mat4 u_modelToWorld;
uniform mat4 u_worldToClip;


void main(void) {

    gl_Position = u_worldToClip * u_modelToWorld * vec4(a_position, 1.0);
    
}


attribute vec3 a_position;
attribute vec4 a_color;

uniform mat4 u_modelToWorld;
uniform mat4 u_worldToClip;

varying vec4 passColor;


void main(void) {

	passColor = a_color;

    gl_Position = u_worldToClip * u_modelToWorld * vec4(a_position, 1.0);
    
}

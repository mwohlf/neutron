// see: http://gamedev.stackexchange.com/questions/11095/opengl-es-2-0-point-sprites-size

attribute vec3 a_position;
//attribute vec2 a_texCoord; 
//attribute vec4 a_color;

uniform mat4 u_modelToWorld;
uniform mat4 u_worldToClip;
uniform float u_thickness;

varying vec4 passColor;
//varying vec2 passTextureCoord;


void main(void) {
    
    //passColor = a_color;
    
    // forward texture coords to the fragment shader
    //passTextureCoord = a_texCoord;
    
    gl_PointSize = 5;

    gl_Position = u_worldToClip * u_modelToWorld * vec4(a_position, 1.0);
    
}

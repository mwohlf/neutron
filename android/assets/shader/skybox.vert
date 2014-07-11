// skybox shader
// see: http://ogldev.atspace.co.uk/www/tutorial25/tutorial25.html

attribute vec3 a_position;
attribute vec2 a_texCoord;

uniform mat4 u_modelToWorld;
uniform mat4 u_worldToClip;

varying vec2 passTextureCoord;

void main(void) {

    // forward texture coords to the fragment shader
    passTextureCoord = a_texCoord;

    // rotate then move the object to its position in the world
    // then from world coord to clip coord
    gl_Position = u_worldToClip * u_modelToWorld * vec4(a_position, 1.0);

    // using w as z-coord is the trick to be able to render the skybox last 
    // without running into trouble with z coords
    gl_Position = gl_Position.xyww;
    
}


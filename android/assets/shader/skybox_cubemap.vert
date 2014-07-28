// skybox shader
// see: http://ogldev.atspace.co.uk/www/tutorial25/tutorial25.html
//      https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests-android/assets/data/g3d/shaders/cubemap.glsl

attribute vec3 a_position;
attribute vec2 a_texCoord;

uniform mat4 u_modelToWorld;
uniform mat4 u_worldToClip;

varying vec2 v_cubeMapUV;

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


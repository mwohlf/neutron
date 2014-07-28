#ifdef GL_ES
  precision mediump float; 
#endif

uniform samplerCube u_environmentCubemap;

varying vec2 v_cubeMapUV;

// see: https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests-android/assets/data/g3d/shaders/cubemap.glsl
void main(void) {

	gl_FragColor = vec4(textureCube(u_environmentCubemap, v_cubeMapUV).rgb, 1.0);
	    
}

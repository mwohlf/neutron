#ifdef GL_ES
  precision mediump float; 
#endif

uniform sampler2D u_texture;

varying vec2 passTextureCoord;

void main(void) {

    gl_FragColor = texture2D(u_texture, passTextureCoord);
    
}

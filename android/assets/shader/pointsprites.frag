#ifdef GL_ES
  precision mediump float; 
#endif

uniform sampler2D u_texture;

varying vec4 passColor;

void main() {

    gl_FragColor = texture2D(u_texture, gl_PointCoord) * passColor;
   
}
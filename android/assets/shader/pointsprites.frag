#ifdef GL_ES
  precision mediump float; 
#endif

uniform sampler2D u_texture;

//varying vec4 passColor;
varying vec2 passTextureCoord;

void main() {

    gl_FragColor = texture2D(u_texture, gl_PointCoord); // * passColor;
    //gl_FragColor = texture2D(u_texture, passTextureCoord); // * passColor;
    //gl_FragColor = vec4(gl_PointCoord.s,gl_PointCoord.t,0,1);
   
}
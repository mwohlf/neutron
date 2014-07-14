#ifdef GL_ES
  precision mediump float; 
#endif

varying vec4 passColor;

void main(void) {

    gl_FragColor = passColor;
    
}

package net.wohlfart.neutron.scene.entity;

import java.util.Collections;

import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.graph.SortTokenImpl;
import net.wohlfart.neutron.util.NodeBuilder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Cube extends AbstractEntity {
	
	private static final String SHADER_NAME = "default";

	@Override
	public void initNodes() {
		nodes = Collections.singleton((INode) new NodeBuilder()
			.useAttributes(new VertexAttributes(
					new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
					new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE),
					new VertexAttribute(Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE)))
		    .useShader(SHADER_NAME)
		    .useSize(5f)
		    .useTexture(new Texture(Gdx.files.internal("badlogic.jpg")))
		    .useSortToken(new SortTokenImpl(false, getPosition(), SHADER_NAME))
			.createCube("cube"));
	}

}

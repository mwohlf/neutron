package net.wohlfart.neutron.scene.entity;

import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.util.NodeBuilder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Quad extends AbstractEntity {

	@Override
	public INode createNode() {
		return new NodeBuilder()
			.useAttributes(new VertexAttributes(
					new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
					new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE),
					new VertexAttribute(Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE)))
		    .useShader("default")
		    .useSize(5f)
		    .useTexture(new Texture(Gdx.files.internal("badlogic.jpg")))
			.createQuad("quad");
	}

}
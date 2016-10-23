package game.rendering.models;

public class BasicModel{
	private int vaoID;
	private int vertexCount;
	
	public BasicModel(int vaoId, int vertexCount){
		this.vaoID = vaoId;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID() {
		return vaoID;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
}
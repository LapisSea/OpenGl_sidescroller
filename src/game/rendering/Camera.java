package game.rendering;

import java.awt.geom.Rectangle2D;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import game.util.MatrixUtil;

public class Camera{
	
	private static final Vector2f RENDER_OFFSET=new Vector2f(),ZOOM_SCALE=new Vector2f();
	public static final int BASE_VIEW_RAD=15;
	private static final Vector4f VEC4=new Vector4f();
	
	public Vector2f pos=new Vector2f();
	public float rotation=0,zoom=1;
	
	public Matrix4f getTransform(){
		RENDER_OFFSET.x=-pos.x;
		RENDER_OFFSET.y=-pos.y;
		float scale=zoom/BASE_VIEW_RAD;
		ZOOM_SCALE.set(scale,scale);
		
		return MatrixUtil.createTransformMat(RENDER_OFFSET, rotation, ZOOM_SCALE);
	}
	
	public class Colider{
		public final Rectangle2D.Float rect,tester=new Rectangle2D.Float(0, 0, 0, 0);
		
		public Colider(){
			Matrix4f mat=getTransform();
			VEC4.z=0;
			VEC4.w=0;
			
			VEC4.x=-1;
			VEC4.y=-1;
			Matrix4f.transform(mat, VEC4, VEC4);
			Vector2f start=new Vector2f(VEC4.x, VEC4.y);
			
			VEC4.x=1;
			VEC4.y=1;
			Matrix4f.transform(mat, VEC4, VEC4);
			
			Vector2f end=new Vector2f(VEC4.x, VEC4.y);
			
			if(end.x<start.x||end.y<start.y){
				Vector2f end1=end;
				end=start;
				start=end1;
			}
			
			rect=new Rectangle2D.Float(start.x, start.y, end.x-start.x, end.y-start.y);
		}
		
		public boolean doesColide(Vector2f objStart, Vector2f objEnd){
			tester.setRect(objStart.x, objStart.y, objEnd.x-objStart.x, objEnd.y-objStart.y);
			return rect.contains(tester);
		}
		
		
		
	}
	
}


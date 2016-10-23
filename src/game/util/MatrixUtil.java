package game.util;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class MatrixUtil{
	
	private static final Vector3f ROT_AXIS=new Vector3f(0, 0, 1),SCALE_VEC=new Vector3f();
	
	public static Matrix4f createTransformMat(Vector2f pos, float rotation, Vector2f scale){
		Matrix4f mat=new Matrix4f();
		mat.translate(pos);
		mat.rotate((float)Math.toRadians(rotation), ROT_AXIS);
		SCALE_VEC.x=scale.x;
		SCALE_VEC.y=scale.y;
		mat.scale(SCALE_VEC);
		return mat;
	}
	
	public static boolean equals(Matrix4f m1, Matrix4f m2){
		if(m1==m2) return true;
		if(m1==null||m2==null) return false;
		
		return  m1.m00==m2.m00&&
				m1.m01==m2.m01&&
				m1.m02==m2.m02&&
				m1.m03==m2.m03&&
				m1.m10==m2.m10&&
				m1.m11==m2.m11&&
				m1.m12==m2.m12&&
				m1.m13==m2.m13&&
				m1.m20==m2.m20&&
				m1.m21==m2.m21&&
				m1.m22==m2.m22&&
				m1.m23==m2.m23&&
				m1.m30==m2.m30&&
				m1.m31==m2.m31&&
				m1.m32==m2.m32&&
				m1.m33==m2.m33;
	}
}

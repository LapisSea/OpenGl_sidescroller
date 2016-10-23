package game.util.registry;


public abstract class IndexableRegisterObject{
	
	
	private int id=-1;
	
	public void registerWithId(int id){
		if(this.id!=-1)return;
		this.id=id;
	}
	
	public int getId(){
		return id;
	}
	
}

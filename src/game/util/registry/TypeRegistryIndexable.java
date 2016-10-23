package game.util.registry;

import java.util.ArrayList;
import java.util.List;

public class TypeRegistryIndexable<T extends IndexableRegisterObject>{
	
	private List<T> data=new ArrayList<>();
	
	public void register(T obj){
		if(data.contains(obj))return;
		obj.registerWithId(data.size());
		data.add(obj);
	}
	
	public T getByid(int id){
		return data.get(id);
	}
	public int getSize(){
		return data.size();
	}
}

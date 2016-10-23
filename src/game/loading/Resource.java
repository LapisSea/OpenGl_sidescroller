package game.loading;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Resource{
	
	protected final String localPath;
	
	public Resource(String localPath){
		this.localPath=localPath;
	}

	public File getFile(){
		return new File(localPath);
	}
	
	public InputStream getStream(){
		try{
			return new FileInputStream(getFile());
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public String read(){
		
		try{
			return new String(Files.readAllBytes(getFile().toPath()));
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	
}

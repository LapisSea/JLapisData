package com.lapissea.datamanager.managers;

import com.lapissea.datamanager.DataSignature;
import com.lapissea.datamanager.Domain;
import com.lapissea.datamanager.DomainRegistry;
import com.lapissea.datamanager.IDataManager;
import com.lapissea.util.NotNull;
import com.lapissea.util.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import java.util.stream.Stream;

@SuppressWarnings("CatchMayIgnoreException")
public class DataManagerMulti implements IDataManager{
	
	private final List<Domain> domains=new ArrayList<>(2);
	
	public DataManagerMulti(){ }
	
	public DataManagerMulti(@NotNull String... domainPaths){
		for(String path : domainPaths){
			registerDomain(path);
		}
	}
	
	public DataManagerMulti(@NotNull Domain... domainPaths){
		for(Domain path : domainPaths){
			registerDomain(path);
		}
	}
	
	@NotNull
	public DataManagerMulti registerDomain(@NotNull String path){
		return registerDomain(DomainRegistry.create(path));
	}
	
	@NotNull
	public DataManagerMulti registerDomain(@NotNull Domain domain){
		if(domains.contains(domain)) return this;
		domains.add(domain);
		return this;
	}
	
	@Nullable
	@Override
	public FileChannel getRandomAccess(@NotNull String localPath, @NotNull Mode mode){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				FileChannel t=domain.getRandomAccess(localPath, mode);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		
		return null;
	}
	
	@Nullable
	@Override
	public BufferedInputStream getInStream(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				BufferedInputStream t=domain.getInStream(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Nullable
	@Override
	public BufferedReader getReader(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				BufferedReader t=domain.getReader(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Override
	public long getSize(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				long t=domain.getSize(localPath);
				if(t!=-1) return t;
			}catch(Exception e){}
		}
		return -1;
	}
	
	@Nullable
	@Override
	public byte[] readAllBytes(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				byte[] t=domain.getBytes(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Nullable
	@Override
	public char[] readAllChars(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				char[] t=domain.getChars(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Nullable
	@Override
	public String readAll(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				String t=domain.readAll(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Nullable
	@Override
	public List<String> getLines(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				List<String> t=domain.getLines(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Override
	public boolean getLines(@NotNull String localPath, @NotNull Consumer<String> lineConsumer){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				if(domain.getLines(localPath, lineConsumer)) return true;
			}catch(Exception e){}
		}
		return false;
	}
	
	@Override
	public boolean getLines(@NotNull String localPath, @NotNull ObjIntConsumer<String> lineConsumer){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				if(domain.getLines(localPath, lineConsumer)) return true;
			}catch(Exception e){}
		}
		return false;
	}
	
	@Nullable
	@Override
	public String[] getDirNames(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				String[] t=domain.getDirNames(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Override
	public boolean exists(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				if(domain.exists(localPath)) return true;
			}catch(Exception e){}
		}
		return false;
	}
	
	@Nullable
	@Override
	public String[] getDirPaths(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				String[] t=domain.getDirPaths(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Nullable
	@Override
	public String[] getDirPathsDeep(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				String[] t=domain.getDirPathsDeep(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Nullable
	@Override
	public Stream<String> getDirPathsS(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				Stream<String> t=domain.getDirPathsS(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Nullable
	@Override
	public Stream<String> getDirPathsDeepS(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				Stream<String> t=domain.getDirPathsDeepS(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@Override
	public boolean canEditCreate(@NotNull String localPath){
		checkInitialization();
		for(Domain d : domains){
			if(d.canEditCreate(localPath)){
				return true;
			}
		}
		return false;
	}
	
	@NotNull
	@Override
	public BufferedOutputStream makeFile(@NotNull String localPath){
		checkInitialization();
		for(Domain d : domains){
			if(d.canEditCreate(localPath)){
				return d.makeFile(localPath);
			}
		}
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void makeFile(@NotNull String localPath, @NotNull byte[] data){
		checkInitialization();
		for(Domain d : domains){
			if(d.canEditCreate(localPath)){
				d.makeFile(localPath, data);
				return;
			}
		}
		throw new UnsupportedOperationException();
	}
	
	@Nullable
	@Override
	public Stream<String> getDirNamesS(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			try{
				Stream<String> t=domain.getDirNamesS(localPath);
				if(t!=null) return t;
			}catch(Exception e){}
		}
		return null;
	}
	
	@NotNull
	@Override
	public IDataManager subData(@NotNull String localPath){
		checkInitialization();
		return new SubDataManager(this, localPath);
	}
	
	@NotNull
	@Override
	public IDataManager subData(@NotNull String... localPaths){
		checkInitialization();
		return new SubDataManagerOrderedFallback(this, localPaths);
	}
	
	@NotNull
	@Override
	public DataSignature createSignature(@NotNull String localPath){
		return new DataSignature(localPath, this);
	}
	
	@Override
	public long getLastChange(@NotNull String localPath){
		checkInitialization();
		
		for(Domain domain : domains){
			long time=domain.getLastChange(localPath);
			if(time!=-1) return time;
		}
		return -1;
	}
	
	private void checkInitialization(){
		if(domains.isEmpty()) throw new IllegalStateException("Domain can not be used without registering a domain!");
	}
	
	@NotNull
	@Override
	public String toString(){
		if(domains.isEmpty()) return "<NO_INIT>";
		if(domains.size()==1) return domains.get(0).getSignature();
		return "<MULTI_SOURCE>";
	}
}

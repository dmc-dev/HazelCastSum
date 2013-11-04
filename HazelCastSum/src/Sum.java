import java.util.Set;
import java.util.concurrent.Callable;
import java.io.Serializable;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IMap;

public class Sum implements Callable<Integer>, Serializable, HazelcastInstanceAware {
  
	private static final long serialVersionUID = 1L;
	
	private HazelcastInstance hazelcast=null;
	private int sum=0;
	private String mapName=null;
    
	public Sum(String map){
		this.mapName=map;
	}

    public Integer call() {
    	    	
    	IMap<Object, Integer> map = hazelcast.getMap(mapName);

        Set<Object> localKeySet = map.localKeySet();
        for (Object key:localKeySet){
            sum += map.get(key);

        	System.out.println(map.get(key));	
        }
        return sum;
    }

	@Override
	public void setHazelcastInstance(HazelcastInstance hazel) {
		hazelcast=hazel;	
	}

}
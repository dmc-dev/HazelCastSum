import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

public class IntProducer {

	private static String mapName = "ints";
	
    public static void main(String[] args) { 
    	
    	Config cfg = new Config();
    	HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
    	
    	Map<Integer, Integer> map = instance.getMap(mapName);
    	
    	int sz = map.size();
    	
    	for(int i=sz; i<sz+3; i++){
    		System.out.println(i);
    		map.put(i, i);
    	}
    	
    
    	System.out.println("Map Size:" + map.size());
    }     		
    
}
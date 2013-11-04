import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;

public class SumTaskClient {

	private ClientConfig clientConfig = new ClientConfig();
	private HazelcastInstance client = null;
	
	private String mapName = "ints";
	
    public SumTaskClient(){
    	
    	clientConfig.addAddress("127.0.0.1:5701");  
    	client = HazelcastClient.newHazelcastClient(clientConfig); 
    }
    
    public void printSize(){
    	IMap<Object, Object> map = client.getMap(mapName);
    	System.out.println("Map Size:" + map.size());
    }
    
    public void sumTask() throws InterruptedException, ExecutionException {
    	   
    	IExecutorService executorService = client.getExecutorService("default");
    	
    	Map<Member, Future<Integer>> futures = executorService.submitToAllMembers(new Sum(mapName));
    	
    	int total=0;
    	for (Future<Integer> future : futures.values()) {
           
			int partial = future.get();
			total+=partial;
            System.out.println("partial="+partial);
    	}
    	System.out.println("total="+total);
    }


    public static void main(String[] args) { 
        
    	SumTaskClient client = new SumTaskClient();
    	
    	client.printSize();
    	
    	try {
			client.sumTask();
		} catch (InterruptedException | ExecutionException e){
			e.printStackTrace();
		}
    }
    
}
package kademlia.simulations;

import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

import kademlia.DefaultConfiguration;
import kademlia.JKademliaNode;
import kademlia.KadConfiguration;
import kademlia.node.KademliaId;
import kademlia.node.Node;

public class ConnectionTest {
	  public static void main(String[] args) throws Exception
	    {
		   JKademliaNode kad1 = new JKademliaNode("wangyunquan", new KademliaId("ASF45678947584567467"), 7574);
		   kad1.bootstrap(new Node(new KademliaId(),InetAddress.getByName(""),6881));
		   
		   
	        KadConfiguration config = new DefaultConfiguration();
            Timer timer = new Timer(true);
            timer.schedule(
                    new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            System.out.println(kad1);
                        }
                    },
                    // Delay                        // Interval
                    config.restoreInterval(), config.restoreInterval()
            );
        }
	    }

package com.buswe.dht;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.buswe.dht.util.KeyComparator;

 

public class DhtRoutingTable {
	 private final DhtNode localNode;  // The current node
	    private transient DhtBucket[] buckets;

	    private transient DhtConfiguration config;

	    public DhtRoutingTable(DhtNode localNode, DhtConfiguration config)
	    {
	        this.localNode = localNode;
	        this.config = config;

	        /* Initialize all of the buckets to a specific depth */
	        this.initialize();

	        /* Insert the local node */
	        this.insert(localNode);
	    }

	    /**
	     * Initialize the JKademliaRoutingTable to it's default state
	     */
	    public final void initialize()
	    {
	        this.buckets = new DhtBucket[DhtNodeId.ID_LENGTH];
	        for (int i = 0; i < DhtNodeId.ID_LENGTH; i++)
	        {
	            buckets[i] = new DhtBucket(i, this.config);
	        }
	    }

	    public void setConfiguration(DhtConfiguration config)
	    {
	        this.config = config;
	    }

	    /**
	     * Adds a contact to the routing table based on how far it is from the LocalNode.
	     *
	     * @param c The contact to add
	     */
	    public synchronized final void insert(DhtContact c)
	    {
	        this.buckets[this.getBucketId(c.getNode().getNodeId())].insert(c);
	    }

	    /**
	     * Adds a node to the routing table based on how far it is from the LocalNode.
	     *
	     * @param n The node to add
	     */
	    public synchronized final void insert(DhtNode n)
	    {
	        this.buckets[this.getBucketId(n.getNodeId())].insert(n);
	    }

	    /**
	     * Compute the bucket ID in which a given node should be placed; the bucketId is computed based on how far the node is away from the Local Node.
	     *
	     * @param nid The NodeId for which we want to find which bucket it belong to
	     *
	     * @return Integer The bucket ID in which the given node should be placed.
	     */
	    public final int getBucketId(DhtNodeId nid)
	    {
	        int bId = this.localNode.getNodeId().getDistance(nid) - 1;

	        /* If we are trying to insert a node into it's own routing table, then the bucket ID will be -1, so let's just keep it in bucket 0 */
	        return bId < 0 ? 0 : bId;
	    }

	    /**
	     * Find the closest set of contacts to a given NodeId
	     *
	     * @param target           The NodeId to find contacts close to
	     * @param numNodesRequired The number of contacts to find
	     *
	     * @return List A List of contacts closest to target
	     */
	    public synchronized final List<DhtNode> findClosest(DhtNodeId target, int numNodesRequired)
	    {
	        TreeSet<DhtNode> sortedSet = new TreeSet<>(new KeyComparator(target));
	        sortedSet.addAll(this.getAllNodes());

	        List<DhtNode> closest = new ArrayList<>(numNodesRequired);

	        /* Now we have the sorted set, lets get the top numRequired */
	        int count = 0;
	        for (DhtNode n : sortedSet)
	        {
	            closest.add(n);
	            if (++count == numNodesRequired)
	            {
	                break;
	            }
	        }
	        return closest;
	    }

	    /**
	     * @return List A List of all Nodes in this JKademliaRoutingTable
	     */
	    public synchronized final List<DhtNode> getAllNodes()
	    {
	        List<DhtNode> nodes = new ArrayList<>();

	        for (DhtBucket b : this.buckets)
	        {
	            for (DhtContact c : b.getContacts())
	            {
	                nodes.add(c.getNode());
	            }
	        }

	        return nodes;
	    }

	    /**
	     * @return List A List of all Nodes in this JKademliaRoutingTable
	     */
	    public final List<DhtContact> getAllContacts()
	    {
	        List<DhtContact> contacts = new ArrayList<>();

	        for (DhtBucket b : this.buckets)
	        {
	            contacts.addAll(b.getContacts());
	        }

	        return contacts;
	    }

	    /**
	     * @return Bucket[] The buckets in this Kad Instance
	     */
	    public final DhtBucket[] getBuckets()
	    {
	        return this.buckets;
	    }

	    /**
	     * Set the KadBuckets of this routing table, mainly used when retrieving saved state
	     *
	     * @param buckets
	     */
	    public final void setBuckets(DhtBucket[] buckets)
	    {
	        this.buckets = buckets;
	    }

	    /**
	     * Method used by operations to notify the routing table of any contacts that have been unresponsive.
	     *
	     * @param contacts The set of unresponsive contacts
	     */
	    public void setUnresponsiveContacts(List<DhtNode> contacts)
	    {
	        if (contacts.isEmpty())
	        {
	            return;
	        }
	        for (DhtNode n : contacts)
	        {
	            this.setUnresponsiveContact(n);
	        }
	    }

	    /**
	     * Method used by operations to notify the routing table of any contacts that have been unresponsive.
	     *
	     * @param n
	     */
	    public synchronized void setUnresponsiveContact(DhtNode n)
	    {
	        int bucketId = this.getBucketId(n.getNodeId());

	        /* Remove the contact from the bucket */
	        this.buckets[bucketId].removeNode(n);
	    }

	    @Override
	    public synchronized final String toString()
	    {
	        StringBuilder sb = new StringBuilder("\nPrinting Routing Table Started ***************** \n");
	        int totalContacts = 0;
	        for (DhtBucket b : this.buckets)
	        {
	            if (b.numContacts() > 0)
	            {
	                totalContacts += b.numContacts();
	                sb.append("# nodes in Bucket with depth ");
	                sb.append(b.getDepth());
	                sb.append(": ");
	                sb.append(b.numContacts());
	                sb.append("\n");
	                sb.append(b.toString());
	                sb.append("\n");
	            }
	        }

	        sb.append("\nTotal Contacts: ");
	        sb.append(totalContacts);
	        sb.append("\n\n");

	        sb.append("Printing Routing Table Ended ******************** ");

	        return sb.toString();
	    }

}

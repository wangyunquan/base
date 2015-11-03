package com.buswe.dht;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;


public class DhtNode {
	private DhtNodeId nodeId;
    private InetAddress inetAddress;
    private int port;
    private final String strRep;

    public DhtNode(DhtNodeId nid, InetAddress ip, int port)
    {
        this.nodeId = nid;
        this.inetAddress = ip;
        this.port = port;
        this.strRep = this.nodeId.toString();
    }

    /**
     * Load the Node's data from a DataInput stream
     *
     * @param in
     *
     * @throws IOException
     */
    public DhtNode(DataInputStream in) throws IOException
    {
        this.fromStream(in);
        this.strRep = this.nodeId.toString();
    }

    /**
     * Set the InetAddress of this node
     *
     * @param addr The new InetAddress of this node
     */
    public void setInetAddress(InetAddress addr)
    {
        this.inetAddress = addr;
    }

    /**
     * @return The NodeId object of this node
     */
    public DhtNodeId getNodeId()
    {
        return this.nodeId;
    }

    /**
     * Creates a SocketAddress for this node
     *
     * @return
     */
    public InetSocketAddress getSocketAddress()
    {
        return new InetSocketAddress(this.inetAddress, this.port);
    }

    public void toStream(DataOutputStream out) throws IOException
    {
        /* Add the NodeId to the stream */
        this.nodeId.toStream(out);

        /* Add the Node's IP address to the stream */
        byte[] a = inetAddress.getAddress();
        if (a.length != 4)
        {
            throw new RuntimeException("Expected InetAddress of 4 bytes, got " + a.length);
        }
        out.write(a);

        /* Add the port to the stream */
        out.writeInt(port);
    }

    public final void fromStream(DataInputStream in) throws IOException
    {
        /* Load the NodeId */
        this.nodeId = new DhtNodeId(in);

        /* Load the IP Address */
        byte[] ip = new byte[4];
        in.readFully(ip);
        this.inetAddress = InetAddress.getByAddress(ip);

        /* Read in the port */
        this.port = in.readInt();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof DhtNode)
        {
        	DhtNode n = (DhtNode) o;
            if (n == this)
            {
                return true;
            }
            return this.getNodeId().equals(n.getNodeId());
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.getNodeId().hashCode();
    }

    @Override
    public String toString()
    {
        return this.getNodeId().toString();
    }
}

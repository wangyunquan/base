package com.buswe.dht;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.dampcake.bencode.BencodeInputStream;
import com.dampcake.bencode.BencodeOutputStream;

 

public class DhtServer {
	   /* Maximum size of a Datagram Packet */
    private static final int DATAGRAM_BUFFER_SIZE = 64 * 1024;      // 64KB

    /* Basic Kad Objects */
    private final transient DhtConfiguration config;
    /* Server Objects */
    private final DatagramSocket socket;
    private transient boolean isRunning;
    private final DhtNode localNode;
    
    /* Factories */
    private final DhtMessageFactory messageFactory;

    private final DhtStatistician statistician;
    
    {
        isRunning = true;
    }
    
    /**
     * Initialize our KadServer
     *
     * @param udpPort      The port to listen on
     * @param mFactory     Factory used to create messages
     * @param localNode    Local node on which this server runs on
     * @param config
     * @param statistician A statistician to manage the server statistics
     *
     * @throws java.net.SocketException
     */
    public DhtServer(int udpPort, DhtMessageFactory mFactory, DhtNode localNode, DhtConfiguration config, DhtStatistician statistician) throws SocketException
    {
        this.config = config;
        this.socket = new DatagramSocket(udpPort);
        this.localNode = localNode;
        this.messageFactory = mFactory;
        this.statistician = statistician;
        /* Start listening for incoming requests in a new thread */
      this.startListener();
    }
    
    /**
     * Starts the listener to listen for incoming messages
     */
    private void startListener()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                listen();
            }
        }.start();
    }

    /**
     * Internal sendMessage method called by the public sendMessage method after a communicationId is generated
     */
    public void sendMessage(DhtNode to, DhtMessage msg) throws Exception
    {
        /* Use a try-with resource to auto-close streams after usage */
        try  
        {
        	ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
            BencodeOutputStream bencoder = new BencodeOutputStream(bout);
            bencoder.writeDictionary(msg.getMessageContent());;
            bencoder.close();

            byte[] data = bout.toByteArray();

            if (data.length > DATAGRAM_BUFFER_SIZE)
            {
                throw new IOException("Message is too big");
            }
            /* Everything is good, now create the packet and send it */
            DatagramPacket pkt = new DatagramPacket(data, 0, data.length);
            pkt.setSocketAddress(to.getSocketAddress());
            socket.send(pkt);
            /* Lets inform the statistician that we've sent some data */
            this.statistician.sentData(data.length);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
    }
    /**
     * Listen for incoming messages in a separate thread
     */
    private void listen()
    {
        try
        {
            while (isRunning)
            {
                try
                {
                    /* Wait for a packet */
                    byte[] buffer = new byte[DATAGRAM_BUFFER_SIZE];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    /* Lets inform the statistician that we've received some data */
                    this.statistician.receivedData(packet.getLength());

                    if (this.config.isTesting())
                    {
                        /**
                         * Simulating network latency
                         * We pause for 1 millisecond/100 bytes
                         */
                        int pause = packet.getLength() / 100;
                        try
                        {
                            Thread.sleep(pause);
                        }
                        catch (InterruptedException ex)
                        {
                        	ex.printStackTrace();
                        }
                    }

                    /* We've received a packet, now handle it */
                    try (ByteArrayInputStream bin = new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength());
                    		BencodeInputStream din = new BencodeInputStream(bin);)
                    {
                    	
                        /* Read in the conversation Id to know which handler to handle this response */
//                        int comm = din.readInt();
//                        byte messCode = din.readByte();
//
//                        Message msg = messageFactory.createMessage(messCode, din);
//                        din.close();
                    	DhtMessage msg=messageFactory.createMessage(din);
                    	
                    	DhtMessageType comm=msg.getMessageType();
                        /* Get a receiver for this message */
                        DhtReceiver receiver = messageFactory.createReceiver(comm, this);
                        /* Invoke the receiver */
                        if (receiver != null)
                        {
                            receiver.receive(msg, comm);
                            //TODO 阻塞线程
                        }
                    }
                }
                catch (Exception e)
                {
                    this.isRunning = false;
                    System.err.println("Server ran into a problem in listener method. Message: " + e.getMessage());
                }
            }
        }
        finally
        {
            if (!socket.isClosed())
            {
                socket.close();
            }
            this.isRunning = false;
        }
    }
     
}

package com.buswe.dht.util;

import java.math.BigInteger;
import java.util.Comparator;

import com.buswe.dht.DhtNode;
import com.buswe.dht.DhtNodeId;

/**
 * A Comparator to compare 2 keys to a given key
 *
 * @author Joshua Kissoon
 * @since 20140322
 */
public class KeyComparator implements Comparator<DhtNode>
{

    private final BigInteger key;

    /**
     * @param key The NodeId relative to which the distance should be measured.
     */
    public KeyComparator(DhtNodeId key)
    {
        this.key = key.getInt();
    }

    /**
     * Compare two objects which must both be of type <code>Node</code>
     * and determine which is closest to the identifier specified in the
     * constructor.
     *
     * @param n1 Node 1 to compare distance from the key
     * @param n2 Node 2 to compare distance from the key
     */
    @Override
    public int compare(DhtNode n1, DhtNode n2)
    {
        BigInteger b1 = n1.getNodeId().getInt();
        BigInteger b2 = n2.getNodeId().getInt();

        b1 = b1.xor(key);
        b2 = b2.xor(key);

        return b1.abs().compareTo(b2.abs());
    }
}

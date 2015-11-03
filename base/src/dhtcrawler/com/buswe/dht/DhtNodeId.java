package com.buswe.dht;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;


public class DhtNodeId {
	public final transient static int ID_LENGTH = 160;
    private byte[] keyBytes;

    /**
     * Construct the DhtNodeId from some string
     *
     * @param data The user generated key string
     */
    public DhtNodeId(String data)
    {
        keyBytes = data.getBytes();
        if (keyBytes.length != ID_LENGTH / 8)
        {
            throw new IllegalArgumentException("Specified Data need to be " + (ID_LENGTH / 8) + " characters long.");
        }
    }

    /**
     * Generate a random key
     */
    public DhtNodeId()
    {
        keyBytes = new byte[ID_LENGTH / 8];
        new Random().nextBytes(keyBytes);
    }

    /**
     * Generate the DhtNodeId from a given byte[]
     *
     * @param bytes
     */
    public DhtNodeId(byte[] bytes)
    {
        if (bytes.length != ID_LENGTH / 8)
        {
            throw new IllegalArgumentException("Specified Data need to be " + (ID_LENGTH / 8) + " characters long. Data Given: '" + new String(bytes) + "'");
        }
        this.keyBytes = bytes;
    }

    /**
     * Load the DhtNodeId from a DataInput stream
     *
     * @param in The stream from which to load the DhtNodeId
     *
     * @throws IOException
     */
    public DhtNodeId(DataInputStream in) throws IOException
    {
        this.fromStream(in);
    }

    public byte[] getBytes()
    {
        return this.keyBytes;
    }

    /**
     * @return The BigInteger representation of the key
     */
    public BigInteger getInt()
    {
        return new BigInteger(1, this.getBytes());
    }

    /**
     * Compares a DhtNodeId to this DhtNodeId
     *
     * @param o The DhtNodeId to compare to this DhtNodeId
     *
     * @return boolean Whether the 2 DhtNodeIds are equal
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof DhtNodeId)
        {
        	DhtNodeId nid = (DhtNodeId) o;
            return this.hashCode() == nid.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 83 * hash + Arrays.hashCode(this.keyBytes);
        return hash;
    }

    /**
     * Checks the distance between this and another DhtNodeId
     *
     * @param nid
     *
     * @return The distance of this DhtNodeId from the given DhtNodeId
     */
    public DhtNodeId xor(DhtNodeId nid)
    {
        byte[] result = new byte[ID_LENGTH / 8];
        byte[] nidBytes = nid.getBytes();

        for (int i = 0; i < ID_LENGTH / 8; i++)
        {
            result[i] = (byte) (this.keyBytes[i] ^ nidBytes[i]);
        }

        DhtNodeId resNid = new DhtNodeId(result);

        return resNid;
    }

    /**
     * Generates a DhtNodeId that is some distance away from this DhtNodeId
     *
     * @param distance in number of bits
     *
     * @return DhtNodeId The newly generated DhtNodeId
     */
    public DhtNodeId generateDhtNodeIdByDistance(int distance)
    {
        byte[] result = new byte[ID_LENGTH / 8];

        /* Since distance = ID_LENGTH - prefixLength, we need to fill that amount with 0's */
        int numByteZeroes = (ID_LENGTH - distance) / 8;
        int numBitZeroes = 8 - (distance % 8);

        /* Filling byte zeroes */
        for (int i = 0; i < numByteZeroes; i++)
        {
            result[i] = 0;
        }

        /* Filling bit zeroes */
        BitSet bits = new BitSet(8);
        bits.set(0, 8);

        for (int i = 0; i < numBitZeroes; i++)
        {
            /* Shift 1 zero into the start of the value */
            bits.clear(i);
        }
        bits.flip(0, 8);        // Flip the bits since they're in reverse order
        result[numByteZeroes] = (byte) bits.toByteArray()[0];

        /* Set the remaining bytes to Maximum value */
        for (int i = numByteZeroes + 1; i < result.length; i++)
        {
            result[i] = Byte.MAX_VALUE;
        }

        return this.xor(new DhtNodeId(result));
    }

    /**
     * Counts the number of leading 0's in this DhtNodeId
     *
     * @return Integer The number of leading 0's
     */
    public int getFirstSetBitIndex()
    {
        int prefixLength = 0;

        for (byte b : this.keyBytes)
        {
            if (b == 0)
            {
                prefixLength += 8;
            }
            else
            {
                /* If the byte is not 0, we need to count how many MSBs are 0 */
                int count = 0;
                for (int i = 7; i >= 0; i--)
                {
                    boolean a = (b & (1 << i)) == 0;
                    if (a)
                    {
                        count++;
                    }
                    else
                    {
                        break;   // Reset the count if we encounter a non-zero number
                    }
                }

                /* Add the count of MSB 0s to the prefix length */
                prefixLength += count;

                /* Break here since we've now covered the MSB 0s */
                break;
            }
        }
        return prefixLength;
    }

    /**
     * Gets the distance from this DhtNodeId to another DhtNodeId
     *
     * @param to
     *
     * @return Integer The distance
     */
    public int getDistance(DhtNodeId to)
    {
        /**
         * Compute the xor of this and to
         * Get the index i of the first set bit of the xor returned DhtNodeId
         * The distance between them is ID_LENGTH - i
         */
        return ID_LENGTH - this.xor(to).getFirstSetBitIndex();
    }

    public void toStream(DataOutputStream out) throws IOException
    {
        /* Add the DhtNodeId to the stream */
        out.write(this.getBytes());
    }

    public final void fromStream(DataInputStream in) throws IOException
    {
        byte[] input = new byte[ID_LENGTH / 8];
        in.readFully(input);
        this.keyBytes = input;
    }

    public String hexRepresentation()
    {
        /* Returns the hex format of this DhtNodeId */
        BigInteger bi = new BigInteger(1, this.keyBytes);
        return String.format("%0" + (this.keyBytes.length << 1) + "X", bi);
    }

    @Override
    public String toString()
    {
        return this.hexRepresentation();
    }
}

package com.konka.dhtsearch;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;

import com.konka.dhtsearch.util.Util;

/**
 * Identifier for nodes. Use {@link KeyFactory} to generate instances of this class.
 * 
 */
public class Key implements Serializable, Comparable<Key> {

	private static final long serialVersionUID = 4137662182397711129L;
	private final byte[] bytes;
	private   String keyid="ddd";

	public Key(final byte[] bytes, String keyid) {
		this.bytes = bytes;
//		this.keyid = keyid;
	}
	public Key(final byte[] bytes ) {
		this.bytes = bytes;
	}

//	public String getKeyid() {
////		return keyid;
//	}
	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}
	/**
	 * Check if a key is 0 key
	 * 
	 * @return true if bytes of this key are 0
	 */
	public boolean isZeroKey() {
		for (final byte x : getBytes())
			if (x != 0)
				return false;
		return true;
	}

	/**
	 * 
	 * @return all the key's bytes
	 */
	public byte[] getBytes() {
		return this.bytes;
	}

	/**
	 * 
	 * @return length of key in bytes
	 */
	public int getByteLength() {
		if(getBytes()==null){
			return 160;
		}
		return getBytes().length;
	}

	/**
	 * 
	 * @param k
	 *            another key
	 * @return 返回异或后的新key
	 */
	public Key xor (final Key k) {
		if (k.getByteLength() != getByteLength()){
			System.out.println("k.getByteLength()=" + k.getByteLength());  //20
			System.out.println(" getByteLength()=" + getByteLength());			//38
			throw new IllegalArgumentException("incompatable key for xor");
		}
		final byte[] b = new byte[getByteLength()];
		for (int i = 0; i < b.length; ++i)
			b[i] = (byte) (getBytes()[i] ^ k.getBytes()[i]);
		return new Key(b);
	}

	/**
	 * @return key的位置，key的头（二进制）的位置（1） //0000 0100 //0000 0010+ 4050515253545556574849505152535455565748 结果157 最大是159
	 */
	public int getFirstSetBitIndex() {
		for (int i = 0; i < getByteLength(); ++i) {
			if (getBytes()[i] == 0)
				continue;

			int j;
			for (j = 7; (getBytes()[i] & (1 << j)) == 0; --j)
				;
			return (getByteLength() - i - 1) * 8 + j;
		}
		return -1;
	}

	/**
	 * @return length of key in bits
	 */
	public int getBitLength() {
		return getByteLength() * 8;
	}

	/**
	 * @return the key BigInteger representation
	 */
	public BigInteger getInt() {
		return new BigInteger(1, getBytes()); // TODO: yoav is getBytes()
												// two-complement?
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null || !getClass().equals(o.getClass()))
			return false;
		return Arrays.equals(getBytes(), ((Key) o).getBytes());
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(getBytes());
	}


	@Override
	public String toString() {
		return Util.hex(getBytes());
	}
	/**
	 * 
	 * @return the key encoded in binary string
	 */
	public String toBinaryString() {
		String $ = "";
		for (int i = 0; i < getByteLength(); ++i) {
			byte b = getBytes()[i];
			// fix negative numbers
			$ += b < 0 ? "1" : "0";
			b &= 0x7F;

			// fix insufficient leading 0s
			final String str = Integer.toBinaryString(b);
			switch (str.length()) {
				case 1:
					$ += "000000";
					break;
				case 2:
					$ += "00000";
					break;
				case 3:
					$ += "0000";
					break;
				case 4:
					$ += "000";
					break;
				case 5:
					$ += "00";
					break;
				case 6:
					$ += "0";
					break;
			}
			$ += str + " ";
		}
		return $;
	}

	@Override
	public int compareTo(final Key arg0) {
		return toString().compareTo(arg0.toString());
	}
}

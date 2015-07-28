package com.buswe.base.utils.image;

import java.io.DataInput;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

public class ImageInfo
{
  public static final int FORMAT_JPEG = 0;
  public static final int FORMAT_GIF = 1;
  public static final int FORMAT_PNG = 2;
  public static final int FORMAT_BMP = 3;
  public static final int FORMAT_PCX = 4;
  public static final int FORMAT_IFF = 5;
  public static final int FORMAT_RAS = 6;
  public static final int FORMAT_PBM = 7;
  public static final int FORMAT_PGM = 8;
  public static final int FORMAT_PPM = 9;
  public static final int FORMAT_PSD = 10;
  private static final String[] FORMAT_NAMES = { "JPEG", "GIF", "PNG", "BMP", 
    "PCX", "IFF", "RAS", "PBM", "PGM", "PPM", "PSD" };
  private static final String[] MIME_TYPE_STRINGS = { "image/jpeg", 
    "image/gif", "image/png", "image/bmp", "image/pcx", "image/iff", 
    "image/ras", "image/x-portable-bitmap", "image/x-portable-graymap", 
    "image/x-portable-pixmap", "image/psd" };
  private int width;
  private int height;
  private int bitsPerPixel;
  private boolean progressive;
  private int format;
  private InputStream in;
  private DataInput din;
  private boolean collectComments = true;
  private Vector comments;
  private boolean determineNumberOfImages;
  private int numberOfImages;
  private int physicalHeightDpi;
  private int physicalWidthDpi;
  
  private void addComment(String s)
  {
    if (this.comments == null) {
      this.comments = new Vector();
    }
    this.comments.addElement(s);
  }
  
  public boolean check()
  {
    this.format = -1;
    this.width = -1;
    this.height = -1;
    this.bitsPerPixel = -1;
    this.numberOfImages = 1;
    this.physicalHeightDpi = -1;
    this.physicalWidthDpi = -1;
    this.comments = null;
    try
    {
      int b1 = read() & 0xFF;
      int b2 = read() & 0xFF;
      if ((b1 == 71) && (b2 == 73)) {
        return checkGif();
      }
      if ((b1 == 137) && (b2 == 80)) {
        return checkPng();
      }
      if ((b1 == 255) && (b2 == 216)) {
        return checkJpeg();
      }
      if ((b1 == 66) && (b2 == 77)) {
        return checkBmp();
      }
      if ((b1 == 10) && (b2 < 6)) {
        return checkPcx();
      }
      if ((b1 == 70) && (b2 == 79)) {
        return checkIff();
      }
      if ((b1 == 89) && (b2 == 166)) {
        return checkRas();
      }
      if ((b1 == 80) && (b2 >= 49) && (b2 <= 54)) {
        return checkPnm(b2 - 48);
      }
      if ((b1 == 56) && (b2 == 66)) {
        return checkPsd();
      }
      return false;
    }
    catch (IOException ioe) {}
    return false;
  }
  
  private boolean checkBmp()
    throws IOException
  {
    byte[] a = new byte[44];
    if (read(a) != a.length) {
      return false;
    }
    this.width = getIntLittleEndian(a, 16);
    this.height = getIntLittleEndian(a, 20);
    if ((this.width < 1) || (this.height < 1)) {
      return false;
    }
    this.bitsPerPixel = getShortLittleEndian(a, 26);
    if ((this.bitsPerPixel != 1) && (this.bitsPerPixel != 4) && (this.bitsPerPixel != 8) && 
      (this.bitsPerPixel != 16) && (this.bitsPerPixel != 24) && 
      (this.bitsPerPixel != 32)) {
      return false;
    }
    int x = (int)(getIntLittleEndian(a, 36) * 0.0254D);
    if (x > 0) {
      setPhysicalWidthDpi(x);
    }
    int y = (int)(getIntLittleEndian(a, 40) * 0.0254D);
    if (y > 0) {
      setPhysicalHeightDpi(y);
    }
    this.format = 3;
    return true;
  }
  
  private boolean checkGif()
    throws IOException
  {
    byte[] GIF_MAGIC_87A = { 70, 56, 55, 97 };
    byte[] GIF_MAGIC_89A = { 70, 56, 57, 97 };
    byte[] a = new byte[11];
    if (read(a) != 11) {
      return false;
    }
    if ((!equals(a, 0, GIF_MAGIC_89A, 0, 4)) && 
      (!equals(a, 0, GIF_MAGIC_87A, 0, 4))) {
      return false;
    }
    this.format = 1;
    this.width = getShortLittleEndian(a, 4);
    this.height = getShortLittleEndian(a, 6);
    int flags = a[8] & 0xFF;
    this.bitsPerPixel = ((flags >> 4 & 0x7) + 1);
    if (!this.determineNumberOfImages) {
      return true;
    }
    if ((flags & 0x80) != 0)
    {
      int tableSize = (1 << (flags & 0x7) + 1) * 3;
      skip(tableSize);
    }
    this.numberOfImages = 0;
    int blockType;
    do
    {
      blockType = read();
      switch (blockType)
      {
      case 44: 
        if (read(a, 0, 9) != 9) {
          return false;
        }
        flags = a[8] & 0xFF;
        this.progressive = ((flags & 0x40) != 0);
        




        int localBitsPerPixel = (flags & 0x7) + 1;
        if (localBitsPerPixel > this.bitsPerPixel) {
          this.bitsPerPixel = localBitsPerPixel;
        }
        if ((flags & 0x80) != 0) {
          skip((1 << localBitsPerPixel) * 3);
        }
        skip(1);
        int n;
        do
        {
          n = read();
          if (n > 0) {
            skip(n);
          } else if (n == -1) {
            return false;
          }
        } while (
        





          n > 0);
        this.numberOfImages += 1;
        break;
      case 33: 
        int extensionType = read();
        if ((this.collectComments) && (extensionType == 254))
        {
          StringBuffer sb = new StringBuffer();
          do
          {
            n = read();
            if (n == -1) {
              return false;
            }
            if (n > 0) {
              for (int i = 0; i < n; i++)
              {
                int ch = read();
                if (ch == -1) {
                  return false;
                }
                sb.append((char)ch);
              }
            }
          } while (
          












            n > 0);
        }
        else
        {
          do
          {
            n = read();
            if (n > 0) {
              skip(n);
            } else if (n == -1) {
              return false;
            }
          } while (
          





            n > 0);
        }
        break;
      case 59: 
        break;
      default: 
        return false;
      }
    } while (blockType != 59);
    return true;
  }
  
  private boolean checkIff()
    throws IOException
  {
    byte[] a = new byte[10];
    if (read(a, 0, 10) != 10) {
      return false;
    }
    byte[] IFF_RM = { 82, 77 };
    if (!equals(a, 0, IFF_RM, 0, 2)) {
      return false;
    }
    int type = getIntBigEndian(a, 6);
    if ((type != 1229734477) && 
      (type != 1346522400)) {
      return false;
    }
    for (;;)
    {
      if (read(a, 0, 8) != 8) {
        return false;
      }
      int chunkId = getIntBigEndian(a, 0);
      int size = getIntBigEndian(a, 4);
      if ((size & 0x1) == 1) {
        size++;
      }
      if (chunkId == 1112361028)
      {
        if (read(a, 0, 9) != 9) {
          return false;
        }
        this.format = 5;
        this.width = getShortBigEndian(a, 0);
        this.height = getShortBigEndian(a, 2);
        this.bitsPerPixel = (a[8] & 0xFF);
        return (this.width > 0) && (this.height > 0) && (this.bitsPerPixel > 0) && (this.bitsPerPixel < 33);
      }
      skip(size);
    }
  }
  
  private boolean checkJpeg()
    throws IOException
  {
    byte[] data = new byte[12];
    for (;;)
    {
      if (read(data, 0, 4) != 4) {
        return false;
      }
      int marker = getShortBigEndian(data, 0);
      int size = getShortBigEndian(data, 2);
      if ((marker & 0xFF00) != 65280) {
        return false;
      }
      if (marker == 65504)
      {
        if (size < 14)
        {
          skip(size - 2);
        }
        else
        {
          if (read(data, 0, 12) != 12) {
            return false;
          }
          byte[] APP0_ID = { 74, 70, 73, 70 };
          if (equals(APP0_ID, 0, data, 0, 5)) {
            if (data[7] == 1)
            {
              setPhysicalWidthDpi(getShortBigEndian(data, 8));
              setPhysicalHeightDpi(getShortBigEndian(data, 10));
            }
            else if (data[7] == 2)
            {
              int x = getShortBigEndian(data, 8);
              int y = getShortBigEndian(data, 10);
              setPhysicalWidthDpi((int)(x * 2.54F));
              setPhysicalHeightDpi((int)(y * 2.54F));
            }
          }
          skip(size - 14);
        }
      }
      else if ((this.collectComments) && (size > 2) && (marker == 65534))
      {
        size -= 2;
        byte[] chars = new byte[size];
        if (read(chars, 0, size) != size) {
          return false;
        }
        String comment = new String(chars, "iso-8859-1");
        comment = comment.trim();
        addComment(comment);
      }
      else
      {
        if ((marker >= 65472) && (marker <= 65487) && (marker != 65476) && 
          (marker != 65480))
        {
          if (read(data, 0, 6) != 6) {
            return false;
          }
          this.format = 0;
          this.bitsPerPixel = ((data[0] & 0xFF) * (data[5] & 0xFF));
          this.progressive = ((marker == 65474) || (marker == 65478) || 
            (marker == 65482) || (marker == 65486));
          this.width = getShortBigEndian(data, 3);
          this.height = getShortBigEndian(data, 1);
          return true;
        }
        skip(size - 2);
      }
    }
  }
  
  private boolean checkPcx()
    throws IOException
  {
    byte[] a = new byte[64];
    if (read(a) != a.length) {
      return false;
    }
    if (a[0] != 1) {
      return false;
    }
    int x1 = getShortLittleEndian(a, 2);
    int y1 = getShortLittleEndian(a, 4);
    int x2 = getShortLittleEndian(a, 6);
    int y2 = getShortLittleEndian(a, 8);
    if ((x1 < 0) || (x2 < x1) || (y1 < 0) || (y2 < y1)) {
      return false;
    }
    this.width = (x2 - x1 + 1);
    this.height = (y2 - y1 + 1);
    
    int bits = a[1];
    int planes = a[63];
    if ((planes == 1) && ((bits == 1) || (bits == 2) || (bits == 4) || (bits == 8))) {
      this.bitsPerPixel = bits;
    } else if ((planes == 3) && (bits == 8)) {
      this.bitsPerPixel = 24;
    } else {
      return false;
    }
    setPhysicalWidthDpi(getShortLittleEndian(a, 10));
    setPhysicalHeightDpi(getShortLittleEndian(a, 10));
    this.format = 4;
    return true;
  }
  
  private boolean checkPng()
    throws IOException
  {
    byte[] PNG_MAGIC = { 78, 71, 13, 10, 26, 10 };
    byte[] a = new byte[27];
    if (read(a) != 27) {
      return false;
    }
    if (!equals(a, 0, PNG_MAGIC, 0, 6)) {
      return false;
    }
    this.format = 2;
    this.width = getIntBigEndian(a, 14);
    this.height = getIntBigEndian(a, 18);
    this.bitsPerPixel = (a[22] & 0xFF);
    int colorType = a[23] & 0xFF;
    if ((colorType == 2) || (colorType == 6)) {
      this.bitsPerPixel *= 3;
    }
    this.progressive = ((a[26] & 0xFF) != 0);
    return true;
  }
  
  private boolean checkPnm(int id)
    throws IOException
  {
    if ((id < 1) || (id > 6)) {
      return false;
    }
    int[] PNM_FORMATS = { 7, 8, 9 };
    this.format = PNM_FORMATS[((id - 1) % 3)];
    boolean hasPixelResolution = false;
    String s;
    for (;;)
    {
      s = readLine();
      if (s != null) {
        s = s.trim();
      }
      if ((s != null) && (s.length() >= 1)) {
        if (s.charAt(0) == '#')
        {
          if ((this.collectComments) && (s.length() > 1)) {
            addComment(s.substring(1));
          }
        }
        else
        {
          if (hasPixelResolution) {
            break;
          }
          int spaceIndex = s.indexOf(' ');
          if (spaceIndex == -1) {
            return false;
          }
          String widthString = s.substring(0, spaceIndex);
          spaceIndex = s.lastIndexOf(' ');
          if (spaceIndex == -1) {
            return false;
          }
          String heightString = s.substring(spaceIndex + 1);
          try
          {
            this.width = Integer.parseInt(widthString);
            this.height = Integer.parseInt(heightString);
          }
          catch (NumberFormatException nfe)
          {
            return false;
          }
          if ((this.width < 1) || (this.height < 1)) {
            return false;
          }
          if (this.format == 7)
          {
            this.bitsPerPixel = 1;
            return true;
          }
          hasPixelResolution = true;
        }
      }
    }
    try
    {
      maxSample = Integer.parseInt(s);
    }
    catch (NumberFormatException nfe)
    {
      int maxSample;
      return false;
    }
    int maxSample;
    if (maxSample < 0) {
      return false;
    }
    for (int i = 0; i < 25; i++) {
      if (maxSample < 1 << i + 1)
      {
        this.bitsPerPixel = (i + 1);
        if (this.format == 9) {
          this.bitsPerPixel *= 3;
        }
        return true;
      }
    }
    return false;
  }
  
  private boolean checkPsd()
    throws IOException
  {
    byte[] a = new byte[24];
    if (read(a) != a.length) {
      return false;
    }
    byte[] PSD_MAGIC = { 80, 83 };
    if (!equals(a, 0, PSD_MAGIC, 0, 2)) {
      return false;
    }
    this.format = 10;
    this.width = getIntBigEndian(a, 16);
    this.height = getIntBigEndian(a, 12);
    int channels = getShortBigEndian(a, 10);
    int depth = getShortBigEndian(a, 20);
    this.bitsPerPixel = (channels * depth);
    return (this.width > 0) && (this.height > 0) && (this.bitsPerPixel > 0) && (this.bitsPerPixel <= 64);
  }
  
  private boolean checkRas()
    throws IOException
  {
    byte[] a = new byte[14];
    if (read(a) != a.length) {
      return false;
    }
    byte[] RAS_MAGIC = { 106, -107 };
    if (!equals(a, 0, RAS_MAGIC, 0, 2)) {
      return false;
    }
    this.format = 6;
    this.width = getIntBigEndian(a, 2);
    this.height = getIntBigEndian(a, 6);
    this.bitsPerPixel = getIntBigEndian(a, 10);
    return (this.width > 0) && (this.height > 0) && (this.bitsPerPixel > 0) && (this.bitsPerPixel <= 24);
  }
  
  private static boolean determineVerbosity(String[] args)
  {
    if ((args != null) && (args.length > 0)) {
      for (int i = 0; i < args.length; i++) {
        if ("-c".equals(args[i])) {
          return false;
        }
      }
    }
    return true;
  }
  
  private static boolean equals(byte[] a1, int offs1, byte[] a2, int offs2, int num)
  {
    while (num-- > 0) {
      if (a1[(offs1++)] != a2[(offs2++)]) {
        return false;
      }
    }
    return true;
  }
  
  public int getBitsPerPixel()
  {
    return this.bitsPerPixel;
  }
  
  public String getComment(int index)
  {
    if ((this.comments == null) || (index < 0) || (index >= this.comments.size())) {
      throw new IllegalArgumentException("Not a valid comment index: " + 
        index);
    }
    return (String)this.comments.elementAt(index);
  }
  
  public int getFormat()
  {
    return this.format;
  }
  
  public String getFormatName()
  {
    if ((this.format >= 0) && (this.format < FORMAT_NAMES.length)) {
      return FORMAT_NAMES[this.format];
    }
    return "?";
  }
  
  public int getHeight()
  {
    return this.height;
  }
  
  private static int getIntBigEndian(byte[] a, int offs)
  {
    return (a[offs] & 0xFF) << 24 | (a[(offs + 1)] & 0xFF) << 16 | 
      (a[(offs + 2)] & 0xFF) << 8 | a[(offs + 3)] & 0xFF;
  }
  
  private static int getIntLittleEndian(byte[] a, int offs)
  {
    return (a[(offs + 3)] & 0xFF) << 24 | (a[(offs + 2)] & 0xFF) << 16 | 
      (a[(offs + 1)] & 0xFF) << 8 | a[offs] & 0xFF;
  }
  
  public String getMimeType()
  {
    if ((this.format >= 0) && (this.format < MIME_TYPE_STRINGS.length))
    {
      if ((this.format == 0) && (this.progressive)) {
        return "image/pjpeg";
      }
      return MIME_TYPE_STRINGS[this.format];
    }
    return null;
  }
  
  public int getNumberOfComments()
  {
    if (this.comments == null) {
      return 0;
    }
    return this.comments.size();
  }
  
  public int getNumberOfImages()
  {
    return this.numberOfImages;
  }
  
  public int getPhysicalHeightDpi()
  {
    return this.physicalHeightDpi;
  }
  
  public float getPhysicalHeightInch()
  {
    int h = getHeight();
    int ph = getPhysicalHeightDpi();
    if ((h > 0) && (ph > 0)) {
      return h / ph;
    }
    return -1.0F;
  }
  
  public int getPhysicalWidthDpi()
  {
    return this.physicalWidthDpi;
  }
  
  public float getPhysicalWidthInch()
  {
    int w = getWidth();
    int pw = getPhysicalWidthDpi();
    if ((w > 0) && (pw > 0)) {
      return w / pw;
    }
    return -1.0F;
  }
  
  private static int getShortBigEndian(byte[] a, int offs)
  {
    return (a[offs] & 0xFF) << 8 | a[(offs + 1)] & 0xFF;
  }
  
  private static int getShortLittleEndian(byte[] a, int offs)
  {
    return a[offs] & 0xFF | (a[(offs + 1)] & 0xFF) << 8;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public boolean isProgressive()
  {
    return this.progressive;
  }
  
  public static void main(String[] args)
  {
    ImageInfo imageInfo = new ImageInfo();
    imageInfo.setDetermineImageNumber(true);
    boolean verbose = determineVerbosity(args);
    if (args.length == 0)
    {
      run(null, System.in, imageInfo, verbose);
    }
    else
    {
      int index = 0;
      while (index < args.length)
      {
        InputStream in = null;
        try
        {
          String name = args[(index++)];
          System.out.print(name + ";");
          if (name.startsWith("http://")) {
            in = new URL(name).openConnection().getInputStream();
          } else {
            in = new FileInputStream(name);
          }
          run(name, in, imageInfo, verbose);
          in.close();
        }
        catch (IOException e)
        {
          System.out.println(e);
          try
          {
            if (in != null) {
              in.close();
            }
          }
          catch (IOException localIOException1) {}
        }
      }
    }
  }
  
  private static void print(String sourceName, ImageInfo ii, boolean verbose)
  {
    if (verbose) {
      printVerbose(sourceName, ii);
    } else {
      printCompact(sourceName, ii);
    }
  }
  
  private static void printCompact(String sourceName, ImageInfo imageInfo)
  {
    String SEP = "\t";
    System.out.println(sourceName + "\t" + imageInfo.getFormatName() + "\t" + 
      imageInfo.getMimeType() + "\t" + imageInfo.getWidth() + "\t" + 
      imageInfo.getHeight() + "\t" + imageInfo.getBitsPerPixel() + 
      "\t" + imageInfo.getNumberOfImages() + "\t" + 
      imageInfo.getPhysicalWidthDpi() + "\t" + 
      imageInfo.getPhysicalHeightDpi() + "\t" + 
      imageInfo.getPhysicalWidthInch() + "\t" + 
      imageInfo.getPhysicalHeightInch() + "\t" + 
      imageInfo.isProgressive());
  }
  
  private static void printLine(int indentLevels, String text, float value, float minValidValue)
  {
    if (value < minValidValue) {
      return;
    }
    printLine(indentLevels, text, Float.toString(value));
  }
  
  private static void printLine(int indentLevels, String text, int value, int minValidValue)
  {
    if (value >= minValidValue) {
      printLine(indentLevels, text, Integer.toString(value));
    }
  }
  
  private static void printLine(int indentLevels, String text, String value)
  {
    if ((value == null) || (value.length() == 0)) {
      return;
    }
    while (indentLevels-- > 0) {
      System.out.print("\t");
    }
    if ((text != null) && (text.length() > 0))
    {
      System.out.print(text);
      System.out.print(" ");
    }
    System.out.println(value);
  }
  
  private static void printVerbose(String sourceName, ImageInfo ii)
  {
    printLine(0, null, sourceName);
    printLine(1, "File format: ", ii.getFormatName());
    printLine(1, "MIME type: ", ii.getMimeType());
    printLine(1, "Width (pixels): ", ii.getWidth(), 1);
    printLine(1, "Height (pixels): ", ii.getHeight(), 1);
    printLine(1, "Bits per pixel: ", ii.getBitsPerPixel(), 1);
    printLine(1, "Progressive: ", ii.isProgressive() ? "yes" : "no");
    printLine(1, "Number of images: ", ii.getNumberOfImages(), 1);
    printLine(1, "Physical width (dpi): ", ii.getPhysicalWidthDpi(), 1);
    printLine(1, "Physical height (dpi): ", ii.getPhysicalHeightDpi(), 1);
    printLine(1, "Physical width (inches): ", ii.getPhysicalWidthInch(), 
      1.0F);
    printLine(1, "Physical height (inches): ", ii.getPhysicalHeightInch(), 
      1.0F);
    int numComments = ii.getNumberOfComments();
    printLine(1, "Number of textual comments: ", numComments, 1);
    if (numComments > 0) {
      for (int i = 0; i < numComments; i++) {
        printLine(2, null, ii.getComment(i));
      }
    }
  }
  
  private int read()
    throws IOException
  {
    if (this.in != null) {
      return this.in.read();
    }
    return this.din.readByte();
  }
  
  private int read(byte[] a)
    throws IOException
  {
    if (this.in != null) {
      return this.in.read(a);
    }
    this.din.readFully(a);
    return a.length;
  }
  
  private int read(byte[] a, int offset, int num)
    throws IOException
  {
    if (this.in != null) {
      return this.in.read(a, offset, num);
    }
    this.din.readFully(a, offset, num);
    return num;
  }
  
  private String readLine()
    throws IOException
  {
    return readLine(new StringBuffer());
  }
  
  private String readLine(StringBuffer sb)
    throws IOException
  {
    boolean finished;
    do
    {
      int value = read();
      finished = (value == -1) || (value == 10);
      if (!finished) {
        sb.append((char)value);
      }
    } while (!finished);
    return sb.toString();
  }
  
  private static void run(String sourceName, InputStream in, ImageInfo imageInfo, boolean verbose)
  {
    imageInfo.setInput(in);
    imageInfo.setDetermineImageNumber(true);
    imageInfo.setCollectComments(verbose);
    if (imageInfo.check()) {
      print(sourceName, imageInfo, verbose);
    }
  }
  
  public void setCollectComments(boolean newValue)
  {
    this.collectComments = newValue;
  }
  
  public void setDetermineImageNumber(boolean newValue)
  {
    this.determineNumberOfImages = newValue;
  }
  
  public void setInput(DataInput dataInput)
  {
    this.din = dataInput;
    this.in = null;
  }
  
  public void setInput(InputStream inputStream)
  {
    this.in = inputStream;
    this.din = null;
  }
  
  private void setPhysicalHeightDpi(int newValue)
  {
    this.physicalWidthDpi = newValue;
  }
  
  private void setPhysicalWidthDpi(int newValue)
  {
    this.physicalHeightDpi = newValue;
  }
  
  private void skip(int num)
    throws IOException
  {
    while (num > 0)
    {
      long result;
      if (this.in != null) {
        result = this.in.skip(num);
      } else {
        result = this.din.skipBytes(num);
      }
      if (result > 0L)
      {
        num = (int)(num - result);
      }
      else
      {
        if (this.in != null) {
          result = this.in.read();
        } else {
          result = this.din.readByte();
        }
        if (result == -1L) {
          throw new IOException("Premature end of input.");
        }
        num--;
      }
    }
  }
}

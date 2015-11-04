package com.buswe.dht;

import java.text.DecimalFormat;

public class DhtStatistician {

    /* How much data was sent and received by the server over the network */
    private long totalDataSent, totalDataReceived;
    private long numDataSent, numDataReceived;

    /* Bootstrap timings */
    private long bootstrapTime;

    /* Content lookup operation timing & route length */
    private int numContentLookups, numFailedContentLookups;
    private long totalContentLookupTime;
    private long totalRouteLength;

    
    {
        this.totalDataSent = 0;
        this.totalDataReceived = 0;
        this.bootstrapTime = 0;
        this.numContentLookups = 0;
        this.totalContentLookupTime = 0;
        this.totalRouteLength = 0;
    }

  
    public void sentData(long size)
    {
        this.totalDataSent += size;
        this.numDataSent++;
    }

  
    public long getTotalDataSent()
    {
        if (this.totalDataSent == 0)
        {
            return 0L;
        }
        
        return this.totalDataSent / 1000L;
    }

  
    public void receivedData(long size)
    {
        this.totalDataReceived += size;
        this.numDataReceived++;
    }

  
    public long getTotalDataReceived()
    {
        if (this.totalDataReceived == 0)
        {
            return 0L;
        }
        return this.totalDataReceived / 1000L;
    }

  
    public void setBootstrapTime(long time)
    {
        this.bootstrapTime = time;
    }

  
    public long getBootstrapTime()
    {
        return this.bootstrapTime / 1000000L;
    }

  
    public void addContentLookup(long time, int routeLength, boolean isSuccessful)
    {
        if (isSuccessful)
        {
            this.numContentLookups++;
            this.totalContentLookupTime += time;
            this.totalRouteLength += routeLength;
        }
        else
        {
            this.numFailedContentLookups++;
        }
    }

  
    public int numContentLookups()
    {
        return this.numContentLookups;
    }

  
    public int numFailedContentLookups()
    {
        return this.numFailedContentLookups;
    }

  
    public long totalContentLookupTime()
    {
        return this.totalContentLookupTime;
    }

  
    public double averageContentLookupTime()
    {
        if (this.numContentLookups == 0)
        {
            return 0D;
        }

        double avg = (double) ((double) this.totalContentLookupTime / (double) this.numContentLookups) / 1000000D;
        DecimalFormat df = new DecimalFormat("#.00");
        return new Double(df.format(avg));
    }

  
    public double averageContentLookupRouteLength()
    {
        if (this.numContentLookups == 0)
        {
            return 0D;
        }
        double avg = (double) ((double) this.totalRouteLength / (double) this.numContentLookups);
        DecimalFormat df = new DecimalFormat("#.00");
        return new Double(df.format(avg));
    }

  
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Statistician: [");

        sb.append("Bootstrap Time: ");
        sb.append(this.getBootstrapTime());
        sb.append("; ");

        sb.append("Data Sent: ");
        sb.append("(");
        sb.append(this.numDataSent);
        sb.append(") ");
        sb.append(this.getTotalDataSent());
        sb.append(" bytes; ");

        sb.append("Data Received: ");
        sb.append("(");
        sb.append(this.numDataReceived);
        sb.append(") ");
        sb.append(this.getTotalDataReceived());
        sb.append(" bytes; ");

        sb.append("Num Content Lookups: ");
        sb.append(this.numContentLookups());
        sb.append("; ");

        sb.append("Avg Content Lookup Time: ");
        sb.append(this.averageContentLookupTime());
        sb.append("; ");

        sb.append("Avg Content Lookup Route Lth: ");
        sb.append(this.averageContentLookupRouteLength());
        sb.append("; ");

        sb.append("]");

        return sb.toString();
    }
}

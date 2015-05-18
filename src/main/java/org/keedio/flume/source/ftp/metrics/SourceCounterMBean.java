/*
    KEEDIO
*/
package org.keedio.flume.source.ftp.metrics;
/**
 *
 * @author Luis Lázaro <lalazaro@keedio.com>
 */
public interface SourceCounterMBean {
    public long getFilesCount();
    public void incrementFilesCount();
    public long getFilesProcCount();
    public void incrementFilesProcCount();
    public long getFilesProcCountError();
    public void incrementFilesProcCountError();
    public long getEventCount();
    public void incrementEventCount();
    public long getSendThroughput();
    public void incrementCountModProc();
    public long getCountModProc();
    public long getLastSent();
    public void incrementCountSizeProc(long size);
    public long getCountSizeProc();
    public long getCountSizeProcKb();
    public long getCountSizeProcMb();
}
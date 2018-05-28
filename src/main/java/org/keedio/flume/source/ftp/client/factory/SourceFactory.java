/*
 * KEEDIO
 */
package org.keedio.flume.source.ftp.client.factory;

import org.apache.flume.Context;
import org.keedio.flume.source.ftp.client.KeedioSource;
import org.keedio.flume.source.ftp.client.sources.FTPSSource;
import org.keedio.flume.source.ftp.client.sources.FTPSource;
import org.keedio.flume.source.ftp.client.sources.SFTPSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Luis Lázaro lalazaro@keedio.com Keedio
 */
public class SourceFactory {

    private KeedioSource keedioSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(KeedioSource.class);
    private static final Integer DISCOVER_DELAY = 10000;
    private static final boolean FLUSHLINE_DEFAULT = true;
    private static final String FOLDER_DEFAULT = System.getProperty("java.io.tmpdir");
    private static final Integer CHUNKSIZE_DEFAULT = 1024;
    private static final String FILENAME_DEFAULT = "default_file_track_status.ser";
    private static final boolean RECURSIVE_DEFAULT = true;
    private static final boolean PROCESSINUSE = true;
    private static final Integer PROCESSINUSE_TIMEOUT = 60;
    private static final String FILE_COMPRESSION_FORMAT = null;
    private static final boolean DELETE_ON_COMPLETION_DEFAULT = false;

    /**
     * Create KeedioSource
     *
     * @param context
     * @return KeedioSource
     */
    public KeedioSource createKeedioSource(Context context) {
        keedioSource = null;
        initSource(context);
        return keedioSource;
    }

    /**
     * Initiate attributes of KeediosSource according to context
     *
     * @param context of the source
     * @return KeedioSource
     */
    public KeedioSource initSource(Context context) {
        switch (context.getString("client.source")) {
            case "ftp":
                keedioSource = new FTPSource();
                initCommonParam(context);
                break;
            case "sftp":
                keedioSource = new SFTPSource();
                SFTPSource sftpSource = new SFTPSource(
                  context.getString("knownHosts"),
                  context.getString("strictHostKeyChecking", "yes")
                );
                keedioSource = sftpSource;
                initCommonParam(context);
                break;
            case "ftps":
                keedioSource = new FTPSSource();
                FTPSSource ftpsSource = new FTPSSource(
                        context.getBoolean("security.enabled"),
                        context.getString("security.cipher"),
                        context.getBoolean("security.certificate.enabled"),
                        context.getString("path.keystore", FOLDER_DEFAULT),
                        context.getString("store.pass")
                );
                keedioSource = ftpsSource;
                initCommonParam(context);
                break;
            default:
                LOGGER.error("Source not found in context");
        }
        return keedioSource;
    }

    /**
     * initialize common parameters for all sources.
     * @param context of source
     */
    public void initCommonParam(Context context) {
        keedioSource.setBufferSize(context.getInteger("buffer.size"));
        keedioSource.setServer(context.getString("name.server"));
        keedioSource.setUser(context.getString("user"));
        keedioSource.setPassword(context.getString("password"));
        keedioSource.setRunDiscoverDelay(context.getInteger("run.discover.delay", DISCOVER_DELAY));
        keedioSource.setWorkingDirectory(context.getString("working.directory"));
        keedioSource.setPort(context.getInteger("port"));
        keedioSource.setFolder(context.getString("folder", FOLDER_DEFAULT));
        keedioSource.setFileName(context.getString("file.name", FILENAME_DEFAULT));
        keedioSource.setFlushLines(context.getBoolean("flushlines", FLUSHLINE_DEFAULT));
        keedioSource.setChunkSize(context.getInteger("chunk.size", CHUNKSIZE_DEFAULT));
        keedioSource.setKeedioFilterRegex(context.getString("filter.pattern", ""));
        keedioSource.setRecursive(context.getBoolean("search.recursive", RECURSIVE_DEFAULT));
        keedioSource.setProcessInUse(context.getBoolean("search.processInUse", PROCESSINUSE));
        keedioSource.setProcessInUseTimeout(context.getInteger("search.processInUseTimeout", PROCESSINUSE_TIMEOUT));
        keedioSource.setFileCompressed(context.getString("compressed", FILE_COMPRESSION_FORMAT));
        keedioSource.setDeleteOnCompletion(context.getBoolean("deleteOnCompletion", DELETE_ON_COMPLETION_DEFAULT));
    }

}

package com.cloud.monitor;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.google.common.collect.Maps;
import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.HashBag;

import java.io.*;
import java.lang.management.*;
import java.util.HashMap;
import java.util.Map;
/**
 * @author summer
 */
public class JVMHelper {
    private static TLogger DB_LOGGER = LoggerUtils.getLogger(JVMHelper.class);

    public static Map<String, String> exportThreadToFile(String filePath, String fileName) {
        File savePath = new File(filePath);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        Writer writer = null;
        Map<String, String> result = null;
        File file = new File(filePath, fileName);
        try {
            writer = new BufferedWriter(new FileWriter(file));
            result = exportThread(writer);
        } catch (Exception e) {
            DB_LOGGER.warn("", e);
            result = Maps.newHashMap();
            result.put("error", e.getClass().getName() + ":" + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
        }
        result.put("fileName", fileName);
        if (file.exists()) {
            result.put("fileSize", "" + file.length());
        }
        return result;
    }

    public static Map<String, String> exportThread(Writer writer) throws IOException {
        Map<Thread, StackTraceElement[]> traceList = Thread.getAllStackTraces();
        Bag bag = new HashBag();
        for (Map.Entry<Thread, StackTraceElement[]> trace : traceList.entrySet()) {
            Thread t = trace.getKey();
            writer.write("\"" + t.getName() + "\" - " + t.getState() + "\n");
            for (StackTraceElement stack : trace.getValue()) {
                writer.write("\tat " + stack.toString() + "\n");
            }
            writer.write("\n");
            bag.add(t.getState().name());
        }
        Map<String, String> result = Maps.newHashMap();
        result.put("threadCount", "" + traceList.size());
        for (Object key : bag.uniqueSet()) {
            result.put((String) key, "" + bag.getCount(key));
        }
        return result;
    }

    public static Map<String, String> exportMBeanThread(Writer writer) throws IOException {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] tiList = bean.getThreadInfo(bean.getAllThreadIds(), true, true);
        Bag bag = new HashBag();
        for (ThreadInfo ti : tiList) {
            appendThreadInfo(writer, ti);
            bag.add(ti.getThreadState().name());
        }
        Map<String, String> result = Maps.newHashMap();
        result.put("threadCount", "" + tiList.length);
        for (Object key : bag.uniqueSet()) {
            result.put((String) key, "" + bag.getCount(key));
        }
        return result;
    }

    private static void appendThreadInfo(Writer writer, ThreadInfo ti) throws IOException {
        writer.append("\"" + ti.getThreadName() + "\"" + " Id=" + ti.getThreadId() + " " + ti.getThreadState());
        if (ti.getLockName() != null) {
            writer.append(" on " + ti.getLockName());
        }
        if (ti.getLockOwnerName() != null) {
            writer.append(" owned by \"" + ti.getLockOwnerName() + "\" Id=" + ti.getLockOwnerId());
        }
        if (ti.isSuspended()) {
            writer.append(" (suspended)");
        }
        if (ti.isInNative()) {
            writer.append(" (in native)");
        }
        writer.append('\n');
        StackTraceElement[] stackTrace = ti.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement ste = stackTrace[i];
            writer.append("\tat " + ste.toString());
            writer.append('\n');
            if (i == 0 && ti.getLockInfo() != null) {
                Thread.State ts = ti.getThreadState();
                switch (ts) {
                    case BLOCKED:
                        writer.append("\t-  blocked on " + ti.getLockInfo());
                        writer.append('\n');
                        break;
                    case WAITING:
                        writer.append("\t-  waiting on " + ti.getLockInfo());
                        writer.append('\n');
                        break;
                    case TIMED_WAITING:
                        writer.append("\t-  waiting on " + ti.getLockInfo());
                        writer.append('\n');
                        break;
                    default:
                }
            }

            for (MonitorInfo mi : ti.getLockedMonitors()) {
                if (mi.getLockedStackDepth() == i) {
                    writer.append("\t-  locked " + mi);
                    writer.append('\n');
                }
            }
        }

        LockInfo[] locks = ti.getLockedSynchronizers();
        if (locks.length > 0) {
            writer.append("\n\tNumber of locked synchronizers = " + locks.length);
            writer.append('\n');
            for (LockInfo li : locks) {
                writer.append("\t- " + li);
                writer.append('\n');
            }
        }
        writer.append('\n');
    }
}

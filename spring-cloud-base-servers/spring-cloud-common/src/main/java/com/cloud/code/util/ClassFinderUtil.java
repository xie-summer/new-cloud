//package com.icloud.code.util;
//
//import com.fasterxml.jackson.databind.util.ClassUtil;
//import com.cloud.dubbo.bytecode.Wrapper;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang.StringUtils;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.net.JarURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.*;
//import java.util.jar.JarEntry;
//import java.util.jar.JarFile;
//
//public class ClassFinderUtil {
//
//    private static final char DOT = '.';
//
//    private static final char SLASH = '/';
//
//    private static final String CLASS_SUFFIX = ".class";
//    /**
//     * URL protocol for a file in the file system: "file"
//     */
//    public static final String URL_PROTOCOL_FILE = "file";
//
//    /**
//     * URL protocol for an entry from a jar file: "jar"
//     */
//    public static final String URL_PROTOCOL_JAR = "jar";
//
//    /**
//     * URL protocol for an entry from a zip file: "zip"
//     */
//    public static final String URL_PROTOCOL_ZIP = "zip";
//
//    /**
//     * URL protocol for an entry from a WebSphere jar file: "wsjar"
//     */
//    public static final String URL_PROTOCOL_WSJAR = "wsjar";
//
//    /**
//     * URL protocol for an entry from a JBoss jar file: "vfszip"
//     */
//    public static final String URL_PROTOCOL_VFSZIP = "vfszip";
//
//    /**
//     * 查找包下所有类.(only classes, exclude jar file)
//     *
//     * @param scannedPackage 如：com.cloud.model
//     * @return
//     */
//    public static List<Class<?>> find(String scannedPackage) {
//        String scannedPath = scannedPackage.replace(DOT, SLASH);
//        Enumeration<URL> resources = null;
//        List<Class<?>> result = new ArrayList<Class<?>>();
//        try {
//            resources = Thread.currentThread().getContextClassLoader().getResources(scannedPath);
//        } catch (IOException e) {
//        }
//        if (resources == null) {
//            return result;
//        }
//        while (resources.hasMoreElements()) {
//            URL resource = resources.nextElement();
//            if (isFileURL(resource)) {
//                result.addAll(findFileClass(resource.getFile(), scannedPackage));
//            } else if (isJarURL(resource)) {
//                //ignore
//            }
//
//        }
//        return result;
//    }
//
//    public static List<Class<?>> findFileClass(String scanDir, String scannedPackage) {
//        List<Class<?>> classes = new ArrayList<Class<?>>();
//        File rootFile = new File(scanDir);
//        for (File file : rootFile.listFiles()) {
//            classes.addAll(find(file, scannedPackage));
//        }
//        return classes;
//    }
//
//    public static boolean isJarURL(URL url) {
//        String protocol = url.getProtocol();
//        return (URL_PROTOCOL_JAR.equals(protocol) || URL_PROTOCOL_ZIP.equals(protocol) ||
//                URL_PROTOCOL_VFSZIP.equals(protocol) || URL_PROTOCOL_WSJAR.equals(protocol));
//    }
//
//    public static boolean isFileURL(URL url) {
//        String protocol = url.getProtocol();
//        return URL_PROTOCOL_FILE.equals(protocol);
//    }
//
//    /**
//     * original from spring context component scan
//     *
//     * @param rootDir
//     * @param subPattern
//     * @return
//     * @throws IOException
//     */
//    protected Set<URL> findJarClass(URL rootDir, String subPattern) throws IOException {
//        URLConnection con = rootDir.openConnection();
//        JarFile jarFile = null;
//        //String jarFileUrl = null;
//        String rootEntryPath = null;
//        boolean newJarFile = false;
//
//        if (con instanceof JarURLConnection) {
//            // Should usually be the case for traditional JAR files.
//            JarURLConnection jarCon = (JarURLConnection) con;
//            //ResourceUtils.useCachesIfNecessary(jarCon);
//            jarFile = jarCon.getJarFile();
//            //jarFileUrl = jarCon.getJarFileURL().toExternalForm();
//            JarEntry jarEntry = jarCon.getJarEntry();
//            rootEntryPath = (jarEntry != null ? jarEntry.getName() : "");
//        }
//        try {
//            if (!"".equals(rootEntryPath) && !rootEntryPath.endsWith("/")) {
//                // Root entry path must end with slash to allow for proper matching.
//                // The Sun JRE does not return a slash here, but BEA JRockit does.
//                rootEntryPath = rootEntryPath + "/";
//            }
//            Set<URL> result = new LinkedHashSet<URL>(8);
//            for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements(); ) {
//                JarEntry entry = entries.nextElement();
//                String entryPath = entry.getName();
//                if (entryPath.startsWith(rootEntryPath)) {
//                    String relativePath = entryPath.substring(rootEntryPath.length());
//                    result.add(createRelative(rootDir, relativePath));
//                }
//            }
//            return result;
//        } finally {
//            // Close jar file, but only if freshly obtained -
//            // not from JarURLConnection, which might cache the file reference.
//            if (newJarFile) {
//                jarFile.close();
//            }
//        }
//    }
//
//    private URL createRelative(URL rootDir, String relativePath) throws MalformedURLException {
//        if (relativePath.startsWith("/")) {
//            relativePath = relativePath.substring(1);
//        }
//        return new URL(rootDir, relativePath);
//    }
//
//    public static List<String> findNoDefaultConstructClass(String scannedPackage) {
//        List<String> result = new ArrayList<String>();
//        List<Class<?>> classList = find(scannedPackage);
//        for (Class clazz : classList) {
//            if (ClassUtil.isConcrete(clazz)) {
//                try {
//                    clazz.newInstance();
//                } catch (Throwable e) {
//                    result.add(clazz.getCanonicalName());
//                }
//            }
//        }
//        return result;
//    }
//
//    private static List<Class<?>> find(File file, String scannedPackage) {
//        List<Class<?>> classes = new ArrayList<Class<?>>();
//        String resource = scannedPackage + DOT + file.getName();
//        if (file.isDirectory()) {
//            for (File child : file.listFiles()) {
//                classes.addAll(find(child, resource));
//            }
//        } else if (resource.endsWith(CLASS_SUFFIX)) {
//            int endIndex = resource.length() - CLASS_SUFFIX.length();
//            String className = resource.substring(0, endIndex);
//            try {
//                classes.add(Class.forName(className));
//            } catch (ClassNotFoundException ignore) {
//            }
//        }
//        return classes;
//    }
//
//    /**
//     * @param
//     * @param clazz
//     * @return
//     * @throws Exception
//     */
//    public static List<MethodLine> findInterfaceMethods(String javaSrc, String clazz) throws Exception {
//        String javaFile = StringUtils.replace(clazz, ".", "/") + ".java";
//        File file = new File(javaSrc, javaFile);
//        List<MethodLine> methods = new ArrayList<>();
//        // System.out.println(file.exists() + ":" + file.getCanonicalPath());
//        List<String> lines = IOUtils.readLines(new FileReader(file));
//        int i = 1;
//        for (String line : lines) {
//            line = StringUtils.trim(line);
//            if (line.startsWith("import") || line.contains("interface") || line.startsWith("package")) {
//                // System.out.println("ignore:" + line);
//            } else if (line.matches("^\\w+.*")) {
//                if (line.startsWith("public")) {
//                    line = line.substring(7).trim();
//                }
//                line = line.replaceAll("<[^>]*>", "");// generation
//                //System.out.println(clazz + ":" + line);
//                methods.add(new MethodLine(clazz, line, i));
//            } else {
//                // System.out.println("unknown:" + line);
//            }
//            i++;
//        }
//        return methods;
//    }
//
//    public static List<String> findStaticFields(Class clazz, boolean addClassPre) {
//        List<String> result = new ArrayList<>();
//        String className = clazz.getSimpleName();
//        for (Field f : clazz.getFields()) {
//            if (Modifier.isStatic(f.getModifiers())) {
//                if (addClassPre) {
//                    result.add(className + "." + f.getName());
//                } else {
//                    result.add(f.getName());
//                }
//            }
//        }
//        return result;
//    }
//
//    /**
//     * @param  : List<ClassName.FieldName>
//     * @param pkg
//     */
//    public static void findAllUnusedStaticFields(String pkg/* package */, String javaSrc) {
//        List<Class> clazzList = ClassFinderUtil.findAllClass(pkg, ClassFilter.ACCEPT_ALL);
//        System.err.println("---start loadsource-------------------------------------------");
//        List<String> javaSourceLines = loadAllJava(javaSrc, LineFilter.ACCEPT_ALL_FILTER);
//        System.err.println("---end loadsource-------------------------------------------");
//        List<String> regList = new ArrayList<>();
//        for (Class clazz : clazzList) {
//            List<String> notUsed = ClassFinderUtil.findUnusedStaticFieldsInternal(clazz, javaSourceLines);
//            if (notUsed.size() > 0) {
//                regList.add(clazz.getCanonicalName() + " search reg: .*(" + StringUtils.join(notUsed, "|") + ").*\\R");
//            }
//        }
//        System.err.println("----------------------- eclipse search regex ----------------------------");
//        for (String reg : regList) {
//            System.out.println(reg);
//        }
//    }
//
//    /**
//     * @param  : List<ClassName.FieldName>
//     * @param
//     */
//    public static List<String> findUnusedStaticFields(Class clazz/**/, String javaSrc) {
//        //Set<String> impClassList = new TreeSet<String>();
//        System.err.println("---start loadsource-------------------------------------------");
//        List<String> javaSourceLines = loadAllJava(javaSrc, LineFilter.ACCEPT_ALL_FILTER);
//        System.err.println("---end loadsource-------------------------------------------");
//        List<String> result = findUnusedStaticFieldsInternal(clazz, javaSourceLines);
//        return result;
//    }
//
//    private static List<String> findUnusedStaticFieldsInternal(Class clazz, List<String> sourceLines) {
//        List<String> notUsed = new ArrayList<>();
//        boolean find = false;
//        List<String> staticFieldList = findStaticFields(clazz, false);
//        String className = clazz.getSimpleName();
//        for (String field : staticFieldList) {
//            String search = className + "." + field;
//            find = false;
//            for (String line : sourceLines) {
//                if (StringUtils.containsIgnoreCase(line, search)) {
//                    find = true;
//                    break;
//                }
//            }
//            if (!find) {
//                notUsed.add(field);
//                System.out.println("not found:" + field);
//            }
//        }
//        System.err.println("-----------------------" + clazz.getCanonicalName() + "------------------------------------");
//        for (String method : notUsed) {
//            System.out.println(method);
//        }
//
//        return notUsed;
//    }
//
//    public static void findNoDefault(String pkg) {
//        List<String> classList = ClassFinderUtil.findNoDefaultConstructClass(pkg);
//        for (String clazz : classList) {
//            System.out.println(clazz);
//        }
//    }
//
//    public static List<String> findInterfaces(String pkg) {
//        List<Class<?>> list = ClassFinderUtil.find(pkg);
//        List<String> result = new ArrayList<String>();
//        for (Class clazz : list) {
//            if (isInterface(clazz)) {
//                result.add(clazz.getCanonicalName());
//            }
//        }
//        return result;
//    }
//
//    public static List<String> findConcreteClass(String pkg) {
//        List<Class<?>> list = ClassFinderUtil.find(pkg);
//        List<String> result = new ArrayList<String>();
//        for (Class clazz : list) {
//            if (ClassUtil.isConcrete(clazz)) {
//                result.add(clazz.getCanonicalName());
//            }
//        }
//        return result;
//    }
//
//    public static List<Class> findAllClass(String pkg, ClassFilter filter) {
//        List<Class<?>> list = ClassFinderUtil.find(pkg);
//        List<Class> result = new ArrayList<Class>();
//        String cn = null;
//        for (Class clazz : list) {
//            if (filter.accept(clazz)) {
//                cn = clazz.getCanonicalName();
//                if (cn == null) {//inner class
//                    System.err.println("NoName:" + clazz);
//                } else {
//                    result.add(clazz);
//                }
//            }
//        }
//        return result;
//    }
//
//    public static List<String> toStringNames(List<Class> clazzList) {
//        List<String> result = new ArrayList<String>();
//        for (Class clazz : clazzList) {
//            String cn = clazz.getCanonicalName();
//            if (cn == null) {//inner class
//                System.err.println("NoName:" + clazz);
//            } else {
//                result.add(cn);
//            }
//        }
//        return result;
//    }
//
//
//    public static boolean isInterface(Class<?> type) {
//        int mod = type.getModifiers();
//        return (mod & Modifier.INTERFACE) > 0;
//    }
//
//    public static void findNotMatchGetSet(String modelPackage) {
//        List<Class<?>> list = ClassFinderUtil.find(modelPackage);
//        for (Class clazz : list) {
//            try {
//                if (ClassUtil.isConcrete(clazz) && clazz.getCanonicalName().indexOf('$') < 0/* 非内部类 */) {
//                    Wrapper wraper = Wrapper.getWrapper(clazz);
//                    List<String> read = new ArrayList<>(Arrays.asList(wraper.getReadPropertyNames()));
//                    List<String> write = new ArrayList<>(Arrays.asList(wraper.getWritePropertyNames()));
//                    List<String> read2 = new ArrayList<>(read);
//                    read2.removeAll(write);
//                    write.removeAll(read);
//                    String msg = "";
//                    if (!read2.isEmpty()) {
//                        msg = "get: " + StringUtils.join(read2, ",");
//                    }
//                    if (!write.isEmpty()) {
//                        msg = "  set: " + StringUtils.join(write, ",");
//                    }
//                    if (StringUtils.isNotBlank(msg)) {
//                        System.out.println(clazz.getCanonicalName() + ":" + msg);
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println(clazz.getCanonicalName() + ":" + e);
//            }
//        }
//    }
//
//    public static void findUnusedClasses(String javaSrc, String... searchPkg) {
//        List<String> importList = loadAllJava(javaSrc, new LineFilter.RegFilter("^import"));
//        for (String pkg : searchPkg) {
//            findUnusedClass(importList, pkg);
//        }
//    }
//
//    public static void findUnusedInterfaceMethod(String interfaceSrc, String javaSource) throws Exception {
//        List<String> interfaces = findInterfaces(interfaceSrc);
//        List<MethodLine> searchCall = new ArrayList<>();
//        for (String clazz : interfaces) {
//            List<MethodLine> methods = findInterfaceMethods(javaSource, clazz);
//            toSimpleCallName(clazz, methods);
//            searchCall.addAll(methods);
//        }
//        System.err.println("---start loadsource-------------------------------------------");
//        List<String> javaSourceLines = loadAllJava(javaSource, LineFilter.ACCEPT_ALL_FILTER);
//        System.err.println("---end loadsource-------------------------------------------");
//        List<MethodLine> notUsed = new ArrayList<>();
//        boolean find = false;
//        for (MethodLine call : searchCall) {
//            if (call.callname == null) {
//                continue;
//            }
//            find = false;
//            for (String line : javaSourceLines) {
//                if (StringUtils.containsIgnoreCase(line, call.callname)) {
//                    find = true;
//                    break;
//                }
//            }
//            if (!find) {
//                notUsed.add(call);
//                System.out.println("not found:" + call);
//            }
//        }
//        System.err.println("-----------------------------------------------------------");
//        for (MethodLine method : notUsed) {
//            System.out.println(method);
//        }
//
//    }
//
//    public static void toSimpleCallName(String clazz, List<MethodLine> methods) {
//        String serviceName = StringUtils.substringAfterLast(clazz, ".");
//        for (MethodLine method : methods) {
//            try {
//                // List<SellSeat> checkAndCreateSeat(MovieItemVo opi, String
//                // seatLabel)
//                int idx1 = method.method.indexOf("(");
//                String[] methodPair = method.method.substring(0, idx1).split("\\s+");
//                String methodName = methodPair[methodPair.length - 1];
//                method.callname = serviceName + "." + methodName;
//            } catch (Exception e) {
//                System.err.print("ERROR:" + method);
//            }
//        }
//    }
//
//    public static List<String> loadAllJava(String javaSrc, LineFilter lineFilter) {
//        List<String> result = new ArrayList<String>();
//        try {
//            List<String> fileList = new ArrayList<String>();
//            FileSearch.searchFile(fileList, javaSrc, "java");
//            for (String file : fileList) {
//                List<String> lines = IOUtils.readLines(new FileReader(file));
//                for (String line : lines) {
//                    if (lineFilter.accept(line)) {
//                        result.add(line);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public static void findUnusedClass(List<String> importList, String pkg) {
//        List<Class> modelList = findAllClass(pkg, ClassFilter.ACCEPT_ALL);
//        Set<String> modelSet = new TreeSet<String>(toStringNames(modelList));
//        System.out.println("------------------------import: " + modelSet.size() + "------------------------------");
//
//        //Set<String> impClassList = new TreeSet<String>();
//        for (String imp : importList) {
//            int index = imp.indexOf(pkg);
//            if (index > 0) {
//                int index2 = imp.indexOf(';');
//                String clazz = StringUtils.substring(imp, index, index2);
//                modelSet.remove(StringUtils.trim(clazz));
//                //System.out.println(clazz);
//            }
//        }
//        System.out.println("------------------------unused: " + pkg + ":" + modelSet.size() + "------------------------------");
//        System.out.println(StringUtils.join(modelSet, "\n"));
//    }
//}
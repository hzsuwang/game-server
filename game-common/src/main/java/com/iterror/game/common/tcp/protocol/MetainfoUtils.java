package com.iterror.game.common.tcp.protocol;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 */
public class MetainfoUtils {

    private static final Logger logger = LoggerFactory.getLogger(MetainfoUtils.class);

    private static MetainfoUtils util   = new MetainfoUtils();

    public static MetainfoUtils getUtil() {
        return util;
    }

    private MetainfoUtils(){
    }

    static public DefaultTypeMetainfo createTypeMetainfo(Collection<String> packages) {
        DefaultTypeMetainfo typeMetainfo = new DefaultTypeMetainfo();

        if (null != packages) {
            for (String pkgName : packages) {
                try {
                    String[] clsNames = PackageUtil.findClassesInPackage(pkgName, null, null);
                    for (String clsName : clsNames) {
                        try {
                            ClassLoader cl = Thread.currentThread().getContextClassLoader();
                            if (logger.isDebugEnabled()) {
                                logger.debug("using ClassLoader {" + cl + "} to load Class {" + clsName + "}");
                            }
                            Class<?> cls = cl.loadClass(clsName);
                            SignalCode attr = cls.getAnnotation(SignalCode.class);
                            if (null != attr) {
                                int value = attr.messageCode();
                                typeMetainfo.add(value, cls);
                                logger.info("metainfo: add " + value + ":=>" + cls);
                            }
                        } catch (ClassNotFoundException e) {
                            logger.error("createTypeMetainfo: ", e);
                        }
                    }
                } catch (IOException e) {
                    logger.error("createTypeMetainfo: ", e);
                }
            }
        }

        return typeMetainfo;
    }
}

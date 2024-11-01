package com.luthiel.RiskCtrlSys.flink.utils;

import com.luthiel.RiskCtrlSys.commons.constants.ConstantsUtil;
import com.luthiel.RiskCtrlSys.utils.common.CommonUtil;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.Script;
import org.apache.flink.api.java.utils.ParameterTool;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.security.MessageDigest;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author: Luthiel
 * description: Groovy 脚本工具类
 * date: 2023
 */

public class GroovyUtil {

    /**
     * groovy 类加载器
     */
    private static GroovyClassLoader LOADER = null;

    private static ConcurrentHashMap<String, Class<Script>> clazzMaps
            = new ConcurrentHashMap<String, Class<Script>>();


    static {
        ParameterTool parameterTool = ParameterUtil.getParameters();

    }



    private static CompilerConfiguration getCompilerConfiguration() {
        CompilerConfiguration config = new CompilerConfiguration();
        return config;
    }

    /**
     * author: Luthiel
     * description: 生成 Groovy 类加载器
     * @param :  
     * @return groovy.lang.GroovyClassLoader 
     */
    public static GroovyClassLoader getEngineByClassLoader(String key) {

        GroovyClassLoader groovyClassLoader = null;
        Class<Script> script = clazzMaps.get(key);
        //每个groovy脚本单独创建一个GroovyClassLoader对象
        if ( script == null ) {
            synchronized (key.intern()) {
                // Double Check
                script = clazzMaps.get(key);
                if ( script == null ) {
                    groovyClassLoader = new GroovyClassLoader(
                            //GroovyClassLoader的父ClassLoader为当前线程的加载器
                            Thread.currentThread().getContextClassLoader(),
                            getCompilerConfiguration()
                    );
                }
            }
        }

        return groovyClassLoader;

    }

    /**
     * author: Luthiel
     * description: 获取Groovy脚本文件
     * @param groovyClass:
     * @return java.io.File
     */
    private static File getClassByFile(String groovyClass) {
        //获取模块路径
        String modulePath = CommonUtil.getModulePath(ConstantsUtil.MODULE_FLINK);
        //获取脚本完整路径
        String _path = modulePath + ConstantsUtil.GROOVY_SCRIPTS_PATH;
        String path = _path+groovyClass+".groovy";
        return new File(path);
    }

    /**
     * author: Luthiel
     * description: 解析 groovy 脚本
     * @param groovyClass:
     * @return void
     */
    public static Object groovyEval(
            String groovyClass,
            String method,
            Object args) {

        Object obj = null;

        //获取脚本文件
        File file = getClassByFile(groovyClass);
        //获取文件的md5编码
        String md5 = fingerKey(fileToString(file));
        //根据md5编码获取GroovyClassLoader单例
        LOADER = getEngineByClassLoader(md5);
        try {
            //解析脚本
            Class<?> groovyScript = LOADER.parseClass(file);

            Class<Script> _groovyScript = (Class<Script>) groovyScript;
            //将文件md5编码以及所对应的 Class<Script> 对象写入HashMap,
            //用于下次的GroovyClassLoader单例判断
            clazzMaps.put(md5, _groovyScript);
            // 获得实例
            GroovyObject groovyObject = (GroovyObject) groovyScript.newInstance();
            // 反射调用方法，传递的 method -> getPattern()
            obj = groovyObject.invokeMethod(method, args);
            return obj;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    /**
     * author: Luthiel
     * description: 生成groovy脚本代码的md5指纹
     * @param scriptText:
     * @return java.lang.String 
     */
    private static String fingerKey(String scriptText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(scriptText.getBytes("utf-8"));

            final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
            StringBuilder ret = new StringBuilder(bytes.length * 2);
            for (int i=0; i<bytes.length; i++) {
                ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
                ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
            }
            return ret.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * author: Luthiel
     * description: File转String
     * @param file:
     * @return java.lang.String
     */
    private static String fileToString(File file) {
        //读取File对象内容转为String
        return CommonUtil.fileToString(file);
    }

    public static void redisLoader() {

    }

    public static void registerScriptWithRedis() {

    }
}

package edu.ncu.xzj.unload;

import edu.ncu.xzj.unload.impl.UnloadImpl;

import java.io.IOException;

/**
 * @author 熊志京
 * @version 1.0
 * @classname javaunloader
 * @description javaunload主程序
 * @date 2022/3/22 18:41
 */
public class JUnloader {
    public static void main(String[] args) throws IOException {
        final long num = new UnloadImpl(args).unload();
        System.out.println((String.format("Total Unloaded: %-5d lines ", num)));
    }
}

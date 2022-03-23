package edu.ncu.xzj.unload.impl;

import edu.ncu.xzj.unload.api.ICLIParse;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 熊志京
 * @version 1.0
 * @classname CLIParseImpl
 * @description 命令行解析, 将参数解析到map
 * @date 2022/3/19 22:58
 */
public class CLIParseImpl implements ICLIParse {

    private Map<String, String> opts;
    private Options options;


    public CLIParseImpl() {
        opts = new HashMap<String, String>();
        this.options = new Options();
        initCLIOpts();
    }

    @Override
    public Map<String, String> getOpts() {
        return opts;
    }

    @Override
    public Map<String, String> parseCLI(String[] args) {
        CommandLine cli = null;
        CommandLineParser parser = new DefaultParser();
        String value;

        int flag = 0;
        try {
            cli = parser.parse(options, args);
            // 数据库配置文件
            if (cli.hasOption("c")) {
                value = cli.getOptionValue("c");
                doConfigFile(value);
            }
            if (cli.hasOption("f")) {
                value = cli.getOptionValue("f");
                if (value == null) {
                    throw new RuntimeException("未获取到指定的sqlfile");
                }
                doSqlFile(value);
            }
            if (cli.hasOption("t")) {
                value = cli.getOptionValue("t");
                if (value == null) {
                    throw new RuntimeException("未获取到指定的tabname");
                }
                opts.put("sql", String.format("select * from %s", value));

            }
            if (cli.hasOption("q")) {
                value = cli.getOptionValue("q");
                if (value == null) {
                    throw new RuntimeException("未获取到指定的sqlfile");
                }
                opts.put("sql", value);
            }
            if (cli.hasOption("d")) {
                value = cli.getOptionValue("d");
                // 缺省值 |@|
                if (value == null) {
                    value = "|@|";
                }
                opts.put("delimiter", value);
            } else {
                value = "|@|";
                opts.put("delimiter", value);
            }
            if (cli.hasOption("o")) {
                value = cli.getOptionValue("o");
                if (value == null) {
                    throw new RuntimeException("未获取到指定的输出文件");
                }
                opts.put("output", value);
            }
        } catch (ParseException e) {
            flag = 1;
            System.err.println(e);
        } catch (IOException e) {
            flag = 1;
            System.err.println(e);
        }

        if (opts.size() == 0 || flag == 1) {
            System.err.println("junloader -c configfile [ -t tabname ] [ -q sql ] [ -f sqlfile ] -d delimiter -o outfile ");
            throw new RuntimeException("未能获取任何有效参数");
        }
        if (!opts.containsKey("output") ||
                !opts.containsKey("sql")) {
            throw new RuntimeException("未能获取任何有效参数");
        }
        return opts;

    }

    protected void doSqlFile(String sqlfile) throws FileNotFoundException {

        try (
                final BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(sqlfile)));
        ) {
            final List<String> lines = reader.lines().collect(Collectors.toList());
            final String sql = lines.stream().collect(Collectors.joining(" "));
            opts.put("sql", sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doConfigFile(String configfile) throws IOException {
        Properties properties = new Properties();
        try (
//                InputStream fd = ClassLoader.getSystemResourceAsStream(configfile)
                InputStream fd = new FileInputStream(configfile);
        ) {
            properties.load(fd);
            properties.forEach((k, v) -> opts.put(k.toString(), v.toString()));
        }
    }

    protected void initCLIOpts() {
        Option option = new Option("c", "configfile", true, "db config file");
        option.setRequired(false);
        options.addOption(option);

        option = new Option("f", "sqlfile", true, "sqlfile");
        option.setRequired(false);
        options.addOption(option);

        option = new Option("t", "table", true, "tablename");
        option.setRequired(false);
        options.addOption(option);

        option = new Option("q", "sql", true, "sql");
        option.setRequired(false);
        options.addOption(option);

        option = new Option("d", "delimiter", true, "delimiter");
        option.setRequired(false);
        options.addOption(option);

        option = new Option("o", "output", true, "output");
        option.setRequired(false);
        options.addOption(option);

    }


}

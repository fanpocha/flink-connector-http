package com.fu;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;

public class HttpConnectorOptions {

    public static final ConfigOption<String> URL =
            ConfigOptions.key("url")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("The HTTP endpoint URL.");


    public static final ConfigOption<String> METHOD =
            ConfigOptions.key("method")
                    .stringType()
                    .defaultValue("GET")
                    .withDescription("Method used for REST executed by  connector.");


    public static final ConfigOption<String> FORMAT =
            ConfigOptions.key("format")
                    .stringType()
                    .defaultValue("file")
                    .withDescription("the type of result .eg.json,file");

    public static final ConfigOption<String> FORMAT_FILE_TYPE =
            ConfigOptions.key("format.file.type")
                    .stringType()
                    .defaultValue("csv")
                    .withDescription("the type of result .eg.csv");
    public static final ConfigOption<String> FORMAT_FILE_CHARSET =
            ConfigOptions.key("format.type.charset")
                    .stringType()
                    .defaultValue("gb2312")
                    .withDescription("the type of result .eg.utf8");
}

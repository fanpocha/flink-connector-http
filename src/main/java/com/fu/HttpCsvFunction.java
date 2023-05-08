package com.fu;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.binary.BinaryStringData;
import org.apache.flink.types.RowKind;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class HttpCsvFunction extends RichSourceFunction<RowData> {
    ReadableConfig config;




    public HttpCsvFunction(ReadableConfig config) {
        this.config = config;
    }

    @Override
    public void run(SourceContext sourceContext) throws Exception {
        if ("get".equalsIgnoreCase(config.getOptional(HttpConnectorOptions.METHOD).isPresent() ?
                config.getOptional(HttpConnectorOptions.METHOD).get() : "get")) {
            OkHttpClient okHttpClient = new OkHttpClient();
            String url = config.getOptional(HttpConnectorOptions.URL).get();
            final Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response rs = okHttpClient.newCall(request).execute();
            InputStream is = rs.body().byteStream();

            InputStreamReader reader = new InputStreamReader(is,
                    config.getOptional(HttpConnectorOptions.FORMAT_FILE_CHARSET).isPresent() ?
                            config.getOptional(HttpConnectorOptions.FORMAT_FILE_CHARSET).get() : "gb2312");

            BufferedReader br = new BufferedReader(reader);
            try {
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    sourceContext.collect(getRowData(RowKind.INSERT, line));
                }
            } catch (Throwable ex) {

                log.error("{}", ex);
                throw new RuntimeException(ex);

            }


        } else if ("post".equalsIgnoreCase(config.getOptional(HttpConnectorOptions.METHOD).get())) {

        }


    }

    private RowData getRowData(RowKind insert, String line) {

        String[] lineArr = line.split(",");
        GenericRowData rdata = new GenericRowData(RowKind.INSERT, lineArr.length);
        for (int i = 0; i < lineArr.length; i++) {
            rdata.setField(i,  BinaryStringData.fromString(lineArr[i]));
        }
        return rdata;
    }

    @Override
    public void cancel() {

    }
}

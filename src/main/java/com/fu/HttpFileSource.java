package com.fu;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.connector.source.*;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.types.RowKind;

import java.util.List;

public class HttpFileSource implements DynamicTableSource, ScanTableSource {


    ReadableConfig config;

    public HttpFileSource(ReadableConfig config) {
        this.config = config;
    }

    @Override
    public DynamicTableSource copy() {
        return new HttpFileSource(config);
    }


    @Override
    public String asSummaryString() {

        return "Http Source";
    }


    @Override
    public ChangelogMode getChangelogMode() {
        return ChangelogMode.newBuilder()
                .addContainedKind(RowKind.INSERT)
                .addContainedKind(RowKind.UPDATE_BEFORE)
                .addContainedKind(RowKind.UPDATE_AFTER)
                .addContainedKind(RowKind.DELETE)
                .build();
    }

    @Override
    public ScanRuntimeProvider getScanRuntimeProvider(ScanContext scanContext) {


        if ("file".equalsIgnoreCase(config.getOptional(HttpConnectorOptions.FORMAT).isPresent() ?
                config.getOptional(HttpConnectorOptions.FORMAT).get() : "file")) {
            if ("csv".equalsIgnoreCase(config.getOptional(HttpConnectorOptions.FORMAT_FILE_TYPE).isPresent() ?
                    config.getOptional(HttpConnectorOptions.FORMAT_FILE_TYPE).get() : "csv")) {
                HttpCsvFunction func = new HttpCsvFunction(config);
                return SourceFunctionProvider.of(func, false);
            }
        }
        return null;
    }
}

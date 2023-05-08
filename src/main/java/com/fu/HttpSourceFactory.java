package com.fu;

import org.apache.commons.compress.utils.Sets;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.factories.DynamicTableSourceFactory;
import org.apache.flink.table.factories.FactoryUtil;

import java.util.Set;

public class HttpSourceFactory implements DynamicTableSourceFactory {


    @Override
    public DynamicTableSource createDynamicTableSource(Context context) {
        FactoryUtil.TableFactoryHelper helper =
                FactoryUtil.createTableFactoryHelper(this, context);
        ReadableConfig readableConfig = helper.getOptions();

        // 获取元数据信息
        TableSchema schema = context.getCatalogTable().getSchema();

//        helper.validateExcept();

        return new HttpFileSource(readableConfig);

    }

    @Override
    public String factoryIdentifier() {
        return "http";
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {

        return Sets.newHashSet(HttpConnectorOptions.URL);
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        return Sets.newHashSet(HttpConnectorOptions.METHOD,
                HttpConnectorOptions.FORMAT,
                HttpConnectorOptions.FORMAT_FILE_TYPE,
                HttpConnectorOptions.FORMAT_FILE_CHARSET
                );
    }
}

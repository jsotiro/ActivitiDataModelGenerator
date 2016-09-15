package com.alfresco.activiti.com.alfresco.activiti.datamodels.utils;

import java.sql.Types;

/**
 * Created by yanni on 14/09/2016.
 */
public class TypeMapper {

    public static String getSqlTypeName(int type) {
        switch (type) {
            case Types.BIT:
                return "number";
            case Types.TINYINT:
                return "number";
            case Types.SMALLINT:
                return "number";
            case Types.INTEGER:
                return "number";
            case Types.BIGINT:
                return "number";
            case Types.FLOAT:
                return "number";
            case Types.REAL:
                return "number";
            case Types.DOUBLE:
                return "number";
            case Types.NUMERIC:
                return "number";
            case Types.DECIMAL:
                return "number";
            case Types.CHAR:
                return "string";
            case Types.VARCHAR:
                return "string";
            case Types.LONGVARCHAR:
                return "string";
            case Types.DATE:
                return "date";
            case Types.TIME:
                return "date";
            case Types.TIMESTAMP:
                return "date";
            case Types.BINARY:
                return "string";
            case Types.VARBINARY:
                return "string";
            case Types.LONGVARBINARY:
                return "string";
            case Types.NULL:
                return "string";
            case Types.OTHER:
                return "string";
            case Types.JAVA_OBJECT:
                return "string";
            case Types.DISTINCT:
                return "string";
            case Types.STRUCT:
                return "string";
            case Types.ARRAY:
                return "string";
            case Types.BLOB:
                return "string";
            case Types.CLOB:
                return "string";
            case Types.REF:
                return "string";
            case Types.DATALINK:
                return "string";
            case Types.BOOLEAN:
                return "number";
            case Types.ROWID:
                return "string";
            case Types.NCHAR:
                return "string";
            case Types.NVARCHAR:
                return "string";
            case Types.LONGNVARCHAR:
                return "string";
            case Types.NCLOB:
                return "string";
            case Types.SQLXML:
                return "string";
        }

        return "?";
    }

}

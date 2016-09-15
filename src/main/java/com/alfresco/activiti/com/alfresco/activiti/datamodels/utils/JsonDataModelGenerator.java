package com.alfresco.activiti.com.alfresco.activiti.datamodels.utils;

/**
 * Created by yanni on 13/09/2016.
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class JsonDataModelGenerator {
   static final Logger logger = LoggerFactory.getLogger(JsonDataModelGenerator.class);
   private Connection connection = null;
   private String selectTemplate = "Select from %s limit 1";

   private ResultSet tables;
   private DatabaseMetaData databaseMetaData;
   private String   catalog          = null;
   private String   schemaPattern    = null;
   private String   tableNamePattern = null;
   private String   columnNamePattern ="%";
   private String[] types            = null;


   public Connection getConnection(){
       return connection;
   }

   public void setConnection(Connection connection) {
       this.connection = connection;
   }


    public String getSelectTemplate() {
        return selectTemplate;
    }

    public void setSelectTemplate(String selectTemplate) {
        this.selectTemplate = selectTemplate;
    }

    private void getConnectionMetadata() throws SQLException {
       databaseMetaData = connection.getMetaData();
       tables = databaseMetaData.getTables(
       catalog, schemaPattern, tableNamePattern, types );

   }


    public JSONObject getModel() throws SQLException {
        getConnectionMetadata();
        JSONObject jsonModel = new JSONObject();
        jsonModel.put("id",0);
        jsonModel.put("dataModelDefinition",getModelDefinition());

        jsonModel.put("dataSourceName",connection.getCatalog());
        jsonModel.put("name",connection.getCatalog());

        return  jsonModel;
    }


    public void export() throws SQLException, IOException {
        export(connection.getCatalog()+".json");
    }

    public void export(String fileName) throws SQLException, IOException {
            FileWriter file = new FileWriter(fileName);
            file.write(getModel().toJSONString());
            file.flush();
            file.close();
    }

    private JSONObject getModelDefinition() throws SQLException {
        JSONObject jsonModelDefinition = new JSONObject();
        jsonModelDefinition.put("dataSourceId",1);
        jsonModelDefinition.put("entities",getEntities(tables));
        jsonModelDefinition.put("name",connection.getCatalog());

        return jsonModelDefinition;
    }

    private JSONArray getEntities(ResultSet tables) {
        JSONArray entities = new JSONArray();
        try {
            while (tables.next()) {
                String tableName = tables.getString(3);
                JSONObject jsonTable = new JSONObject();
                jsonTable.put("name", tableName );
                jsonTable.put("description", "Entity for table " + tableName);
                jsonTable.put("tableName", tableName);
                jsonTable.put("attributes",getEntityAttributes(tableName));
                entities.add(jsonTable);

            }

        } catch (SQLException e) {
            logger.error("ERROR whilst getting entities ",e);
        }
        return entities;

    }

    public Set<String> getPrimaryKeyColumnsForTable(String tableName) throws SQLException {
        ResultSet pkColumns = connection.getMetaData().getPrimaryKeys(null, null, tableName);
         SortedSet<String> pkColumnSet = new TreeSet<String>();
         while (pkColumns.next()) {
                String pkColumnName = pkColumns.getString("COLUMN_NAME");
                pkColumnSet.add(pkColumnName);
            }
            return pkColumnSet;
    }

    public  JSONArray getEntityAttributes(String table) throws SQLException {
        JSONArray attributes= new JSONArray();
        try {
            Set<String> keys = getPrimaryKeyColumnsForTable(table);
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(String.format(selectTemplate,table));
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();
            for(int i = 1; i <=columnCount; i++){
                JSONObject jsonField = new JSONObject();
                jsonField.put("name",metaData.getColumnLabel(i));
                jsonField.put("mappedName",metaData.getColumnName(i));
                jsonField.put("description","");
                jsonField.put("type",TypeMapper.getSqlTypeName(metaData.getColumnType(i)));
                jsonField.put("required",metaData.isNullable(i)==0 && !metaData.isAutoIncrement(i));
                jsonField.put("generatedValue", metaData.isAutoIncrement(i));
                jsonField.put("primaryKey", keys.contains(metaData.getColumnName(i)));
                attributes.add(jsonField);
                }
            }
            catch (SQLException e) {
                logger.error("ERROR whilst getting entity details ",e);
        }
        return attributes;
    }



}

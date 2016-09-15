package com.alfresco.activiti.com.alfresco.activiti.datamodels.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class CLIApp {


    public static void main(String[] argv) throws SQLException, IOException {
        String propsFilename="./generator.properties";
        Properties props = new Properties();
        Class driver = null;
        if ( argv.length > 0 )
            propsFilename = argv[0];
        System.out.println("-------- Activiti JDBC Data CLIApp CLI------------");
        try{
            InputStream inStream = new FileInputStream(propsFilename) ;
            props.load(inStream);
            }
            catch (Exception e) {
                System.out.println("Error reading utils propertes file " + propsFilename);
                System.out.println(e);
            }

        try {
            System.out.println("Looking for JDBC driver " + props.getProperty("driver"));
            driver = Class.forName(props.getProperty("driver"));
            System.out.println("Driver found and loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR. JDBC Driver " + props.getProperty("driver") + " not found");
            e.printStackTrace();
            return;
        }

        System.out.println("JDBC Driver " + driver.getName() + " Registered");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(props.getProperty("url"),props.getProperty("username"), props.getProperty("password"));

        } catch (SQLException e) {
            System.out.println("ERROR. Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }


        if (connection != null) {
            System.out.println("Connection established");
            {
               JsonDataModelGenerator modelGenerator = new JsonDataModelGenerator();
               modelGenerator.setConnection(connection);
               modelGenerator.setSelectTemplate(props.getProperty("sql"));
               modelGenerator.export();

               System.out.println("Exported data model to file " + connection.getCatalog()+".json");
            }
        } else {
            System.out.println("ERROR.Failed to make connection!");
        }
    }
}
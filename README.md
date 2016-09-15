<h1 align="center">Activiti Data Model Generator CLI utility</h1>

>Utility to generate Activity BPM data models from RDBMs databases

Activiti BPM Enterprise Suite recently introduced in 1.5 the ability to map database tables as entity models that can then be bound to forms, UIs, process, expressions and updated via a Store Entity task. 

This command line utility allows you to overcome the tedious task of entering data model details. Instead it uses a jdbc connection to the source database and generates the json model to import in Activiti ES 1.5
##  How to use the ActivitiDataModelGenerator utility:
You can either download the source code and use it from your development environment or run the pre-compiled jar and the associated generator.properties file.


```bash
``` java -jar <path to jar>/ActivitiDataModelGenerator.jar
```

The jar file contains all dependencies, including the jdbc drivers for MySQL, Postgress, SQLLite, H2, MS SQL, Oracle. You need to adjust the generator.properties file to your requirements and save it to your working directory (i.e. where you execute the jar from)


## Configuring the generator.properties file

The generation of the json model is driven by the generator.properties file. The file is expected to be in the working directory and allows you to specify the following properties. For MySQL, all you have to do is change the url to point to your database and supply the correct user name and password. For other RDBMS you need to specificy the driver class. 
The utility uses SELECT statements based on the sql template, to get metadata. 
The active one "sql=select * from %s limit 1
" will work for MySQL For Oracle, MSQL Acess 

```importer.properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/sakila?characterEncoding=UTF-8
username=alfresco
password=alfresco
# Use this if the source database is MySQL, SQL lite, Postgress, H2
sql=select * from %s limit 1
# Use this if the source database is Oracle
#sql=select * from {%s} WHERE ROWNUM<=1
# Use this if the source database is SQL Server & Access
#sql=select TOP 1 * from {%s}
```

##  Generate a model

##  Import the generated model in Activiti


First, move in the folder where you want create your project.

```bash
yo ng2-alfresco-component
```

<img src="assets/generator.png" alt='alfresco generator' >

## License
[MIT](ponent/blob/master/LICENSE)
 

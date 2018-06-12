package eu.nimble.service.tracking.impl;
/*
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.types.DataTypes.*;

import java.util.Arrays;
import java.util.List;


public class DataFrameTest {

	
	public static void main(String[] args){

		DataFrameTest test = new DataFrameTest();
		test.getDataFrame().show();
		
		System.out.println("Hello World");
		
	}
	
	private Dataset getDataFrame() {

		SparkSession spark = SparkSession
				  .builder()
				  .appName("Java Spark SQL basic example")
				  .config("spark.some.config.option", "some-value")
				  .getOrCreate();
				
        StructType schema = createStructType(new StructField[]{
                createStructField("id", IntegerType, false),
                createStructField("step1", LongType, false),
                createStructField("step2", LongType, false),
                createStructField("step3", LongType, false),
                createStructField("step4", LongType, false),

        });
        
        List<Row> trainingData = Arrays.asList(
        		RowFactory.create(1, 1, 2, 3, 4),
        		RowFactory.create(1, 1, 4, 7, 10)  
        );

        
     // Apply the schema to the RDD
        Dataset<Row> testDataFrame = spark.createDataFrame(trainingData, schema);
        
        return testDataFrame;
    }
}
*/
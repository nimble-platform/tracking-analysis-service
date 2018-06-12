package eu.nimble.service.tracking.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import joinery.DataFrame;
import joinery.DataFrame.Function;

public class NDDataSetTest {

	public static void main(String[] args){
		DataFrame<Object> df = new DataFrame<>("step1", "step2", "step3", "step4");
	
		df.append("row1", Arrays.asList(1, 2, 3, 4));
		df.append("row2", Arrays.asList(1, 4, 7, 10));
		df.append("row3", Arrays.asList(1));
//		df.apply(new Function() { 
//			          public Object apply(Object value) {
//			        	  		if(value == null)
//			        	  			return 0;
//								return value;
//				          }
//				      });
		System.out.println(df.get("row3", "step1") == null);
		System.out.println(df.index());
		System.out.println(df.get("row3", "step2") == null);
		
		// step 1 duration, need step 1 and step 2 data
		List<Object> step1List = df.col("step1");
		List<Object> step2List = df.col("step2");
		
		List<Object> result = new ArrayList();
		long sum = 0;
		int validNum = 0;
		for (int i = 0; i < step1List.size(); i++) {
		   if (step1List.get(i) != null && step2List.get(i) != null) {
			   validNum +=1;
			   sum += (Long.parseLong(step2List.get(i).toString()) - Long.parseLong(step1List.get(i).toString()));
		   }
		}	
		
		long avg = sum/validNum;
		System.out.println("avg val: " + avg);

//		
//		// remove index with null
//		List validStep1 = new ArrayList();
//		List validStep2 = new ArrayList();
//		for (int i = 0; i < step1List.size(); i++) {
//			   if (step1List.get(i) != null && step2List.get(i) != null) {
//				   validStep1.add(step1List.get(i));
//				   validStep2.add(step2List.get(i));
//			   }
//			}	
//		
//		//System.out.println(df.get("row3", "step1") == null);
//		
//		INDArray nd = Nd4j.create(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, new int[]{2, 6});
	}
	
}



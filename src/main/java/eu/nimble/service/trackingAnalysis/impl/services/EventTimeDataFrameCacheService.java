//package eu.nimble.service.trackingAnalysis.impl.services;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//
///**
// * This class is used to cache the history tracking data for a given product class.
// * So that it is then not necessary to retrieve tracking data for all history product items each time on analysis.
// *
// * @author dqu
// *
// */
//@Component
//public class EventTimeDataFrameCacheService {
//		// in mill seconds
//		final long ONE_DAY = 24 * 60 * 60 * 1000;
//		// todo: get the value from spring property value
//		final long FIVE_MIN = 5 * 60 * 1000;
//
//
//		/**
//		 * Key is product class, value is the T&T event time data frame.
//		 *
//		 * Within the data frame:
//		 * 	Column label are the step IDs in the process template; row labels are the product IDs i.e. EPC codes.
//		 * 	Values in a data frame are the event times (in mill seconds) of respective steps of a specific product ID.
//		 */
//		private static Map<String, DataFrame<Long>> eventTimeDataFrameStore = new HashMap<String, DataFrame<Long>>();
//
//
//	    @CachePut(value="ttEvents", key="#productClass")
//	    public DataFrame<Long> putEventTimeDataFrame(String productClass, DataFrame<Long> df){
//	    	eventTimeDataFrameStore.put(productClass, df );
//	        return df;
//	    }
//
//	    @Cacheable(value="ttEvents", key="#productClass")
//	    public DataFrame<Long> get(String productClass){
//	        DataFrame<Long> df = eventTimeDataFrameStore.get(productClass);
//	        return df;
//	    }
//
//	    @CacheEvict(value = "ttEvents", key = "#productClass")
//	    public void evict(String productClass){
//	    }
//
//	    @Scheduled(fixedDelay  = FIVE_MIN)
//	    @CacheEvict(allEntries = true, value = "ttEvents")
//	    public void clearCache() {
//	    }
//}

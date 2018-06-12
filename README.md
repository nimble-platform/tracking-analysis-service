# Tracking Analysis Service
This service is used to get estimated duration of each production process step based on 
the analysis on the tracking records of history product items.
It assume that each product class has one production process template, and each product items belongs to the same product 
class has therefore the same production process template. 
It runs on the NIMBLE platform and will call the T&T APIs that defined as interface between companies and the NIMBLE platform.
Definition of the T&T API can be found under: https://app.swaggerhub.com/apis/BIB1/NIMBLE_Tracking/0.0.2 
The T&T APIs should be implemented by companies and published to NIMBLE platform during product catalogue publishing. 
	

 For example, we have now an order with product EPC code "Product_1", which belongs to published product 
 "Product_bathroom", and has the process template: enter-->production--->shipping--->Delivered. And according to the T 
  & T Events, it is at Moment in the step "production", and we need to use T & T analysis service to calculate the 
 duration from "production" to "shipping" , as well as to "Delivered".
  
 In history orders in NIMBLE platform, there are an order with product "Product_bathroom" with EPC Code "Product_2",  
 "Product_3", and another order with product "NIMBLE_BATH" with EPC Code "NIMBLE_MAGIC". In this case, NIMBLE platform 
 know that the Code "Product_2",  "Product_3" belongs to the same product and share the same process template, but not 
 "NIMBLE_MAGIC". So the NIMBLE platform i.e. your API send us the code "Product_2",  "Product_3" . We can then gather 
 the T & T Events from "Product_2",  "Product_3" to have a calculation. For example, for code "Product_2", the step 
 "production" has taken 1 day, and for code  "Product_3"  it has taken 3 days, then we can estimate that for the 
 current code "Product_1", it can take around 2 days.


you can run this service as a Docker with the following command (just an example):
	1. mvn clean package
	2. mvn docker:build
	3. docker run –p 8118:8091  -e BP_TRACKING_HOST=nimble-staging.salzburgresearch.at -e BP_TRACKING_PORT=80 tracking-analysis-service:0.0.3   (BP_TRACKING_HOST and BP_TRACKING_PORT is the host and port of the NIMBLE service, which provides tracking meta data for each product item.)


	

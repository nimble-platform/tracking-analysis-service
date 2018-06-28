package eu.nimble.service.trackingAnalysis.impl.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import eu.nimble.service.trackingAnalysis.impl.dao.EPCISObjectEvent;
import eu.nimble.service.trackingAnalysis.impl.dao.EPCTrackingMetaData;
import eu.nimble.service.trackingAnalysis.impl.dao.ProductTrackingResult;
import eu.nimble.service.trackingAnalysis.impl.dao.ProductionProcessStep;
import eu.nimble.service.trackingAnalysis.impl.dao.ProductionProcessTemplate;
import eu.nimble.service.trackingAnalysis.impl.services.TrackingAnalysisService;

@CrossOrigin()
@RestController
public class TrackingAnalysisController {

    private static Logger log = LoggerFactory.getLogger(TrackingAnalysisController.class);
    
    @Autowired
    TrackingAnalysisService srv;
   
    /**
     * Get tracking analysis result for a given product item.
     * 
     * @param itemID product EPC code, e.g. urn:epc:id:sgtin:0614141.lindback.testproduct
     * @return On success: JSON structure, which is a list of process steps from its production process template, 
     * 		with attributes to indicate if a process step has been triggered, and if not, when will the step be triggered as estimated/analyzed.
     * e.g. 
     * [  
		   {  
		      "estimatedEventTime":null,
		      "stepTriggered":true,
		      "id":"1",
		      "hasPrev":"",
		      "readPoint":"urn:epc:id:sgln:readPoint.lindbacks.1",
		      "bizLocation":"urn:epc:id:sgln:bizLocation.lindbacks.2",
		      "bizStep":"urn:epcglobal:cbv:bizstep:other",
		      "hasNext":"2"
		   },
		   {  
		      "estimatedEventTime":{  
		         "$date":1525833211116
		      },
		      "stepTriggered":false,
		      "id":"2",
		      "hasPrev":"1",
		      "readPoint":"urn:epc:id:sgln:readPoint.lindbacks.2",
		      "bizLocation":"urn:epc:id:sgln:bizLocation.lindbacks.3",
		      "bizStep":"urn:epcglobal:cbv:bizstep:installing",
		      "hasNext":"3"
		   },
		   {  
		      "estimatedEventTime":{  
		         "$date":1525919611116
		      },
		      "stepTriggered":false,
		      "id":"3",
		      "hasPrev":"2",
		      "readPoint":"urn:epc:id:sgln:readPoint.lindbacks.3",
		      "bizLocation":"urn:epc:id:sgln:bizLocation.lindbacks.4",
		      "bizStep":"urn:epcglobal:cbv:bizstep:entering_exiting",
		      "hasNext":"4"
		   },
		   {  
		      "estimatedEventTime":{  
		         "$date":1526092411116
		      },
		      "stepTriggered":false,
		      "id":"4",
		      "hasPrev":"3",
		      "readPoint":"urn:epc:id:sgln:readPoint.lindbacks.4",
		      "bizLocation":"urn:epc:id:sgln:bizLocation.lindbacks.5",
		      "bizStep":"urn:epcglobal:cbv:bizstep:shipping",
		      "hasNext":""
		   }
		]
		On Failure: An empty list will be returned, when the product item is not found.
     */
    @RequestMapping("/simpleTrackingAnalysis/{itemID:.+}")
    public List<ProductionProcessStep> simpleTrackingAnalysis(@PathVariable String itemID, @RequestHeader(value = "Authorization") String bearer) {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", bearer);
    	
    	ProductTrackingResult trackingResult = srv.getProductTrackingResult(itemID, headers);
    	if(trackingResult.getEpcisObjEvents().isEmpty())
    	{
    		log.info("No tracking records for the item with EPC:" + itemID);
    		return new ArrayList<ProductionProcessStep>();
    	}
    	
    	EPCISObjectEvent lastObjEvent = trackingResult.getLastEvent();
    	
    	ProductionProcessTemplate procTemplate = srv.getProductionProcessTemplateForEPC(itemID, headers);
    	EPCTrackingMetaData tMetadata = srv.getTrackingMetaDataForEPC(itemID, headers);
    	String productClass = tMetadata.getRelatedProductId();
    	
    	ProductionProcessStep lastProcStep = trackingResult.getMatchedProcStepForEvent(lastObjEvent, procTemplate);
    	List<ProductionProcessStep> unfinishedProcSteps = procTemplate.getStepsAfter(lastProcStep);
    	
    	for(ProductionProcessStep step: unfinishedProcSteps)
    	{
    		long duration = srv.getDurationBetweenProcessSteps(productClass, procTemplate, lastProcStep, step, headers);
    		step.setStepTriggered(false);
    		long estimatedEventTime = lastObjEvent.getEventTime().getDate() + duration;
    		step.setEstimatedEventTime(estimatedEventTime);
    	}
    	return procTemplate.getSteps();
    }
    
}

package eu.nimble.service.trackingAnalysis.impl.dao;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Production process template.
 * 
 * Assumption: one start step, a single final step, and no loop steps.
 * 
 * @author dqu
 *
 */
public class ProductionProcessTemplate {

	private List<ProductionProcessStep> steps;
	

	public List<ProductionProcessStep> getSteps() {
		return steps;
	}

	public void setSteps(List<ProductionProcessStep> steps) {
		this.steps = steps;
	}
		
	public ProductionProcessStep getFirstStep()
	{
		Optional<ProductionProcessStep> matchingObject = steps.stream().
			    filter(step -> step.getHasPrev().trim().isEmpty()).
			    findFirst();
		
		return matchingObject.orElse(null);
	}
	
	public ProductionProcessStep getFinalStep()
	{
		Optional<ProductionProcessStep> matchingObject = steps.stream().
			    filter(step -> step.getHasNext().trim().isEmpty()).
			    findFirst();
		
		return matchingObject.orElse(null);
	}
	
	/**
	 * Get steps direct next to the given process step
	 * @param curStep
	 * @return
	 */
	public List<ProductionProcessStep> getNextStepsOf(ProductionProcessStep curStep)
	{
		String curStepID = curStep.getId();
		
		List<ProductionProcessStep> matchingObjects = steps.stream().
			    filter(step -> step.getHasPrev().equals(curStepID)).
			    collect(Collectors.toList());
		
		return matchingObjects;
	}
	
	/**
	 * Get all steps after the given process step
	 * @param curStep
	 * @return
	 */
	public List<ProductionProcessStep> getStepsAfter(ProductionProcessStep curStep)
	{
		List<ProductionProcessStep> nextSteps = new ArrayList<ProductionProcessStep>();
		
		Deque<ProductionProcessStep> stepStack = new ArrayDeque<ProductionProcessStep>();
		
		List<ProductionProcessStep> directNextSteps = this.getNextStepsOf(curStep);
		for(ProductionProcessStep directNextStep: directNextSteps)
		{
			stepStack.push(directNextStep);
		}
		
		while(!stepStack.isEmpty())
		{
			ProductionProcessStep nextStep = stepStack.pop();
			if(!nextSteps.contains(nextStep))
			{
				nextSteps.add(nextStep);
			}
			
			for(ProductionProcessStep step: this.getNextStepsOf(nextStep))
			{
				stepStack.push(step);
			}
		}
		
		return nextSteps;
	}
	
	public List<ProductionProcessStep> getPrevStepsOf(ProductionProcessStep curStep)
	{
		String curStepID = curStep.getId();
		
		List<ProductionProcessStep> matchingObjects = steps.stream().
			    filter(step -> step.getHasNext().equals(curStepID)).
			    collect(Collectors.toList());
		
		return matchingObjects;
	}
}

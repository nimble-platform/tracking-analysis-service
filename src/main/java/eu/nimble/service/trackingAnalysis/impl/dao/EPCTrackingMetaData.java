package eu.nimble.service.trackingAnalysis.impl.dao;

public class EPCTrackingMetaData
{
    private String masterUrl;

    private String productionProcessTemplate;

    private String eventUrl;

    private String relatedProductId;

    public String getMasterUrl ()
    {
        return masterUrl;
    }

    public void setMasterUrl (String masterUrl)
    {
        this.masterUrl = masterUrl;
    }

    public String getProductionProcessTemplate ()
    {
        return productionProcessTemplate;
    }

    public void setProductionProcessTemplate (String productionProcessTemplate)
    {
        this.productionProcessTemplate = productionProcessTemplate;
    }

    public String getEventUrl ()
    {
        return eventUrl;
    }

    public void setEventUrl (String eventUrl)
    {
        this.eventUrl = eventUrl;
    }

    public String getRelatedProductId ()
    {
        return relatedProductId;
    }

    public void setRelatedProductId (String relatedProductId)
    {
        this.relatedProductId = relatedProductId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [masterUrl = "+masterUrl+", productionProcessTemplate = "+productionProcessTemplate+", eventUrl = "+eventUrl+", relatedProductId = "+relatedProductId+"]";
    }
}

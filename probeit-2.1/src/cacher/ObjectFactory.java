
package cacher;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cacher package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetThumbnailCachedNodesetList_QNAME = new QName("http://cacher/", "getThumbnailCachedNodesetList");
    private final static QName _GetVisualizations_QNAME = new QName("http://cacher/", "getVisualizations");
    private final static QName _GetThumbnail_QNAME = new QName("http://cacher/", "getThumbnail");
    private final static QName _GetThumbnailResponse_QNAME = new QName("http://cacher/", "getThumbnailResponse");
    private final static QName _GetThumbnailCachedNodesetListResponse_QNAME = new QName("http://cacher/", "getThumbnailCachedNodesetListResponse");
    private final static QName _GetViewersResponse_QNAME = new QName("http://cacher/", "getViewersResponse");
    private final static QName _GetVisualizationsResponse_QNAME = new QName("http://cacher/", "getVisualizationsResponse");
    private final static QName _GetViewers_QNAME = new QName("http://cacher/", "getViewers");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cacher
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetThumbnail }
     * 
     */
    public GetThumbnail createGetThumbnail() {
        return new GetThumbnail();
    }

    /**
     * Create an instance of {@link GetViewersResponse }
     * 
     */
    public GetViewersResponse createGetViewersResponse() {
        return new GetViewersResponse();
    }

    /**
     * Create an instance of {@link GetViewers }
     * 
     */
    public GetViewers createGetViewers() {
        return new GetViewers();
    }

    /**
     * Create an instance of {@link GetVisualizationsResponse }
     * 
     */
    public GetVisualizationsResponse createGetVisualizationsResponse() {
        return new GetVisualizationsResponse();
    }

    /**
     * Create an instance of {@link GetThumbnailResponse }
     * 
     */
    public GetThumbnailResponse createGetThumbnailResponse() {
        return new GetThumbnailResponse();
    }

    /**
     * Create an instance of {@link GetThumbnailCachedNodesetListResponse }
     * 
     */
    public GetThumbnailCachedNodesetListResponse createGetThumbnailCachedNodesetListResponse() {
        return new GetThumbnailCachedNodesetListResponse();
    }

    /**
     * Create an instance of {@link GetThumbnailCachedNodesetList }
     * 
     */
    public GetThumbnailCachedNodesetList createGetThumbnailCachedNodesetList() {
        return new GetThumbnailCachedNodesetList();
    }

    /**
     * Create an instance of {@link GetVisualizations }
     * 
     */
    public GetVisualizations createGetVisualizations() {
        return new GetVisualizations();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetThumbnailCachedNodesetList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cacher/", name = "getThumbnailCachedNodesetList")
    public JAXBElement<GetThumbnailCachedNodesetList> createGetThumbnailCachedNodesetList(GetThumbnailCachedNodesetList value) {
        return new JAXBElement<GetThumbnailCachedNodesetList>(_GetThumbnailCachedNodesetList_QNAME, GetThumbnailCachedNodesetList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVisualizations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cacher/", name = "getVisualizations")
    public JAXBElement<GetVisualizations> createGetVisualizations(GetVisualizations value) {
        return new JAXBElement<GetVisualizations>(_GetVisualizations_QNAME, GetVisualizations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetThumbnail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cacher/", name = "getThumbnail")
    public JAXBElement<GetThumbnail> createGetThumbnail(GetThumbnail value) {
        return new JAXBElement<GetThumbnail>(_GetThumbnail_QNAME, GetThumbnail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetThumbnailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cacher/", name = "getThumbnailResponse")
    public JAXBElement<GetThumbnailResponse> createGetThumbnailResponse(GetThumbnailResponse value) {
        return new JAXBElement<GetThumbnailResponse>(_GetThumbnailResponse_QNAME, GetThumbnailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetThumbnailCachedNodesetListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cacher/", name = "getThumbnailCachedNodesetListResponse")
    public JAXBElement<GetThumbnailCachedNodesetListResponse> createGetThumbnailCachedNodesetListResponse(GetThumbnailCachedNodesetListResponse value) {
        return new JAXBElement<GetThumbnailCachedNodesetListResponse>(_GetThumbnailCachedNodesetListResponse_QNAME, GetThumbnailCachedNodesetListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetViewersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cacher/", name = "getViewersResponse")
    public JAXBElement<GetViewersResponse> createGetViewersResponse(GetViewersResponse value) {
        return new JAXBElement<GetViewersResponse>(_GetViewersResponse_QNAME, GetViewersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVisualizationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cacher/", name = "getVisualizationsResponse")
    public JAXBElement<GetVisualizationsResponse> createGetVisualizationsResponse(GetVisualizationsResponse value) {
        return new JAXBElement<GetVisualizationsResponse>(_GetVisualizationsResponse_QNAME, GetVisualizationsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetViewers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cacher/", name = "getViewers")
    public JAXBElement<GetViewers> createGetViewers(GetViewers value) {
        return new JAXBElement<GetViewers>(_GetViewers_QNAME, GetViewers.class, null, value);
    }

}

/**
 * 
 */
package probeIt.ui.workflow;

/**
 * @author Leonardo Salayandia
 *
 */
public class URI {

	/**
	 * Gets the local name portion of a URI of the format: namespace#localname.
	 * @param uri
	 * @return null if uri is null, empty string if uri does not contain # char, localname portion of uri otherwise.
	 */
	public static String getLocalName(String uri) {
		String localname = null;
		if (uri != null) {
			int idx = uri.indexOf('#');
			localname = (idx < 0) ? "" : uri.substring(idx+1);
		}
		return localname;
	}
	
	/**
	 * Get the namespace portion of a URI of the format: namespace#localname.
	 * @param uri
	 * @return null if uri is null, the original uri argument if it does not contain the # char, namespace portion of uri otherwise.
	 */
	public static String getNameSpace(String uri) {
		String ns = null;
		if (uri != null) {
			int idx = uri.indexOf('#');
			ns = (idx < 0) ? uri : uri.substring(0,idx);
		}
		return ns;
	}
}

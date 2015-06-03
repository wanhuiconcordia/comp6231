/**
 * 
 */
package tools;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

/**
 * @author Ting Zhang
 * @date 2015-05-25
 */
public class ProductList implements Serializable{
	Map<String , Product> productList = new HashMap<>();
	
	public Map<String,Product> getProductList(){
		return productList;
	}
	
	/**
	 * Load from XML file, initialize the product object with specified productType according to the content in XML file
	 * 
	 * @param product type
	 * @throws Exception 
	 */
	/*
	public Product getProductInfo(String typ, String manufacturerName) throws Exception {
		if(typ!="DVD player"&&typ!="video camera"&&typ!="TV")
			throw new Exception("Error input of product type!");
		
		String filepath = new String(System.getProperty("user.dir") + "/src/xml/" + manufacturerName + "_products.xml");
		FileManager xmlfile = new FileManager(filepath);
		Element root = xmlfile.Read();
		List<Element> nodes = root.elements("product");
		for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
			Element me = (Element) it.next();
			Product np = new Product();
			np = Product.load(me);
			np.setManufacturerName(manufacturerName);
			System.out.println(np.getProductType());
			if(np.getProductType().equals(typ))
				return np;		
			
		}
		System.out.println("type not found in xml file"+filepath);
		return null;
	}
	*/
	
	/**
	 * Load from productList  the product  with the specified productType 
	 * 
	 * @param product type
	 * 
	 * @return Product
	 * 
	 * @throws Exception 
	 */
	public Product getProductInfo(String typ) throws Exception {
		if(typ.equalsIgnoreCase("DVD player")||typ.equalsIgnoreCase("video camera")||typ.equalsIgnoreCase("TV"))
			return productList.get(typ);	

		return null;		
		
	}
	
	/**
	 * Load all the products of a specific manufacturer info productList from the XML file
	 * 
	 * @param manufacturer name
	 *           
	 * @throws Exception
	
	 */
   public void loadProducts(String mfn)throws Exception{
		
		String filepath = new String(System.getProperty("user.dir")
				+ "/src/com/manufacturer/Data/" + mfn + "_products.xml");
		FileManager xmlfile = new FileManager(filepath);
		Element root = xmlfile.Read();
		List<Element> nodes = root.elements("product");
		for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
			Element me = (Element) it.next();
			Product nm = new Product();
			nm = Product.load(me);
			nm.setManufacturerName(mfn);
			//System.out.println(nm);
			productList.put(nm.getProductType(),nm );
		}
		
	}


}

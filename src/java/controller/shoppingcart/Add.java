/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.shoppingcart;

import controller.BaseController;
import dal.ProductDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.InvoiceLine;
import model.Product;

/**
 *
 * @author sonnt
 */
public class Add extends BaseController {

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productid = Integer.parseInt( request.getParameter("productid"));
        ProductDAO db = new ProductDAO();
        Product product = db.get(productid);
        HttpSession session = request.getSession(true);
        ArrayList<InvoiceLine> lines = (ArrayList<InvoiceLine> )
                session.getAttribute("shoppingcart");
        if(lines ==null)
            lines = new ArrayList<>();
        
        int indexOfProduct = -1;
        for (int i = 0;i< lines.size();i++) {
            InvoiceLine line  = lines.get(i);
            if(line.getProduct().getId() == product.getId())
            {
                indexOfProduct = i;
                break;
            }
        }
        
        if(indexOfProduct == -1) // case of product does not exsits in the shopping cart
        {
            InvoiceLine line = new InvoiceLine();
            line.setProduct(product);
            line.setQuantity(1);
            line.setUnitprice(product.getPrice());
            lines.add(line);
        }
        else
        {
            lines.get(indexOfProduct).setQuantity(lines.get(indexOfProduct)
                    .getQuantity()+1);
        }
        session.setAttribute("shoppingcart", lines);
        response.sendRedirect("../product/list");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.shoppingcart;

import controller.BaseController;
import dal.CustomerDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Customer;
import model.InvoiceLine;

/**
 *
 * @author sonnt
 */
public class View extends BaseController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<InvoiceLine> lines = ( ArrayList<InvoiceLine>)
                request.getSession(true).getAttribute("shoppingcart");
        if(lines == null)
            lines = new ArrayList<>();
        CustomerDAO db = new CustomerDAO();
        ArrayList<Customer> customers = db.getAll();
        request.setAttribute("customers", customers);
        request.setAttribute("lines", lines);
        request.getRequestDispatcher("../view/shoppingcart/View.jsp").forward(request, response);
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}

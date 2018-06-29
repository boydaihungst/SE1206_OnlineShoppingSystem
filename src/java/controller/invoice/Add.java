/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.invoice;

import controller.BaseController;
import dal.CustomerDAO;
import dal.InvoiceDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Customer;
import model.Invoice;
import model.InvoiceLine;

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
        String paymentmethod = request.getParameter("paymentmethod");
        int customerid = Integer.parseInt(request.getParameter("customerid"));
        CustomerDAO custDAO = new CustomerDAO();
        Customer customer = custDAO.get(customerid);
        
        HttpSession session = request.getSession(true);
        ArrayList<InvoiceLine> lines =(ArrayList<InvoiceLine>) 
                session.getAttribute("shoppingcart");
        
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setLines(lines);
        invoice.setPaymentmethod(paymentmethod);
        //insert invoice
        InvoiceDAO invDB = new InvoiceDAO();
        invDB.insert(invoice);
        //-- response to user
        session.setAttribute("shoppingcart", null);//reset order
        
        
        
    }
    
}

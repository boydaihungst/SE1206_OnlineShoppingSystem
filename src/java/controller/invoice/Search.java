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
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Customer;
import model.Invoice;

/**
 *
 * @author sonnt
 */
public class Search extends BaseController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDAO custDB = new CustomerDAO();
        ArrayList<Customer> customers = custDB.getAll();
        request.setAttribute("customers",customers);
        
        //invoiceid=&customerid=-1&from=&to=&paymentmethod=&status=open
        //load invoice id
        String invoiceid = request.getParameter("invoiceid");
        if(invoiceid ==null || invoiceid.trim().length()==0)
        {
            invoiceid = "-1";
        }
        //load customer id
        String customerid = request.getParameter("customerid");
        if(customerid ==null || customerid.trim().length()==0)
        {
            customerid = "-1";
        }
        //load From 
        String raw_from = request.getParameter("from");
        Date from = null;
        if(raw_from !=null && raw_from.trim().length()>0)
        {
            from = Date.valueOf(raw_from);
        }
        
        //load to 
        String raw_to = request.getParameter("to");
        Date to = null;
        if(raw_to !=null && raw_to.trim().length()>0)
        {
            to = Date.valueOf(raw_to);
        }
        
        //load payment method
         String paymentmethod = request.getParameter("paymentmethod");
        //load status
        String status = request.getParameter("status");
        InvoiceDAO invoiceDB = new InvoiceDAO();
        ArrayList<Invoice> invoices = invoiceDB.search(Integer.parseInt(invoiceid), from, to, 
                paymentmethod, Integer.parseInt(customerid), status);
        
        request.setAttribute("invoices",invoices);
        request.getRequestDispatcher("../view/invoice/Search.jsp").forward(request, response);
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

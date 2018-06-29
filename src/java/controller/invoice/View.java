/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.invoice;

import dal.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.InvoiceLine;

/**
 *
 * @author DrAgOn
 */
public class View extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InvoiceDAO iDAO = new InvoiceDAO();
        
        String invoiceId = request.getParameter("invoiceid");
        ArrayList<InvoiceLine> invoiceLines = iDAO.getInvoiceLinesByInvoiceID(Integer.parseInt(invoiceId));
        request.setAttribute("invoiceLines", invoiceLines);
        request.getRequestDispatcher("/view/invoice/View.jsp").forward(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("view/invoice/View.jsp").forward(request, response);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Invoice;
import model.InvoiceLine;
import model.Product;

/**
 *
 * @author sonnt
 */
public class InvoiceDAO extends BaseDAO<Invoice> {

    @Override
    public ArrayList<Invoice> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Invoice get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Invoice model) {
        try {
            connection.setAutoCommit(false);
            //insert Invoice
            String sql_insert_invoice = "INSERT INTO [Invoices]\n"
                    + "           ([orderdate]\n"
                    + "           ,[paymentmethod]\n"
                    + "           ,[customerid]\n"
                    + "             ,[status])\n"
                    + "     VALUES\n"
                    + "           (GETDATE()\n"
                    + "           ,?\n"
                    + "           ,?,'open')";
            PreparedStatement cm_insert_invoice
                    = connection.prepareStatement(sql_insert_invoice);
            cm_insert_invoice.setString(1, model.getPaymentmethod());
            cm_insert_invoice.setInt(2, model.getCustomer().getId());
            cm_insert_invoice.executeUpdate();
            //get Invoice ID
            String sql_select_invoiceid = "SELECT @@IDENTITY as id";
            PreparedStatement cm_select_invoiceid
                    = connection.prepareStatement(sql_select_invoiceid);
            ResultSet rs = cm_select_invoiceid.executeQuery();
            if (rs.next()) {
                model.setId(rs.getInt("id"));
            }
            // insert invoice lines

            for (InvoiceLine line : model.getLines()) {
                String sql_insert_line = "INSERT INTO [InvoiceLines]\n"
                        + "           ([invoiceid]\n"
                        + "           ,[productid]\n"
                        + "           ,[quantity]\n"
                        + "           ,[unitprice])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?)";
                PreparedStatement cm_insert_line
                        = connection.prepareStatement(sql_insert_line);
                cm_insert_line.setInt(1, model.getId());
                cm_insert_line.setInt(2, line.getProduct().getId());
                cm_insert_line.setFloat(3, line.getQuantity());
                cm_insert_line.setFloat(4, line.getUnitprice());
                cm_insert_line.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ArrayList<InvoiceLine> getInvoiceLinesByInvoiceID(int invoiceId) {
        ArrayList<InvoiceLine> invoiceLines = null;
        try {
            invoiceLines = new ArrayList<>();
            String sql = "	select * from (SELECT p.name,\n"
                    + "			li.invoiceid,\n"
                    + "			li.quantity,\n"
                    + "			li.unitprice\n"
                    + "	  FROM [InvoiceLines] as li inner join Products as p \n"
                    + "	  ON li.productid=p.id AND li.invoiceid=?) f inner join Invoices i \n"
                    + "	  on f.invoiceid=i.id";
            PreparedStatement command = connection.prepareStatement(sql);
            command.setInt(1, invoiceId);
            ResultSet rs = command.executeQuery();
            while (rs.next()) {
                InvoiceLine il = new InvoiceLine();
                Invoice i = new Invoice();
                Product p = new Product();
                i.setId(rs.getInt("invoiceid"));
                p.setName(rs.getString("name"));
                il.setInvoice(i);
                il.setProduct(p);
                il.setQuantity(rs.getFloat("quantity"));
                il.setUnitprice(rs.getFloat("unitprice"));
                invoiceLines.add(il);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invoiceLines;
    }

    public ArrayList<Invoice> search(int id, Date from, Date to, String paymentmethod, int customerid, String status) {
        ArrayList<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT i.[id]\n"
                + "      ,i.[orderdate]\n"
                + "      ,i.[paymentmethod]\n"
                + "      ,i.[customerid]\n"
                + "      ,i.[status]\n"
                + "      ,c.name\n"
                + "  FROM [Invoices] i INNER JOIN [Customers] c\n"
                + "  ON i.customerid = c.id\n"
                + "  WHERE 1=1 ";
        int paramIndex = 0;
        HashMap<Integer, Object[]> params = new HashMap<>();
        if (id != -1) {
            sql += " AND i.id = ?";
            paramIndex++;
            Object[] values = {id, "INT"};
            params.put(paramIndex, values);
        }

        if (from != null) {
            sql += " AND i.orderdate >= ? ";
            paramIndex++;
            Object[] values = {from, "DATE"};
            params.put(paramIndex, values);
        }

        if (to != null) {
            sql += " AND i.orderdate<=?";
            paramIndex++;
            Object[] values = {to, "DATE"};
            params.put(paramIndex, values);
        }

        if (paymentmethod != null && paymentmethod.trim().length() > 0) {
            sql += " AND i.paymentmethod like '%'+?+'%'";
            paramIndex++;
            Object[] values = {paymentmethod, "STRING"};
            params.put(paramIndex, values);
        }

        if (status != null && status.trim().length() > 0) {
            sql += " AND i.status = ?";
            paramIndex++;
            Object[] values = {status, "STRING"};
            params.put(paramIndex, values);
        }

        if (customerid != -1) {
            sql += " AND i.customerid = ?";
            paramIndex++;
            Object[] values = {customerid, "INT"};
            params.put(paramIndex, values);
        }

        try {
            PreparedStatement command = connection.prepareStatement(sql);
            Set<Integer> keys = params.keySet();
            for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
                Integer key = iterator.next();
                Object[] value = params.get(key);
                if (value[1].equals("INT")) {
                    command.setInt(key, (Integer) value[0]);
                } else if (value[1].equals("STRING")) {
                    command.setString(key, (String) value[0]);
                } else if (value[1].equals("DATE")) {
                    command.setDate(key, (Date) value[0]);
                }
            }

            ResultSet rs = command.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setOrderdate(rs.getDate("orderdate"));
                invoice.setPaymentmethod(rs.getString("paymentmethod"));
                Customer c = new Customer();
                c.setId(rs.getInt("customerid"));
                c.setName(rs.getString("name"));
                invoice.setCustomer(c);
                invoice.setStatus(rs.getString("status"));
                invoices.add(invoice);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invoices;
    }

}

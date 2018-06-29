<%-- 
    Document   : View
    Created on : Jun 20, 2018, 3:39:06 PM
    Author     : sonnt
--%>

<%@page import="model.Customer"%>
<%@page import="model.InvoiceLine"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>You shopping cart</title>
        <%
            ArrayList<InvoiceLine> lines = ( ArrayList<InvoiceLine>)
            request.getAttribute("lines");
            ArrayList<Customer> customers = ( ArrayList<Customer>)
            request.getAttribute("customers");
        %>
    </head>
    <body>
        
        <form id="frmCheckout" action="../invoice/add" method="POST" >
        Customers: 
        <select name="customerid">
            <%for(Customer c : customers){ %>
            <option value="<%=c.getId()%>"><%=c.getName()%></option>
            <%}%>
        </select> 
        <br/>
        Payment Method: <input type="text" name="paymentmethod"/>
        </form>
        <table>
            <tr>
                <td>Product</td>
                <td>Quantity</td>
                <td>Unit Price</td>
            </tr>
            <%for(InvoiceLine line : lines){ %>
                <tr>
                    <td><%=line.getProduct().getName() %> </td>
                    <td><%=line.getQuantity() %></td>
                    <td><%=line.getUnitprice() %></td>
                 </tr>
            <%}%>
        </table> 
        <input type="button" value="Checkout Order"
               onclick="document.getElementById('frmCheckout').submit();"   
               
               />
    </body>
</html>

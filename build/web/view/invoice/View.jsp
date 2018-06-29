<%-- 
    Document   : View
    Created on : Jun 29, 2018, 9:55:44 AM
    Author     : DrAgOn
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.InvoiceLine"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View invoice</title>
        <%
            ArrayList<InvoiceLine> invoiceLines = (ArrayList<InvoiceLine>) request.getAttribute("invoiceLines");
            float totalPrice=0;
        %>
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Unit</th>
                    <th>Unit price</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (InvoiceLine line : invoiceLines) {%>
                <tr>
                    <td><%=  line.getProduct().getName() %> </td>
                    <td><%=  line.getQuantity()%></td>
                    <td><%=  line.getUnitprice()%></td>
                </tr> 
                
                <% totalPrice+= (line.getQuantity()*line.getUnitprice()); }
                %>
            </tbody>
        </table>
            <div>Total =$<%= totalPrice%> </div>
    </body>
</html>

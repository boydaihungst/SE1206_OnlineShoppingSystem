<%-- 
    Document   : Search
    Created on : Jun 25, 2018, 2:53:20 PM
    Author     : sonnt
--%>

<%@page import="model.Invoice"%>
<%@page import="model.Customer"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invoice Search</title>
        <%
            ArrayList<Customer> customers = (ArrayList<Customer>)
            request.getAttribute("customers");
            
            ArrayList<Invoice> invoices = (ArrayList<Invoice>)
            request.getAttribute("invoices");
        %>
    </head>
    <body>
    <form action="search" method="GET">
        <table border="1">
            <tr>
                <td>
                    ID:
                </td>
                 <td>
                     <input type="number" name="invoiceid"/>
                </td>
            </tr>
            <tr>
                <td>
                    Customers:
                </td>
                 <td>
                     <select name="customerid">
                         <option value="-1">-----ALL-----</option>
                         <%for(Customer c : customers){ %>
                         <option value="<%=c.getId()%>"><%=c.getName()%></option>
                         <%}%>
                     </select>    
                </td>
            </tr>
            <tr>
                <td>
                    From:
                </td>
                 <td>
                     <input type="date" name="from"/>
                </td>
            </tr>
            <tr>
                <td>
                    To:
                </td>
                 <td>
                     <input type="date" name="to"/>
                </td>
            </tr>
            <tr>
                <td>
                    Payment Method:
                </td>
                 <td>
                     <input type="text" name="paymentmethod"/>
                </td>
            </tr>
            <tr>
                <td>
                    Status:
                </td>
                 <td>
                     <select name="status">
                         <option value="">-----ALL-----</option>
                         <option value="open">open</option>
                         <option value="close">close</option>
                         <option value="done">done</option>
                     </select>    
                </td>
            </tr>
            <tr align="right">
                <td colspan="2" >
                    <input type="submit" value="Search" />
                </td>
            </tr>
            
        </table>    
    </form>
    <table border="1">
        <tr> 
            <td>ID</td>
            <td>Order Date</td>
            <td>Payment Method</td>
            <td>Status</td>
            <td>Customer</td>
        </tr>
        <%for(Invoice i : invoices){ %>
        <tr>
            <td><%=i.getId() %></td>
            <td><a href="search?customerid=<%=i.getCustomer().getId() %>"><%=i.getCustomer().getName() %></a></td>
            <td><a href="view?invoiceid=<%=i.getId()%>"><%=i.getOrderdate() %></a></td>
            <td><%=i.getPaymentmethod() %></td>
            <td><%=i.getStatus() %></td>
        </tr>    
        <%}%>
        
    </table>    
                     
    </body>
</html>

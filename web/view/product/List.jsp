<%-- 
    Document   : List
    Created on : Jun 20, 2018, 3:21:26 PM
    Author     : sonnt
--%>

<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List of Products</title>
        <%
            ArrayList<Product> products = (ArrayList<Product>)
            request.getAttribute("products");
        %>
    </head>
    <body>
        <a href="../shoppingcart/view"> View Your Cart</a>
        <%for(Product p : products) {%>
        <div>
            <div><%=p.getName()%></div>
            <div><%=p.getPrice() %></div>
            <div>
                <form action="../shoppingcart/add" method="POST" >
                    <input type="hidden" value="<%=p.getId()%>" name="productid" />
                    <input type="submit" value="Add To Cart"/>
                </form>    
            </div>
            <hr/>
        </div> 
        <%}%>
    </body>
</html>

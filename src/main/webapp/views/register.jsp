<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="L"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="a" %>

<L:Login title="Register">

	<jsp:attribute name="head"></jsp:attribute>
	
	<jsp:attribute name="content">
		<f:form action="registered" modelAttribute="logindb">
			<Table>
				<tr>
					<td>User ID</td>
					<td><f:input path="userId"></f:input></td>
					<td><f:errors path="userId"></f:errors></td>	
				</tr>
				<tr>
					<td>Password</td>
					<td><f:input path="password" type="password"></f:input></td>
					<td><f:errors path="password"></f:errors></td>
				</tr>
				<tr>
					<td>Role</td>
					<td> 
						<f:select id="Roles" path="role">
							<f:option name="customer" value="customer">Customer</f:option>
							<f:option name="branch" value="branch">Branch Admin</f:option>
							<f:option name="admin" value="admin">Admin</f:option>
						</f:select>
					</td>
				</tr>
				<tr>
					<td><input type="submit" value="Create" ></td>
				</tr>
			</Table>
			<a:out value="${msg}"></a:out>
		</f:form> 
	</jsp:attribute>

</L:Login>
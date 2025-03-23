<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="L"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="a" %>

<L:Login title="Verify">

	<jsp:attribute name="head"></jsp:attribute>
	
	<jsp:attribute name="content">
		<f:form action="verified" method="put">
			<Table>
				<tr>
					<td><input type="hidden" name="email" value="${email}" /></td>
					<td><input type="submit" value="Verify Email" ></td>
				</tr>
			</Table>
			<a:out value="${msg}"></a:out>
		</f:form> 
	</jsp:attribute>

</L:Login>
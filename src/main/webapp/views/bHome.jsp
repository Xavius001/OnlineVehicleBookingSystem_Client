<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="B"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="a" %>

<B:Branch title="Branch Admin Home">

	<jsp:attribute name="head"></jsp:attribute>
	
	<jsp:attribute name="content">
		<a:choose>
		<a:when test="${display}">
			<f:form action="http://localhost:8080/OnlineVehicleBookingSystem/BranchHomepage/SaveInfo" modelAttribute="branchdb">
				<Table>
					<tr>
						<td>Login Id</td>
						<td><f:input type="text" path="branchId.userId" value="${sessionScope.logindb.userId}" readonly="true"></f:input></td>
						<td><f:errors path="branchId"></f:errors></td>
					</tr>
					<tr>
						<td>Branch Location</td>
						<td><f:input path="bLoc" ></f:input></td>
						<td><f:errors path="bLoc"></f:errors></td>
					</tr>
					<tr>
						<td>Address</td>
						<td><f:input path="address" type="address"></f:input></td>
						<td><f:errors path="address" type="address"></f:errors></td>
					</tr>
					<!-- <tr>
						<td>Email</td>
						<td><f:input path="email"></f:input></td>
						<td><f:errors path="email"></f:errors></td>
					</tr> -->
					<tr>
						<td>Phone Number</td>
						<td><f:input path="pno"></f:input></td>
						<td><f:errors path="pno"></f:errors></td>
					</tr>
					<tr>
						<td><input type="submit" value="Save Info"></td>
					</tr>
				</Table>
				<a:out value="${msg}"></a:out>
			</f:form> 
		</a:when>
		<a:otherwise>
			<f:form modelAttribute="branchdb">
				<Table>
					<tr>
						<td>Login Id</td>
						<td><f:input type="text" path="branchId.userId" value="${sessionScope.logindb.userId}" readonly="true"></f:input></td>
						<td><f:errors path="branchId"></f:errors></td>
					</tr>
					<tr>
						<td>Branch Location</td>
						<td><f:input path="bLoc" value="${branchdb.bLoc}" readonly="true"></f:input></td>
						<td><f:errors path="bLoc"></f:errors></td>
					</tr>
					<tr>
						<td>Address</td>
						<td><f:input path="address" type="address" value="${branchdb.address}" readonly="true"></f:input></td>
						<td><f:errors path="address" type="address"></f:errors></td>
					</tr>
					<!-- <tr>
						<td>Email</td>
						<td><f:input path="email" value="${branchdb.email}" readonly="true"></f:input></td>
						<td><f:errors path="email"></f:errors></td>
					</tr> -->
					<tr>
						<td>Phone Number</td>
						<td><f:input path="pno" value="${branchdb.pno}" readonly="true"></f:input></td>
						<td><f:errors path="pno"></f:errors></td>
					</tr>
				</Table>
				<a:out value="${msg}"></a:out>
			</f:form>
		</a:otherwise>
		</a:choose>
	</jsp:attribute>

</B:Branch>
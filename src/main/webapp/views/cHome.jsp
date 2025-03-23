<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="C"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="a" %>

<C:Customer title="Customer Home">

	<jsp:attribute name="head"></jsp:attribute>
	
	<jsp:attribute name="content">
		<a:choose>
			<a:when test="${display}">
				<f:form action="http://localhost:8080/OnlineVehicleBookingSystem/CustomerHomepage/SaveInfo" modelAttribute="customerdb">
					<Table>
						<tr>
						<td>Login Id</td>
						<td><f:input type="text" path="custId.userId" value="${sessionScope.logindb.userId}" readonly="true"></f:input></td>
						<td><f:errors path="custId"></f:errors></td>
					</tr>
						<tr>
							<td>Customer Name</td>
							<td><f:input path="name"></f:input></td>
							<td><f:errors path="name"></f:errors></td>
						</tr>
						<tr>
							<td>Date of Birth</td>
							<td><f:input path="dob" type="date"></f:input></td>
							<td><f:errors path="dob" type="date"></f:errors></td>
						</tr>
						<tr>
							<td>Address</td>
							<td><f:input path="address"></f:input></td>
							<td><f:errors path="address"></f:errors></td>
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
							<td>Occupation</td>
							<td><f:input path="occupation"></f:input></td>
							<td><f:errors path="occupation"></f:errors></td>
						</tr>
						<tr>
							<td><input type="submit" value="Save Info"></td>
						</tr>
					</Table>
					<a:out value="${msg}"></a:out>
				</f:form>
			</a:when>
			<a:otherwise>
				<f:form modelAttribute="customerdb">
					<Table>
						<tr>
						<td>Email</td>
						<td><f:input type="text" path="custId.userId" value="${sessionScope.logindb.userId}" readonly="true"></f:input></td>
						<td><f:errors path="custId"></f:errors></td>
					</tr>
						<tr>
							<td>Customer Name</td>
							<td><f:input path="name" value="${customerdb.name}" readonly="true"></f:input></td>
							<td><f:errors path="name"></f:errors></td>
						</tr>
						<tr>
							<td>Date of Birth</td>
							<td><f:input path="dob" type="date" value="${customerdb.dob}" readonly="true"></f:input></td>
							<td><f:errors path="dob" type="date"></f:errors></td>
						</tr>
						<tr>
							<td>Address</td>
							<td><f:input path="address" value="${customerdb.address}" readonly="true"></f:input></td>
							<td><f:errors path="address"></f:errors></td>
						</tr>
						<!-- <tr>
							<td>Email</td>
							<td><f:input path="email" value="${customerdb.email}" readonly="true"></f:input></td>
							<td><f:errors path="email"></f:errors></td>
						</tr> -->
						<tr>
							<td>Phone Number</td>
							<td><f:input path="pno" value="${customerdb.pno}" readonly="true"></f:input></td>
							<td><f:errors path="pno"></f:errors></td>
						</tr>
						<tr>
							<td>Occupation</td>
							<td><f:input path="occupation" value="${customerdb.occupation}" readonly="true"></f:input></td>
							<td><f:errors path="occupation"></f:errors></td>
						</tr>
						<tr>
						</tr>
					</Table>
					<a:out value="${msg}"></a:out>
				</f:form>
			</a:otherwise>
		</a:choose>
	</jsp:attribute>
</C:Customer>
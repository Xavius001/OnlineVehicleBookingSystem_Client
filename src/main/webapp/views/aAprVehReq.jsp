<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="A"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="a" %>

<A:Admin title="Approve New Vehicle Request">

	<jsp:attribute name="head"></jsp:attribute>
	
	<jsp:attribute name="content">
		<a:if test="${vlist!=null}">
					<f:form modelAttribute="vlist">
						<Table border="1">
							<tr>
								<td> </td>
								<td>Vehicle ID</td>
								<td>Manufacturer Name</td>
								<td>Price</td>
								<td>Color</td>
								<td>Stock</td>
								<td>Branch Id</td>
								<td>Status</td>
								<td>Request Stock</td>
							</tr>
							<a:forEach var="v" items="${vlist}">
								<tr>
									<td><input type="checkbox" name="requests[]" value="${v.vehicleId}"></td>
									<td>${v.vehicleId}</td>
									<td>${v.manufactureName}</td>
									<td>${v.price}</td>
									<td>${v.color}</td>
									<td>${v.stock}</td>
									<td>${v.branchId.branchId.userId}</td>
									<td>${v.status}</td>
									<td>${v.requestStock}</td>
								</tr>
							</a:forEach>
						</Table>
						<input type="submit" value="Approve" formaction="http://localhost:8080/OnlineVehicleBookingSystem/ApproveNewVehicleRequest/Approve">
						<input type="submit" value="Reject" formaction="http://localhost:8080/OnlineVehicleBookingSystem/ApproveNewVehicleRequest/Reject">
						<a:out value="${msg}"></a:out>
						<a:forEach var="m" items="${msglist}">
							<a:out value="${m}"></a:out><br>
						</a:forEach>
					</f:form>
				</a:if>
	</jsp:attribute>

</A:Admin>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="B"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="a" %>

<B:Branch title="Request New Vehicles">

	<jsp:attribute name="head"></jsp:attribute>
	
	<jsp:attribute name="content">
		
		
		<f:form action="http://localhost:8080/OnlineVehicleBookingSystem/RequestNewVehicles/Request" modelAttribute="vehicledb">
			<Table>
				<tr>
					<td>Vehicle ID</td>
					<td><f:input path="vehicleId"></f:input></td>
					<td><f:errors path="vehicleId"></f:errors></td>	
				</tr>
				<!-- <tr>
					<td>Manufacturer Name</td>
					<td><f:input path="manufactureName"></f:input></td>
					<td><f:errors path="manufactureName"></f:errors></td>
				</tr>
				<tr>
					<td>Price</td>
					<td><f:input path="price"></f:input></td>
					<td><f:errors path="price"></f:errors></td>
				</tr>
				<tr>
					<td>Color</td>
					<td><f:input path="color"></f:input></td>
					<td><f:errors path="color"></f:errors></td>	
				</tr>
				<tr>
					<td>Stock</td>
					<td><f:input path="stock"></f:input></td>
					<td><f:errors path="stock"></f:errors></td>	
				</tr>
				<tr>
					<td>Branch Id</td>
					<td><f:input path="branchId.branchId.userId"></f:input></td>
					<td><f:errors path="branchId.branchId.userId"></f:errors></td>	
				</tr> -->
				<tr>
					<td>Request Stock</td>
					<td><f:input path="requestStock"></f:input></td>
					<td><f:errors path="requestStock"></f:errors></td>	
				</tr>
				<tr>
					<td><input type="submit" value="Request" ></td>
				</tr>
			</Table>
			<a:out value="${msg}"></a:out>
			<a:if test="${vlist!=null}">
					<f:form modelAttribute="vlist">
						<Table border="1">
							<tr>
								<td>Vehicle ID</td>
								<td>Manufacturer Name</td>
								<td>Price</td>
								<td>Color</td>
								<td>Stock</td>
								<td>Branch Id</td>
								<td>Status</td>
								<td>Requested Stock</td>
							</tr>
							<a:forEach var="v" items="${vlist}">
								<tr>
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
					</f:form>
			</a:if>
		</f:form>
		
		
	</jsp:attribute>

</B:Branch>
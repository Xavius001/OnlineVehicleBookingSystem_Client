<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="C"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="a" %>

<C:Customer title="Vehicle Search">

	<jsp:attribute name="head"></jsp:attribute>
	
	<jsp:attribute name="content">
		

			<!-- <f:form action="http://localhost:8080/OnlineVehicleBookingSystem/VehicleSearch/Display" modelAttribute="vehicledb">
					<Table>
						<tr>
							<td>Manufacturer Name</td>
							<td><f:input path="manufactureName"></f:input></td>
							<td><f:errors path="manufactureName"></f:errors></td>
						</tr>
						<tr>
							<td>Price Range</td>
							<td><input name="price1" value="${price1}"></input></td>
							<td>-</td>
							<td><input name="price2" value="${price2}"></input></td>
						</tr>
						<tr>
							<td>Color</td>
							<td><f:input path="color" ></f:input></td>
							<td><f:errors path="color"></f:errors></td>
						</tr>
						<tr>
							<td>Seating Capacity</td>
							<td><f:input path="seatingCapacity"></f:input></td>
							<td><f:errors path="seatingCapacity"></f:errors></td>
						</tr>
						<tr>
							<td>Branch Id</td>
							<a:if test="${branchIds!=null}"> 
								<td> 
									<f:select id="Branches" path="branchId.branchId.userId">
									<a:forEach var="b" items="${branchIds}">
										<f:option name="${b}" value="${b}">${b}</f:option>
									</a:forEach>
									</f:select>
								</td>		
							</a:if>
						</tr>
						<tr>
							<td><input type="submit" value="Search"></td>
						</tr>
					</Table>
					<a:out value="${msg}"></a:out>
				</f:form> -->
				<a:if test="${vlist!=null}">
					<f:form action="http://localhost:8080/OnlineVehicleBookingSystem/VehicleSearch/Book" modelAttribute="vlist">
						<Table border="1">
							<tr>
								<td> </td>
								<td>Manufacturer Name</td>
								<td>Price</td>
								<td>Color</td>
								<td>Seating Capacity</td>
								<td>Branch Id</td>
							</tr>
							<a:forEach var="v" items="${vlist}">
								<tr>
									<td><input type="checkbox" name="vehicles[]" value="${v.vehicleId}"> </td>
									<td>${v.manufactureName}</td>
									<td>${v.price}</td>
									<td>${v.color}</td>
									<td>${v.seatingCapacity}</td>
									<td>${v.branchId.branchId.userId}</td>
								</tr>
							</a:forEach>
						<tr>
							<td><input type="submit" value="Book"></td>
						</tr>
						</Table>
						<a:out value="${msg2}"></a:out>
					</f:form>
				</a:if>
	</jsp:attribute>

</C:Customer>
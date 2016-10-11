package net.http

import scala.util.{Try, Success, Failure}
import net.service.OrderService
import OrderService._

object HttpOrderService {

	sealed trait HttpResponse 
	case object Ok                  extends HttpResponse
	case object BadRequest          extends HttpResponse
	case object InternalServerError extends HttpResponse

	case class OrderRequest(id: Long, newQuantity: Int)

	// PUT /order/{id} 
	// Update the quantity of an Order by ID
	def createItem(o: OrderRequest): HttpResponse = 
		OrderService.update(o) match {
			case Right(_)               => Ok
			case Left(NoOrderFound)     => BadRequest
			case Left(DbUpdateError(t)) => InternalServerError
		}

}
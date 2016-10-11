package net.service

import java.sql.SQLException

import net.http.HttpOrderService.OrderRequest

object OrderService {

	sealed trait UpdateOrderError
	case object NoOrderFound               extends UpdateOrderError
	case class DbUpdateError(t: Throwable) extends UpdateOrderError

	// An Order that exists in the system, i.e. DB
	case class Order(id: Long, quantity: Int)

	def update(req: OrderRequest): Either[UpdateOrderError, Unit] = 
		get(req) match {
			case Right(Some(order)) => updateHelper(order)
			case Right(None)        => Left(NoOrderFound)
			case Left(e)            => Left(e)
		}

	private def updateHelper(o: Order): Either[UpdateOrderError, Unit] = 
		Left(DbUpdateError(new SQLException("Constraint XYZ violated.")))

	private def get(o: OrderRequest): Either[UpdateOrderError, Option[Order]] =
		Right( None )
}
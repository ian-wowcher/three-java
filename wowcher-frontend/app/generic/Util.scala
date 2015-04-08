package generic

/**
 * Created on 22/08/2014.
 */
object Util {
  def using[T <: {def close(): Unit}, V](f: => T)(g: T => V): V = {
    val resource = f
    try {
      g(resource)
    } finally {
      resource.close()
    }
  }
}

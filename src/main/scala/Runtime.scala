import sttp.model.Uri
import zio.{Schedule, Unsafe}

object Runtime {
  def run(location: Uri) = {
    var task = zio.ZIO.from(location)

    Unsafe.unsafe { implicit unsafe =>
      zio.Runtime.default.unsafe.run(task.repeat(Schedule.forever))
    }
  }
}

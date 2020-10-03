import com.google.inject.AbstractModule
import services.{Words, WordList}

class Module extends AbstractModule {
  override def configure() = {
    bind(classOf[Words]).to(classOf[WordList]).asEagerSingleton()
  }
}

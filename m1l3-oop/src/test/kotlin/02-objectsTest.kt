import org.junit.Test

class ObjectsExample {
    companion object { //это особый объект, который привязан к классу, а не к его экземпляру
        init {
            println("companion inited") // инициализация при загрузке класса ObjectsExample
        }
        fun doSmth() {
            println("companion object")
        }
    }

    /* object в Kotlin используется для создания синглтонов, то есть классов,
    у которых существует только один экземпляр. Вот несколько примеров, когда object может быть полезен:
    1.Хранение глобальных данных или состояний
    2.Работа с базами данных или сетевыми соединениями:
    3.Работа с внешними ресурсами
    */
    object A {
        init {
            println("A inited") // ленивая инициализация при первом обращении к А
        }
        fun doSmth() {
            println("object A")
        }
    }
}

class ObjectsTest {
    @Test
    fun test() {
        ObjectsExample()
        ObjectsExample.doSmth()
        ObjectsExample.A.doSmth()
        ObjectsExample.A.doSmth()
    }
}

// IS_APPLICABLE: false
// WITH_RUNTIME
fun test(list: List<String>) {
    list.forEach { item ->
        println(item)
        println(item)
    }<caret>
}
fun main()
{
    val items = listOf(2,3,5,6)
    var x = 0 ;
    for (item in items)
    {

    }
    while (x <=2)
    {
        x = x + 1
    }
    when (x)
    {
        1,5 -> x = x - 2
        2 -> x = x + 4
        else -> {
            x = x + 8
        }
    }
}
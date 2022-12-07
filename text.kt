class s
{
    var x = 5
    var y = 9
    fun main()
    {
        x = x * 2 * 8 + y - 3 / 4
    }
    fun test(a:Int , b:Int) : Int
    {
        return 1
    }
}
class w
{
    var t = 0
    var z = 0
    fun myfun()
    {
        if (2 !== 3 || 4 != 3 && 5 < 3 && 8 > 3 || 5 >= 3)
        {
            z = z - t
        }
    }
    fun controltest()
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
}
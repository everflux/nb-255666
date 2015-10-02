package de.jugmuenster.nbbug;

public class TrampolineDemo
{

    public static void main(String[] args)
    {
        Trampoline<Long> factorial = Factorial.factorial(1, 20);
        System.out.println("factorial: " + factorial.result());
    }
}

//base trampolin
abstract class Trampoline<T>
{
    public T get() { return null; }

    public Trampoline<T> apply() { return null; }

    public T result()
    {
        Trampoline<T> trampoline = this;
        while (trampoline.get() == null)
        {
            trampoline = trampoline.apply();
        }
        return trampoline.get();
    }
}

//actual implementation
class Factorial
{
    static Trampoline<Long> factorial(long sum, int n)
    {
        if (n <= 1)
        {
            return new Trampoline<Long>()
            {
                @Override
                public Long get() { return sum; }
            };
        }
        return new Trampoline<Long>()
        {
            @Override
            public Trampoline<Long> apply()
            {
                return factorial(sum * n, n-1);
            }
        };
    }
}

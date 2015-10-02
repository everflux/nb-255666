package de.jugmuenster.nbbug;

import java.util.stream.Stream;

public class Trampoline2Demo
{

    public static void main(String[] args)
    {
        Trampoline2<Long> factorial = factorial(1, 20);
        System.out.println("factorial: " + factorial.result());
    }
    
    static Trampoline2<Long> factorial(long sum, long n)
    {
        if(n <= 1)
        {
            return Trampoline2.done(sum);
        }
        return () -> factorial(sum * n, n-1);
    }

}

@FunctionalInterface
interface Trampoline2<T>
{
    Trampoline2<T> apply(); //for stream iteration

    default boolean isComplete()
    {
        return false;
    }

    default T result() //external entry point
    {
        return Stream.iterate(this, Trampoline2::apply)
                .filter(Trampoline2::isComplete)
                .findFirst()
                .get()
                .result();
    }

    static <T> Trampoline2<T> done(final T value)
    {
        return new Trampoline2<T>()
        {
            @Override
            public boolean isComplete()
            {
                return true;
            }

            @Override
            public T result()
            {
                return value;
            }

            @Override
            public Trampoline2<T> apply()
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}

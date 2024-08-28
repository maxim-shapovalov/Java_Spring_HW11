package ru.gb.spring.my_timesheet.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class) // для способа 2
class TaxCalculatorTest {
    // Способ 1
    @Test
    void testGetPriceWithTax() {
        TaxResolver mock = Mockito.mock(TaxResolver.class); // мок - подмена какого-то объекта, здесь подменяем TaxResolver, чтобы к нему не обращаться
//    when(mock.getCurrentTax()).thenReturn(0.2);
        doReturn(0.2).when(mock).getCurrentTax(); // этот вариант лучше

        TaxCalculator taxCalculator = new TaxCalculator(mock);
        assertEquals(120.0, taxCalculator.getPriceWithTax(100.0));

        verify(mock).getCurrentTax(); // убедиться, что метод действительно был вызван, например для void метода
    }

    // Способ 2
//    @Mock
//    TaxResolver mock;
//
//    @Test
//    void testGetPriceWithTax() {
//        doReturn(0.2).when(mock).getCurrentTax();
//
//        TaxCalculator taxCalculator = new TaxCalculator(mock);
//        assertEquals(120.0, taxCalculator.getPriceWithTax(100.0));
//
//        verify(mock).getCurrentTax();
//    }
}

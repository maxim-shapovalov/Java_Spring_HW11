package ru.gb.spring.my_timesheet.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ObjectServiceTest {
    // по сути проверям обращение одного объекта к другому, т.к. для создания одного нужен другой (который мы не создаём, а используем мок)
    @Mock // здесь объявлем мок
    MinioClient minioClient;

    @InjectMocks // вставляем подходящий мок из объявленных выше
    ObjectService objectService;

    @Test
    void saveObject() {
        objectService.saveObject("objectId");

        verify(minioClient).saveObject(eq("objectId"));
    }
}
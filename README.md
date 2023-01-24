# Kitty - Тестовый проект

## Краткое описание

### Приложение которое загружает список котиков из https://api.thecatapi.com/. В качестве стека основных технологий были использованы: Room, Dagger Hilt, Jetpack Compose, Coroutines, Room, Paging 3 (ужасный ужас), Coil. Для push "Initial commit" понадобилось приблизительно 8 - 10 часов.   

### Краткое руководство пользователя
1. В двух вкладках в tab_bar отображаются списки с лентой котиков и избранными котиками. 
2. Оба списка обновляемые (pull2refresh)
3. Скачивание котиков доступно только из вкладки Избранное, на длинный тап на картинку котика. 

### Что нужно сделать
1. Написать свою реализацию пагинации, так как Paging 3 библиотека не удовлетворяет всем моим требованиям
2. Скачивание фото не стабильно, нужно разобраться почему. 
3. Избранное запуливает много @GET запросов, тоже нужно смотреть
4. Провести плотный анализ UI/UX (приложение выглядит максимально топорно)
5. Разобраться с многомодульностью (брать версии зависимостей из buildSrc, провести рефакторинг build.gradle файлов)

### Что было сделано неплохо
1. Первый раз работал с пагинацией, достаточно быстро получилось разобраться, но нужно писать реализацию под свои нужды
2. Неплохая архитектура, грамотное разбиение на модули (но в архитектуре presentation слоя нужны доработки, а именно State машина)
3. Использование Hilt вместо классического Dagger 2 (разница не велика, но кода действительно меньше)
4. Вспомнил работу с Retrofit, так как последнее время работал только с WebSocketApi

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest2 {

    @BeforeEach
    public void setUp() {
        //открываем сайт
        open("http://localhost:9999/");
    }


    //отправляем пустую формуу
    @Test
    void emptyFormSent() {
        //нажимаем кнопку забронировать
        $(byText("Забронировать")).click();
        //проверка успешной отправки формы
        $("[data-test-id = 'city'] [class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    //валидная отправка данных
    @Test
    void shouldFormSentSuccessfully() {
        //заполняем поле город
        $("[class='input__control'][placeholder='Город']").setValue("Москва");
        //заполняем поле дата
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        String newDate = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[class='input__control'][placeholder='Дата встречи']").setValue(newDate);
        //заполняем поле имя и фамилия
        $("[class='input__control'][name='name']").setValue("Иван Иван");
        //заполняем поле телефон
        $("[class='input__control'][name='phone']").setValue("+79998887766");
        //ставим галочку согласия
        $("[data-test-id='agreement']").click();
        //нажимаем кнопку забронировать
        $(byText("Забронировать")).click();

        //проверка успешной отправки формы
        $("[class='notification__title']").shouldHave(exactText("Успешно!"), Duration.ofSeconds(15));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно забронирована на " + newDate));
    }


    //не заполнено имя
    @Test
    void notFilledName() {
        //заполняем поле город
        $("[class='input__control'][placeholder='Город']").setValue("Москва");
        //заполняем поле дата
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +3);
        $("[class='input__control'][placeholder='Дата встречи']").setValue("cal.getTime()");
        //заполняем поле телефон
        $("[class='input__control'][name='phone']").setValue("+79998887766");
        //ставим галочку согласия
        $("[data-test-id='agreement']").click();
        //нажимаем кнопку забронировать
        $(byText("Забронировать")).click();

        //проверка успешной отправки формы
        $("[data-test-id = 'name'] [class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    //имя на латынице
    @Test
    void sentNameLatin() {
        //заполняем поле город
        $("[class='input__control'][placeholder='Город']").setValue("Москва");
        //заполняем поле дата
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +3);
        $("[class='input__control'][placeholder='Дата встречи']").setValue("cal.getTime()");
        //заполняем поле имя и фамилия
        $("[class='input__control'][name='name']").setValue("Ivan Ivan");
        //заполняем поле телефон
        $("[class='input__control'][name='phone']").setValue("+79998887766");
        //ставим галочку согласия
        $("[data-test-id='agreement']").click();
        //нажимаем кнопку забронировать
        $(byText("Забронировать")).click();

        //проверка успешной отправки формы
        $("[data-test-id = 'name'] [class='input__sub']").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    //не заполняем только город
    @Test
    void notFilledCity() {
        //заполняем поле дата
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +3);
        $("[class='input__control'][placeholder='Дата встречи']").setValue("cal.getTime()");
        //заполняем поле имя и фамилия
        $("[class='input__control'][name='name']").setValue("Ivan Ivan");
        //заполняем поле телефон
        $("[class='input__control'][name='phone']").setValue("+79998887766");
        //ставим галочку согласия
        $("[data-test-id='agreement']").click();
        //нажимаем кнопку забронировать
        $(byText("Забронировать")).click();

        //проверка успешной отправки формы
        $("[data-test-id = 'city'] [class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    //не заполняем телефон
    @Test
    void notFilledPhone() {
        //заполняем поле город
        $("[class='input__control'][placeholder='Город']").setValue("Москва");
        //заполняем поле дата
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +3);
        $("[class='input__control'][placeholder='Дата встречи']").setValue("cal.getTime()");
        // $("[class='calendar__day']").click();
        //заполняем поле имя и фамилия
        $("[class='input__control'][name='name']").setValue("Иван Иван");
        //ставим галочку согласия
        $("[data-test-id='agreement']").click();
        //нажимаем кнопку забронировать
        $(byText("Забронировать")).click();

        //проверка успешной отправки формы
        $("[data-test-id = 'phone'] [class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    //вводим телефон из 3 цифр и без +
    @Test
    void sentPhone3() {
        //заполняем поле город
        $("[class='input__control'][placeholder='Город']").setValue("Москва");
        //заполняем поле дата
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +3);
        $("[class='input__control'][placeholder='Дата встречи']").setValue("cal.getTime()");
        //заполняем поле имя и фамилия
        $("[class='input__control'][name='name']").setValue("Иван Иван");
        //заполняем поле телефон
        $("[class='input__control'][name='phone']").setValue("799");
        //ставим галочку согласия
        $("[data-test-id='agreement']").click();
        //нажимаем кнопку забронировать
        $(byText("Забронировать")).click();

        //проверка успешной отправки формы
        $("[data-test-id = 'phone'] [class='input__sub']").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    //не дали согласия
    @Test
    void notClick() {
        //заполняем поле город
        $("[class='input__control'][placeholder='Город']").setValue("Москва");
        //заполняем поле дата
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +3);
        $("[class='input__control'][placeholder='Дата встречи']").setValue("cal.getTime()");
        //заполняем поле имя и фамилия
        $("[class='input__control'][name='name']").setValue("Иван Иван");
        //заполняем поле телефон
        $("[class='input__control'][name='phone']").setValue("+79998887766");
        //нажимаем кнопку забронировать
        $(byText("Забронировать")).click();

        //проверка успешной отправки формы
        $(".input_invalid").shouldHave(appear);
    }

    //не заполняем поле дата
    @Test
    void notFilledData() {
        //заполняем поле город
        $("[class='input__control'][placeholder='Город']").setValue("Москва");
        //чистка поля с датой по умолчанию
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        //заполняем поле имя и фамилия
        $("[class='input__control'][name='name']").setValue("Иван Иван");
        //заполняем поле телефон
        $("[class='input__control'][name='phone']").setValue("+79998887766");
        //ставим галочку согласия
        $("[data-test-id='agreement']").click();
        //нажимаем кнопку забронировать
        $(byText("Забронировать")).click();

        //проверка успешной отправки формы
        $("[data-test-id='date'] [class='input__sub']").shouldHave(exactText("Неверно введена дата"), Duration.ofSeconds(15));
    }

    //заполняем дату, задним числом
    @Test
    void enterLessCurrentDate() {
        //заполняем поле город
        $("[class='input__control'][placeholder='Город']").setValue("Москва");
        //чистка поля с датой по умолчанию
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        //вводим дату
        //кликаем по полю с датой
        $("[data-test-id='date'] input").click();
        //вчитаем 7 дней из текущей даты
        String newDate = LocalDate.now().minusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(newDate);
        //заполняем поле имя и фамилия
        $("[class='input__control'][name='name']").setValue("Иван Иван");
        //заполняем поле телефон
        $("[class='input__control'][name='phone']").setValue("+79998887766");
        //ставим галочку согласия
        $("[data-test-id='agreement']").click();
        //нажимаем кнопку забронировать
        $(byText("Забронировать")).click();

        //проверка успешной отправки формы
        $("[data-test-id='date'] [class='input__sub']").shouldHave(exactText("Заказ на выбранную дату невозможен"), Duration.ofSeconds(15));
    }

}
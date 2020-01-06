package org.volgatech.javacore.streams.tasks;

import org.volgatech.javacore.streams.model.*;
import org.volgatech.javacore.streams.util.AveragingBigDecimalCollector;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Этот класс имеет несколько методов для получения статистической информации по покупателмя и заказам в интернет-магазине.
 * Используя Java Stream API, реализуйте методы класса
 * Каждый метод имеет юнит-тесты. Вы можете использовать вызов <code>mvn test</code>, чтобы проверить вашу реализацию
 *
 * Доменная модель магазина находиться в пакете <code>org.volgatech.javacore.streams.model</code>.
 */
class OrderStats {

    /**
     * Task 1 (⚫⚫⚪⚪⚪)
     * <p>
     * Имея стрим пользователей, верните список заказов, которые оплачены определенным типом карты (Visa или MasterCard)
     *
     * @param customers stream of customers
     * @param cardType  credit card type
     * @return list, containing orders paid with provided card type
     */
    static List<Order> ordersForCardType(final Stream<Customer> customers, PaymentInfo.CardType cardType) {
        return customers
                .flatMap(customer -> customer.getOrders().stream()
                        .filter(order -> order.getPaymentInfo().getCardType() == cardType))
                .collect(Collectors.toList());
    }

    /**
     * Task 2 (⚫⚫⚪⚪⚪)
     * <p>
     * Имея стрим заказов, верните отображение, где ключами являются различные размеры заказов, а значениями списки заказов с соответсвующим размером.
     * Размер заказа - это общее количество продуктов в заказе.
     *
     * @param orders stream of orders
     * @return map, where order size values mapped to lists of orders
     */
    static Map<Integer, List<Order>> orderSizes(final Stream<Order> orders) {
        return orders.collect(Collectors.groupingBy(order
                -> order.getOrderItems().stream().mapToInt(OrderItem::getQuantity).sum()));
    }

    /**
     * Task 3 (⚫⚫⚫⚪⚪)
     * <p>
     * Имея стрим заказов, верните true, если все заказы содержат хотя бы один продукт заданного цвета, иначе верните false
     *
     * @param orders stream of orders
     * @param color  product color to test
     * @return boolean, representing if every order in the stream contains product of specified color
     */
    static Boolean hasColorProduct(final Stream<Order> orders, final Product.Color color) {
        return orders.allMatch(
                order -> order.getOrderItems().stream().anyMatch(
                        item -> item.getProduct().getColor() == color
                )
        );
    }

    /**
     * Task 4 (⚫⚫⚫⚫⚪)
     * <p>
     * Имея стрим пользователей, верните отображение, где email пользователя отображается на количество различных кредитных карт,
     * использованных для оплаты этим пользователям
     *
     * @param customers stream of customers
     * @return map, where for each customer email there is a long referencing a number of different credit cards this customer uses.
     */
    static Map<String, Long> cardsCountForCustomer(final Stream<Customer> customers) {
        Map<String, Long> cardsCountForCustomer = new TreeMap<>();

        customers.forEach(customer -> {
            long cardsCount = customer.getOrders().stream()
                    .map(order -> order.getPaymentInfo().getCardNumber())
                    .distinct()
                    .count();

            cardsCountForCustomer.put(customer.getEmail(), cardsCount);
        });

        return cardsCountForCustomer;
    }

    /**
     * Task 5 (⚫⚫⚫⚫⚫)
     * <p>
     * Имея стрим покупателей, верните Optional, содержащий самое популярное название страны.
     * Название страны, которое чаще всего используется в addressInfo у покупателя.
     * Если меются две или более страны с одинаковой частотой использования у покупателей, верните название страны наименьшей длины
     * Если стрим покупателей пустой, Optional.empty должен быть возвращен
     * <p>
     * Пример: Стрим, содержащий
     * Customer#1 -> USA
     * Customer#2 -> France
     * Customer#3 -> Japan
     * Customer#4 -> USA
     * Customer#5 -> Japan
     * <p>
     * "USA" должно быть возвращено.
     *
     * @param customers stream of customers
     * @return java.util.Optional containing the name of the most popular country
     */
    static Optional<String> mostPopularCountry(final Stream<Customer> customers) {
        Map<String, List<Customer>> countriesPopularity = customers.collect(Collectors.groupingBy(customer
                -> customer.getAddress().getCountry()));

        if (countriesPopularity.isEmpty()) {
            return Optional.empty();
        }

        String mostPopularCountry = countriesPopularity.entrySet().stream().max((country1, country2) -> {
            Integer popularity1 = country1.getValue().size();
            Integer popularity2 = country2.getValue().size();
            Integer length1 = country1.getKey().length();
            Integer length2 = country2.getKey().length();

            int result = popularity1.compareTo(popularity2);

            if (result != 0)
            {
                return result;
            }

            result = length2.compareTo(length1);

            return result;
        }).get().getKey();

        return Optional.of(mostPopularCountry);
    }

    /**
     * Task 6 (⚫⚫⚫⚫⚫)
     * <p>
     * Имея стрим покупателей, верните среднюю стоимость продуктов, оплаченных кредитной картой с заданным номером
     * <p>
     * Если заказ содержит, следующие продукты:
     * [
     * Product1(price = 100$, quantity = 2),
     * Product2(price = 160$, quantity = 1)
     * ]
     * тогда средняя стоимость продуктов будет 120$ ((100 * 2 + 160 * 1) / 3)
     * <p>
     * Так как цена продукта представлена как BigDecimal, необходимо использовать кастомный коллектор AveragingBigDecimalCollector
     *
     * @param customers  stream of customers
     * @param cardNumber card number to check
     * @return average price of the product, ordered with the provided card
     */
    static BigDecimal averageProductPriceForCreditCard(final Stream<Customer> customers, final String cardNumber) {
        return customers
                .flatMap(customer -> customer.getOrders().stream()
                        .filter(order -> order.getPaymentInfo().getCardNumber().equals(cardNumber))
                        .flatMap(order -> order.getOrderItems().stream()
                                .flatMap(item -> Stream.generate(() -> item.getProduct().getPrice())
                                        .limit(item.getQuantity()))))
                .collect(new AveragingBigDecimalCollector());
    }
}

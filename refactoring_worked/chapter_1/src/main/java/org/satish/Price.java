package org.satish;

abstract class Price {
    abstract int getPriceCode();

    abstract double getCharge(int daysRented);

    abstract int getFrequentRenterPoints(int daysRented);
}
